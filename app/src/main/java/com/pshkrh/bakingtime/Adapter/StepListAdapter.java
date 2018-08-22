package com.pshkrh.bakingtime.Adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pshkrh.bakingtime.Model.Step;
import com.pshkrh.bakingtime.R;

import java.util.ArrayList;

public class StepListAdapter extends
        RecyclerView.Adapter<StepListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.ingredient_step_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Snackbar.make(view,"Intent Goes Here!",Snackbar.LENGTH_SHORT);
        }
    }

    private ArrayList<Step> mSteps;

    public StepListAdapter(ArrayList<Step> steps) {
        mSteps = steps;
    }

    @Override
    public StepListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View stepView = inflater.inflate(R.layout.ingredient_step_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(stepView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(StepListAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Step step = mSteps.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(step.getShortDesc());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        if(mSteps!=null)
            return mSteps.size();
        else return 0;
    }

}
