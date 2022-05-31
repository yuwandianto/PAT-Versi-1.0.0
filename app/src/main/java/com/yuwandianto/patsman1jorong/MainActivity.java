package com.yuwandianto.patsman1jorong;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ProgressDialog loading, statusKoneksi;


    public ListView lv;

    String namaKelas, alamat;

    private static String JSON_URL = "https://json.yuwandianto.web.id/data_kelas.json";

    ArrayList<HashMap<String,String>> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isConnected(this)) {
            startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
            finish();
        } else {

            loading = ProgressDialog.show(MainActivity.this,"Silakan tunggu !","Sedang memuat data ...");

            listData = new ArrayList<>();

            lv = findViewById(R.id.listKelas);

            getData getData = new getData();
            getData.execute();
        }



    }

    private void showCostumDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Silakan periksa koneksi internet anda").
                setCancelable(false)
                .setPositiveButton("Pengaturan Koneksi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        startActivity(new Intent(Settings.ACTION_DATA_USAGE_SETTINGS));
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }


    private boolean isConnected(MainActivity mainActivity) {

        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobiledata = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi != null && wifi.isConnected()) || (mobiledata != null && mobiledata.isConnected())) {
            return true;
        } else {
            return false;
        }
    }


    public class getData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            String curent = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);

                    int data = isr.read();

                    while (data != -1) {
                        curent += (char) data;
                        data = isr.read();

                    }

                    return curent;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if (urlConnection != null ) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return curent;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    namaKelas = jsonObject1.getString("kelas");

                    HashMap<String,String> data = new HashMap<>();

                    data.put("namaKelas", namaKelas);

                    listData.add(data);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ListAdapter listAdapter = new SimpleAdapter(
                    MainActivity.this,
                    listData,
                    R.layout.item_data,
                    new String[] {"namaKelas"},
                    new int[] {R.id.namaKelas}
            );

            lv.setAdapter(listAdapter);
            loading.cancel();
            lv.setClickable(true);

            Toast.makeText(MainActivity.this, "Data berhasil di ambil dari server, silakan pilih kelas", Toast.LENGTH_SHORT).show();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    JSONObject jsonObjecta = null;
                    try {
                        jsonObjecta = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONObject jsonArraya = jsonObjecta.getJSONArray("data").getJSONObject(position);
                        daftarMapel(jsonArraya);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }

    private void daftarMapel(JSONObject mapel) {

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("mapel", mapel.toString());
        startActivity(intent);

    }

}