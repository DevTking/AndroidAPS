package so.ttq.cracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

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
        setContentView(R.layout.aty_checkmain);
        tv = findViewById(R.id.check_tv);
        loading = findViewById(R.id.check_loading);

        loading.setVisibility(View.INVISIBLE);
        tv.setText("permission error");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this
                    , new String[]{
                            Manifest.permission.READ_PHONE_STATE
                    }, 1
            );
        } else {
            checking();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            checking();
        } else {
            ActivityCompat.requestPermissions(this
                    , new String[]{
                            Manifest.permission.READ_PHONE_STATE
                    }, 1
            );
        }
    }

    @SuppressLint("MissingPermission")
    private void checking() {
        try {
            try {
                loading.setVisibility(View.VISIBLE);

                final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                final String tag = tm.getDeviceId() + "";

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

    private void showUiToast(final String text) {
        handler.post(() -> {
            Toast.makeText(CheckMainAty.this, text, Toast.LENGTH_SHORT).show();
        });
    }

    private static class NetReturn {
        int retCode;
    }
}
