package com.example.success.ui.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.success.CalorieDetection;
import com.example.success.CreateRoom;
import com.example.success.DatabaseInterface;
import com.example.success.MainActivity;
import com.example.success.MainViewModel;
import com.example.success.R;
import com.example.success.RoomAdapter;
import com.example.success.databinding.FragmentHomeBinding;
import com.example.success.databinding.FragmentRoomBinding;
import com.example.success.entity.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomFragment extends Fragment {

    private FragmentRoomBinding binding;
    private RecyclerView recyclerView;
    public RoomAdapter roomAdapter;
    DatabaseInterface db = MainActivity.db;
    List<Room> roomList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RoomViewModel roomViewModel =
                new ViewModelProvider(this).get(RoomViewModel.class);

        binding = FragmentRoomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 初始化房间列表（roomList）的数据

        recyclerView = root.findViewById(R.id.room_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        roomList = db.getAllRoom();
        roomAdapter = new RoomAdapter(roomList);
        recyclerView.setAdapter(roomAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView createRoom = getActivity().findViewById(R.id.add_room_icon);
        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CalorieDetection.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        View root = binding.getRoot();

        // 初始化房间列表（roomList）的数据

        recyclerView = root.findViewById(R.id.room_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        roomList = db.getAllRoom();

        roomAdapter = new RoomAdapter(roomList);
        recyclerView.setAdapter(roomAdapter);
    }

}

