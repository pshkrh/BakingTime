package com.pshkrh.bakingtime.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    public ArrayList<Step> mSteps = new ArrayList<>();

    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private long mPlayerPosition;
    private int position;

    public DetailsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details,container,false);

        final Bundle bundle = this.getArguments();
        mSteps = bundle.getParcelableArrayList("Steps");

        position = bundle.getInt("Position");
        String desc = mSteps.get(position).getDesc();
        String videoUrl = mSteps.get(position).getVideoUrl();
        String photoUrl = mSteps.get(position).getThumbnailUrl();
        mPlayerView = rootView.findViewById(R.id.exoplayer);
        ImageView recipeImage = rootView.findViewById(R.id.recipe_image);
        if(TextUtils.isEmpty(videoUrl)) {
            mPlayerView.setVisibility(View.GONE);
            recipeImage.setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(photoUrl)){
                    Glide.with(getContext())
                            .load(photoUrl)
                            .into(recipeImage);
            }
            else {
                    Glide.with(getContext())
                            .load(R.drawable.oven)
                            .into(recipeImage);
            }
        }
        TextView stepDesc = rootView.findViewById(R.id.step_description);
        stepDesc.setText(desc);

        final ViewPager mViewPager = getActivity().findViewById(R.id.pager);

        ImageButton prev = rootView.findViewById(R.id.button_prev);
        ImageButton next = rootView.findViewById(R.id.button_next);

        if(position == 0){
            prev.setVisibility(View.INVISIBLE);
        }

        if(position == mSteps.size()-1){
            next.setVisibility(View.INVISIBLE);
        }

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = bundle.getInt("Position");
                mViewPager.setCurrentItem(pos-1,true);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = bundle.getInt("Position");
                mViewPager.setCurrentItem(pos+1,true);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        //initializePlayer();
    }

    public static DetailsFragment newInstance(ArrayList<Step> mSteps, int position){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Steps",mSteps);
        bundle.putInt("Position",position);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    private void initializePlayer(Uri mediaUri){
        if(mExoPlayer==null){
            //Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            RenderersFactory render = new DefaultRenderersFactory(getContext());
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(render,defaultTrackSelector,loadControl);
            mPlayerView.requestFocus();
            mPlayerView.setPlayer(mExoPlayer);

            DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), "yourApplicationName"), defaultBandwidthMeter);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaUri);

            mExoPlayer.prepare(videoSource);
            mExoPlayer.setPlayWhenReady(true);

        }
    }

    private void releasePlayer(){
        if(mExoPlayer!=null) {
            mExoPlayer.setPlayWhenReady(false);
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mExoPlayer==null){
            initializePlayer(Uri.parse(mSteps.get(position).getVideoUrl()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(this.isVisible()){
            if(!isVisibleToUser)
                mExoPlayer.stop();
            else
                mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mExoPlayer!=null && mExoPlayer.getPlayWhenReady()) {
            mPlayerPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.setPlayWhenReady(false);
        }

        //outState.putLong("Seek",mPlayerPosition);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            if(mExoPlayer!=null){
                mExoPlayer.seekTo(mPlayerPosition);
                mExoPlayer.setPlayWhenReady(true);
            }
        }
        //mExoPlayer.seekTo(savedInstanceState.getLong("Seek"));
    }
}
