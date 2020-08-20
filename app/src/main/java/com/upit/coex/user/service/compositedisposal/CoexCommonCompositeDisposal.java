package com.upit.coex.user.service.compositedisposal;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class CoexCommonCompositeDisposal {

    private HashMap<String,Disposable> mMapDisposal;

    private static volatile CoexCommonCompositeDisposal sInstance = null;

    private CoexCommonCompositeDisposal(){
        mMapDisposal = new HashMap<>();
    }

    public static CoexCommonCompositeDisposal getInstance(){
        if (sInstance == null){
            synchronized (CoexCommonCompositeDisposal.class){
                sInstance = new CoexCommonCompositeDisposal();
            }
        }
        return sInstance;
    }

    public void addDisposal(String inputString,Disposable inputDisposal){
        mMapDisposal.put(inputString,inputDisposal);
    }

    public void removeDisposal(String inputString){
        if (mMapDisposal.get(inputString).isDisposed()){
            mMapDisposal.get(inputString).dispose();
        }
        mMapDisposal.remove(inputString);
    }

    public void clearAll(){
        for(Map.Entry<String, Disposable> entry : mMapDisposal.entrySet()) {
            if(entry.getValue().isDisposed()){
                entry.getValue().dispose();
            }
            mMapDisposal.remove(entry.getKey());
        }
    }
}
