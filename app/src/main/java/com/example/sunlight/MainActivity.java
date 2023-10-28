package com.example.sunlight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class MainActivity extends Activity {
    public static String LOGIN_MESSAGE = "mainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

    }

    public void sign_up(View view) {
        Intent intent = new Intent(this, AfterLogIn.class);
        EditText plaintext = (EditText) findViewById(R.id.userName);
        String message = plaintext.getText().toString();
        intent.putExtra(LOGIN_MESSAGE, message);
        startActivity(intent);
    }
}
