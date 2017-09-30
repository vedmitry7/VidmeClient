package com.vedmitryapps.vidmeclient.model.api;


import com.vedmitryapps.vidmeclient.model.objects.AuthResponse;
import com.vedmitryapps.vidmeclient.model.objects.VidmeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VidmeService {

    @GET("videos/featured")
    Call<VidmeResponse> getFeaturedVideo(@Query("offset") int offset,
                                         @Query("limit") int limit);

    @GET("videos/new")
    Call<VidmeResponse> getNewVideo(@Query("offset") int offset,
                                    @Query("limit") int limit);

    @GET("/videos/feed")
    Call<VidmeResponse> getFeedVideo(@Query("offset") int offset,
                                     @Query("limit") int limit,
                                     @Query("token") String token);

    @POST("auth/create")
    Call<AuthResponse> createAuth(@Query("username") String username,
                                  @Query("password") String password);

}
