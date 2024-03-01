package com.xiaomi.midemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.xiaomi.midemo.activity.ActivityManager;
import com.xiaomi.midemo.activity.MediaDetailActivity;
import com.xiaomi.midemo.R;
import com.xiaomi.midemo.entity.ItemDTO;

import java.util.List;

public class RecyclerAdapterContent extends RecyclerView.Adapter<RecyclerAdapterContent.ContentHolder> {
    private final Context context;
    private final List<ItemDTO> items;

    public RecyclerAdapterContent(List<ItemDTO> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ContentHolder(inflater.inflate(R.layout.item_course, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContentHolder holder, int position) {
        ItemDTO item = items.get(position);
        final int position1 = position;
        holder.text.setOnClickListener(v -> RecyclerAdapterContent.this.onClick(position1));
        holder.image.setOnClickListener(v -> RecyclerAdapterContent.this.onClick(position1));
        holder.text.setText(item.getTitle());
        Glide.with(context)
                .load(item.getImageURL())
                .placeholder(R.drawable.pure)
                .error(R.drawable.pure)
                .transform(new RoundedCorners(20))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void onClick(int position) {
        Activity curActivity = ActivityManager.getCurActivity();
        Intent intent = new Intent(curActivity, MediaDetailActivity.class);
        Bundle data = new Bundle();
        data.putParcelable(ActivityManager.MEDIA_DETAIL,items.get(position));
        intent.putExtras(data);
        curActivity.startActivity(intent);
    }

    public static class ContentHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;

        public ContentHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.image_text);
        }
    }
}
