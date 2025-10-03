package com.blanki.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StyleAdapter extends RecyclerView.Adapter<StyleAdapter.StyleViewHolder> {

    private final String[] styles;
    private final OnStyleClickListener listener;

    public interface OnStyleClickListener {
        void onStyleClick(String style);
    }

    public StyleAdapter(String[] styles, OnStyleClickListener listener) {
        this.styles = styles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StyleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_style, parent, false);
        return new StyleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StyleViewHolder holder, int position) {
        String style = styles[position];
        holder.styleButton.setText(style);
        holder.itemView.setOnClickListener(v -> listener.onStyleClick(style));
    }

    @Override
    public int getItemCount() {
        return styles.length;
    }

    static class StyleViewHolder extends RecyclerView.ViewHolder {
        Button styleButton;

        public StyleViewHolder(@NonNull View itemView) {
            super(itemView);
            styleButton = itemView.findViewById(R.id.style_button);
        }
    }
}
