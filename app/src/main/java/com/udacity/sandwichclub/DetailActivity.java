package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        mSandwich = sandwich;
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
        TextView originTextView = findViewById(R.id.origin_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        TextView alsoKnownTextView = findViewById(R.id.also_known_tv);

        originTextView.setText(mSandwich.getPlaceOfOrigin());
        descriptionTextView.setText(mSandwich.getDescription());

        for (String s : mSandwich.getIngredients()) {
            if (s.equals(mSandwich.getIngredients().get(mSandwich.getIngredients().size() - 1))) {
                ingredientsTextView.append(s);
            } else {
                ingredientsTextView.append(s + "\n");
            }
        }

        for (String s : mSandwich.getAlsoKnownAs()) {
            if (s.equals(mSandwich.getAlsoKnownAs().get(mSandwich.getAlsoKnownAs().size() - 1))) {
                alsoKnownTextView.append(s);
            } else {
                alsoKnownTextView.append(s + "\n");
            }
        }

        if (alsoKnownTextView.getText().equals("")) {
            alsoKnownTextView.setText("None");
        }
    }
}
