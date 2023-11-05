package com.example.success;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        byte[] byteArray = sportRecord.getImageBit1();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        holder.ImageViewSportItem.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailOfSport.class);
                //传递用户头像
                //TODO:设置用户图像
                //传递用户名
                intent.putExtra("textViewUserIdInDetail", "Arong");//TODO:设置userId
                //传递创建时间
                intent.putExtra("textViewCreateDateInDetail", holder.createDateTextView.getText().toString());
                //传递运动项目
                intent.putExtra("textViewSportNameInDetail", sportRecord.getSportName());
                //传递运动时间
                intent.putExtra("textViewDurationInDetail", String.valueOf(sportRecord.getDuration()));
                //传递运动地点
                intent.putExtra("textViewSportLocationInDetail", String.valueOf(sportRecord.getSportLocation()));
                //传递图片1
                intent.putExtra("image1", sportRecord.getImageBit1());
                //传递图片2
                if (sportRecord.getImageBit2() != null) {
                    intent.putExtra("image2", sportRecord.getImageBit2());
                }
                //启动活动
                v.getContext().startActivity(intent);
            }
        });
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
        public ImageView ImageViewSportItem;

        public ViewHolder(View view) {
            super(view);
            sportNameTextView = view.findViewById(R.id.textViewSportName);
            durationTextView = view.findViewById(R.id.textViewDuration);
            sportLocationTextView = view.findViewById(R.id.textViewSportLocation);
            createDateTextView = view.findViewById(R.id.textViewCreateDate);
            ImageViewSportItem = view.findViewById(R.id.ImageViewSportItem);
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