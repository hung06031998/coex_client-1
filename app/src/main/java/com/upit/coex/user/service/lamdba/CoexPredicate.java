package com.upit.coex.user.service.lamdba;

import java.util.function.Predicate;


 class CoexPredicate {

    //way1
    public void acceptMyPredicate(int a, Predicate b){
        b.test(a);
    }

    //way2
    public boolean doMyPredicate(int a){
        return a<5;
    }

    public void example(){

        int a = 3;

        //way1
        acceptMyPredicate(a,(a1)->{
            return a<3;
        });

        //way2
        Predicate myPredicate = (a1) -> doMyPredicate((int)a1);
        myPredicate.test(a);
    }

}
