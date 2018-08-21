package com.pshkrh.bakingtime.Adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pshkrh.bakingtime.Model.Recipe;
import com.pshkrh.bakingtime.R;

import java.util.List;

public class RecipeAdapter extends
        RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Snackbar.make(itemView,"Intent Goes Here!",Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private List<Recipe> mRecipes;

    public RecipeAdapter(List<Recipe> recipes) {
        mRecipes = recipes;
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View recipeView = inflater.inflate(R.layout.recipe_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(recipeView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Recipe recipe = mRecipes.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(recipe.getName());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        if(mRecipes!=null)
            return mRecipes.size();
        else return 0;
    }


}