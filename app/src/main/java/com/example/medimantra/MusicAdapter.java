package com.example.medimantra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private final List<String> musicList;
    private final OnMusicClickListener listener;

    public interface OnMusicClickListener {
        void onMusicClick(String musicName);
    }

    public MusicAdapter(List<String> musicList, OnMusicClickListener listener) {
        this.musicList = musicList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        String musicName = musicList.get(position);
        holder.musicTitle.setText(musicName);
        holder.itemView.setOnClickListener(v -> listener.onMusicClick(musicName));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    // 🔧 Make this public
    public static class MusicViewHolder extends RecyclerView.ViewHolder {
        TextView musicTitle;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            musicTitle = itemView.findViewById(R.id.musicTitle);
        }
    }
}
