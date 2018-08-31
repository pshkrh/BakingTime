package com.pshkrh.bakingtime.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.pshkrh.bakingtime.Model.Step;
import com.pshkrh.bakingtime.R;

import java.util.ArrayList;

public class DetailsFragment extends Fragment {

    private static final String TAG = "DetailsFragment";
    private static final String VIDEO_URL = "videoUrl";
    private static final String THUMBNAIL_URL = "thumbnaiUrl";
    private static final String DESCRIPTION = "description";
    private static final String LAST_PLAYED_POSITION = "lastplayed";

    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;

    private String videoUrl,thumbnailUrl,desc;

    public static DetailsFragment newInstance(String videoUrl, String thumbnailUrl, String desc) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_URL,videoUrl);
        bundle.putString(THUMBNAIL_URL,thumbnailUrl);
        bundle.putString(DESCRIPTION,desc);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            videoUrl = getArguments().getString(VIDEO_URL);
            thumbnailUrl = getArguments().getString(THUMBNAIL_URL);
            desc = getArguments().getString(DESCRIPTION);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        setLayout(rootView);
        initializePlayer(Uri.parse(videoUrl), savedInstanceState);

        return rootView;
    }

    private void setLayout(View rootView) {
        mPlayerView = rootView.findViewById(R.id.exoplayer);
        ImageView recipeImage = rootView.findViewById(R.id.recipe_image);
        if (TextUtils.isEmpty(videoUrl)) {
            mPlayerView.setVisibility(View.GONE);
            recipeImage.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(thumbnailUrl)
                    .apply(new RequestOptions().error(R.drawable.oven))
                    .into(recipeImage);
        }

        TextView stepDesc = rootView.findViewById(R.id.step_description);
        stepDesc.setText(desc);
    }

    private void initializePlayer(Uri mediaUri, Bundle savedInstanceState) {
        if (mExoPlayer == null) {
            //Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            RenderersFactory render = new DefaultRenderersFactory(getContext());
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(render, defaultTrackSelector, loadControl);
            mPlayerView.requestFocus();
            mPlayerView.setPlayer(mExoPlayer);

            DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), "BakingTime"), defaultBandwidthMeter);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaUri);
            if (savedInstanceState != null)
                mExoPlayer.seekTo(savedInstanceState.getLong(LAST_PLAYED_POSITION));
            mExoPlayer.prepare(videoSource, false, true);
            mExoPlayer.setPlayWhenReady(true);
            mPlayerView.hideController();
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.setPlayWhenReady(false);
            mExoPlayer.stop();
            mExoPlayer.release();
        }
        mExoPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Util.SDK_INT > 23)
            releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(LAST_PLAYED_POSITION, mExoPlayer.getCurrentPosition());
    }
}