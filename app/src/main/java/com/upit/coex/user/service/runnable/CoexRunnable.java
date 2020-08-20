package com.upit.coex.user.service.runnable;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class CoexRunnable {
    public static <T> Observable runFunction(Callable<? extends T> supplier){
        return Observable.fromCallable(supplier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                 ;
    }

    static int i = 1;
    public static void runChainFunction(Callable ...suppliers){
        Observable tt;
        if (suppliers.length > 0) {
            tt = Observable.fromCallable(suppliers[0])
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            for (i = 1 ;i < suppliers.length ; i++) {
                tt.observeOn(AndroidSchedulers.mainThread())
                        .map(o -> {
                            return suppliers[i].call();
                        });
            }
        }
    }

//    public static <T> Flowable runFunctionFlowable(Callable<? extends T> supplier){
//        return Flowable.fromCallable(supplier)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

    public static <T> Single runFunctionSingle(Callable<? extends T> supplier){
        return Single.fromCallable(supplier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
