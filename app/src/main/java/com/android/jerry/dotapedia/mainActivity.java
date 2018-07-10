package com.android.jerry.dotapedia;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class mainActivity extends AppCompatActivity implements Serializable {

    public static final int NEW_ITEM_ACTIVITY_REQUEST_CODE = 1;

    // Connect the data between UI and ViewModel
    private DotapediaViewModel mDotapediaViewModel;

    // Related views
    private RecyclerView recyclerView, recyclerViewHero;
    private LinearLayout logoView;
    private FrameLayout searchLayout;
    private EditText searchTextField;
    private TextView spacer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get views
        logoView = findViewById(R.id.logoView);
        recyclerView = findViewById(R.id.recyclerViewItem);
        recyclerViewHero = findViewById(R.id.recyclerViewHero);
        searchLayout = findViewById(R.id.searchLayout);
        searchTextField = findViewById(R.id.searchTextField);
        spacer = findViewById(R.id.recyclerSpacer);

        // Bottom Navigator
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_main_title);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_main_title:
                        spacer.setBackgroundColor(Color.TRANSPARENT);
                        logoView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        recyclerViewHero.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Welcome to DOTAPEDIA !", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation_item:
                        spacer.setBackgroundResource(R.drawable.gradient_background_list);
                        logoView.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerViewHero.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Ordered by item price", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation_hero:
                        spacer.setBackgroundResource(R.drawable.gradient_background_list);
                        logoView.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        recyclerViewHero.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Ordered by hero name", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

        // Add RecyclerView of items
        final ItemListAdapter itemListAdapter = new ItemListAdapter(this);
        recyclerView.setAdapter(itemListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.INVISIBLE);

        // Add RecyclerView of heroes
        final HeroListAdapter heroListAdapter = new HeroListAdapter(this);
        recyclerViewHero.setAdapter(heroListAdapter);
        recyclerViewHero.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHero.setVisibility(View.INVISIBLE);

        // ViewModelProviders linking process
        mDotapediaViewModel = ViewModelProviders.of(this).get(DotapediaViewModel.class);
        mDotapediaViewModel.getLiveItemFeed().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                // Update the cached copy of the items in the itemListAdapter
                itemListAdapter.setItems(items);
            }
        });
        mDotapediaViewModel.getLiveHeroFeed().observe(this, new Observer<List<Hero>>() {
            @Override
            public void onChanged(@Nullable List<Hero> heroes) {
                // Update the cached copy of the heroes in the heroListAdapter
                heroListAdapter.setHeroes(heroes);
            }
        });

        // Toggle search field
        final FloatingActionButton toggleSearchButton = findViewById(R.id.toggleSearchButton);
        toggleSearchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (searchLayout.getVisibility() == View.GONE) {
                    spacer.setVisibility(View.VISIBLE);
                    searchLayout.setVisibility(View.VISIBLE);
                    searchLayout.animate().setDuration(200).alpha(1.0f);
                } else {
                    // Hide search bar and reset to show-all
                    spacer.setVisibility(View.GONE);
                    searchLayout.animate().setDuration(200).alpha(0.0f);
                    searchLayout.setVisibility(View.GONE);
                    searchTextField.setText("");
                    mDotapediaViewModel.searchThis("");
                    InputMethodManager inputManager = (InputMethodManager) searchTextField.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputManager != null) {
                        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });

        // User search functionality
        searchTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    // Reset to show-all
                    mDotapediaViewModel.searchThis("");
                } else {
                    // Search user input string
                    mDotapediaViewModel.searchThis(editable.toString());
                }
            }
        });
    }

    // TODO in development. Receiving Data from NewItemActivity
    // usage:
    // Intent intent = new Intent(mainActivity.this, NewItemActivity.class);
    // startActivityForResult(intent, NEW_ITEM_ACTIVITY_REQUEST_CODE);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Item item = new Item(data.getStringExtra("itemName"));
            mDotapediaViewModel.insert(item);
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }
}
