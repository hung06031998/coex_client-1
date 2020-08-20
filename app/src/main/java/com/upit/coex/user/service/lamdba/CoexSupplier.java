package com.upit.coex.user.service.lamdba;

import java.util.function.Supplier;

public class CoexSupplier {

    //way1
    public void acceptMySupplier(Object a,Supplier b){
        a = b.get();
    }

    //way2
    public int doMyFuSupplier(){
        return 12;
    }

    public void example(){

        Object a = new Integer(12);

        //way1
        acceptMySupplier(a,()->{
            return 23;
        });

        //way2
        Supplier mySupplier = () -> doMyFuSupplier();
        mySupplier.get();
    }

}
