package com.upit.coex.user.service.lamdba;

public class CoexLambda {
    interface myFunc {
        boolean execute(int a , int b);
    }

    //way2
    public void doMyFunc(int a, int b, myFunc c){
        c.execute(a,b);
    }

    //way1
    public boolean acceptMyFunc(int a, int b){
        return a < b;
    }

    public void example(){
        int a = 2;
        int b = 3;

        //way 1
        myFunc c = (input1, input2) -> acceptMyFunc(input1,input1);
        c.execute(a,b);

        //way 2
        doMyFunc(a, b, (a1, b1) -> a1 < b1);
    }

}
