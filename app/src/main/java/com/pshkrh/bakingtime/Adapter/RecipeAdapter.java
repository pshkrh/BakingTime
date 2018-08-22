package com.pshkrh.bakingtime.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pshkrh.bakingtime.Model.Ingredient;
import com.pshkrh.bakingtime.Model.Recipe;
import com.pshkrh.bakingtime.Model.Step;
import com.pshkrh.bakingtime.R;
import com.pshkrh.bakingtime.Activity.RecipeActivity;

import java.util.ArrayList;

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

                Recipe recipe = mRecipes.get(position);
                ArrayList<Ingredient> ingredientArrayList = recipe.getIngredients();
                ArrayList<Step> stepArrayList = recipe.getSteps();

                Intent intent = new Intent(view.getContext(), RecipeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("Ingredients",ingredientArrayList);
                bundle.putParcelableArrayList("Steps",stepArrayList);
                //bundle.putInt("Position",position);
                intent.putExtra("IngredientStepBundle",bundle);
                view.getContext().startActivity(intent);
            }
        }
    }

    private ArrayList<Recipe> mRecipes;

    public RecipeAdapter(ArrayList<Recipe> recipes) {
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