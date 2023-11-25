package com.example.success;

import java.io.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.net.URLEncoder;

public class CalorieDetection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_detection);

    }

    public void dish(View view) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/dish";
        try {
            // 本地文件路径
            String filePath = "hamburger.png";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam + "&top_num=" + 5;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.dd72145ec83b679e7aded83a92a1f3d9.2592000.1703039681.282335-43106670";

            String result = HttpUtil.post(url, accessToken, param);
            Log.d("CalorieResult", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAccessTokenInBackground() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String accessToken = GetAccessToken();
                    // 处理获取到的accessToken，例如打印或更新UI
                    Log.d("AccessToken", accessToken);
                } catch (IOException e) {
                    // 处理异常，例如打印错误日志或更新UI
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String GetAccessToken() throws IOException {

        final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token?client_credentials&client_id=UQt4g6fauZR2a0a4TzAKT6va&client_secret=2x68Dm3k2m68sQYEPEcqsm00iC94Lb2g&grant_type=client_credentials")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return response.body().string();
    }


}
