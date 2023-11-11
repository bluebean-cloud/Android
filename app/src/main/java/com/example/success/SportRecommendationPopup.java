package com.example.success;

import java.util.Random;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.success.Weather.WeatherApiClient;
import com.example.success.entity.SportRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SportRecommendationPopup {

    private Context context;
    private PopupWindow popupWindow;

    TextView textWeather;
    TextView textRecommendation;
    TextView textRecommendBottom;
    //适合室内的运动
    static ArrayList<String> sportInside = new ArrayList<>();
    //所有运动
    static ArrayList<String> sportOutside = new ArrayList<>();

    static {
        sportInside.add("排球");
        sportInside.add("羽毛球");
        sportInside.add("乒乓球");
        sportInside.add("台球");
        sportInside.add("游泳");
        sportInside.add("健身");

        sportOutside.add("排球");
        sportOutside.add("羽毛球");
        sportOutside.add("乒乓球");
        sportOutside.add("台球");
        sportOutside.add("游泳");
        sportOutside.add("健身");
        sportOutside.add("TD线");
        sportOutside.add("跑步");
        sportOutside.add("篮球");
        sportOutside.add("网球");
        sportOutside.add("足球");
        sportOutside.add("健身");
    }

    public SportRecommendationPopup(Context context) {
        this.context = context;
        initPopup();
    }

    private void initPopup() {
        View popupView = LayoutInflater.from(context).inflate(R.layout.sport_recommendation_layout, null);

        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        textWeather = popupView.findViewById(R.id.textWeather);
        textRecommendation = popupView.findViewById(R.id.textRecommendation);
        textRecommendBottom = popupView.findViewById(R.id.textRecommendBottom);
        //进行推荐
        recommend();
    }

    public void recommend() {
        //设置天气信息和推荐内容
        final int[] temp = new int[1];
        final String[] weather = {""};
        WeatherApiClient.getCurrentWeather("Beijing", new WeatherApiClient.WeatherCallback() {
            @Override
            public void onSuccess(String weatherDescription, double temperature) {
                // 处理成功获取到的天气和温度数据
                temp[0] = (int) (temperature - 273);
                weather[0] = weatherDescription;
                String s = weatherDescription + " " + temp[0] + "°C";
                textWeather.setText(s);
            }

            @Override
            public void onFailure(String errorMessage) {
                // 处理请求失败的情况
                Log.e("Weather", "Error: " + errorMessage);
            }
        });
        //进行推荐,根据天气和温度进行推荐
        DatabaseInterface db = MainActivity.db;
        List<SportRecord> sportRecordsForOwn = db.getUserRecord(CurrentUser.getUser().getId());
        List<SportRecord> sportRecordsForAll = db.getAllRecord();
        HashMap<String, Integer> sportTimesForOwn = new HashMap<>();
        HashMap<String, Integer> sportTimesForAll = new HashMap<>();
        //个人总运动次数
        int cntForOwn = 0;
        //所有人运动次数
        int cntForAll = 0;
        //统计个人每一种运动的次数
        for (SportRecord sportRecord : sportRecordsForOwn) {
            int old = sportTimesForOwn.getOrDefault(sportRecord.getSportName(), 0);
            sportTimesForOwn.put(sportRecord.getSportName(), old + 1);
            cntForOwn++;
        }
        //统计所有人每一种运动的次数
        for (SportRecord sportRecord : sportRecordsForAll) {
            int old = sportTimesForAll.getOrDefault(sportRecord.getSportName(), 0);
            sportTimesForAll.put(sportRecord.getSportName(), old + 1);
            cntForAll++;
        }
        //根据运动记录和天气综合推荐
        double rate = 0.8;//个人运动所占权重
        double maxScore = 0;
        double score;
        Random r = new Random();
        String targetSport = sportOutside.get(r.nextInt(sportOutside.size()));
        HashMap<String, Double> scores = new HashMap<>();
        for (String sportName : sportTimesForAll.keySet()) {
            if (cntForOwn != 0) {
                score = sportTimesForOwn.getOrDefault(sportName, 0) * 1.0 / cntForOwn * rate +
                        sportTimesForAll.getOrDefault(sportName, 0) * 1.0 / cntForAll * (1.0 - rate);
            } else {
                score = sportTimesForAll.getOrDefault(sportName, 0) * 1.0 / cntForAll * (1.0 - rate);
            }
            scores.put(sportName, score);
            if (score >= maxScore) {
                maxScore = score;
                targetSport = sportName;
            }
        }
        //小于10度
        if (temp[0] <= 10) {
            if (weather[0].equals("Rain") || weather[0].equals("Mist") || weather[0].equals("Thunderstorm") ||
                    weather[0].equals("Snow") || weather[0].equals("Smoke") || weather[0].equals("Haze")) {
                maxScore = 0;
                for (String sportName : sportInside) {
                    score = scores.getOrDefault(sportName, 0.0);
                    if (score >= maxScore) {
                        maxScore = score;
                        targetSport = sportName;
                    }
                }
                textRecommendBottom.setText("天冷了,注意保暖");
            } else {
                textRecommendBottom.setText("坚持锻炼,风雨无阻");
            }
        } else {
            if (weather[0].equals("Rain") || weather[0].equals("Mist") || weather[0].equals("Thunderstorm") ||
                    weather[0].equals("Snow") || weather[0].equals("Smoke") || weather[0].equals("Haze")) {
                maxScore = 0;
                for (String sportName : sportInside) {
                    score = scores.getOrDefault(sportName, 0.0);
                    if (score >= maxScore) {
                        maxScore = score;
                        targetSport = sportName;
                    }
                }
                textRecommendBottom.setText("坚持锻炼,风雨无阻");
            } else {
                textRecommendBottom.setText("一起锻炼锻炼吧");
            }
        }
        textRecommendation.setText(targetSport);
    }

    public void show(View anchorView) {
        View parentView = anchorView.getRootView();
        // 显示弹出窗口在屏幕中心
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }
}


