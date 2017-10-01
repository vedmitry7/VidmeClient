package com.vedmitryapps.vidmeclient;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class App extends Application {

    public static final String APP_PREFERENCES = "mysettings";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_TOKEN_END = "token_end_date";

    private static SharedPreferences sharedPreferences;

    public SharedPreferences getPreferences(){
        if(sharedPreferences == null){
            sharedPreferences = getApplicationContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        }

        return sharedPreferences;
    }
}
