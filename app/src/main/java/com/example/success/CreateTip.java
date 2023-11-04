package com.example.success;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class CreateTip extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tip);

        DatabaseInterface db = MainActivity.db;
        Button publish = findViewById(R.id.buttonPublish);
        publish.setOnClickListener(view -> {
            EditText editTitle = findViewById(R.id.editTextTitle);
            EditText editContent = findViewById(R.id.editTextContent);
            String title = editTitle.getText().toString();
            String content = editContent.getText().toString();
            db.createTip(title, content);
            finish();
        });
    }

}
