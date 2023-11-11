package com.example.success;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class CreateTip extends Activity {
    private final int TAKE_PHOTO = 4;
    private final int CHOOSE_PHOTO = 5;
    private int requestCode = 0;

    private Uri imageUri;
    private ImageView tipImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tip);
        tipImage = findViewById(R.id.tip_photo);

        DatabaseInterface db = MainActivity.db;
        Button publish = findViewById(R.id.buttonPublish);
        Button addPhoto = findViewById(R.id.buttonAddImage);

        publish.setOnClickListener(view -> {
            EditText editTitle = findViewById(R.id.tipTitle);
            EditText editContent = findViewById(R.id.tipContent);
            String title = editTitle.getText().toString();
            String content = editContent.getText().toString();
            Bitmap bitmap = null;
            if (tipImage.getDrawable() != null) {
                bitmap = ((BitmapDrawable) tipImage.getDrawable()).getBitmap();
            }
            if (title.isEmpty()) {
                Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
            } else if (content.isEmpty()) {
                Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            } else {
                db.createTip(MainActivity.name, title, content, bitmap);
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void addPhoto(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("请选择照片");
        builder.setPositiveButton("打开相册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestCode = CHOOSE_PHOTO;
                choosePhoto();
            }
        });
        builder.setNegativeButton("拍照", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestCode = TAKE_PHOTO;
                takePhoto();
            }
        });
        builder.show();
    }

    public void choosePhoto() {

        // 在点击事件里动态申请WRITE_EXTERNAL_STORAGE这个危险权限。表示同时授予程序对SD卡读和写的能力
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(intent, requestCode);
        }

    }

    public void takePhoto() {

        // 创建File对象，用于存储拍照后的图片，将之存放在手机SD卡的应用关联缓存目录下
        // 调用getExternalCacheDir()方法可以得到该目录

        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        System.out.println(getExternalCacheDir().toString());
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 进行判断，如果运行设备版本低于Android 7.0就调用Uri的fromFile()方法将File对象转换成Uri对象
        // 否则，就调用FileProvider的getUriForFile()方法将File对象转换成一个封装过的Uri对象
        // 该方法接收三个参数，第一个要求为Context对象，第二个为任意字符串，第三为刚创建的File对象
        // FileProvider是一种特殊的内容提供器，可以选择性地将封装过的Uri共享给外部，从而提高的应用的安全性
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(this, "com.example.cameraalbumtest.fileprovider", outputImage);

        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        System.out.println("imageUri:::" + imageUri.toString());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
        } else {
            openCamera();
        }

        // 启动相机程序
    }

    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (this.requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        //System.out.println("TAKE_PHOTO" + bitmap);
                        tipImage.setImageBitmap(bitmap);
                        //System.out.println("picture" + ((BitmapDrawable)picture.getDrawable()).getBitmap());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        assert data != null;
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {

        String imagePath = null;
        Uri uri = data.getData();

        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document的id处理
            String docId = DocumentsContract.getDocumentId(uri);
            assert uri != null;
            // 如果Uri的authority是meida的格式的话，document需要再进行一次解析，通过字符串分割的方式取出真正的数字id
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                // 解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(docId));
                imagePath = getImagePath(contentUri, null);
            }

        } else {
            assert uri != null;
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                // 如果是content类型的uri，则使用普通方式
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                // 如果是file类型的uri，直接获取图片路径即可
                imagePath = uri.getPath();
            }
        }
        if (BitmapFactory.decodeFile(imagePath) == null) {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        } else {
            tipImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        if (BitmapFactory.decodeFile(imagePath) == null) {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        } else {
            tipImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }
    }

    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }



}
