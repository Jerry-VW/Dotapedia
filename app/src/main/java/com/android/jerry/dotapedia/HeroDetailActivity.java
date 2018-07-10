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

public class HeroDetailActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get views
        final TextView mName = findViewById(R.id.detailHeroName);
        ImageView mIcon = findViewById(R.id.detailHeroIconImage);
        TextView mCategory = findViewById(R.id.detailHeroCategory);
        TextView mBiography = findViewById(R.id.detailHeroBiography);

        // Set contents from intent
        Hero hero = (Hero) getIntent().getSerializableExtra("hero_to_show");
        mName.setText(hero.getName());
        mIcon.setImageResource(getResources().getIdentifier(hero.getIconPath(), "drawable", getPackageName()));
        mCategory.setText(hero.getCategory());
        mBiography.setText(hero.getBiography());

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
