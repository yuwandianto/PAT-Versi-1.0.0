package com.yuwandianto.patsman1jorong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity3 extends AppCompatActivity {
    WebView webviewku;
    WebSettings websetingku;
    TextView txtKu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private boolean isConnected2(MainActivity3 mainActivity3) {

        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity3.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobiledata = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi != null && wifi.isConnected()) || (mobiledata != null && mobiledata.isConnected())) {
            return true;
        } else {
            return false;
        }
    }
}