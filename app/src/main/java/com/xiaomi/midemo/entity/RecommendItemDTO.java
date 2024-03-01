package com.xiaomi.midemo.entity;

import com.google.gson.annotations.SerializedName;

public class RecommendItemDTO {
    @SerializedName("images")
    private String images;
    @SerializedName("right_top_corner")
    private String rightTopCorner;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getRightTopCorner() {
        return rightTopCorner;
    }

    public void setRightTopCorner(String rightTopCorner) {
        this.rightTopCorner = rightTopCorner;
    }
}
