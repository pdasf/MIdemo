package com.xiaomi.midemo.entity;

import com.google.gson.annotations.SerializedName;

public class VideoDTO {
    @SerializedName("ci")
    private String ci;
    @SerializedName("video_name")
    private String videoName;
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
}
