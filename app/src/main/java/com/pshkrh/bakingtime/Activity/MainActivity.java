package com.pshkrh.bakingtime.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.pshkrh.bakingtime.Adapter.RecipeAdapter;
import com.pshkrh.bakingtime.Model.Ingredient;
import com.pshkrh.bakingtime.Model.Recipe;
import com.pshkrh.bakingtime.Model.Step;
import com.pshkrh.bakingtime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    public static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public static final String TAG = "MainActivity";

    public ArrayList<Recipe> mRecipes = new ArrayList<>();
    public ArrayList<Ingredient> mIngredients = new ArrayList<>();
    public ArrayList<Step> mSteps = new ArrayList<>();

    public RecyclerView recyclerView;
    public CoordinatorLayout mCoordinatorLayout;

    public ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCoordinatorLayout = findViewById(R.id.main_layout);
        mProgressBar = findViewById(R.id.progress_bar);

        loadRecipes(RECIPE_URL);

        recyclerView = findViewById(R.id.main_recycler);
        RecipeAdapter recipeAdapter = new RecipeAdapter(mRecipes);
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }

    public void loadRecipes(String url){

        ConnectivityManager cm =
                (ConnectivityManager)MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        mProgressBar.setVisibility(View.VISIBLE);

        if(isConnected){

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    Log.d(TAG,"JSON Array = " + response.toString());
                    mProgressBar.setVisibility(View.GONE);
                    try{
                        for(int i=0;i<response.length();i++){
                            JSONObject recipejson = response.getJSONObject(i);
                            int recipeId = recipejson.getInt("id");
                            String recipeName = recipejson.getString("name");
                            JSONArray ingredientsList = recipejson.getJSONArray("ingredients");
                            JSONArray stepsList = recipejson.getJSONArray("steps");

                            mIngredients = new ArrayList<>();
                            for(int j=0;j<ingredientsList.length();j++){
                                JSONObject ingredientJson = ingredientsList.getJSONObject(j);
                                int quantity = ingredientJson.getInt("quantity");
                                String measure = ingredientJson.getString("measure");
                                String ingredientName = ingredientJson.getString("ingredient");
                                Log.d(TAG,"Ingredients of item " + String.valueOf(j) + " = " + String.valueOf(quantity) + measure + ingredientName);
                                Ingredient ingredient = new Ingredient(quantity,measure,ingredientName);
                                mIngredients.add(ingredient);
                            }

                            mSteps = new ArrayList<>();
                            for(int j=0;j<stepsList.length();j++){
                                JSONObject stepsJson = stepsList.getJSONObject(j);
                                int stepId = stepsJson.getInt("id");
                                String shortDesc = stepsJson.getString("shortDescription");
                                String desc = stepsJson.getString("description");
                                String videoUrl = stepsJson.getString("videoURL");
                                String thumbnailUrl = stepsJson.getString("thumbnailURL");
                                Step step = new Step(stepId,shortDesc,desc,videoUrl,thumbnailUrl);
                                mSteps.add(step);
                            }


                            Recipe recipe = new Recipe(recipeId,recipeName,mIngredients,mSteps);
                            mRecipes.add(recipe);
                        }

                        RecipeAdapter recipeAdapter = new RecipeAdapter(mRecipes);
                        recyclerView.setAdapter(recipeAdapter);
                        recipeAdapter.notifyDataSetChanged();

                    }
                    catch(JSONException je){
                        je.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                    mProgressBar.setVisibility(View.GONE);
                    Log.d(TAG, "Request fail! Status code: " + statusCode);
                    Log.d(TAG, "Fail response: " + response);
                    Log.e(TAG, e.toString());
                    Snackbar.make(mCoordinatorLayout, "Something went wrong, please try again!", Snackbar.LENGTH_SHORT).show();
                }
            });

        }
        else{
            mProgressBar.setVisibility(View.GONE);
            Snackbar.make(mCoordinatorLayout, "Internet Connection Required!", Snackbar.LENGTH_SHORT).show();
        }

    }


}
