package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ActivityDetailBinding activityDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Log.i(DetailActivity.class.getCanonicalName(), "Populating the UI");

        String placeOfOrigin = setNAIfEmpty(sandwich.getPlaceOfOrigin());
        activityDetailBinding.originTv.setText(placeOfOrigin);

        String description = setNAIfEmpty(sandwich.getDescription());
        activityDetailBinding.descriptionTv.setText(description);

        String alsoKnownAs = setNAIfEmpty(convertListToStr(sandwich.getAlsoKnownAs()));
        activityDetailBinding.alsoKnownTv.setText(alsoKnownAs);

        String ingredients = setNAIfEmpty(convertListToStr(sandwich.getIngredients()));
        activityDetailBinding.ingredientsTv.setText(ingredients);
    }

    private String convertListToStr(List<String> list){
        StringBuilder result = new StringBuilder();
        for (String line: list){
            result.append("• ").append(line).append("\n");
        }
        return result.toString();
    }

    private String setNAIfEmpty(String str){
        if (TextUtils.isEmpty(str)){
            return "N/A";
        }else {
            return str;
        }
    }
}
