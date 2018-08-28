package com.pshkrh.bakingtime.Adapter;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.pshkrh.bakingtime.Fragment.DetailsFragment;
import com.pshkrh.bakingtime.Model.Step;

import java.util.ArrayList;


public class CustomPagerAdapter extends FragmentStatePagerAdapter {

    private Bundle stepsBundle;

    public CustomPagerAdapter(FragmentManager fm, Bundle bundle) {
        super(fm);
        stepsBundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        return DetailsFragment.newInstance(stepsBundle.<Step>getParcelableArrayList("Steps"),position);
    }

    @Override
    public int getCount() {
        ArrayList<Step> mSteps = stepsBundle.getParcelableArrayList("Steps");
        if(mSteps!=null)
            return mSteps.size();
        else
            return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}
