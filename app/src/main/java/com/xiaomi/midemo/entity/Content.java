package com.xiaomi.midemo.entity;

import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("media")
    private MediaDTO media;
    @SerializedName("recommend")
    private RecommendDTO recommend;

    public MediaDTO getMedia() {
        return media;
    }

    public void setMedia(MediaDTO media) {
        this.media = media;
    }

    public RecommendDTO getRecommend() {
        return recommend;
    }

    public void setRecommend(RecommendDTO recommend) {
        this.recommend = recommend;
    }

}
