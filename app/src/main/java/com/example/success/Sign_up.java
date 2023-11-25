package com.example.success;

import static android.content.ContentValues.TAG;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Sign_up extends Activity {
    CheckBox autologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseInterface db = new DatabaseInterface(this);
        setContentView(R.layout.activity_sign_up);
        Intent intent = new Intent(Sign_up.this, MainActivity.class);

        //防止返回最后返回到登陆注册界面
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        //自动登录逻辑
        autologin = findViewById(R.id.check_autologin);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String userName = bundle.getString("userName", null);
            String password = bundle.getString("password", null);
            intent.putExtra("name", userName);
            intent.putExtra("passwd", password);
            int ret = db.userSignIn(userName, password);
            if (ret == 1) {
                startActivity(intent);
                CurrentUser.logInUser(db.getUserByName(userName));
                Toast.makeText(Sign_up.this, "登录成功", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
        }

        Button sureButton = (Button) findViewById(R.id.sureButton);
        sureButton.getBackground().setAlpha(65);
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name_text = (EditText) findViewById(R.id.input_name);
                EditText passwd_text = (EditText) findViewById(R.id.input_passwd);
                String name = name_text.getText().toString();
                String passwd = passwd_text.getText().toString();
                intent.putExtra("name", name);
                intent.putExtra("passwd", passwd);
                // 访问数据库，确认name与passwd有效
                int ret = db.userSignIn(name, passwd);
                if (ret == 1) {
                    startActivity(intent);
                    finish();
                    CurrentUser.logInUser(db.getUserByName(name));
                    if (autologin.isChecked()) {
                        CurrentUser.saveUserInfo(Sign_up.this, name, passwd);
                    }
                    else{
                        CurrentUser.removeUserInfo(Sign_up.this);
                    }
                    Toast.makeText(Sign_up.this, "登录成功", Toast.LENGTH_SHORT).show();
                } else if (ret == 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Sign_up.this);
                    dialog.setMessage("用户名不存在,请先注册");
                    CurrentUser.removeUserInfo(Sign_up.this);
                    dialog.show();
                    //Toast.makeText(Sign_up.this, "用户名不存在,请先注册", Toast.LENGTH_LONG).show();
                } else if (ret == 2) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Sign_up.this);
                    dialog.setMessage("密码错误");
                    CurrentUser.removeUserInfo(Sign_up.this);
                    dialog.show();
                    //Toast.makeText(Sign_up.this, "密码错误", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}