package com.example.success;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.success.entity.Knowledge;
import com.example.success.entity.Word;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.success.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static MainViewModel mainViewModel;
    @SuppressLint("StaticFieldLeak")
    public static DatabaseInterface db;
    static String name;
    static String passwd;
    static ArrayList<Word> wordTask; //单词背诵
    static ArrayList<Knowledge> knowledgeTask; //知识点背诵
    static int wordSum; //单词总数
    static int knowledgeSum; //任务总数
    static int wordIndex; //当前单词index
    static int knowledgeIndex; //当前任务index

    static ArrayList<Word> wordTest; //单词考察
    static ArrayList<Knowledge> knowledgeTest; //知识点考察
    static int wordTestSum;
    static int knowledgeTestSum;
    static int wordTestIndex;
    static int knowledgeTestIndex;
    //此处将考察和背诵分开处理，数据从不同的接口导入，可以保证需要记忆内容是一致的
    /*
    + ArrayList<Word>
    + ArrayList<Knowledge>
    + date created_time
    + int current_idnex
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseInterface(this);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        passwd = intent.getStringExtra("passwd");
        mainViewModel.setTextName(name);
        mainViewModel.setTextPasswd(passwd);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_square, R.id.navigation_room, R.id.navigation_person)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        initData();
        mainViewModel.setKnowledgeIndex(knowledgeIndex);
        mainViewModel.setKnowledgeSum(knowledgeSum);
        mainViewModel.setKnowledgetestIndex(knowledgeTestIndex);
        mainViewModel.setKnowledgeTestSum(knowledgeTestSum);
        mainViewModel.setWordTestIndex(wordTestIndex);
        mainViewModel.setWordIndex(wordIndex);
        mainViewModel.setWordSum(wordSum);
        mainViewModel.setWordTestSum(wordTestSum);

        // 创建弹出窗口
        SportRecommendationPopup sportPopup = new SportRecommendationPopup(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sportPopup.show(findViewById(R.id.container)); // 传入一个触发弹出窗口的 View
            }
        }, 500); // 500毫秒的延迟，你可以根据实际情况调整

    }

    public void click(View view) {
        Intent intent = new Intent(this, takePhoto.class);
        startActivity(intent);
    }

    public void initData() {
        wordTask = (ArrayList<Word>) db.getWordTaskForReview(name);
        knowledgeTask = (ArrayList<Knowledge>) db.getKnowledgeTaskForReview(name);
        wordSum = wordTask.size();
        knowledgeSum = knowledgeTask.size();
        knowledgeIndex = 0;
        wordIndex = 0;

        wordTest = (ArrayList<Word>) db.getWordTask(name);
        knowledgeTest = (ArrayList<Knowledge>) db.getKnowledgeTask(name);
        wordTestSum = wordTest.size();
        knowledgeTestSum = knowledgeTest.size();
        knowledgeTestIndex = 0;
        wordTestIndex = 0;

        db.initLabel();
        //db.testWord();
        //db.testKnowledge();
        //db.deleteTodayWordTask(name);
        //db.deleteTodayKnowledgeTask(name);
        //db.addLabel("测试1");
        //db.addLabel("测试2");
        //db.addLabel("测试3");
    }

    public static void updateData() {
        wordTask = (ArrayList<Word>) db.getWordTaskForReview(name);
        knowledgeTask = (ArrayList<Knowledge>) db.getKnowledgeTaskForReview(name);
        wordSum = wordTask.size();
        knowledgeSum = knowledgeTask.size();
        knowledgeIndex = 0;
        wordIndex = 0;
        wordTest = (ArrayList<Word>) db.getWordTask(name);
        knowledgeTest = (ArrayList<Knowledge>) db.getKnowledgeTask(name);
        wordTestSum = wordTest.size();
        knowledgeTestSum = knowledgeTest.size();
        knowledgeTestIndex = 0;
        wordTestIndex = 0;
        updateMainView();
    }

    public static void updateTest() {
        wordTest = (ArrayList<Word>) db.getWordTask(name);
        knowledgeTest = (ArrayList<Knowledge>) db.getKnowledgeTask(name);
        wordTestSum = wordTest.size();
        knowledgeTestSum = knowledgeTest.size();
        knowledgeTestIndex = 0;
        wordTestIndex = 0;
        updateMainView();
    }

    public static void updateMainView() {
        mainViewModel.setKnowledgeIndex(knowledgeIndex);
        mainViewModel.setKnowledgeSum(knowledgeSum);
        mainViewModel.setKnowledgetestIndex(knowledgeTestIndex);
        mainViewModel.setKnowledgeTestSum(knowledgeTestSum);
        mainViewModel.setWordTestIndex(wordTestIndex);
        mainViewModel.setWordIndex(wordIndex);
        mainViewModel.setWordSum(wordSum);
        mainViewModel.setWordTestSum(wordTestSum);
    }

}