package com.example.womensskill;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveLogin {

    //Saving Values
    public static void save(Context context, String name, String value) {
        SharedPreferences s =context.getSharedPreferences("WomenSkill",Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = s.edit();
        edt.putString(name,value);
        edt.apply();

    }

    public static String read(Context context,String name,String defaultValue) {
        SharedPreferences s =context.getSharedPreferences("WomenSkill",Context.MODE_PRIVATE);
        return s.getString(name, defaultValue);
    }


}
