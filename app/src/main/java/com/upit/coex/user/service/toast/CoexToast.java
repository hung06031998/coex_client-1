package com.upit.coex.user.service.toast;

import android.content.Context;
import android.widget.Toast;

public class CoexToast {

    public static void makeToast(Context context, int duration, String ...obj){
        StringBuilder content = new StringBuilder();
        for(String item: obj)
        {
            content.append(item);
        }
        Toast.makeText(context,content,duration).show();
    }

}
