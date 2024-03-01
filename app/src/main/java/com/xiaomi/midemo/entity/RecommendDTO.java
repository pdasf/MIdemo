package com.xiaomi.midemo.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendDTO {
    @SerializedName("items")
    private List<RecommendItemDTO> items;
    @SerializedName("title")
    private String title;

    public List<RecommendItemDTO> getItems() {
        return items;
    }

    public void setItems(List<RecommendItemDTO> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
