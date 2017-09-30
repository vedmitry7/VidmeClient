package com.vedmitryapps.vidmeclient.model.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static VidmeService service;

    public static VidmeService getService(){
        if(service == null) {
            Retrofit builder = new Retrofit.Builder()
                    .baseUrl("https://api.vid.me/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = builder.create(VidmeService.class);
        }
        return service;
    }
}
