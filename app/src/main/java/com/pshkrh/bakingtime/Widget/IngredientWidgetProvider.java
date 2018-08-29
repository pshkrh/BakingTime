package com.pshkrh.bakingtime.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.pshkrh.bakingtime.Model.Ingredient;
import com.pshkrh.bakingtime.R;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider
{
    public static ArrayList<Ingredient> mIngredients;

    public IngredientWidgetProvider() {}

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetIds[], ArrayList<Ingredient> ingredients) {
        mIngredients = ingredients;
        for (int appWidgetId : appWidgetIds)
        {
            Intent intent = new Intent(context, ListViewService.class);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
            views.setRemoteAdapter(R.id.list_view_widget, intent);
            ComponentName component = new ComponentName(context, IngredientWidgetProvider.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.list_view_widget);
            appWidgetManager.updateAppWidget(component, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {}

    @Override
    public void onDisabled(Context context) {}


}
