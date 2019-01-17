package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        JSONObject nameJson;
        JSONArray  alsoKnownAsJson,ingredientsJson;
        try {
            JSONObject   jsonObj     = new JSONObject(json);
            nameJson =jsonObj.getJSONObject("name");
            List<String> alsoKnownAs = new ArrayList<>();
            List<String> ingredients = new ArrayList<>();
            alsoKnownAsJson =nameJson.getJSONArray("alsoKnownAs");
            ingredientsJson=jsonObj.getJSONArray("ingredients");
            if(alsoKnownAsJson!=null){
                for (int i=0;i<alsoKnownAsJson.length();i++){
                    alsoKnownAs.add(alsoKnownAsJson.getString(i));
                }
            }
            if(ingredientsJson!=null){
                for (int i=0;i<ingredientsJson.length();i++){
                    ingredients.add(ingredientsJson.getString(i));
                }
            }
            sandwich = new Sandwich(nameJson.getString("mainName"),alsoKnownAs,jsonObj.getString("placeOfOrigin"),
                    jsonObj.getString("description"),jsonObj.getString("image"),ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
