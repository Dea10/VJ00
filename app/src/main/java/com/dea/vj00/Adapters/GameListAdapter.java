package com.dea.vj00.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dea.vj00.POJOs.GameListElement;
import com.dea.vj00.R;

import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.MyViewHolder> {
    private List<GameListElement> mDataset;
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
        String game_name = mDataset.get(position).game_name;
        String game_platform = mDataset.get(position).game_platform;
        String game_status = mDataset.get(position).game_status;

        holder.tv_game_platform.setText("Platform: " + game_platform);
        holder.tv_game_name.setText(game_name);
        //holder.iv_game_status.setImageResource(R.drawable.baseline_check_circle_black_48);

        switch (game_status) {
            case "started":
                holder.iv_game_status.setImageResource(R.drawable.baseline_videogame_asset_24);
                break;
            case "finished":
                holder.iv_game_status.setImageResource(R.drawable.baseline_check_circle_24);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_game_name;
        public TextView tv_game_platform;
        public ImageView iv_game_status;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_game_name = itemView.findViewById(R.id.tv_game_name);
            tv_game_platform = itemView.findViewById(R.id.tv_game_platform);
            iv_game_status = itemView.findViewById(R.id.iv_game_status);

            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public GameListAdapter(Context context, List<GameListElement> games, List<String> ids) {
        this.mInflater = LayoutInflater.from(context);
        this.mDataset = games;
        this.mIdDataset = ids;
    }

    public String getItem(int id) {
        return mIdDataset.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
