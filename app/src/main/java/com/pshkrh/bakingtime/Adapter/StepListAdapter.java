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

    public interface OnStepClickListener {
        void onStepSelected(View view, int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameTextView;
        private OnStepClickListener mStepClickListener;

        public ViewHolder(View itemView, OnStepClickListener stepClickListener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.ingredient_step_name);
            mStepClickListener = stepClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mStepClickListener.onStepSelected(view,getAdapterPosition());
        }
    }

    private ArrayList<Step> mSteps;
    private OnStepClickListener mStepClickListener;

    public StepListAdapter(ArrayList<Step> steps,OnStepClickListener listener) {
        mSteps = steps;
        mStepClickListener = listener;
    }

    @Override
    public StepListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View stepView = inflater.inflate(R.layout.ingredient_step_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(stepView,mStepClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepListAdapter.ViewHolder viewHolder, int position) {
        Step step = mSteps.get(position);

        TextView textView = viewHolder.nameTextView;
        textView.setText(step.getShortDesc());
    }

    @Override
    public int getItemCount() {
        if(mSteps!=null)
            return mSteps.size();
        else return 0;
    }

}
