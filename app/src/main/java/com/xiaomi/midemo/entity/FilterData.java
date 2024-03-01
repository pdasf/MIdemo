package com.xiaomi.midemo.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterData {
    @SerializedName("items")
    private List<ItemDTO> items;

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

}
