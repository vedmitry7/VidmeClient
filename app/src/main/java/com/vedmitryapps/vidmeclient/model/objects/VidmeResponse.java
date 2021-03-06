
package com.vedmitryapps.vidmeclient.model.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VidmeResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("videos")
    @Expose
    private List<Video> videos = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
