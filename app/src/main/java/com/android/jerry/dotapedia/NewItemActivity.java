package com.android.jerry.dotapedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class NewItemActivity extends AppCompatActivity implements Serializable {

    // views in layout
    private TextView mNewNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        mNewNameText = findViewById(R.id.newNameText);

        final Button saveButton = findViewById(R.id.newSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                // check if it has enough data to save
                if (TextUtils.isEmpty(mNewNameText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Data good to go, create a Item object and send it over
                    String name = mNewNameText.getText().toString();
                    replyIntent.putExtra("itemName", name);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
