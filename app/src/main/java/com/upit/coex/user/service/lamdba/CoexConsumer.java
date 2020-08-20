package com.upit.coex.user.service.lamdba;

import com.upit.coex.user.service.logger.L;

import java.util.function.Consumer;

public class CoexConsumer {

    //way1
    public void doMyConsumger(int a, Consumer command){
        command.accept(a);
    }

    //way2
    public void acceptMyConsumer(int a){
        L.d("CoexConsumer",a+"");
    }

    public void example(){
        int a = 2;
        //way1
        doMyConsumger(a, (a1) -> L.d("CoexConsumer",a1+""));

        //way2
        Consumer c = (a1) -> acceptMyConsumer((int)a1);
        c.accept(a);
    }

}
