package com.pshkrh.bakingtime.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.pshkrh.bakingtime.Model.Ingredient;

import java.util.ArrayList;

public class UpdateWidgetService extends IntentService
{
    public static final String WIDGET_UPDATE_ACTION = "com.pshkrh.bakingtime.update_widget_service";
    private ArrayList<Ingredient> mIngredients;

    public UpdateWidgetService()
    {
        super("UpdateWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        if (intent != null && intent.getAction().equals(WIDGET_UPDATE_ACTION))
        {
            Bundle bundle = intent.getBundleExtra("IngredientBundle");
            ArrayList<Ingredient> ingredients = bundle.getParcelableArrayList("Ingredients");
            if(ingredients!=null){
                mIngredients = ingredients;
            }

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
            IngredientWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetIds,mIngredients);
        }
    }
}