package so.ttq.cracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import info.nightscout.androidaps.MainActivity;
import info.nightscout.androidaps.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by [dev.tking] on 2018/10/30.
 * Copyright (c) 2018 [dev.tking@outlook.com] All Rights Reserved.
 * -----------------------------------------------------
 * |                       _oo0oo_                     |
 * |                      o8888888o                    |
 * |                      88" . "88                    |
 * |                      (| -_- |)                    |
 * |                      0\  =  /0                    |
 * |                    ___/`---'\___                  |
 * |                  .' \\|     |# '.                 |
 * |                 / \\|||  :  |||# \                |
 * |                / _||||| -:- |||||- \              |
 * |               |   | \\\  -  #/ |   |              |
 * |               | \_|  ''\---/''  |_/ |             |
 * |               \  .-\__  '-'  ___/-. /             |
 * |             ___'. .'  /--.--\  `. .'___           |
 * |          ."" '<  `.___\_<|>_/___.' >' "".         |
 * |         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       |
 * |         \  \ `_.   \_ __\ /__ _/   .-` /  /       |
 * |     =====`-.____`.___ \_____/___.-`___.-'=====    |
 * |                       `=---='                     |
 * |     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   |
 * |                                                   |
 * |          Buddha bless          never BUG          |
 * -----------------------------------------------------
 */
public class CheckMainAty extends AppCompatActivity {
    static final String KEY = CheckMainAty.class.getName();
    private Handler handler = new Handler();
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new GsonBuilder().create();
    private TextView tv;
    private View loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        setContentView(R.layout.aty_checkmain);
        tv = findViewById(R.id.check_tv);
        loading = findViewById(R.id.check_loading);

        loading.setVisibility(View.INVISIBLE);
        tv.setText("permission error");
        findViewById(R.id.btn_copy).setOnClickListener(v -> disposeCopy());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this
                    , new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                            , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 1
            );
        } else {
            checking();
        }
    }

    /**
     * 复制内容到剪切板
     *
     * @param copyStr
     * @return
     */
    private boolean copy(String copyStr) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void disposeCopy() {
        String tag = tv.getText().toString().trim();
        copy(tag);
        Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            checking();
        } else {
            ActivityCompat.requestPermissions(this
                    , new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                            , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 1
            );
        }
    }

    @SuppressLint("MissingPermission")
    private void checking() {
        try {
            try {
                loading.setVisibility(View.VISIBLE);
                final String tag = loadTag();

                tv.setText(tag);

                new Thread(() -> {
                    try {
                        try {
                            Response response = client.newCall(new Request.Builder().url("https://https.ttq.so/web/Aps/signIn.html?deviceKey=" + tag).build()).execute();
                            String str = response.body().string();
                            NetReturn netReturn = gson.fromJson(str, NetReturn.class);

                            if (1 == netReturn.retCode) {
                                handler.post(() -> {
                                    Intent intent = new Intent(CheckMainAty.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                });
                            } else {
                                showUiToast("denied");
                                return;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            showUiToast("net error");
                            return;
                        }
                    } finally {

                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    private String loadTag() {
        String tag = loadTagFromDisk();
        if (null == tag) {
            tag = UUID.randomUUID().toString().replace("-", "");
            saveTagToDisk(tag);
            Log.d("LoadTag", "新建 tag->" + tag);
        } else {
            Log.d("LoadTag", "重复 tag->" + tag);
        }
        return tag;
    }

    private void saveTagToDisk(String tag) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(getPackageName() + File.separator + "tags"), "device");
            if (file.getParentFile().mkdirs()) {
                Log.d("LoadTag", "create dirs->" + file.getParentFile().getAbsolutePath());
            }
            RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
            accessFile.writeUTF(tag);
            accessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadTagFromDisk() {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(getPackageName() + File.separator + "tags"), "device");
            RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
            String tag = accessFile.readUTF();
            return tag;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showUiToast(final String text) {
        handler.post(() -> {
            Toast.makeText(CheckMainAty.this, text, Toast.LENGTH_SHORT).show();
        });
    }

    private static class NetReturn {
        int retCode;
    }
}
