package com.itingxi.Adapter;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itingxi.R;
import com.itingxi.interFace.OnLoadMoreListener;
import com.itingxi.model.Movies;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> implements View.OnClickListener{
    private ArrayList<Movies> moviesArrayList = null;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;
    private boolean loading;

    private static final int TYPE_ITEM =1;  //普通Item View
    private static final int TYPE_FOOTER = 0;  //顶部FootView

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener onLoadMoreListener;

    public MoviesAdapter(ArrayList<Movies> arrayList,RecyclerView recyclerView){
        this.moviesArrayList = arrayList;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!loading
                    && totalItemCount <= (lastVisibleItem + visibleThreshold)){
                        if (onLoadMoreListener != null){
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public MoviesAdapter(ArrayList<Movies> moviesArrayList){
        this.moviesArrayList = moviesArrayList;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MoviesViewHolder vh;
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
            vh = new MoviesViewHolder(view);

            //将创建的VIEW注册成点击事件
            view.setOnClickListener(this);
        } else {
            View foot_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar1, parent, false);
            vh = new FootViewHolder(foot_view);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {

        if (holder instanceof MoviesViewHolder) {
            Movies movies = moviesArrayList.get(position);
            holder.title.setText(movies.getMovie_title());
            holder.tvlength.setText("时长:" + movies.getMovie_length());
            holder.tvclick.setText("观看:" + movies.getMovie_click()  + "次");
            ImageLoader.getInstance().displayImage(movies.getMovie_image(),holder.tvivpic);

            //将数据保存在itemView，以便点击时获取
            holder.itemView.setTag(movies);
        }else if (holder instanceof FootViewHolder){
                ((FootViewHolder) holder).progressBar.setIndeterminate(true);
            }
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size() ;
    }

    @Override
    public int getItemViewType(int position) {
        return moviesArrayList.get(position) != null ? TYPE_ITEM : TYPE_FOOTER;
    }

    @Override
    public void onClick(View v) {
        if (onRecyclerViewItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            onRecyclerViewItemClickListener.onItemClick(v,(Movies)v.getTag());
        }

    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener){
        this.onRecyclerViewItemClickListener = listener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {

        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    /**
     * 底部FootView布局
     */
    public class FootViewHolder extends  MoviesViewHolder{
        public ProgressBar progressBar;
        public FootViewHolder(View view) {
            super(view);
            progressBar=(ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    /**
     * 数据列表适配器
     */
    public class MoviesViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView tvclick;
        private TextView tvlength;
        private ImageView tvivpic;

        public MoviesViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tvTitle);
            tvclick = (TextView) itemView.findViewById(R.id.tvClick);
            tvlength = (TextView) itemView.findViewById(R.id.tvLength);
            tvivpic = (ImageView) itemView.findViewById(R.id.ivpic);
        }
    }
}