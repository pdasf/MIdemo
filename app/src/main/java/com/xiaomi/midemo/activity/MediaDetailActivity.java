package com.xiaomi.midemo.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.xiaomi.midemo.R;
import com.xiaomi.midemo.adapter.EpisodeAdapter;
import com.xiaomi.midemo.adapter.GenresAdapter;
import com.xiaomi.midemo.adapter.RecommendAdapter;
import com.xiaomi.midemo.decoration.SpacesItemDecoration;
import com.xiaomi.midemo.entity.Content;
import com.xiaomi.midemo.entity.ItemDTO;
import com.xiaomi.midemo.entity.VideoDTO;
import com.xiaomi.midemo.request.Request1;
import com.xiaomi.midemo.request.RetrofitHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MediaDetailActivity extends BaseActivity {
    private static final String TAG = "MediaDetailActivity";
    private NiceVideoPlayer player;
    private TextView courseTitle;
    private RecyclerView courseInfo;
    private TextView mediaDesc;
    private RecyclerView episodeCount;
    private RecyclerView episode;
    private RecyclerView recommend;
    private List<VideoDTO> videos;
    private TxVideoPlayerController controller;
    private LinearLayoutManager episodeManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_detial);
        ItemDTO info = Objects.requireNonNull(getIntent().getExtras()).getParcelable(ActivityManager.MEDIA_DETAIL);
        if (info == null) {
            Log.e(TAG, "get intent's bundle error: ");
            ActivityManager.finishAll();
        } else {
            getMediaDetailPage(info);
        }

        player = findViewById(R.id.player);
        courseTitle = findViewById(R.id.course_title);
        courseInfo = findViewById(R.id.genres);
        mediaDesc = findViewById(R.id.media_desc);
        findViewById(R.id.open_vip).setOnClickListener(v -> {
            // TODO
        });
        findViewById(R.id.collect).setOnClickListener(v -> {
            // TODO
        });
        episodeCount = findViewById(R.id.episode_count);
        episode = findViewById(R.id.episode);
        recommend = findViewById(R.id.recommend);
    }

    @SuppressLint("DefaultLocale")
    private void initPage(Content content) {
        player = (NiceVideoPlayer) findViewById(R.id.player);
        player.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
        controller = new TxVideoPlayerController(this);
        player.setController(controller);
        courseTitle.setText(content.getMedia().getMediaName());
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        courseInfo.setAdapter(new GenresAdapter(this, Arrays.asList(content.getMedia().getPremiereDate(),
                content.getMedia().getGenres())));
        courseInfo.setLayoutManager(linearLayoutManager1);
        courseInfo.addItemDecoration(new SpacesItemDecoration(SpacesItemDecoration.HORIZONTAL, 20));
        mediaDesc.setText(content.getMedia().getDesc());
        mediaDesc.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15); //TODO
        mediaDesc.setTextColor(Color.parseColor("#FF7986a0"));
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < (content.getMedia().getVideos().size() + 9) / 10; i++) {
            temp.add(String.format("%d-%d", i * 10 + 1, (i + 1) * 10));
        }
        episodeCount.setAdapter(new GenresAdapter(this, temp));
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        episodeCount.setLayoutManager(linearLayoutManager2);
        episodeCount.addItemDecoration(new SpacesItemDecoration(SpacesItemDecoration.HORIZONTAL, 30));
        episode.setAdapter(new EpisodeAdapter(this,
                content.getMedia().getVideos().stream()
                        .map(VideoDTO::getVideoName)
                        .collect(Collectors.toList())));
        episodeManger = new LinearLayoutManager(this);
        episodeManger.setOrientation(LinearLayoutManager.HORIZONTAL);
        episode.setLayoutManager(episodeManger);
        episode.addItemDecoration(new SpacesItemDecoration(SpacesItemDecoration.HORIZONTAL, 10));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recommend.setLayoutManager(gridLayoutManager);
        recommend.setAdapter(new RecommendAdapter(this, content.getRecommend().getItems()));
        recommend.addItemDecoration(new SpacesItemDecoration(SpacesItemDecoration.GRID, 10, 4));
        initPlayer(content.getMedia().getVideos().get(0));
    }

    private void getMediaDetailPage(ItemDTO info) {
        Request1 request = RetrofitHandler.retrofit.create(Request1.class);
        Map<String, String> args = new HashMap<>();
        args.put("id", info.getId());
        args.put("title", info.getTitle());
        Observable<Content> observable = request.getContent(args);
        observable.subscribeOn(Schedulers.io())               // 在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 回到主线程 处理请求结果
                .subscribe(new Observer<Content>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "MediaDetail request 开始采用subscribe连接");
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onNext(Content content) {
                        initPage(content);
                        videos = content.getMedia().getVideos();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "MediaDetail 请求失败" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "MediaDetail 请求成功");
                    }
                });
    }

    private void initPlayer(VideoDTO video) {
        player.setUp(video.getUrl(), null);
        controller.setImage(R.mipmap.fornt);
        controller.setTitle(video.getVideoName());
    }

    public void onEpisodeClick(int position) {
        initPlayer(videos.get(position));
        player.release();
        player.start();
    }

    public void onEpisodeCountClick(int position){
        episodeManger.scrollToPositionWithOffset(10 * position,0);
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }
}