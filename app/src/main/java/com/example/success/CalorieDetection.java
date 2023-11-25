package com.example.success;

import java.io.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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

    private ActivityResultLauncher<Intent> register;

    private byte[] imgByte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_detection);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result != null && result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri imgUri = result.getData().getData();
                if (imgUri != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imgUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        imgByte = stream.toByteArray();
                        Log.d("TAG", imgByte.toString());
                        inputStream.close();
                        dish();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void takePhoto(View view) {
        register.launch(new Intent(this, takePhoto.class));
    }

    public void dish() {


        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/dish";
        try {
            // 本地文件路径
        //    String filePath = "hamburger.png";
        //    byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgByte);
            Log.d("TAG", imgStr);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            Log.d("TAG", imgParam);
            String param = "image=" + imgParam + "&top_num=" + 5;

            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    try {
                        // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
                        String accessToken = "24.49c4a6a64de6bd2914f4061e8019293e.2592000.1703513103.282335-43106670";

                        String result = HttpUtil.post(url, accessToken, param);
                        Log.d("CalorieResult", result);
                        return result;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(String result) {
                    // 处理网络请求结果
                    if (result != null) {
                        // 在这里处理网络请求成功的情况
                    } else {
                        // 在这里处理网络请求失败的情况
                    }
                }
            }.execute();

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
