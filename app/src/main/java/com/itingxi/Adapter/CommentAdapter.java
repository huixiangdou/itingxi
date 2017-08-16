package com.itingxi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itingxi.R;
import com.itingxi.model.MoviesComment;

import java.util.ArrayList;

/**
 * Created by quanhai on 16/7/10.
 */
/*
    评论列表适配器
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private ArrayList<MoviesComment> moviesCommentsArrayList;

    public CommentAdapter(ArrayList arrayList) {
        this.moviesCommentsArrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MoviesComment moviesComment = moviesCommentsArrayList.get(position);
        holder.saytext.setText(moviesComment.getSayText());
        holder.username.setText(moviesComment.getUserName());

    }

    @Override
    public int getItemCount() {
        Log.d("comment_size",moviesCommentsArrayList.size()+"");
        return moviesCommentsArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView username;
        private TextView saytext;

        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.pl_userName);
            saytext = (TextView) itemView.findViewById(R.id.pl_sayText);
        }
    }
//    private Context context;
//    private List<MoviesComment> moviesPlList;
//
//    public CommentAdapter(Context context, List<MoviesComment> arrayList) {
//        this.context = context;
//        this.moviesPlList = arrayList;
//    }
//
//    @Override
//    public int getCount() {
//        return moviesPlList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return moviesPlList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View  convertView, ViewGroup viewGroup) {
//        if(convertView == null){
//            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item,null);
//        }
//        TextView pl_userName = (TextView) convertView.findViewById(R.id.pl_userName);
//        TextView pl_sayText = (TextView) convertView.findViewById(R.id.pl_sayText);
//
//        MoviesComment movie_pl = moviesPlList.get(position);
//        pl_userName.setText(movie_pl.getUserName());
//        pl_sayText.setText(movie_pl.getSayText());
//
//        return convertView;
//    }
}
