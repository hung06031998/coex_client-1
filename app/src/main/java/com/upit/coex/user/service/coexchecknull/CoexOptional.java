package com.upit.coex.user.service.coexchecknull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CoexOptional {

    private static volatile CoexOptional sInstance = null;

    private Optional<Object> checkNull;

    private CoexOptional(){
    };

    public static CoexOptional getInstance(){
        if(sInstance == null){
            synchronized (CoexOptional.class){
                sInstance = new CoexOptional();
            }
        }
        return sInstance;
    }

    public CoexOptional setObject(Object object){
        checkNull = Optional.ofNullable(object);
        return this;
    }

    public boolean isNull(Object object){
        return checkNull.isPresent();
    }

    public CoexOptional doIfNotNullAnThen(Consumer command){
         checkNull.ifPresent(command);
         return this;
    }

    public CoexOptional doIfNullAndThen(Supplier command){
        checkNull.orElseGet(command);
        return this;
    }

    public CoexOptional doOnNext(Function command){
         checkNull.map(command);
        return this;
    }

    public CoexOptional search(Predicate search){
         checkNull.filter(search);
        return this;
    }

    //result
    public Object getValue(){
        return checkNull.get();
    }

}