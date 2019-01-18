package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";

    private static final int DEFAULT_POSITION = -1;

    ImageView ingredientsIv;

    TextView txtOrigin, txtDescription, txtKnownAs;

    Sandwich sandwich;

    ListView ingredientListView;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        txtOrigin = findViewById(R.id.origin_tv);
        txtDescription = findViewById(R.id.description_tv);
        txtKnownAs = findViewById(R.id.also_known_tv);
        ingredientListView = findViewById(R.id.ingredients_tv);
        container = findViewById(R.id.container);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        if(sandwich.getPlaceOfOrigin().isEmpty())
            txtOrigin.setText(getString(R.string.no_data));
        else txtOrigin.setText(sandwich.getPlaceOfOrigin());

        if(sandwich.getDescription().isEmpty())
            txtDescription.setText(getString(R.string.no_data));
        else txtDescription.setText(sandwich.getDescription());

        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        StringBuilder stringBuilder = new StringBuilder();
        if (alsoKnownList != null && !alsoKnownList.isEmpty()) {
            for (int i = 0; i < alsoKnownList.size(); i++) {
                stringBuilder.append(sandwich.getAlsoKnownAs().get(i));
                stringBuilder.append(", ");
            }
            txtKnownAs.setText(stringBuilder);
        } else txtKnownAs.setText(getString(R.string.no_data));
        if (sandwich.getIngredients() != null) {
        List<String> ingredientList = sandwich.getIngredients();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    ingredientList);

            ingredientListView.setAdapter(arrayAdapter);
        }

    }
}
