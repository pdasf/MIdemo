package com.xiaomi.midemo.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MediaDTO {
    @SerializedName("media_name")
    private String mediaName;
    @SerializedName("genres")
    private String genres;
    @SerializedName("premiere_date")
    private String premiereDate;
    @SerializedName("videos")
    private List<VideoDTO> videos;
    @SerializedName("desc")
    private String desc;
    @SerializedName("image")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(String premiereDate) {
        this.premiereDate = premiereDate;
    }

    public List<VideoDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoDTO> videos) {
        this.videos = videos;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
