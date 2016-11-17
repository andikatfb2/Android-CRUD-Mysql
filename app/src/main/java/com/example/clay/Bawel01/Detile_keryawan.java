package com.example.clay.Bawel01;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.graphics.Color.BLUE;

/**
 * Created by CLAY on 29/06/2016.
 */
public class Detile_keryawan extends Activity implements View.OnClickListener {
    Button btnUpdate,btnDelete,btnBack;
    EditText edt_id,edt_nama,edt_jabatan,edt_pendidikan,edt_alamat,edt_kelamin;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aturdata);
        Intent intent = getIntent();// mengambil nilai id dari kelas detail data

        id = intent.getStringExtra(Koneksi.URL_ID);

        edt_id =(EditText)findViewById(R.id.edit_id);
        edt_nama =(EditText)findViewById(R.id.edit_nama);
        edt_kelamin =(EditText)findViewById(R.id.edit_kelamin);
        edt_jabatan =(EditText)findViewById(R.id.edit_Jabatan);
        edt_pendidikan =(EditText)findViewById(R.id.edit_pendidikan);
        edt_alamat =(EditText)findViewById(R.id.edit_alamat);

        edt_id.setText(id);
        btnUpdate = (Button)findViewById(R.id.button_update);
        btnDelete = (Button)findViewById(R.id.button_delete);
        btnBack = (Button)findViewById(R.id.button_back);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        // panggil perintah getData
        getData();
    }
    // Buat Method GetData untuk ambil data diserver
    private void getData(){
        class getData extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Detile_keryawan.this,"Proses Data...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                // Method Show Data
                ShowData(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Koneksi.URL_GET_ID,id);
                return s;
            }
        }
        getData ge = new getData();
        ge.execute();
    }

    // Method ShowData untuk tampilkan data pada setiap EditText
    private void ShowData(String json){
        try {
            // Jadikan sebagai JSON object
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Koneksi.TAG_JSON_ARRAY);
            JSONObject jo = result.getJSONObject(0);
            // Data berdasarkan di Tabel Database
            String nama = jo.getString(Koneksi.TAG_NAMA);
            String kelamin =jo.getString(Koneksi.TAG_JENDER);
            String pendidikan = jo.getString(Koneksi.TAG_PENDIDIKAN);
            String jabatan = jo.getString(Koneksi.TAG_JABATAN);
            String alamat = jo.getString(Koneksi.TAG_ALAMAT);
// Tampilkan setiap data JSON format kedalam setiap EditText
            edt_nama.setText(nama);
            edt_kelamin.setText(kelamin);
            edt_pendidikan.setText(pendidikan);
            edt_jabatan.setText(jabatan);
            edt_alamat.setText(alamat);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Method Untuk Update Data
    private void updateData(){
        final String nama = edt_nama.getText().toString().trim();
        final String kelamin = edt_kelamin.getText().toString().trim();
        final String pendidikan = edt_pendidikan.getText().toString().trim();
        final String jabatan = edt_jabatan.getText().toString().trim();
        final String alamat = edt_alamat.getText().toString().trim();

        class updateData extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Detile_keryawan.this,"Update Data...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Detile_keryawan.this, "Karyawan "+nama+" TerUpdate.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Detile_keryawan.this, List_karyawan.class));

            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Koneksi.KEY_EMP_ID,id);
                hashMap.put(Koneksi.KEY_EMP_NAMA,nama);
                hashMap.put(Koneksi.KEY_EMP_JENDER,kelamin);
                hashMap.put(Koneksi.KEY_EMP_PENDIDIKAN,pendidikan);
                hashMap.put(Koneksi.KEY_EMP_JABATAN,jabatan);
                hashMap.put(Koneksi.KEY_EMP_ALAMAT,alamat);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Koneksi.URL_UPDATE_EMP, hashMap);

                return s;
            }
        }

        updateData ue = new updateData();
        ue.execute();
    }
    private void confirmDelete(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Hapus Data Karyawan.?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Buatkan method hapus data kemudian dipanggil disini
                        deleteData();

                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        btnDelete.setBackgroundColor(Color.CYAN);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // Method Hapus Data
    private void deleteData(){
        class DeleteData extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Detile_keryawan.this, "Delete Data...", "Wait...", false, false);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Detile_keryawan.this, "Terhapus..", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Detile_keryawan.this, List_karyawan.class));

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Koneksi.URL_DELETE_EMP, id);
                return s;
            }
        }

        DeleteData de = new DeleteData();
        de.execute();
    }

    @Override
    public void onClick(View v) {
        if(v==btnUpdate){
            btnUpdate.setBackgroundColor(Color.BLUE);
            updateData();
        }else if(v==btnDelete){
            btnDelete.setBackgroundColor(Color.BLUE);

            confirmDelete();
        }else if(v==btnBack){
            btnBack.setBackgroundColor(Color.BLUE);
            // startActivity(new Intent(this,List_karyawan.class));
            Intent intent = new Intent(Detile_keryawan.this,Menu_utama.class);
            finish(); startActivity(intent);

        }

    }
}
