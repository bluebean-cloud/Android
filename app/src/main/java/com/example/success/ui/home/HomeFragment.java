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
import com.example.success.MainViewModel;
import com.example.success.R;
import com.example.success.SportRecord;
import com.example.success.SportRecordAdapter;
import com.example.success.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
            // 处理从添加运动记录界面返回的数据
            // 更新列表或执行其他操作
            assert data != null;
            String sportName = data.getStringExtra("sportName"); // 从添加页面传回运动名称
            String duration = data.getStringExtra("duration"); // 从添加页面传回运动时间
            String sportLocation = data.getStringExtra("sportLocation"); // 从添加页面传回运动地点
            String createDate = getCurrentTime(); // 获取当前的时间
            int image_num = data.getIntExtra("image_num", 0);
            byte[] byteArray1;
            byte[] byteArray2;
            SportRecord newSportRecord = null;
            // 创建新的SportRecord对象
            if (image_num == 1) {
                byteArray1 = data.getByteArrayExtra("image1");
                newSportRecord = new SportRecord(sportName, duration, sportLocation, createDate, byteArray1);
            } else if (image_num == 2) {
                byteArray1 = data.getByteArrayExtra("image1");
                byteArray2 = data.getByteArrayExtra("image2");
                newSportRecord = new SportRecord(sportName, duration, sportLocation, createDate, byteArray1, byteArray2);
            }
            // 将新的运动记录添加到适配器中
            adapter.addSportRecord(newSportRecord);
        }
    }

    public String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        // 设置格式化的SimpleDateFormat对象，指定中国语言环境
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        // 创建时区（TimeZone）对象，设置时区为“亚洲/重庆"
        TimeZone TZ = TimeZone.getTimeZone("Asia/Chongqing");
        // 将SimpleDateFormat强制转换为DateFormat
        DateFormat df = null;
        try {
            df = (DateFormat) sdf;
        } catch (Exception E) {
            E.printStackTrace();
        }
        // 为DateFormat对象设置时区
        df.setTimeZone(TZ);
        // 获取时间表达式
        return df.format(cal.getTime());
    }

    //重新显示原有的界面
    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 创建适配器
        adapter = new SportRecordAdapter(sportRecords);
        adapter.setRecyclerView(recyclerView); // 设置 RecyclerView

        // 将适配器绑定到RecyclerView
        recyclerView.setAdapter(adapter);
    }
}