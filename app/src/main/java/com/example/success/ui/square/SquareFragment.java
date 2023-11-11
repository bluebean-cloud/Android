package com.example.success.ui.square;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.success.CreateTip;
import com.example.success.DatabaseInterface;
import com.example.success.MainActivity;
import com.example.success.R;
import com.example.success.ShowTip;
import com.example.success.databinding.FragmentSquareBinding;
import com.example.success.entity.SportTip;
import com.example.success.entity.User;

import java.util.ArrayList;
import java.util.List;

public class SquareFragment extends Fragment {
    private FragmentSquareBinding binding;
    RecyclerView recyclerView;
    TipAdapter tipAdapter;
    List<SportTip> list = new ArrayList<>();
    View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSquareBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initTips();

        final Button createTip = binding.addWordButton;
        createTip.setOnClickListener(view -> {
            Intent jump = new Intent(getActivity(), CreateTip.class);
            startActivity(jump);
        });

        return root;
    }

    private void initTips() {
        DatabaseInterface db = MainActivity.db;
        root = binding.getRoot();
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);

        recyclerView = binding.tipsView;
        list = db.getAllTip();
        tipAdapter = new TipAdapter();
        recyclerView.setAdapter(tipAdapter);
        recyclerView.addItemDecoration(divider);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onResume() {
        super.onResume();
        initTips();
    }

    class TipAdapter extends RecyclerView.Adapter<TipHolder> {

        @NonNull
        @Override
        public TipHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.tips_list, null);
            TipHolder holder = new TipHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull TipHolder holder, int position) {
            DatabaseInterface db = MainActivity.db;
            SportTip tip = list.get(position);
            String title = tip.getTitle();
            String content = tip.getContent();
            if (title.length() > 10) {
                title = title.substring(0, 10);
                title += "...";
            }
            if (content.length() > 30) {
                content = content.substring(0, 30);
                content += "...";
            }
            Long userId = tip.getUserId();
            if (userId != null) {
                User user = db.getUserById(userId);
                holder.user.setText(user.getName());
            }
            holder.title.setText(title);
            holder.content.setText(content);
            holder.content.setOnClickListener(view -> {
                Intent jump = new Intent(getActivity(), ShowTip.class);
                jump.putExtra("pos", position);
                startActivity(jump);
            });
            byte[] bytes = tip.getPhoto();
            if (bytes != null) {
                holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class TipHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        TextView user;
        ImageView imageView;

        public TipHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tip_title);
            content = itemView.findViewById(R.id.tip_content);
            imageView = itemView.findViewById(R.id.tip_list_photo);
            user = itemView.findViewById(R.id.tip_user);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
