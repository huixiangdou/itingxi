package com.itingxi.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itingxi.R;
import com.itingxi.data.MovieContract;
import com.itingxi.model.Movies;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by quanhai on 2017/8/14.
 */

public class MovieHistoryAdapter extends RecyclerView.Adapter<MovieHistoryAdapter.HistoryViewHolder> implements View.OnClickListener {
    private Cursor mCursor;
    private Context context;
    private Movies movies;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;

    public MovieHistoryAdapter(Context context, Cursor cursor){
        this.context = context;
        this.mCursor = cursor;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);//获取 一个布局填充器
        View view = layoutInflater.inflate(R.layout.list_item,parent,false);//填充指定布局
        view.setOnClickListener(this);//设置点击监听器
        return new HistoryViewHolder(view);
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }

        //获取一个视频对象
        movies = new Movies(
                mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_ID)),
                mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_TITLE)),
                mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_JUMU)),
                mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_PLAYER)),
                mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_CLICK)),
                mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_PLAY_ID)),
                mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_PLAY_URL)),
                mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_GOOD)),
                mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_TAGS)),
                mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_IMAGE)),
                mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_LENGTH))
        );

        //绑定view数据
        holder.title.setText(movies.getMovie_title());
        holder.tvclick.setText("点击:" + movies.getMovie_click() + " 次");
        holder.tvlength.setText("时长:" + movies.getMovie_length());
        String image = movies.getMovie_image();
        ImageLoader.getInstance().displayImage(image,holder.tvivpic);


        //设置数据tag
        String movie_id = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_ID));
        holder.itemView.setTag(R.id.tag_movie,movies);
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

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    @Override
    public void onClick(View v) {
        if (onRecyclerViewItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            onRecyclerViewItemClickListener.onItemClick(v,v.getTag(R.id.tag_movie));
            Log.d("onClick","onclick");
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener){
        this.onRecyclerViewItemClickListener = listener;
        Log.d("onClick","onclick");
    }

    //内部类,用于绑定列表的子类
    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView tvclick;
        private TextView tvlength;
        private ImageView tvivpic;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            tvclick = (TextView) itemView.findViewById(R.id.tvClick);
            tvlength = (TextView) itemView.findViewById(R.id.tvLength);
            tvivpic = (ImageView) itemView.findViewById(R.id.ivpic);
        }
    }
}
