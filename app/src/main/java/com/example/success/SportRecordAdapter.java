package com.example.success;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SportRecordAdapter extends RecyclerView.Adapter<SportRecordAdapter.ViewHolder> {
    private List<SportRecord> sportRecords;
    private RecyclerView recyclerView;

    public SportRecordAdapter(List<SportRecord> sportRecords) {
        this.sportRecords = sportRecords;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SportRecord sportRecord = sportRecords.get(position);
        holder.sportNameTextView.setText(sportRecord.getSportName());
        holder.durationTextView.setText(String.valueOf(sportRecord.getDuration()));
        holder.sportLocationTextView.setText(String.valueOf(sportRecord.getSportLocation()));
        holder.createDateTextView.setText(String.valueOf(sportRecord.getCreateDate()));
    }

    @Override
    public int getItemCount() {
        return sportRecords.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView sportNameTextView;
        public TextView durationTextView;
        public TextView sportLocationTextView;
        public TextView createDateTextView;

        public ViewHolder(View view) {
            super(view);
            sportNameTextView = view.findViewById(R.id.textViewSportName);
            durationTextView = view.findViewById(R.id.textViewDuration);
            sportLocationTextView = view.findViewById(R.id.textViewSportLocation);
            createDateTextView = view.findViewById(R.id.textViewCreateDate);
        }
    }

    public void addSportRecord(SportRecord sportRecord) {
        sportRecords.add(0, sportRecord);
        notifyItemInserted(0); // 通知适配器新记录已插入到位置 0

        // 滚动到最新的记录位置
        recyclerView.scrollToPosition(0); // 或者使用 smoothScrollToPosition(0)方法，但前者更丝滑
    }

    // 设置 RecyclerView
    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}