package com.example.success;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class RoomDetail extends AppCompatActivity {

    Long currentRoomId;
    AlertDialog.Builder dialog;
    Long currentUserId = CurrentUser.getUser().getId();
    DatabaseInterface db = MainActivity.db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dialog = new AlertDialog.Builder(RoomDetail.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        // 初始化各个TextView
        TextView roomNameTextView = findViewById(R.id.room_detail_name);
        TextView roomAdminNameTextView = findViewById(R.id.room_detail_admin_name);
        TextView sportTypeTextView = findViewById(R.id.room_detail_sport_type);
        TextView maxUserNumberTextView = findViewById(R.id.room_detail_user_number);
        TextView startTimeTextView = findViewById(R.id.room_detail_start_time);
        TextView endTimeTextView = findViewById(R.id.room_detail_end_time);
        TextView describeTextView = findViewById(R.id.room_detail_describe);
//        TextView roomUserTextView = findViewById(R.id.)

        // 获取传递的信息
        Intent intent = getIntent();
        String roomName = intent.getStringExtra("room_name");
        String roomAdminName = intent.getStringExtra("room_admin_name");
        String sportType = intent.getStringExtra("sport_type");
        String currentUserNumber = intent.getStringExtra("current_user_number");
        String maxUserNumber = intent.getStringExtra("max_user_number");
        String startTime = intent.getStringExtra("start_time");
        String endTime = intent.getStringExtra("end_time");
        String describe = intent.getStringExtra("describe");
        currentRoomId = intent.getLongExtra("room_id",0);


        // 将信息显示在相应的TextView中
        roomNameTextView.setText(roomName);
        roomAdminNameTextView.setText(roomAdminName);
        sportTypeTextView.setText(sportType);
        maxUserNumberTextView.setText(String.format("%s/%s", currentUserNumber, maxUserNumber));
        startTimeTextView.setText(startTime);
        endTimeTextView.setText(endTime);
        describeTextView.setText(describe);

        Button joinOrQuitRoomTextView = findViewById(R.id.join_room);
        if(db.isInRoom(currentRoomId, currentUserId)){
            if(Objects.equals(db.getAllUserInRoom(currentRoomId).get(0).getId(), currentUserId)) {
                joinOrQuitRoomTextView.setText("解散房间");
            } else {
                joinOrQuitRoomTextView.setText("退出房间");
            }
        }
        else {
            joinOrQuitRoomTextView.setText("加入房间");
        }
    }

    public void joinOrQuitRoom(View view) {
        if (db.isInRoom(currentRoomId, currentUserId)) {
            if(Objects.equals(db.getAllUserInRoom(currentRoomId).get(0).getId(), currentUserId)) {
                dialog.setMessage("确认解散？");
            } else {
                dialog.setMessage("确认退出？");
            }
        } else {
            dialog.setMessage("确认加入？");
        }

        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setCancelable(true); // false点击对话框以外的区域是否让对话框消失

        // 设置正面按钮
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (db.isInRoom(currentUserId, currentUserId)) {
                    if(Objects.equals(db.getAllUserInRoom(currentRoomId).get(0).getId(), currentUserId)) {
                        Toast.makeText(RoomDetail.this, "已解散房间", Toast.LENGTH_LONG).show();
                        db.deleteRoom(currentRoomId);
                    } else {
                        Toast.makeText(RoomDetail.this, "已退出房间", Toast.LENGTH_LONG).show();
                        db.quitRoom(currentRoomId, currentUserId);
                    }

                } else {
                    Toast.makeText(RoomDetail.this, "已加入房间", Toast.LENGTH_LONG).show();
                    db.joinRoom(currentRoomId, currentUserId);
                }
                dialog.dismiss();
                finish();

            }
        });

        // 设置反面按钮
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show(); // 显示对话框
    }

}