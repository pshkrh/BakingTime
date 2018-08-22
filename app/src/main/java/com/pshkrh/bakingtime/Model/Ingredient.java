package com.pshkrh.bakingtime.Model;

import android.graphics.Movie;
import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    public int quantity;
    public String measure, ingredient;

    public Ingredient(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public Ingredient(Parcel p) {
        quantity = p.readInt();
        measure = p.readString();
        ingredient = p.readString();
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>(){
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
