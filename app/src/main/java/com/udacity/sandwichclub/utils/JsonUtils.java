package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME_KEY = "name";
    private static final String MAIN_NAME_KEY = "mainName";
    private static final String ALSO_KNOWN_AS_KEY = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    private static final String DESCRIPTION_KEY = "description";
    private static final String IMAGE_KEY = "image";
    private static final String INGREDIENTS_KEY = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;

        try {
            JSONObject sandwichJson = new JSONObject(json);
            sandwich = new Sandwich();

            // Setting mainName
            sandwich.setMainName(sandwichJson.getJSONObject(NAME_KEY).getString(MAIN_NAME_KEY));

            // Setting alsoKnownAs
            JSONArray alsoKnownJson = sandwichJson.getJSONObject(NAME_KEY).getJSONArray(ALSO_KNOWN_AS_KEY);
            List<String> alsoKnownAs = buildListForJson(alsoKnownJson);
            sandwich.setAlsoKnownAs(alsoKnownAs);

            // Setting placeOfOrigin
            sandwich.setPlaceOfOrigin(sandwichJson.getString(PLACE_OF_ORIGIN_KEY));

            // Setting description
            sandwich.setDescription(sandwichJson.getString(DESCRIPTION_KEY));

            // Setting image
            sandwich.setImage(sandwichJson.getString(IMAGE_KEY));

            // Setting ingredients
            JSONArray ingredientsJson = sandwichJson.getJSONArray(INGREDIENTS_KEY);
            List<String> ingredients = buildListForJson(ingredientsJson);
            sandwich.setIngredients(ingredients);
        } catch (JSONException e) {
            Log.e(JsonUtils.class.getCanonicalName(), e.getMessage());
        }

        return sandwich;
    }


    private static List<String> buildListForJson(JSONArray alsoKnownJsonArray) throws JSONException {
        List<String> alsoKnownAsList = new ArrayList<>();
        for (int i=0; i<alsoKnownJsonArray.length(); i++){
            alsoKnownAsList.add(alsoKnownJsonArray.get(i).toString());
        }
        return alsoKnownAsList;
    }
}
