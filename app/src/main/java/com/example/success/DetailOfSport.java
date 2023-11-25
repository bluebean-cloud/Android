package com.example.success;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class DetailOfSport extends Activity {
    ImageView imageViewUserHeaderImageInSportDetail;
    ImageView imageViewSportDetail1;
    ImageView imageViewSportDetail2;
    ImageView imageViewSportDetail3;
    TextView textViewUserIdInDetail;
    TextView textViewCreateDateInDetail;
    TextView textViewSportDetail1; //字符串运动类型
    TextView textViewSportNameInDetail;
    TextView textViewSportDetail2;//字符串运动时间
    TextView textViewDurationInDetail;
    TextView textViewSportDetail3;//字符串运动地点
    TextView textViewSportLocationInDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_of_sport);
        //find相应的组件
        imageViewUserHeaderImageInSportDetail = findViewById(R.id.imageViewUserHeaderImageInSportDetail);
        imageViewSportDetail1 = findViewById(R.id.imageViewSportDetail1);
        imageViewSportDetail2 = findViewById(R.id.imageViewSportDetail2);
        imageViewSportDetail3 = findViewById(R.id.imageViewSportDetail3);
        //用户名
        textViewUserIdInDetail = findViewById(R.id.textViewUserIdInDetail);
        //创建时间
        textViewCreateDateInDetail = findViewById(R.id.textViewCreateDateInDetail);
        //运动类型
        textViewSportNameInDetail = findViewById(R.id.textViewSportNameInDetail);
        //运动时间
        textViewDurationInDetail = findViewById(R.id.textViewDurationInDetail);
        //运动地点
        textViewSportLocationInDetail = findViewById(R.id.textViewSportLocationInDetail);

        //设置参数
        Intent intent = getIntent();
        //用户头像
        byte[] array = CurrentUser.getUser().getUserPhoto();
        Bitmap bitHeader = BitmapFactory.decodeByteArray(array, 0, array.length);
        imageViewUserHeaderImageInSportDetail.setImageBitmap(bitHeader);
        //用户名
        textViewUserIdInDetail.setText(intent.getStringExtra("textViewUserIdInDetail"));
        //创建时间
        textViewCreateDateInDetail.setText(intent.getStringExtra("textViewCreateDateInDetail"));
        //运动类型
        textViewSportNameInDetail.setText(intent.getStringExtra("textViewSportNameInDetail"));
        //运动时间
        textViewDurationInDetail.setText(intent.getStringExtra("textViewDurationInDetail"));
        //运动地点
        textViewSportLocationInDetail.setText(intent.getStringExtra("textViewSportLocationInDetail"));
        //图片1
        byte[] byteArray = intent.getByteArrayExtra("image1");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageViewSportDetail1.setImageBitmap(bitmap);
        //图片2,如果有图片2
        if (intent.hasExtra("image2")) {
            byteArray = intent.getByteArrayExtra("image2");
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageViewSportDetail2.setImageBitmap(bitmap);
        }
    }
}