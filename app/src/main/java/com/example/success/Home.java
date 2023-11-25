package com.example.success;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Home extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature((Window.FEATURE_NO_TITLE));
        setContentView(R.layout.activity_home);

        //自动登录逻辑
        if (getIntent().getAction() != null && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            CurrentUser user = CurrentUser.getUserInfo(this);
            if (user != null) {
                Intent startSignUp = new Intent(Home.this, Sign_up.class);
                startSignUp.putExtra("userName", user.getName());
                startSignUp.putExtra("password", user.getPassword());
                startActivity(startSignUp);
            }
        }

        Button signIn = (Button) findViewById(R.id.signInButton);
        signIn.getBackground().setAlpha(65);
        Button signUp = (Button) findViewById(R.id.signUpButton);
        signUp.getBackground().setAlpha(65);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startSignIn = new Intent(Home.this,Sign_In.class);
                startActivity(startSignIn);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startSignUp = new Intent(Home.this,Sign_up.class);
                startActivity(startSignUp);
            }
        });
    }

}
