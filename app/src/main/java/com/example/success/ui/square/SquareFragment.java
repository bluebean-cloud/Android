package com.example.success.ui.square;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.success.databinding.FragmentSquareBinding;
import com.example.success.entity.SportTip;

import java.util.ArrayList;
import java.util.List;

public class SquareFragment extends Fragment {
    private FragmentSquareBinding binding;
    RecyclerView recyclerView;
    TipAdapter tipAdapter;
    List<SportTip> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DatabaseInterface db = MainActivity.db;
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
        View root = binding.getRoot();
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
            SportTip tip = list.get(position);
            holder.title.setText(tip.getTitle());
            holder.content.setText(tip.getContent());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class TipHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;

        public TipHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tip_title);
            content = itemView.findViewById(R.id.tip_content);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
