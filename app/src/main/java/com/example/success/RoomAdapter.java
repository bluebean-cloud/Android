package com.example.success;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.success.entity.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList;
    DatabaseInterface db = MainActivity.db;

    public RoomAdapter(List<Room> roomList) {
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.roomName.setText(room.getRoomName());
        holder.roomMaxNumber.setText(String.format("%d/%d", room.getJoinUsers().size(), room.getMaxUserNumber()));
        holder.roomSportType.setText(String.valueOf(room.getSportType()));
        holder.roomStartTime.setText(String.valueOf(room.getStartTime()));
        holder.roomEndTime.setText(String.valueOf(room.getEndTime()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RoomDetail.class);
                intent.putExtra("room_id", room.getId());
                intent.putExtra("room_name", room.getRoomName());
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
