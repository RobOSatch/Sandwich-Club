package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();

        try {
            JSONObject sandwichData = new JSONObject(json);

            // Extract sandwich details
            String mainName = sandwichData.getJSONObject("name").getString("mainName");
            JSONArray tempAlsoKnownAs = sandwichData.getJSONObject("name").getJSONArray("alsoKnownAs");
            String placeOfOrigin = sandwichData.getString("placeOfOrigin");
            String description = sandwichData.getString("description");
            String image = sandwichData.getString("image");
            JSONArray tempIngredients = sandwichData.getJSONArray("ingredients");

            // Convert JSONArrays to lists
            List<String> alsoKnownAs = new ArrayList<>();
            List<String> ingredients = new ArrayList<>();

            for (int i = 0; i < tempAlsoKnownAs.length(); i++) {
                alsoKnownAs.add(tempAlsoKnownAs.getString(i));
            }

            for (int i = 0; i < tempIngredients.length(); i++) {
                ingredients.add(tempIngredients.getString(i));
            }

            // Build model object
            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException ex) {
            Log.e(JsonUtils.class.toString(), "Unexpexted exception: " + ex.getMessage());
            ex.printStackTrace();
        }

        return sandwich;
    }
}
