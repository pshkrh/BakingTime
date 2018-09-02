package com.pshkrh.bakingtime.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.pshkrh.bakingtime.Fragment.DetailsFragment;
import com.pshkrh.bakingtime.Model.Ingredient;
import com.pshkrh.bakingtime.Model.Recipe;
import com.pshkrh.bakingtime.Model.Step;
import com.pshkrh.bakingtime.R;
import com.pshkrh.bakingtime.Fragment.RecipeFragment;
import com.pshkrh.bakingtime.Widget.UpdateWidgetService;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    public static final String TAG = "RecipeActivity";
    private static final String FRAGMENT_POSITION = "FragmentPosition";
    private static final String STEPS = "Steps";
    private static final String INGREDIENTS = "Ingredients";
    private static final String ING_STEP_BUNDLE = "IngredientStepBundle";
    private static final String ING_BUNDLE = "IngredientBundle";
    private static final String TWO_PANE = "TwoPane";
    private static final String VIDEO_URL = "videoUrl";
    private static final String THUMBNAIL_URL = "thumbnaiUrl";
    private static final String DESCRIPTION = "description";

    public ArrayList<Ingredient> mIngredients = new ArrayList<>();
    public ArrayList<Step> mSteps = new ArrayList<>();

    public static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState==null){
            RecipeFragment recipeFragment = new RecipeFragment();
            Bundle bundle = getIntent().getBundleExtra(ING_STEP_BUNDLE);
            bundle.putBoolean(TWO_PANE,mTwoPane);
            recipeFragment.setArguments(bundle);
            mIngredients = bundle.getParcelableArrayList(INGREDIENTS);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_container,recipeFragment)
                    .commit();
        }

        if(findViewById(R.id.details_container)!=null){
            mTwoPane = true;
            if(savedInstanceState==null) {
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(createDetailsBundle());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.details_container, detailsFragment)
                        .commit();
            }
            Log.d(TAG,"Two Pane is True (RecipeActivity)");

        } else{
            mTwoPane = false;
        }

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

    public void startWidgetService()
    {
        Intent intent = new Intent(RecipeActivity.this, UpdateWidgetService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(INGREDIENTS, mIngredients);
        intent.putExtra(ING_BUNDLE,bundle);
        intent.setAction(UpdateWidgetService.WIDGET_UPDATE_ACTION);
        startService(intent);
    }

    public Bundle createDetailsBundle(){
        Bundle ingStepBundle = getIntent().getBundleExtra(ING_STEP_BUNDLE);
        mSteps = ingStepBundle.getParcelableArrayList(STEPS);
        int pos = 0;
        Bundle detailsBundle = new Bundle();
        detailsBundle.putString(VIDEO_URL,mSteps.get(pos).getVideoUrl());
        detailsBundle.putString(THUMBNAIL_URL,mSteps.get(pos).getThumbnailUrl());
        detailsBundle.putString(DESCRIPTION,mSteps.get(pos).getDesc());
        return detailsBundle;
    }

}
