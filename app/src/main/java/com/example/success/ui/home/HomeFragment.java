package com.example.success.ui.home;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.success.AddSportRecordActivity;
import com.example.success.CurrentUser;
import com.example.success.DatabaseInterface;
import com.example.success.MainActivity;
import com.example.success.MainViewModel;
import com.example.success.R;
import com.example.success.entity.SportRecord;
import com.example.success.SportRecordAdapter;
import com.example.success.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class HomeFragment extends Fragment {
    // 提醒管理
    private FragmentHomeBinding binding;
    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private SportRecordAdapter adapter;
    private static final int ADD_RECORD_REQUEST_CODE = 1;

    DatabaseInterface db = MainActivity.db;
    List<SportRecord> sportRecords = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //获得view
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 初始化RecyclerView
        recyclerView = root.findViewById(R.id.recyclerViewForSportRecord);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 创建适配器
        sportRecords = db.getUserRecord(CurrentUser.getUser().getId());
        //将sportRecord逆序
        Collections.reverse(sportRecords);
        adapter = new SportRecordAdapter(sportRecords);
        adapter.setRecyclerView(recyclerView); // 设置 RecyclerView

        // 将适配器绑定到RecyclerView
        recyclerView.setAdapter(adapter);

        //设置addRecordButton的点击函数
        FloatingActionButton addRecordButton = root.findViewById(R.id.buttonAddRecord);
        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddSportRecordActivity.class);
                startActivityForResult(intent, ADD_RECORD_REQUEST_CODE);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_RECORD_REQUEST_CODE && resultCode == RESULT_OK) {
        }
    }

    //重新显示原有的界面
    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 创建适配器
        sportRecords = db.getUserRecord(CurrentUser.getUser().getId());
        //将sportRecord逆序
        Collections.reverse(sportRecords);
        adapter = new SportRecordAdapter(sportRecords);
        adapter.setRecyclerView(recyclerView); // 设置 RecyclerView

        // 将适配器绑定到RecyclerView
        recyclerView.setAdapter(adapter);
    }
}