package com.pshkrh.bakingtime.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    private int id;
    private String name;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mSteps;

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps) {
        this.id = id;
        this.name = name;
        mIngredients = ingredients;
        mSteps = steps;
    }

    @SuppressWarnings("unchecked")
    public Recipe(Parcel p) {
        id = p.readInt();
        name = p.readString();
        mIngredients = p.readArrayList(Ingredient.class.getClassLoader());
        mSteps = p.readArrayList(Step.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return mIngredients;
    }

    public ArrayList<Step> getSteps() {
        return mSteps;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeList(mIngredients);
        dest.writeList(mSteps);
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>(){
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
