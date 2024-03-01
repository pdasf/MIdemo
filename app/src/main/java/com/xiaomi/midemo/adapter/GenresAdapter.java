package com.xiaomi.midemo.adapter;

import android.app.Activity;
import android.graphics.Color;
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

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenresHolder> {
    private final Activity activity;
    private final List<String> data;

    public GenresAdapter(Activity activity, List<String> data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public GenresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GenresHolder(LayoutInflater.from(activity).inflate(R.layout.episode_count, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GenresHolder holder, int position) {
        holder.text.setText(data.get(position));
        holder.text.setTextColor(Color.parseColor("#FF7986a0"));
        holder.text.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15); //TODO
        MediaDetailActivity activity1 = (MediaDetailActivity) activity;
        holder.itemView.setOnClickListener(v -> activity1.onEpisodeCountClick(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GenresHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public GenresHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.episode_count);
        }
    }
}
