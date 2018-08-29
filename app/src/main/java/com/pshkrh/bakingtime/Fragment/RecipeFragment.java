package com.pshkrh.bakingtime.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pshkrh.bakingtime.Activity.DetailsActivity;
import com.pshkrh.bakingtime.Activity.MainActivity;
import com.pshkrh.bakingtime.Activity.RecipeActivity;
import com.pshkrh.bakingtime.Adapter.IngredientListAdapter;
import com.pshkrh.bakingtime.Adapter.StepListAdapter;
import com.pshkrh.bakingtime.Model.Ingredient;
import com.pshkrh.bakingtime.Model.Recipe;
import com.pshkrh.bakingtime.Model.Step;
import com.pshkrh.bakingtime.R;
import com.pshkrh.bakingtime.Widget.UpdateWidgetService;

import java.util.ArrayList;

public class RecipeFragment extends Fragment implements StepListAdapter.OnStepClickListener{

    public static final String TAG = "RecipeFragment";
    private ArrayList<Ingredient> mIngredients = new ArrayList<>();
    private ArrayList<Step> mSteps = new ArrayList<>();

    public RecipeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipes,container,false);
        try{
            Bundle bundle = this.getArguments();

            mIngredients = bundle.getParcelableArrayList("Ingredients");
            mSteps = bundle.getParcelableArrayList("Steps");

            // RecyclerView setup

            RecyclerView stepsRecyclerView = rootView.findViewById(R.id.steps_recycler);


            StepListAdapter stepListAdapter = new StepListAdapter(mSteps,this);

            stepsRecyclerView.setAdapter(stepListAdapter);
            stepsRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            stepsRecyclerView.addItemDecoration(itemDecoration);

            RecyclerView ingredientsRecyclerView = rootView.findViewById(R.id.ingredients_recycler);
            IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(mIngredients);
            ingredientsRecyclerView.setAdapter(ingredientListAdapter);
            ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            ingredientsRecyclerView.addItemDecoration(itemDecoration);
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStepSelected(View view, int position) {
        //Toast.makeText(getActivity(), "Position = " + position, Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Steps",mSteps);
        bundle.putInt("Position",position);
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
