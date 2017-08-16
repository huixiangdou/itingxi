package com.itingxi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itingxi.model.Tag;
import com.itingxi.R;

import java.util.ArrayList;

/**
 * Created by quanhai on 16/7/12.
 */
/*
    自定义标签适配器
 */
/*
recyclerAdater
 */
public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> implements View.OnClickListener{
    private ArrayList arrayList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public TagAdapter(ArrayList arrayList) {
        this.arrayList = arrayList;
    }

    /*
        自定义的ViewHolder，持有每个Item的的所有界面元素
    */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView_list_tag);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }

    /*
    define interface
     */
    public interface OnRecyclerViewItemClickListener{
        void onItemClick(View view, String tagName);
    }

    //创建一个viewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    //绑定一个viewholder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tag tag = (Tag) arrayList.get(position);
        holder.textView.setText(tag.getTagName());

        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(tag.getTagName());
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //返回item数量
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
