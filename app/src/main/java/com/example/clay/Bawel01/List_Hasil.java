package com.example.clay.Bawel01;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CLAY on 13/07/2016.
 */
public class List_Hasil extends Activity implements ListView.OnItemClickListener {
    Button back;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(List_Hasil.this,Detail_Hasil.class);
        HashMap<String,String> map = (HashMap)parent.getItemAtPosition(position);

        // selanjutnya kita akan mengambil data berdasarkan id. TAG_ID dari clas Koneksi
        String empId = map.get(Koneksi.TAG_ID).toString();
        intent.putExtra(Koneksi2.URL_ID,empId);
        // mulai akctivity
        startActivity(intent);
    }
    private ListView lView;
    private String Json_STRING;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_hasil_perhitungan);
        lView = (ListView)findViewById(R.id.list_hasil_perhitungan);
        lView.setOnItemClickListener(this);
        getJSON();
        back =(Button)findViewById(R.id.button_kembaliHasilPerhitungan);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setBackgroundColor(Color.BLUE);
               finish(); startActivity(new Intent(List_Hasil.this,Menu_utama.class));
            }
        });
    }
    private void TampilData(){
        JSONObject jsonObject =null;
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(Json_STRING);
            JSONArray result = jsonObject.getJSONArray(Koneksi.TAG_JSON_ARRAY);
            // fungsi perulangan untuk mengambil baris pada table
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                String id = jo.getString(Koneksi2.TAG_ID);
                String nama =jo.getString(Koneksi2.TAG_NAMA);
                String jabatan =jo.getString(Koneksi2.TAG_JABATAN);
                String hasil =jo.getString(Koneksi2.TAG_HASIL);

                HashMap<String,String> karyawan = new HashMap<>();
                karyawan.put(Koneksi2.TAG_ID,id);
                karyawan.put(Koneksi2.TAG_NAMA,". "+nama);
                karyawan.put(Koneksi2.TAG_HASIL," ("+hasil+")");
                list.add(karyawan);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        ListAdapter listAdapter = new SimpleAdapter(
                List_Hasil.this,list,R.layout.ligat_hasil_perhitungan,new String[]{Koneksi2.TAG_ID,Koneksi2.TAG_NAMA,Koneksi2.TAG_JABATAN,Koneksi2.TAG_HASIL},
                new int[]{R.id.id3,R.id.nama3,R.id.jabatan1,R.id.hasil});
        lView.setAdapter(listAdapter);

    }
    public void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(List_Hasil.this,"Pengambilan Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Json_STRING = s;
                TampilData();
            }

            @Override
            protected String doInBackground(Void... v) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Koneksi2.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

}
