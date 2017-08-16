package com.itingxi.Adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itingxi.R;
import com.itingxi.model.Channel;

import java.util.ArrayList;

/**
 * Created by quanhai on 2016/12/17.
 * 栏目适配器
 */

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<Channel> channelArrayList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private Cursor mCursor;

    public ChannelAdapter(ArrayList arrayList) {
        this.channelArrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Channel channel = channelArrayList.get(position);
        holder.textView.setText(channel.getClassName());
        holder.itemView.setTag(channel);

    }

    @Override
    public int getItemCount() {
        return channelArrayList.size();
    }

    @Override
    public void onClick(View v) {

        if (onRecyclerViewItemClickListener != null){
            onRecyclerViewItemClickListener.onItemClick(v,(Channel)v.getTag());
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener){
        this.onRecyclerViewItemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView_list_tag);
        }
    }


}
