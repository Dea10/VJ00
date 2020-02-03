package com.dea.vj00;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.MyViewHolder> {
    private List<Game> mDataset;

    @Override
    public GameListAdapter.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder( GameListAdapter.MyViewHolder holder, int position) {
        String game_name = mDataset.get(position).name;
        holder.tv_game_name.setText(game_name);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_game_name;

        public MyViewHolder(View view) {
            super(view);
            tv_game_name = view.findViewById(R.id.tv_game_name);
        }
    }

    public GameListAdapter(List<Game> games) {
        mDataset = games;
    }
}
