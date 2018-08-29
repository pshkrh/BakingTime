package com.pshkrh.bakingtime.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.pshkrh.bakingtime.Model.Ingredient;
import com.pshkrh.bakingtime.Model.Recipe;
import com.pshkrh.bakingtime.R;
import com.pshkrh.bakingtime.Fragment.RecipeFragment;
import com.pshkrh.bakingtime.Widget.UpdateWidgetService;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    public static final String TAG = "RecipeActivity";
    public ArrayList<Ingredient> mIngredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setArguments(getIntent().getBundleExtra("IngredientStepBundle"));
        mIngredients = getIntent().getBundleExtra("IngredientStepBundle").getParcelableArrayList("Ingredients");

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipe_container,recipeFragment)
                .commit();

        startWidgetService();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void startWidgetService()
    {
        Intent intent = new Intent(RecipeActivity.this, UpdateWidgetService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Ingredients", mIngredients);
        intent.putExtra("IngredientBundle",bundle);
        intent.setAction(UpdateWidgetService.WIDGET_UPDATE_ACTION);
        startService(intent);
    }
}
