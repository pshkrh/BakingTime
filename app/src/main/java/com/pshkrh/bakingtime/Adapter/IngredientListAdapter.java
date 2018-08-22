package com.pshkrh.bakingtime.Adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.pshkrh.bakingtime.Model.Ingredient;
import com.pshkrh.bakingtime.R;

import java.util.ArrayList;

public class IngredientListAdapter extends
        RecyclerView.Adapter<IngredientListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public FrameLayout mFrameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.ingredient_step_name);
            mFrameLayout = itemView.findViewById(R.id.ingredient_step_item_layout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Snackbar.make(view,"Intent Goes Here!",Snackbar.LENGTH_SHORT);
        }
    }

    private ArrayList<Ingredient> mIngredients;

    public IngredientListAdapter(ArrayList<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    @Override
    public IngredientListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View IngredientView = inflater.inflate(R.layout.ingredient_step_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(IngredientView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(IngredientListAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Ingredient ingredient = mIngredients.get(position);

        // Set the background of the root layout to nothing
        FrameLayout frameLayout = viewHolder.mFrameLayout;
        frameLayout.setBackgroundResource(0);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        String str = ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient();
        textView.setText(str);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        if(mIngredients!=null)
            return mIngredients.size();
        else return 0;
    }

}
