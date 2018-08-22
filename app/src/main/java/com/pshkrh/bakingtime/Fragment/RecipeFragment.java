package com.pshkrh.bakingtime.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pshkrh.bakingtime.Adapter.IngredientListAdapter;
import com.pshkrh.bakingtime.Adapter.StepListAdapter;
import com.pshkrh.bakingtime.Model.Ingredient;
import com.pshkrh.bakingtime.Model.Recipe;
import com.pshkrh.bakingtime.Model.Step;
import com.pshkrh.bakingtime.R;

import java.util.ArrayList;

public class RecipeFragment extends Fragment {

    public static final String TAG = "RecipeFragment";

    public RecipeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipes,container,false);
        try{
            Bundle bundle = this.getArguments();

            ArrayList<Ingredient> ingredients = bundle.getParcelableArrayList("Ingredients");
            ArrayList<Step> steps = bundle.getParcelableArrayList("Steps");

            // RecyclerView setup

            RecyclerView stepsRecyclerView = rootView.findViewById(R.id.steps_recycler);
            StepListAdapter stepListAdapter = new StepListAdapter(steps);
            stepsRecyclerView.setAdapter(stepListAdapter);
            stepsRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            stepsRecyclerView.addItemDecoration(itemDecoration);

            RecyclerView ingredientsRecyclerView = rootView.findViewById(R.id.ingredients_recycler);
            IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(ingredients);
            ingredientsRecyclerView.setAdapter(ingredientListAdapter);
            ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            ingredientsRecyclerView.addItemDecoration(itemDecoration);
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return rootView;
    }
}
