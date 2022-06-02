package com.yuwandianto.patsman1jorong;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    public ListView lv2;

    String namaMapel;

    ArrayList<HashMap<String, String>> listData;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listData = new ArrayList<>();

        lv2 = findViewById(R.id.listMapel);

        JSONObject mapel = null;
        try {
           mapel = new JSONObject(getIntent().getStringExtra("mapel"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray daftarMapel = mapel.getJSONArray("MataPelajaran");
            String namaKelas = mapel.getString("kelas");

            TextView txtPilihMapel = findViewById(R.id.txtPilihMapel);
            txtPilihMapel.setText("Daftar Mata Pelajaran "+namaKelas);

            for (int i = 0; i < daftarMapel.length(); i++) {

                JSONObject namamapel = new JSONObject(daftarMapel.getString(i));
                namaMapel = namamapel.getString("namaMapel");
                HashMap<String,String> data = new HashMap<>();

                data.put("namaMapel", namaMapel);

                listData.add(data);

            }

            ListAdapter listAdapter = new SimpleAdapter(
                    MainActivity2.this,
                    listData,
                    R.layout.data_mapel,
                    new String[] {"namaMapel"},
                    new int[] {R.id.namaMapel}
            );

            lv2.setAdapter(listAdapter);

            lv2.setClickable(true);

            lv2.setOnItemClickListener((adapterView, view, position, l) -> {
                try {
                    String alamat = daftarMapel.getJSONObject(position).getString("alamat");
                    keweb(alamat);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void keweb(String alamat) {
        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
        intent.putExtra("alamat", alamat);
        startActivity(intent);
    }


}