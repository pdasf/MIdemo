package com.xiaomi.midemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.xiaomi.midemo.R;
import com.xiaomi.midemo.entity.RecommendItemDTO;

import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendHolder> {
    private Context context;
    private List<RecommendItemDTO> items;

    public RecommendAdapter(Context context, List<RecommendItemDTO> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecommendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendHolder(LayoutInflater.from(context).inflate(R.layout.item_recommend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendHolder holder, int position) {
        Glide.with(context)
                .load(items.get(position).getImages())
                .placeholder(R.drawable.pure)
                .error(R.drawable.pure)
                .transform(new RoundedCorners(20))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RecommendHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public RecommendHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recommend_image);
        }
    }
}
