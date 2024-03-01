package com.xiaomi.midemo.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Frontpage {

    @SerializedName("title")
    private String title;
    @SerializedName("select_options")
    private List<List<SelectOptionsDTO>> selectOptions;
    @SerializedName("items")
    private List<ItemDTO> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<List<SelectOptionsDTO>> getSelectOptions() {
        return selectOptions;
    }

    public void setSelectOptions(List<List<SelectOptionsDTO>> selectOptions) {
        this.selectOptions = selectOptions;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }


}
