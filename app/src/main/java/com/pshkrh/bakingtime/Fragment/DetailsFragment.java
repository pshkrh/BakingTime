package com.pshkrh.bakingtime.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.pshkrh.bakingtime.Model.Step;
import com.pshkrh.bakingtime.R;
import java.util.ArrayList;

public class DetailsFragment extends Fragment {

    public ArrayList<Step> mSteps = new ArrayList<>();

    public DetailsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details,container,false);

        Bundle bundle = this.getArguments();
        mSteps = bundle.getParcelableArrayList("Steps");
        int position = bundle.getInt("Position");
        String desc = mSteps.get(position).getDesc();

       // Toast.makeText(getContext(), "Position Clicked = " + position, Toast.LENGTH_SHORT).show();


        //SimpleExoPlayerView simpleExoPlayerView = rootView.findViewById(R.id.exoplayer);
        TextView stepDesc = rootView.findViewById(R.id.step_description);
        stepDesc.setText(desc);

        ImageButton prev = rootView.findViewById(R.id.button_prev);
        ImageButton next = rootView.findViewById(R.id.button_next);

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

}
