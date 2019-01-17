package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject   jsonObj     = new JSONObject(json);
            List<String> alsoKnownAs = new ArrayList<>();
            List<String> ingredients = new ArrayList<>();
            for (int i=0;i<jsonObj.getJSONArray("alsoKnownAs").length();i++){
                alsoKnownAs.add(jsonObj.getJSONArray("alsoKnownAs").getString(i));
            }
            for (int i=0;i<jsonObj.getJSONArray("ingredients").length();i++){
                ingredients.add(jsonObj.getJSONArray("ingredients").getString(i));
            }
            sandwich = new Sandwich(jsonObj.getString("mainName"),alsoKnownAs,jsonObj.getString("placeOfOrigin"),
                    jsonObj.getString("description"),jsonObj.getString("image"),ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
