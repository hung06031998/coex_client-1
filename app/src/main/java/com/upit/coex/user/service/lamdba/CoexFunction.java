package com.upit.coex.user.service.lamdba;

import java.util.function.Function;

public class CoexFunction {

    public int congVoiHai(int a){
        return a + 2;
    }

    public int nhanVoiBa(int a){
        return a * 3;
    }

    //way1
    public boolean acceptMyFunction(int a, int b){
        return a < b;
    }

    //way2
    public int doMyFunction(int a, Function c){
        return (int)c.apply(a);
    }

    public void example(){
        int a = 2;
        int b = 3;

        //way 1
        Function myF1 = (a1) -> congVoiHai((int)a1);

        Function myF2 = (a2) -> nhanVoiBa((int)a2);

        myF1.andThen(myF2).apply(a);

        //way 2
        int result = doMyFunction(a, (a1) -> (int)a1 + 2);

    }

}
