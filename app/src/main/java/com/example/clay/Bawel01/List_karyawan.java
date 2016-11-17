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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CLAY on 29/06/2016.
 */
public class List_karyawan extends Activity implements ListView.OnItemClickListener{

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,Detile_keryawan.class);
        HashMap<String,String> map = (HashMap)parent.getItemAtPosition(position);

        // selanjutnya kita akan mengambil data berdasarkan id. TAG_ID dari clas Koneksi
        String empId = map.get(Koneksi.TAG_ID).toString();
        intent.putExtra(Koneksi.URL_ID,empId);
        // mulai akctivity
        startActivity(intent);
    }
    private ListView lView;
    private String Json_STRING;
    public static EditText edt_cari;
    String nama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        lView = (ListView)findViewById(R.id.listViewRead);
        lView.setOnItemClickListener(this);
        getJSON();
        Button refresh = (Button)findViewById(R.id.button_REFRESH);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJSON();
            }
        });
        final Button btn_kembali = (Button)findViewById(R.id.button_kembaliHasilPerhitungan);
        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_kembali.setBackgroundColor(Color.BLUE);
                startActivity(new Intent(List_karyawan.this, Menu_utama.class));
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

                String id = jo.getString(Koneksi.TAG_ID);
                String nama =jo.getString(Koneksi.TAG_NAMA);

                HashMap<String,String> karyawan = new HashMap<>();
                karyawan.put(Koneksi.TAG_ID,id);
                karyawan.put(Koneksi.TAG_NAMA,". "+nama);
                list.add(karyawan);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        ListAdapter listAdapter = new SimpleAdapter(
            List_karyawan.this,list,R.layout.lihatdata,new String[]{Koneksi.TAG_ID,Koneksi.TAG_NAMA},
            new int[]{R.id.id,R.id.nama});
        lView.setAdapter(listAdapter);

        }
    public void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(List_karyawan.this,"Pengambilan Data","Wait...",false,false);
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
                String s = rh.sendGetRequest(Koneksi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    // cari data karyawan
    public void getJSONcari(){
        class getJSONcari extends AsyncTask<Void,Void,String>{
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(List_karyawan.this,"Pengambilan Data","Wait...",false,false);
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
                String s = rh.sendGetRequest(Koneksi.URL_SEARCH_EMP);
                return s;
            }
        }
        getJSONcari gj = new getJSONcari();
        gj.execute();
    }
    // cari
    private void CariKaryawan(){
        // Ubah setiap View EditText ke tipe Data String
        // radioButton = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

       // Pembuatan Class AsyncTask yang berfungsi untuk koneksi ke Database Server

        class CariKaryawan extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(List_karyawan.this,"Proses Kirim Data...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(List_karyawan.this,nama+" Pencarian..!", Toast.LENGTH_LONG).show();


            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                // Sesuaikan bagian ini dengan field di tabel Mahasiswa
                params.put(Koneksi.KEY_EMP_NAMA,nama);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendGetRequestParam(Koneksi.URL_SEARCH_EMP, "JIMI");

                return res;
            }
        }
        // Jadikan Class TambahData Sabagai Object Baru
        CariKaryawan ae = new CariKaryawan();
        ae.execute();
    }
    }

