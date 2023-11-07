package com.example.success.ui.room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.success.AddFriend;
import com.example.success.CreateRoom;
import com.example.success.R;
import com.example.success.databinding.FragmentRoomBinding;
import com.example.success.entity.Room;
import com.example.success.showFriends;

import java.util.List;

public class RoomFragment extends Fragment {

    private FragmentRoomBinding binding;
    public List<Room> roomList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RoomViewModel roomViewModel =
                new ViewModelProvider(this).get(RoomViewModel.class);

        binding = FragmentRoomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
                Intent intent = new Intent(getActivity(), CreateRoom.class);
                startActivity(intent);
            }
        });

    }

}

