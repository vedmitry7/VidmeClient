package com.vedmitryapps.vidmeclient.model.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Auth {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("expires")
    @Expose
    private String expires;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
