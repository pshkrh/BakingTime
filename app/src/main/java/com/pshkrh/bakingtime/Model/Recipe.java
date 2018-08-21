package com.pshkrh.bakingtime.Model;

import java.util.ArrayList;

public class Recipe {
    int id;
    String name;
    ArrayList<Ingredient> mIngredients;
    ArrayList<Step> mSteps;

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps) {
        this.id = id;
        this.name = name;
        mIngredients = ingredients;
        mSteps = steps;
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
}
