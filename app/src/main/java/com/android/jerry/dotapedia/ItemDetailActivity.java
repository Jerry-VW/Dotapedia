package com.android.jerry.dotapedia;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class ItemDetailActivity extends AppCompatActivity implements Serializable {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Views
        final TextView mName = findViewById(R.id.detailName);
        ImageView mIcon = findViewById(R.id.detailIconImage);
        TextView mPrice = findViewById(R.id.detailPrice);
        TextView mAttributes = findViewById(R.id.detailAttributes);
        TextView mEffects = findViewById(R.id.detailEffects);
        TextView mDescription = findViewById(R.id.detailDescription);

        Item item = (Item) getIntent().getSerializableExtra("item_to_show");
        mName.setText(item.getName());
        mIcon.setImageResource(getResources().getIdentifier(item.getIconPath(), "drawable", getPackageName()));
        mPrice.setText(String.valueOf(item.getPrice()));
        mAttributes.setText(item.getAttributes());
        mEffects.setText(item.getEffects());
        mDescription.setText(item.getDescription());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Detail of " + mName.getText(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
