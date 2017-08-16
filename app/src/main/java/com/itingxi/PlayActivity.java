package com.itingxi;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.itingxi.Adapter.CommentAdapter;
import com.itingxi.Adapter.TagAdapter;
import com.itingxi.async.ClientUtils;
import com.itingxi.async.CntvAsyncResponseHandler;
import com.itingxi.async.CommentAsyncResponseHandler;
import com.itingxi.async.YoukuAsyncResponseHandler;
import com.itingxi.basic.BasicActivity;
import com.itingxi.data.MovieDbHelper;
import com.itingxi.listener.SampleListener;
import com.itingxi.model.Cntv_video;
import com.itingxi.model.Movies;
import com.itingxi.model.MoviesComment;
import com.itingxi.model.Tag;
import com.itingxi.model.Youku_video;
import com.itingxi.utils.HttpUtils;
import com.itingxi.utils.StringUtils;
import com.itingxi.video.LandLayoutVideo;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
public class PlayActivity extends BasicActivity {

    /**
     *  视频播放页面
     * 1.视频播放
     * 2.视频标签
     * 3.视频评论
     * 4.视频下载连接处理
     */

    private String tvode;//播发器地址
    private String title;
    private Movies movie;
    private ArrayList<Tag> tagArrayList;
    private RecyclerView tagRecyclerView;
    private TagAdapter tagAdapter;
    public static PlayActivity playActivityInstance;
    private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;
    private Youku_video youku_video;
    private Cntv_video cntv_video;
    private boolean isPause;//视频播放状态
    private boolean isPlay;//视频播放状态
    private OrientationUtils orientationUtils;
    private GSYVideoOptionBuilder gsyVideoOption;
    private ImageView imageView;
    private MovieDbHelper movieDbHelper;
    private SQLiteDatabase mDb;
    /**
     * 获取TAG数据
     * @param movie
     * @return
     */
    private ArrayList<Tag> getTagArrayList(Movies movie) {
        String movie_tags = movie.getMovie_tags();
        String[] tags = movie_tags.split(",");
        tagArrayList = new ArrayList<Tag>();
        for (int i = 0; i < tags.length; i++) {
            tagArrayList.add(new Tag(tags[i]));
        }
        return tagArrayList;
    }

    @BindView(R.id.video_displayer)
    LandLayoutVideo landLayoutVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        setContentView(R.layout.activity_play);
        playActivityInstance = this;
        ButterKnife.bind(this);

        String movie_play_url = null;
        //获取movies元信息
        Intent intent = getIntent();
        movie = intent.getParcelableExtra("MOVIES");

        //增加视频封面
        imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageURI(Uri.parse(movie.getMovie_image()));

