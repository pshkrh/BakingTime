package com.pshkrh.bakingtime.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.pshkrh.bakingtime.Model.Ingredient;
import com.pshkrh.bakingtime.R;

import java.util.ArrayList;

public class ListViewService extends RemoteViewsService {

    public ListViewsFactory onGetViewFactory(Intent intent) {

        return new ListViewsFactory(this.getApplicationContext());
    }
}

class ListViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<Ingredient> mIngredients;

    public ListViewsFactory(Context mContext)
    {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {}

    @Override
    public void onDataSetChanged() {
        mIngredients = IngredientWidgetProvider.mIngredients;
    }

    @Override
    public void onDestroy() {}

    @Override
    public int getCount() {
        if (mIngredients==null)
            return 0;
        else return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.recipe_widget_text, mIngredients.get(position).getIngredient());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}