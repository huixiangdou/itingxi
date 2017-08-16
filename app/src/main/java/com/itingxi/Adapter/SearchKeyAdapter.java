package com.itingxi.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itingxi.R;
import com.itingxi.data.MovieContract;

/**
 * Created by quanhai on 16/7/12.
 */
/*
    自定义标签适配器
 */
/*
recyclerAdater
 */
public class SearchKeyAdapter extends RecyclerView.Adapter<SearchKeyAdapter.SearchKeyViewHolder> implements View.OnClickListener{
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Cursor mCursor;

    public SearchKeyAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.mCursor = cursor;
    }

    /**
     *
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
    */
    public class SearchKeyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public SearchKeyViewHolder(View itemView) {
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
    public SearchKeyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tag_item,parent,false);
        SearchKeyViewHolder viewHolder = new SearchKeyViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    //绑定一个viewholder
    @Override
    public void onBindViewHolder(SearchKeyViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }
        String tagText = mCursor.getString(mCursor.getColumnIndex(MovieContract.SearchKey.SEARCH_STRING));
        holder.textView.setText(tagText);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(tagText);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //返回item数量
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    /**
     * 交换光标
     * @param newCursor
     */
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }
}
