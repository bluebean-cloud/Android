package com.example.success;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddSportRecordActivity extends Activity {
    public EditText editTextSportName;
    public EditText editTextDuration;
    public EditText editTextSportLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sport_record);
        // FindView
        editTextSportName = findViewById(R.id.editTextSportName);
        editTextDuration = findViewById(R.id.editTextDuration);
        editTextSportLocation = findViewById(R.id.editTextSportLocation);
        // 点击editText可以选择运动种类
        setChooseSport();
        // 点击时间editText可以选择时间日期
        setChooseTime();
    }

    public void setChooseSport() {
        editTextSportName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建 PopupMenu
                PopupMenu popupMenu = new PopupMenu(AddSportRecordActivity.this, editTextSportName);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popupMenu.getMenu()); // 添加菜单项

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
                            editTextSportName.setText(item.getTitle());
                            return true;
                        }
                    }
                });

                // 显示 PopupMenu
                popupMenu.show();
            }
        });
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
                editTextSportName.setText(customInput);
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

    public void setChooseTime() {
        Calendar calendar = Calendar.getInstance();
        editTextDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前日期的年、月、日、时、分
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                // 创建日期选择框（DatePickerDialog）
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddSportRecordActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                                // 创建时间选择框（TimePickerDialog）
                                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSportRecordActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @SuppressLint("SetTextI18n")
                                            @Override
                                            public void onTimeSet(TimePicker view, int selectedHourOfDay, int selectedMinute) {
                                                // 处理选择的日期和时间
                                                // 格式化分钟部分，确保是两位数
                                                @SuppressLint("DefaultLocale") String formattedHour = String.format("%02d", selectedHourOfDay);
                                                @SuppressLint("DefaultLocale") String formattedMinute = String.format("%02d", selectedMinute);
                                                // 更新 EditText 显示选择的日期和时间
                                                editTextDuration.setText(selectedYear + "-" + (selectedMonth + 1) + "-" +
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

    public void onConfirmButtonClick(View view) {
        // 保存运动记录数据
        String sportName = editTextSportName.getText().toString();
        String duration = editTextDuration.getText().toString();
        String sportLocation = editTextSportLocation.getText().toString();
        // 创建一个Intent，将结果传递回上一个界面
        Intent resultIntent = new Intent();
        resultIntent.putExtra("sportName", sportName);
        resultIntent.putExtra("duration", duration);
        resultIntent.putExtra("sportLocation", sportLocation);
        setResult(RESULT_OK, resultIntent);
        // 结束当前界面，返回到上一个界面
        finish();
    }
}