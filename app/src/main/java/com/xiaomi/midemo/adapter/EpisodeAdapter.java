package com.xiaomi.midemo.adapter;

import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaomi.midemo.R;
import com.xiaomi.midemo.activity.MediaDetailActivity;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeHolder> {
    private final Activity activity;
    private final List<String> data;

    public EpisodeAdapter(Activity activity, List<String> data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public EpisodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodeHolder(LayoutInflater.from(activity).inflate(R.layout.item_episode, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeHolder holder, int position) {
        holder.text.setText(data.get(position));
        holder.text.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15); //TODO
        MediaDetailActivity activity1 = (MediaDetailActivity) activity;
        holder.itemView.setOnClickListener(v -> activity1.onEpisodeClick(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class EpisodeHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public EpisodeHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.episode_title);
        }
    }
}
