package com.example.maintenancebuddy.ui.recyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ModelViewHolder<T> extends RecyclerView.ViewHolder {

    public ModelViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void onBind(T model, OnItemClickListener<T> onItemClickListener) {
        if(onItemClickListener != null) {
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(model));
        }
        onBindModel(model);
    }

    public abstract void onBindModel(T model);
}
