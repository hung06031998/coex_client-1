package com.upit.coex.user.service.runnable;


import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CoexChainedRunnable {

    private static volatile CoexChainedRunnable sInstance = null;

    private Observable coexChainedObservale;

    private CoexChainedRunnable(){

    }

    public static CoexChainedRunnable getInstance(){

        if(sInstance == null){
            synchronized (CoexChainedRunnable.class) {
                sInstance = new CoexChainedRunnable();
            }
        }
        return sInstance;
    }

    public Observable command(Callable command){
        coexChainedObservale = io.reactivex.Observable.fromCallable(command)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return coexChainedObservale;
    }

    public Observable doOnNextIndependdance(Callable command){
        return coexChainedObservale.map((result) -> {return command;}).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable doOnNext(Callable command){
       return coexChainedObservale.map(result -> {

            return command;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
