package com.pshkrh.bakingtime.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.pshkrh.bakingtime.Model.Step;
import com.pshkrh.bakingtime.R;
import java.util.ArrayList;

public class DetailsFragment extends Fragment {

    public ArrayList<Step> mSteps = new ArrayList<>();

    private SimpleExoPlayer mExoPlayer;
    private PlayerControlView mPlayerControlView;
    private long mPlayerPosition;

    public DetailsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details,container,false);

        final Bundle bundle = this.getArguments();
        mSteps = bundle.getParcelableArrayList("Steps");
        int position = bundle.getInt("Position");
        String desc = mSteps.get(position).getDesc();

       // Toast.makeText(getContext(), "Position Clicked = " + position, Toast.LENGTH_SHORT).show();


        mPlayerControlView = rootView.findViewById(R.id.exoplayer);
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

        initializePlayer(Uri.parse(mSteps.get(position).getVideoUrl()));

        return rootView;
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
            RenderersFactory renderersFactory = new DefaultRenderersFactory(getContext());
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory,trackSelector,loadControl);
            mPlayerControlView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getContext(),"Recipes");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,new DefaultDataSourceFactory(getContext(),userAgent),
                    new DefaultExtractorsFactory(),
                    null,null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer(){
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onStop() {
        releasePlayer();
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        mPlayerPosition = mExoPlayer.getCurrentPosition();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        mExoPlayer.seekTo(mPlayerPosition);
        super.onViewStateRestored(savedInstanceState);
    }
}
