package com.upit.coex.user.service.rxjavasenddata;
import android.util.Pair;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class CoexSendData {

    public static volatile CoexSendData sInstance = null;


    private BehaviorSubject mBehaviorSubject;

    private CoexSendData(){
        mBehaviorSubject = BehaviorSubject.create();
    }


    public static CoexSendData getInstance(){
        if(sInstance == null){
            synchronized (CoexSendData.class){
                sInstance = new CoexSendData();
            }
        }
        return sInstance;
    }

    public void sendData(Pair<String, Object> o){
        this.mBehaviorSubject.onNext(o);
    }

    public Observable<Pair<String,Object>> receive(){
        return this.mBehaviorSubject;
    }

}
