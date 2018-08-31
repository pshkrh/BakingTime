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

    public static ArrayList<Step> mSteps = new ArrayList<>();

    private static final String TAG = "DetailsFragment";
    public static final String FRAGMENT_TAG = "DF";

    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    public static long mPlayerPosition = -1;
    public static int mFragmentPosition = 0;
    public static boolean mMoving;
    public DetailsFragment detailsFragment;


    public DetailsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details,container,false);

        if(getArguments()!=null){
            Bundle bundle = this.getArguments();
            mSteps = bundle.getParcelableArrayList("Steps");
            mMoving = bundle.getBoolean("Moving");
            if(mMoving){
                mPlayerPosition = 0;
            }
            else{
                mPlayerPosition = bundle.getLong("PlayerPosition");
            }
            mFragmentPosition = bundle.getInt("FragmentPosition");
            Log.d(TAG,"GetArguments()\nPlayer pos = " + mPlayerPosition + "\nFragment pos = " + mFragmentPosition + "\nMoving = " + mMoving);
        }

        setLayout(rootView);
        initializePlayer(Uri.parse(mSteps.get(mFragmentPosition).getVideoUrl()));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
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
                    Util.getUserAgent(getContext(), "BakingTime"), defaultBandwidthMeter);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaUri);

            if(mPlayerPosition != -1){
                mExoPlayer.seekTo(mPlayerPosition);
            }
            mExoPlayer.prepare(videoSource,false,true);
            mExoPlayer.setPlayWhenReady(true);
            mPlayerView.hideController();
        }
    }

    private void releasePlayer(){
        if(mExoPlayer!=null) {
            mExoPlayer.setPlayWhenReady(false);
            mExoPlayer.stop();
            mExoPlayer.release();
        }
        mExoPlayer = null;
    }

    public void changeFragment(){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Steps",mSteps);
        bundle.putInt("FragmentPosition",mFragmentPosition);
        bundle.putLong("PlayerPosition",mPlayerPosition);
        bundle.putBoolean("Moving",mMoving);
        detailsFragment = new DetailsFragment();
        detailsFragment.setRetainInstance(true);
        detailsFragment.setArguments(bundle);
        if(getActivity()!=null)
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.details_frame, detailsFragment)
                    .commit();
    }

    private void setLayout(View rootView){
        String desc = mSteps.get(mFragmentPosition).getDesc();
        String videoUrl = mSteps.get(mFragmentPosition).getVideoUrl();
        String photoUrl = mSteps.get(mFragmentPosition).getThumbnailUrl();
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

        ImageButton prev = rootView.findViewById(R.id.button_prev);
        ImageButton next = rootView.findViewById(R.id.button_next);

        if(mFragmentPosition == 0){
            prev.setVisibility(View.INVISIBLE);
        }

        if(mFragmentPosition == mSteps.size()-1){
            next.setVisibility(View.INVISIBLE);
        }

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentPosition--;
                mMoving = true;
                changeFragment();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentPosition++;
                mMoving = true;
                changeFragment();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if(Util.SDK_INT > 23)
            releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause() called");
        mMoving = false;
        if(mExoPlayer!=null){
            mPlayerPosition = mExoPlayer.getCurrentPosition();
        }
        if(getActivity()!=null)
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        if(Util.SDK_INT <= 23){
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume() called");
        if(getView()!=null) {
            setLayout(getView());
            initializePlayer(Uri.parse(mSteps.get(mFragmentPosition).getVideoUrl()));
        }
    }
}