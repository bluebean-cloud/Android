package com.example.success;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.success.entity.Knowledge;
import com.example.success.entity.User;
import com.example.success.ui.room.RoomFragment;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateRoom extends AppCompatActivity {

    EditText roomName;
    EditText sportType;
    EditText maxUseNumber;
    EditText startTime;
    EditText endTime;
    EditText rallyPosition;
    EditText roomDetail;

    DatabaseInterface db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = MainActivity.db;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        roomName = findViewById(R.id.room_name);
        sportType = findViewById(R.id.sport_type);
        maxUseNumber = findViewById(R.id.max_user_number);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);roomDetail = findViewById(R.id.room_detail);
        chooseRoomType();
        setTime(startTime);
        setTime(endTime);
    }

    public void chooseRoomType() {
        sportType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建 PopupMenu
                PopupMenu popupMenu = new PopupMenu(CreateRoom.this, sportType);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.sport_type, popupMenu.getMenu()); // 添加菜单项

                // 设置菜单项点击事件
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_other) {
                            // 如果选择了"其他"
                            showCustomInputDialog();
                            return true;
                        } else {
                            // 用户选择了一个选项
                            sportType.setText(item.getTitle());
                            return true;
                        }
                    }
                });

                popupMenu.show();
            }
        });
    }

    public void saveRoom(View view) {
        // 获取用户输入的房间信息
        String roomNameText = roomName.getText().toString();
        String sportTypeText = sportType.getText().toString();
        String maxUserNumberText = maxUseNumber.getText().toString();
        String startTimeText = startTime.getText().toString();
        String endTimeText = endTime.getText().toString();
        String roomDetailText = roomDetail.getText().toString();

        // 检查是否有空项
        if (TextUtils.isEmpty(roomNameText) || TextUtils.isEmpty(sportTypeText) ||
                TextUtils.isEmpty(maxUserNumberText) || TextUtils.isEmpty(startTimeText) ||
                TextUtils.isEmpty(endTimeText) || TextUtils.isEmpty(roomDetailText)) {
            Toast.makeText(this, "请填写所有房间信息", Toast.LENGTH_SHORT).show();
            return;
        }

        // 检查开始时间是否早于结束时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            Date startDate = sdf.parse(startTimeText);
            Date endDate = sdf.parse(endTimeText);

            if (startDate.after(endDate)) {
                Toast.makeText(this, "结束时间应晚于开始时间", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "日期格式错误", Toast.LENGTH_SHORT).show();
            return;
        }

        // 检查开始时间是否晚于当前时间
        Date currentDate = new Date();
        try {
            Date startDate = sdf.parse(startTimeText);

            if (startDate.before(currentDate)) {
                Toast.makeText(this, "开始时间应晚于当前时间", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "日期格式错误", Toast.LENGTH_SHORT).show();
            return;
        }

        // 保存房间信息
        User currentUser = CurrentUser.getUser();
        db.addRoom(currentUser, maxUserNumberText, roomNameText, sportTypeText,
                roomDetailText, startTimeText, endTimeText);
        Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();

        // 结束当前 Activity
        finish();
    }



    private void showCustomInputDialog() {
        // 创建一个对话框，包含一个 EditText 来让用户输入自定义内容
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("运动项目");
        final EditText input = new EditText(this);
        builder.setView(input);

        // 设置对话框按钮点击事件
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String customInput = input.getText().toString();
                sportType.setText(customInput);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // 显示对话框
        builder.show();
    }

    public void setTime(EditText Time) {
        Calendar calendar = Calendar.getInstance();
        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前日期的年、月、日、时、分
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                // 创建日期选择框（DatePickerDialog）
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateRoom.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                                // 创建时间选择框（TimePickerDialog）
                                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateRoom.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @SuppressLint("SetTextI18n")
                                            @Override
                                            public void onTimeSet(TimePicker view, int selectedHourOfDay, int selectedMinute) {
                                                // 处理选择的日期和时间
                                                // 格式化分钟部分，确保是两位数
                                                @SuppressLint("DefaultLocale") String formattedHour = String.format("%02d", selectedHourOfDay);
                                                @SuppressLint("DefaultLocale") String formattedMinute = String.format("%02d", selectedMinute);
                                                // 更新 EditText 显示选择的日期和时间
                                                Time.setText(selectedYear + "-" + (selectedMonth + 1) + "-" +
                                                        selectedDayOfMonth + " " + formattedHour + ":" + formattedMinute);
                                            }
                                        }, hour, minute, true);

                                // 显示时间选择框
                                timePickerDialog.show();
                            }
                        }, year, month, day);

                // 显示日期选择框
                datePickerDialog.show();
            }
        });
    }
}