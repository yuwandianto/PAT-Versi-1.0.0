package com.yuwandianto.patsman1jorong;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity3 extends AppCompatActivity {
    WebView webviewku;
    WebSettings websetingku;
    TextView txtKu;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main3);

        txtKu = (TextView)findViewById(R.id.txtKu);

        webviewku = (WebView)findViewById(R.id.webView);
        websetingku = webviewku.getSettings();
        websetingku.setJavaScriptEnabled(true);
        websetingku.setDomStorageEnabled(true);
        websetingku.setLoadWithOverviewMode(true);
        websetingku.setUseWideViewPort(true);
        websetingku.setBuiltInZoomControls(true);
        websetingku.setDisplayZoomControls(false);
        websetingku.setSupportZoom(true);
        websetingku.setDefaultTextEncodingName("utf-8");
        webviewku.setWebViewClient(new WebViewClient());

        if (!isConnected2(MainActivity3.this)) {
            txtKu.setText("Silakan cek koneksi internet anda !");
            webviewku.destroy();
        } else {

            String alamat = getIntent().getStringExtra("alamat");

            if (alamat.equals("0")) {
                txtKu.setText("Maaf, soal belum diaktifkan. silakan sesuaikan dengan jadwal yang ada");
                webviewku.destroy();
            } else if (alamat.equals("1")) {
                txtKu.setText("Maaf, jadwal untuk pelajaran ini sudah berakhir.");
                webviewku.destroy();
            } else {
                webviewku.loadUrl(alamat);
            }
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(),0);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(),0);
    }


    //    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        hideNavigationBar();
//    }
//
//    private void hideNavigationBar() {
//        final View decorView = this.getWindow().getDecorView();
//        final int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                MainActivity3.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        decorView.setSystemUiVisibility(uiOptions);
//                    }
//                });
//            }
//        };
//        timer.scheduleAtFixedRate(task, 1,2);
//    }

    private boolean isConnected2(MainActivity3 mainActivity3) {

        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity3.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobiledata = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifi != null && wifi.isConnected()) || (mobiledata != null && mobiledata.isConnected());
    }
}