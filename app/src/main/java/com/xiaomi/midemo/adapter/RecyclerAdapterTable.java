package com.xiaomi.midemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaomi.midemo.R;

import java.util.List;

public class RecyclerAdapterTable extends RecyclerView.Adapter<RecyclerAdapterTable.TableHolder> {
    private final List<String> tags;
    private final Context context;
    private OnItemClickListener onItemClickListener = null;
    public volatile int selectedItem;

    public RecyclerAdapterTable(List<String> tag, Context context) {
        this.tags = tag;
        this.context = context;
    }

    @NonNull
    @Override
    public TableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TableHolder(LayoutInflater.from(context).inflate(R.layout.item_table_text, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull TableHolder holder, int position) {
        String tag = tags.get(position);
        holder.text.setText(tag);

        /*设置选中状态*/
        if (position == selectedItem) {
            holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.select, null));
            holder.text.setTextColor(Color.parseColor("#3985ff"));
        } else {
            holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.pure, null));
            holder.text.setTextColor(Color.parseColor("#1c2b47"));
        }

        holder.text.setOnClickListener(v -> onItemClickListener.onItemClick(holder.itemView, position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public static class TableHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public TableHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.table_text);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}


