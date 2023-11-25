package com.example.success;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.success.entity.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList;
    DatabaseInterface db = MainActivity.db;

    private enum RoomStatus {
        PREPARING,   // 准备开始
        ONGOING,     // 正在进行
        FINISHED     // 已经结束
    }

    public RoomAdapter(List<Room> roomList) {
        this.roomList = roomList;
        sortRooms();  // 对房间按开始时间排序
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.roomName.setText(room.getRoomName());
        holder.roomMaxNumber.setText(String.format("%d/%d", db.getAllUserInRoom(room.getId()).size(), room.getMaxUserNumber()));
        holder.roomSportType.setText(String.valueOf(room.getSportType()));
        holder.roomStartTime.setText(String.valueOf(room.getStartTime()));
        holder.roomEndTime.setText(String.valueOf(room.getEndTime()));

        RoomStatus status = getRoomStatus(room);
        setRoomStatusColor(holder.itemView, status);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RoomDetail.class);
                intent.putExtra("room_id", room.getId());
                intent.putExtra("room_name", room.getRoomName());
                intent.putExtra("room_admin_name", String.valueOf(room.getJoinUsers().get(0).getName()));
                intent.putExtra("sport_type", String.valueOf(room.getSportType()));
                intent.putExtra("current_user_number", String.valueOf(db.getAllUserInRoom(room.getId()).size()));
                intent.putExtra("max_user_number", String.valueOf(room.getMaxUserNumber()));
                intent.putExtra("start_time", String.valueOf(room.getStartTime()));
                intent.putExtra("end_time", String.valueOf(room.getEndTime()));
                intent.putExtra("describe", String.valueOf(room.getRoomDescribe()));

                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    private void sortRooms() {
        Collections.sort(roomList, new Comparator<Room>() {
            @Override
            public int compare(Room room1, Room room2) {
                return room1.getStartTime().compareTo(room2.getStartTime());
            }
        });
    }

    private RoomStatus getRoomStatus(Room room) {
        Date currentDate = new Date();
        Date startTime = parseDateString(room.getStartTime());
        Date endTime = parseDateString(room.getEndTime());

        if (startTime.after(currentDate)) {
            return RoomStatus.PREPARING;  // 准备开始
        } else if (endTime.after(currentDate)) {
            return RoomStatus.ONGOING;  // 正在进行
        } else {
            return RoomStatus.FINISHED;  // 已经结束
        }
    }

    private Date parseDateString(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 根据房间状态设置不同背景颜色
    private void setRoomStatusColor(View itemView, RoomStatus status) {
        switch (status) {
            case PREPARING:
                itemView.setBackgroundColor(Color.parseColor("#FFFFCC"));
                break;
            case ONGOING:
                itemView.setBackgroundColor(Color.parseColor("#CCD9FF"));
                break;
            case FINISHED:
                itemView.setBackgroundColor(Color.parseColor("#FFCCCC"));
                break;
            default:
                itemView.setBackgroundColor(Color.parseColor("#0"));
                break;
        }
    }


    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        public TextView roomName;
        public TextView roomSportType;
        public TextView roomMaxNumber;
        public TextView roomStartTime;
        public TextView roomEndTime;

        public RoomViewHolder(View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.room_card_name);
            roomSportType = itemView.findViewById(R.id.room_card_sport_type);
            roomMaxNumber = itemView.findViewById(R.id.room_card_max_number);
            roomStartTime = itemView.findViewById(R.id.room_card_start_time);
            roomEndTime = itemView.findViewById(R.id.room_card_end_time);
        }
    }
}