        resolveNormalVideoUI();

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, landLayoutVideo);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        gsyVideoOption = new GSYVideoOptionBuilder();

        //获取movie标签
        tagArrayList = getTagArrayList(movie);
        tagRecyclerView = (RecyclerView) findViewById(R.id.movies_tags_recycler_view);


        tagRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        tagAdapter = new TagAdapter(tagArrayList);
        tagRecyclerView.setAdapter(tagAdapter);

        tagAdapter.setOnItemClickListener(new TagAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String tagName) {
                if (ListActivity.listActivityInstance != null) {
                    ListActivity.listActivityInstance.finish();
                }

                Intent tagIntent = new Intent(PlayActivity.this, ListActivity.class);
                String url = Application.getTagsJson + tagName;
                tagIntent.putExtra(Application.URL_MESSAGE, url);
                tagIntent.putExtra(Application.TEXT_MESSAGE, tagName);

                startActivity(tagIntent);
            }
        });

        tvode = StringUtils.getTvode(movie.getMovie_play_url());//解析数据库视频地址::::::adfasd:::::
        title = movie.getMovie_title();//获取视频标题

        //优酷:3 土豆:5 cntv:4
        int movie_play_id = Integer.parseInt(movie.getMovie_play_id());
        switch (movie_play_id){
            //优酷
            case 3:
                String url = Application.YOUKU_ANDROID +tvode;
                ClientUtils.getClientUtils().getNetDate(url,new YoukuAsyncResponseHandler(PlayActivity.this,this,"youku_video"));
                break;
            //cntv
            case 4:
                Button down_button = (Button) findViewById(R.id.down_button);
                down_button.setVisibility(View.INVISIBLE);
                String cntv_url = Application.CNTV_ANDROID +tvode;
                ClientUtils.getClientUtils().getNetDate(cntv_url,new CntvAsyncResponseHandler(PlayActivity.this,this,"cntv_video"));
                break;
        }

        getComment();//加载评论

        //增加点击
        HttpUtils.setOnclick(movie.getMovie_id());

        //添加历史记录
        movieDbHelper = new MovieDbHelper(this);//获取数据库帮手
        mDb = movieDbHelper.getWritableDatabase();//获取数据库
        movieDbHelper.removeHistoryMovie(mDb,movie.getMovie_id());//历史数据存在,删除
        movieDbHelper.addNewMovie(movie,mDb);//添加数据到历史数据库中
    }

    //提交评论
    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.pl_editText);
        String sayText = editText.getText().toString();

        //评论内容判断
        if(sayText == null || sayText.trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"内容不能为空",Toast.LENGTH_SHORT).show();
            return;
        }else if(sayText.length()>32){
            Toast.makeText(getApplicationContext(),"评论内容过长",Toast.LENGTH_SHORT).show();
            return;
        }

        //获取mac地址
        String mac = HttpUtils.getMacAddress(getApplicationContext());
        HttpUtils.setPinglun(movie.getMovie_id(), mac, sayText);
        editText.setText("");

        //加载评论数据
        getComment();
        Toast.makeText(getApplicationContext(),"评论成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载评论数据
     */
    public void getComment(){
        String name = "comment";
        String url = "http://www.itingxi.com/e/extend/app2/getCommentJson.php?id=" + movie.getMovie_id();
        ClientUtils.getClientUtils().getNetDate(url,new CommentAsyncResponseHandler(PlayActivity.this,this,name));
    }

    /**
     *下载链接
     * @param view
     */
    public void filedownload(View view) {
        Intent intent = new Intent(this, TasksManagerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("MOVIE", movie);
        bundle.putString("videoUrl",youku_video.getYouku_mp4_url());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    public void onResume() {
        isPause = false;
        super.onResume();

        //友盟统计
        MobclickAgent.onPageStart("play");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        isPause = true;
        super.onPause();

        //友盟统计
        MobclickAgent.onPageEnd("play");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    public void onSuccess(ArrayList arrayList) {
        ArrayList<MoviesComment> moviesCommentArrayList = arrayList;
        String sayText = moviesCommentArrayList.get(0).getSayText();
        Log.d("play_sytext",sayText);
        commentAdapter = new CommentAdapter(moviesCommentArrayList);
        commentRecyclerView = (RecyclerView) findViewById(R.id.pl_listView);
        commentRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        Log.d("play_commentAdapter",commentAdapter.getItemCount() + "");
        commentAdapter.notifyDataSetChanged();//adapter数据刷新
        commentRecyclerView.setAdapter(commentAdapter);
    }

    public void onSuccess(Cntv_video cntv_video){
        this.cntv_video = cntv_video;
        String cntvM3u8Url = cntv_video.getCntv_m3u8_url();
        if (cntvM3u8Url == null || TextUtils.isEmpty(cntvM3u8Url)){
            cntvM3u8Url = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
            Toast.makeText(PlayActivity.this,"加载失败,稍后重试",Toast.LENGTH_SHORT).show();
        }
        videoPlayer(imageView,cntvM3u8Url);
        Log.d("cntvM3u8Url",cntvM3u8Url);
    }

    public void onSuccess(Youku_video youkuvideo){
        this.youku_video = youkuvideo;
        String youkuM3u8Url = youkuvideo.getYouku_m3u8_url();
        if (youkuM3u8Url == null || TextUtils.isEmpty(youkuM3u8Url) ){
            youkuM3u8Url = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
            Toast.makeText(PlayActivity.this,"加载失败,稍后重试",Toast.LENGTH_SHORT).show();
        }
        videoPlayer(imageView,youkuM3u8Url);
        Log.d("youkuM3u8Url",youkuM3u8Url);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!landLayoutVideo.isIfCurrentIsFullscreen()) {
                    landLayoutVideo.startWindowFullscreen(PlayActivity.this, true, true);
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (landLayoutVideo.isIfCurrentIsFullscreen()) {
                    StandardGSYVideoPlayer.backFromWindowFull(this);
                }
                if (orientationUtils != null) {
                    orientationUtils.setEnable(true);
                }
            }
        }
    }

    private void resolveNormalVideoUI() {
        //增加title
        landLayoutVideo.getTitleTextView().setVisibility(View.VISIBLE);
        landLayoutVideo.getTitleTextView().setText("爱听戏");
        landLayoutVideo.getBackButton().setVisibility(View.VISIBLE);
    }

    public void videoPlayer(ImageView imageView,String url){
        gsyVideoOption.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setUrl(url)
                .setCacheWithPlay(false)
                .setVideoTitle("爱听戏")
                .setStandardVideoAllCallBack(new SampleListener() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        Debuger.printfError("***** onPrepared **** " + objects[0]);
                        Debuger.printfError("***** onPrepared **** " + objects[1]);
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[1]);//当前全屏player
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                    }

                    @Override
                    public void onClickStartError(String url, Object... objects) {
                        super.onClickStartError(url, objects);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock);
                        }
                    }
                }).build(landLayoutVideo);

        landLayoutVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                landLayoutVideo.startWindowFullscreen(PlayActivity.this, true, true);
            }
        });
    }
}