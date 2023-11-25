package com.example.success;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.example.success.entity.User;

public class CurrentUser {

    private static User currentUser;

    private final String name;
    private final String password;

    public CurrentUser(String name, String password){
        this.name = name;
        this.password = password;
    }

    public static User getUser() {
        return currentUser;
    }

    // 用户是否已经登录
    public static boolean userLoggedIn() {
        return currentUser != null;
    }

    // 登录账号
    public static void logInUser(User user) {
        currentUser = user;
    }

    public static void saveUserInfo(Context context, String userName, String userPassWord) {
        SharedPreferences userInfo = context.getSharedPreferences("autologin", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = userInfo.edit();
        edit.putString("userName", userName);
        edit.putString("passWord", userPassWord);
        edit.putBoolean("autologin", true);
        edit.apply();
    }

    public static void removeUserInfo(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("autologin", Context.MODE_PRIVATE).edit();
        edit.clear();
        edit.apply();
    }


    @Nullable
    public static CurrentUser getUserInfo(Context context) {
        SharedPreferences userInfo = context.getSharedPreferences("autologin", Context.MODE_PRIVATE);
        String userName = userInfo.getString("userName", null);
        String passWord = userInfo.getString("passWord", null);
        if (userName == null || userName.equals("") || passWord == null || passWord.equals("") || !userInfo.getBoolean("autologin", false)) {
            return null;
        }
        return new CurrentUser(userName, passWord);
    }


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
