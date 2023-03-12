package com.example.giveandtake;

import android.content.Context;
import android.app.Application;

public class MyApplication extends  Application{
    static private Context context;
    public static Context getMyContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
