package com.dea.vj00;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.MyViewHolder> {
    private List<String> mDataset;
    private List<String> mIdDataset;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    @Override
    public GameListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.from(parent.getContext()).inflate(R.layout.game_list, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(GameListAdapter.MyViewHolder holder, int position) {
        String game_name = mDataset.get(position);
        holder.tv_game_name.setText(game_name);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_game_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_game_name = itemView.findViewById(R.id.tv_game_name);
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public GameListAdapter(Context context, List<String> games, List<String> ids) {
        this.mInflater = LayoutInflater.from(context);
        this.mDataset = games;
        this.mIdDataset = ids;
    }

    String getItem(int id) {
        return mIdDataset.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
