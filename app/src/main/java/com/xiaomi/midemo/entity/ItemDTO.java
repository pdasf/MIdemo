package com.xiaomi.midemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


public class ItemDTO implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("imageURL")
    private String imageURL;

    protected ItemDTO(Parcel in) {
        id = in.readString();
        title = in.readString();
        imageURL = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public static final Creator<ItemDTO> CREATOR = new Creator<ItemDTO>() {
        @Override
        public ItemDTO createFromParcel(Parcel in) {
            return new ItemDTO(in);
        }

        @Override
        public ItemDTO[] newArray(int size) {
            return new ItemDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(imageURL);
    }
}