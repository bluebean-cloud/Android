package com.example.success;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.net.URLEncoder;

public class CalorieDetection extends AppCompatActivity {

    private ActivityResultLauncher<Intent> register;
    private String accessToken;

    String calorieResult;
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
                        ((ImageView)findViewById(R.id.food_picture)).setImageBitmap(bitmap);
                        Log.d("TAG", imgByte.toString());
                        inputStream.close();
                        ((TextView)findViewById(R.id.calorie_result)).setText("正在检测卡路里...");
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

        getAccessTokenInBackground();
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/dish";
        try {

            String imgStr = Base64Util.encode(imgByte);
            Log.d("TAG", imgStr);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            Log.d("TAG", imgParam);
            String param = "image=" + imgParam + "&top_num=" + 5;

            ExecutorService executor = Executors.newSingleThreadExecutor();

            Future<String> future = executor.submit(() -> {
                try {
                    // 执行网络请求，获取 calorieResult
                    return HttpUtil.post(url, "24.49c4a6a64de6bd2914f4061e8019293e.2592000.1703513103.282335-43106670", param);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            executor.shutdown();

            try {
                // 等待异步任务完成，获取结果
                String calorieResult = future.get();
                if (calorieResult != null) {
                    try {
                        // 假设 calorieResult 是一个 JSON 字符串
                        JSONObject jsonResult = new JSONObject(calorieResult);

                        // 从 JSON 中提取所需的信息，这里假设 result 是一个数组
                        JSONArray resultArray = jsonResult.getJSONArray("result");

                        // 假设你需要显示前三名的信息
                        if(resultArray.length() >= 3){
                            JSONObject firstFood = resultArray.getJSONObject(0);
                            JSONObject secondFood = resultArray.getJSONObject(1);
                            JSONObject thirdFood = resultArray.getJSONObject(2);
                            DecimalFormat DecimalFormat = new DecimalFormat("0.00%");

                            // 提取前三名的名称、卡路里和概率
                            String firstName = firstFood.getString("name");
                            String firstCalorie = firstFood.optString("calorie", "暂无数据");
                            if(firstCalorie != "暂无数据") firstCalorie = firstCalorie + "卡";
                            String firstProbability = firstFood.getString("probability");
                            double firstProbabilityPercentage = Double.parseDouble(firstProbability);
                            String firstFormattedPercentage = DecimalFormat.format(firstProbabilityPercentage);

                            String secondName = secondFood.getString("name");
                            String secondCalorie = secondFood.optString("calorie", "暂无数据");
                            if(secondCalorie != "暂无数据") secondCalorie = secondCalorie + "卡";
                            String secondProbability = secondFood.getString("probability");
                            double secondProbabilityPercentage = Double.parseDouble(secondProbability);
                            String secondFormattedPercentage = DecimalFormat.format(secondProbabilityPercentage);

                            String thirdName = thirdFood.getString("name");
                            String thirdCalorie = thirdFood.optString("calorie", "暂无数据");
                            if(thirdCalorie != "暂无数据") thirdCalorie = thirdCalorie + "卡";
                            String thirdProbability = thirdFood.getString("probability");
                            double thirdProbabilityPercentage = Double.parseDouble(thirdProbability);
                            String thirdFormattedPercentage = DecimalFormat.format(thirdProbabilityPercentage);

                            // 构建包含信息的字符串
                            String resultText = "1. " + firstName + "  " + firstCalorie + "  置信度: " + firstFormattedPercentage + "\n" +
                                    "2. " + secondName + "  " + secondCalorie + "  置信度: " + secondFormattedPercentage + "\n" +
                                    "3. " + thirdName + "  " + thirdCalorie  + "  置信度: " + thirdFormattedPercentage;

                            // 在 TextView 中设置文本
                            TextView calorieResultTextView = findViewById(R.id.calorie_result);
                            calorieResultTextView.setText(resultText);
                        }
                        else {
                            JSONObject firstFood = resultArray.getJSONObject(0);
                            DecimalFormat DecimalFormat = new DecimalFormat("0.00%");
                            String firstName = firstFood.getString("name");
                            String firstCalorie = firstFood.optString("calorie", "暂无数据");
                            if(firstCalorie != "暂无数据") firstCalorie = firstCalorie + "卡";
                            String firstProbability = firstFood.getString("probability");
                            double firstProbabilityPercentage = Double.parseDouble(firstProbability);
                            String firstFormattedPercentage = DecimalFormat.format(firstProbabilityPercentage);

                            String resultText = "1. " + firstName + "  " + firstCalorie + "  置信度: " + firstFormattedPercentage;

                            // 在 TextView 中设置文本
                            TextView calorieResultTextView = findViewById(R.id.calorie_result);
                            calorieResultTextView.setText(resultText);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAccessTokenInBackground() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    accessToken = GetAccessToken();
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
