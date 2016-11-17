package com.example.clay.Bawel01;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CLAY on 29/06/2016.
 */
public class List_karyawan2 extends Activity implements ListView.OnItemClickListener{
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(List_karyawan2.this,Perhitungan.class);
        HashMap<String,String> map = (HashMap)parent.getItemAtPosition(position);

        // selanjutnya kita akan mengambil data berdasarkan id. TAG_ID dari clas Koneksi
        String empId = map.get(Koneksi.TAG_ID).toString();
        intent.putExtra(Koneksi.URL_ID,empId);
        // mulai akctivity
        startActivity(intent);

    }

    private ListView lView;
    private String Json_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_perhitungan);
        lView = (ListView)findViewById(R.id.listViewRead2);
        lView.setOnItemClickListener(this);
        getJSON();


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
                String jabatan =jo.getString(Koneksi.TAG_JABATAN);

                HashMap<String,String> karyawan = new HashMap<>();
                karyawan.put(Koneksi.TAG_ID,id);
                karyawan.put(Koneksi.TAG_NAMA,"). "+nama);
                karyawan.put(Koneksi.TAG_JABATAN,"\t ("+jabatan+")");
                list.add(karyawan);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        ListAdapter listAdapter = new SimpleAdapter(
            List_karyawan2.this,list,R.layout.lihatdata_perhitungan,new String[]{Koneksi.TAG_ID,Koneksi.TAG_NAMA,Koneksi.TAG_JABATAN},
            new int[]{R.id.id2,R.id.nama2,R.id.jabatan});
        lView.setAdapter(listAdapter);

        }
    public void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(List_karyawan2.this,"Pengambilan Data","Wait...",false,false);
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
    }

