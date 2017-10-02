package com.vedmitryapps.vidmeclient;


import android.app.Application;

public class App extends Application {

    public static final String APP_PREFERENCES = "mysettings";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_TOKEN_END = "token_end_date";
    public static final String BASE_URL = "https://api.vid.me/";
    public static final int DOWNLOAD_LIMIT = 10;

}
