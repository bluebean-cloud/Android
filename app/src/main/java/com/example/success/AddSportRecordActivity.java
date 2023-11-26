package com.example.success;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AddSportRecordActivity extends Activity {
    public EditText editTextSportName;
    public EditText editTextDuration;
    public EditText editTextSportLocation;
    public ImageView record_photo1;
    public ImageView record_photo2;

    private final int TAKE_PHOTO = 4;
    private final int CHOOSE_PHOTO = 5;
    private int requestCode = 0;

    private Uri imageUri;

    private ImageView curImage;

    private int imageIndex = 0; //如果imageIndex为0,说明没有上传图片


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sport_record);
        // FindView
        editTextSportName = findViewById(R.id.editTextSportName);
        editTextDuration = findViewById(R.id.editTextDuration);
        editTextSportLocation = findViewById(R.id.editTextSportLocation);
        record_photo1 = findViewById(R.id.record_photo1);
        record_photo2 = findViewById(R.id.record_photo2);
        curImage = record_photo1;
        // 点击editText可以选择运动种类
        setChooseSport();
        // 点击时间editText可以选择时间日期
        setChooseTime();
    }

    public void setChooseSport() {
        editTextSportName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建 PopupMenu
                PopupMenu popupMenu = new PopupMenu(AddSportRecordActivity.this, editTextSportName);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popupMenu.getMenu()); // 添加菜单项

                // 设置菜单项点击事件
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_other) {
                            // 如果选择了"其他"
                            showCustomInputDialog();
                            return true;
                        } else {
                            // 用户选择了一个选项
                            editTextSportName.setText(item.getTitle());
                            return true;
                        }
                    }
                });

                // 显示 PopupMenu
                popupMenu.show();
            }
        });
    }

    private void showCustomInputDialog() {
        // 创建一个对话框，包含一个 EditText 来让用户输入自定义内容
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("运动项目");
        final EditText input = new EditText(this);
        builder.setView(input);

        // 设置对话框按钮点击事件
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String customInput = input.getText().toString();
                editTextSportName.setText(customInput);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // 显示对话框
        builder.show();
    }

    public void setChooseTime() {
        Calendar calendar = Calendar.getInstance();
        editTextDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前日期的年、月、日、时、分
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                // 创建日期选择框（DatePickerDialog）
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddSportRecordActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                                // 创建时间选择框（TimePickerDialog）
                                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSportRecordActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @SuppressLint("SetTextI18n")
                                            @Override
                                            public void onTimeSet(TimePicker view, int selectedHourOfDay, int selectedMinute) {
                                                // 处理选择的日期和时间
                                                // 格式化分钟部分，确保是两位数
                                                @SuppressLint("DefaultLocale") String formattedHour = String.format("%02d", selectedHourOfDay);
                                                @SuppressLint("DefaultLocale") String formattedMinute = String.format("%02d", selectedMinute);
                                                // 更新 EditText 显示选择的日期和时间
                                                editTextDuration.setText(selectedYear + "-" + (selectedMonth + 1) + "-" +
                                                        selectedDayOfMonth + " " + formattedHour + ":" + formattedMinute);
                                            }
                                        }, hour, minute, true);

                                // 显示时间选择框
                                timePickerDialog.show();
                            }
                        }, year, month, day);

                // 显示日期选择框
                datePickerDialog.show();
            }
        });
    }

    public void onConfirmButtonClick(View view) {
        //添加填写为空的提醒
        if (editTextSportName.getText().toString().isEmpty()) {
            Toast.makeText(this, "运动项目不能为空", Toast.LENGTH_SHORT).show();
        } else if (editTextDuration.getText().toString().isEmpty()) {
            Toast.makeText(this, "运动时间不能为空", Toast.LENGTH_SHORT).show();
        } else if (editTextSportLocation.getText().toString().isEmpty()) {
            Toast.makeText(this, "运动地点不能为空", Toast.LENGTH_SHORT).show();
        } else if (imageIndex == 0) { //如果imageIndex为0,说明没有上传图片
            Toast.makeText(this, "请至少上传一张照片", Toast.LENGTH_SHORT).show();
        } else {
            // image_num
            int image_num = 1;
            // 保存运动记录数据
            String sportName = editTextSportName.getText().toString();
            String duration = editTextDuration.getText().toString();
            String sportLocation = editTextSportLocation.getText().toString();
            Bitmap bitmap1 = ((BitmapDrawable) record_photo1.getDrawable()).getBitmap();
            Bitmap bitmap2 = null;
            //不仅不是空,并且不能是上传图标
            if (imageIndex >= 2) { //上传了两张图片
                bitmap2 = ((BitmapDrawable) record_photo2.getDrawable()).getBitmap();
                image_num = 2;
            }
            // 将Bitmap转换为字节数组
            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
            byte[] byteArray1 = stream1.toByteArray();
            byte[] byteArray2 = null;
            if (image_num == 2) {
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                byteArray2 = stream2.toByteArray();
            }
            // 创建一个Intent，将结果传递回上一个界面
            Intent resultIntent = new Intent();
            //将新记录添加到数据库中
            DatabaseInterface db = MainActivity.db;
            db.createSportRecord(CurrentUser.getUser().getId(), sportName, duration, sportLocation, getCurrentTime(), byteArray1, byteArray2);
            setResult(RESULT_OK, resultIntent);
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            // 结束当前界面，返回到上一个界面
            finish();
        }
    }

    public String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        // 设置格式化的SimpleDateFormat对象，指定中国语言环境
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        // 创建时区（TimeZone）对象，设置时区为“亚洲/重庆"
        TimeZone TZ = TimeZone.getTimeZone("Asia/Chongqing");
        // 将SimpleDateFormat强制转换为DateFormat
        DateFormat df = null;
        try {
            df = (DateFormat) sdf;
        } catch (Exception E) {
            E.printStackTrace();
        }
        // 为DateFormat对象设置时区
        df.setTimeZone(TZ);
        // 获取时间表达式
        return df.format(cal.getTime());
    }

    public void addSportPhoto(View view) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("请选择照片");
        builder.setPositiveButton("打开相册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestCode = CHOOSE_PHOTO;
                choosePhoto();
                if (imageIndex == 0) {
                    curImage = record_photo1;
                    imageIndex++;
                } else if (imageIndex == 1) {
                    curImage = record_photo2;
                    imageIndex++;
                } else {
                    curImage = record_photo2;
                }
            }
        });
        builder.setNegativeButton("拍照", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestCode = TAKE_PHOTO;
                takePhoto();
                if (imageIndex == 0) {
                    curImage = record_photo1;
                    imageIndex++;
                } else if (imageIndex == 1) {
                    curImage = record_photo2;
                    imageIndex++;
                } else {
                    curImage = record_photo2;
                }
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
                        curImage.setImageBitmap(bitmap);
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
        if (imageIndex == 1) {
            Drawable drawable = getResources().getDrawable(R.drawable.upload);
            record_photo2.setImageDrawable(drawable);
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
            curImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        if (BitmapFactory.decodeFile(imagePath) == null) {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        } else {
            curImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
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