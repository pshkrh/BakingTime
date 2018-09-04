package com.pshkrh.bakingtime;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.pshkrh.bakingtime.Activity.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class BakingTimeBasicTests {

    private int stepsRand,recipeRand;
    private IdlingResource mIdlingResource;

    @Rule public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    // This constructor will generate a random number between 0 and 3 to choose a recipe
    public BakingTimeBasicTests(){
        Random random = new Random();
        recipeRand = random.nextInt(3);
        stepsRand = random.nextInt(5);
    }

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();

        // Register the Idling Resource
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    // This test checks if the RecyclerView in MainActivity is clickable
    @Test
    public void MainActivityRecyclerTest(){
        // Wait for 2 seconds to ensure the RecyclerView has loaded completely
        // and is capable of handling click events

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.main_recycler))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(recipeRand,click()));
    }

    // This test checks if the entire Ingredients List has been loaded and if the last item is clickable

    @Test
    public void RecipeActivityRecyclerTest(){
        MainActivityRecyclerTest();
        onView(withId(R.id.steps_recycler))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(stepsRand,click()));
    }

    // This test checks if the Step Description TextView in DetailsActivity is clickable

    @Test
    public void DetailsActivityDescriptionText(){
        MainActivityRecyclerTest();
        onView(withId(R.id.steps_recycler))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(stepsRand,click()));
        onView(withId(R.id.step_description)).perform(click());
    }


    @After
    public void unregisterIdlingResource(){
        if(mIdlingResource!=null){

            // Unregister the Idling Resource
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
