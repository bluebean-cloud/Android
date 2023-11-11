package com.example.success;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.success.entity.SportTip;
import com.example.success.entity.User;

import java.util.List;

public class ShowTip extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseInterface db = MainActivity.db;
        setContentView(R.layout.tip_detail);
        int pos = getIntent().getIntExtra("pos", 0);
        List<SportTip> tips = db.getAllTip();
        TextView title = findViewById(R.id.test_view_tip_detail_title);
        TextView content = findViewById(R.id.test_view_tip_detail_content);
        ImageView imageView = findViewById(R.id.test_view_tip_detail_image);
        TextView user = findViewById(R.id.test_view_tip_detail_user);
        SportTip tip = tips.get(pos);
        title.setText(tip.getTitle());
        content.setText(tip.getContent());
        User user1 = db.getUserById(tip.getUserId());
        user.setText(user1.getName());
        byte[] bytes = tip.getPhoto();
        if (bytes != null) {
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        }
    }

    private void setCardView() {
        
    }
}
