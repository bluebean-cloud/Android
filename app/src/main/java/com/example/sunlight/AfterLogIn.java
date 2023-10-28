package com.example.sunlight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AfterLogIn extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_log_in);

        Intent intent = getIntent();
        TextView text = findViewById(R.id.show);
        String message = "欢迎你，" + intent.getStringExtra(MainActivity.LOGIN_MESSAGE);
        text.setText(message);
    }
}