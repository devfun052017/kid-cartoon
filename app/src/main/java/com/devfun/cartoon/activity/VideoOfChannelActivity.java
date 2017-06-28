package com.devfun.cartoon.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devfun.cartoon.R;
import com.devfun.cartoon.adapter.VideoOfChannelAdapter;
import com.devfun.cartoon.base.BaseActivity;
import com.devfun.cartoon.fragment.PlaylistOfVideoFragment;
import com.devfun.cartoon.helper.OnItemClickListener;
import com.devfun.cartoon.model.BaseListModel;
import com.devfun.cartoon.model.BaseModel;
import com.devfun.cartoon.model.VideoModel;
import com.devfun.cartoon.network.GenerateService;
import com.devfun.cartoon.network.YoutubeService;
import com.devfun.cartoon.utils.Constants;
import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggablePanel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.orhanobut.logger.Logger;

/**
 * *******************************************
 * * Project cartoon                **
 * * Created by Simon on 6/24/2017.           **
 * * Copyright (c) 2017 by DevFun        **
 * * All rights reserved                    **
 * *******************************************
 */

public class VideoOfChannelActivity extends BaseActivity<BaseListModel<VideoModel>>
        implements OnItemClickListener {
    private RecyclerView mRecyclerView;
    private EditText mSearchView;
    private SwipeRefreshLayout mRefreshLayout;
    private VideoOfChannelAdapter mAdapter;
    private String mNextToken;
    private String mChannelId;
    private boolean isLoading;
    //Youtube Panel
    private DraggablePanel mDraggablePanel;
    private YouTubePlayer mPlayer;
    private YouTubePlayerSupportFragment mPlayerSupportFragment;
    private PlaylistOfVideoFragment mPlaylistOfVideo;
    private boolean isFullScreen;

    @Override
    protected void onDataResult(BaseListModel<VideoModel> result) {
        mNextToken = result.getNextPageToken();
        if (isLoading && !mRefreshLayout.isRefreshing()) {
            mAdapter.showFooter(false);
            mAdapter.appendItems(result.getItems());
        } else {
            mAdapter.setData(result.getItems());
        }
        mRefreshLayout.setRefreshing(false);
        isLoading = false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_of_channel);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mSearchView = (EditText) findViewById(R.id.activityVideoOfChannel_editText_search);
        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    searchVideo();
                }
                return true;
            }
        });
        TextView fTitle = (TextView) findViewById(R.id.activityVideoOfChannel_textView_title);
        mChannelId = getIntent().getStringExtra("channelId");
        String title = getIntent().getStringExtra("channelName");
        if (!TextUtils.isEmpty(title)) {
            fTitle.setText(title);
        }
        findViewById(R.id.activityVideoOfChannel_imageView_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
        mRecyclerView = (RecyclerView) findViewById(R.id.activityVideoOfChannel_recyclerView);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activityVideoOfChannel_swipeRefresh);
        initRecyclerView();
        initRefresh();
        //
        initYoutubeFragment();
        initDraggablePanel();
        pullToRefreshData();
        if (!TextUtils.isEmpty(mChannelId)) {
            mSearchView.setVisibility(View.GONE);
            fTitle.setVisibility(View.VISIBLE);
        } else {
            mSearchView.setVisibility(View.VISIBLE);
            fTitle.setVisibility(View.GONE);
        }
    }

    private void getVideoOfChannel() {
        request(GenerateService.getInstance()
                .createService(YoutubeService.class).getItemsOfChannel(mChannelId, mNextToken));
    }

    private void searchVideo() {
        if (TextUtils.isEmpty(mSearchView.getText().toString())) {
            mRefreshLayout.setRefreshing(false);
            return;
        }
        request(GenerateService.getInstance()
                .createService(YoutubeService.class)
                .searchVideo(mSearchView.getText().toString(), mNextToken));
    }

    private void initRefresh() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLoading) return;
                pullToRefreshData();
            }
        });
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    private void pullToRefreshData() {
        mNextToken = null;
        mRefreshLayout.setRefreshing(true);
        if (!TextUtils.isEmpty(mChannelId)) {
            getVideoOfChannel();
        } else {
            searchVideo();
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new VideoOfChannelAdapter();
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager fManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItem = fManager.findLastVisibleItemPosition();
                int totalItemCount = fManager.getItemCount();
                if (!isLoading && totalItemCount > 0
                        && totalItemCount <= (lastVisibleItem + 3) &&
                        !TextUtils.isEmpty(mNextToken)) {
                    loadMore();
                }
            }
        });
    }

    private void loadMore() {
        mAdapter.showFooter(true);
        isLoading = true;
        if (!TextUtils.isEmpty(mChannelId)) {
            getVideoOfChannel();
        } else {
            searchVideo();
        }
    }

    private void hideKeyboard() {
        if (mSearchView.getVisibility() == View.VISIBLE) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
        }
    }

    @Override
    public void onItemClick(BaseModel item, int position) {
        if (isLoading) return;
        if (item instanceof VideoModel) {
            mPlayer.cueVideo(((VideoModel) item).getVideoId().getId());
            mPlaylistOfVideo.setChannelTitle(((VideoModel) item).getSnippet().getChannelTitle());
            showDraggable();
        }
        hideKeyboard();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //region Draggable Setup
    private void initYoutubeFragment() {
        mPlayerSupportFragment = new YouTubePlayerSupportFragment();
        mPlayerSupportFragment.setRetainInstance(true);
        mPlayerSupportFragment.initialize(Constants.API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    mPlayer = youTubePlayer;
                    mPlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean b) {
                            Logger.d("playing");
                            isFullScreen = b;
                        }
                    });
                    mPlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                        @Override
                        public void onLoading() {

                        }

                        @Override
                        public void onLoaded(String s) {
                            mPlayer.play();
                        }

                        @Override
                        public void onAdStarted() {

                        }

                        @Override
                        public void onVideoStarted() {

                        }

                        @Override
                        public void onVideoEnded() {

                        }

                        @Override
                        public void onError(YouTubePlayer.ErrorReason errorReason) {

                        }
                    });
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi tải video.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initDraggablePanel() {
        mDraggablePanel = (DraggablePanel) findViewById(R.id.activityVideoOfChannel_draggablePanel);
        mDraggablePanel.setFragmentManager(getSupportFragmentManager());
        mDraggablePanel.setTopFragment(mPlayerSupportFragment);
        mDraggablePanel.setTopViewHeight(getResources().getDimensionPixelSize(R.dimen._168sdp));
        //
        mPlaylistOfVideo = new PlaylistOfVideoFragment();
        mPlaylistOfVideo.setListener(this);
        mDraggablePanel.setBottomFragment(mPlaylistOfVideo);
        mDraggablePanel.setVisibility(View.GONE);
        mDraggablePanel.setDraggableListener(new DraggableListener() {
            @Override
            public void onMaximized() {

            }

            @Override
            public void onMinimized() {

            }

            @Override
            public void onClosedToLeft() {
                pauseVideo();
            }

            @Override
            public void onClosedToRight() {
                pauseVideo();
            }
        });
        mDraggablePanel.initializeView();
    }

    private void pauseVideo() {
        if (mPlayer != null) {
            mPlayer.pause();
            mDraggablePanel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDraggablePanel.getVisibility() == View.VISIBLE) {
            if (isFullScreen) {
                mPlayer.setFullscreen(false);
            } else if (mDraggablePanel.isMaximized()) {
                mDraggablePanel.minimize();
            } else {
                super.onBackPressed();
            }
        } else
            super.onBackPressed();
    }

    private void showDraggable() {
        if (mDraggablePanel.getVisibility() == View.VISIBLE
                && mDraggablePanel.isMaximized()) {
            return;
        }
        if (mDraggablePanel.getVisibility() == View.GONE) {
            mDraggablePanel.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up));
        }
        mDraggablePanel.maximize();
        mDraggablePanel.setVisibility(View.VISIBLE);
    }
    //endregion
}
