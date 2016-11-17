package com.example.clay.Bawel01;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by CLAY on 13/07/2016.
 */
public class Detail_Hasil extends Activity implements View.OnClickListener {
    TextView txt_id,txt_nama,txt_jabatan,txt_kehadiran,txt_penunhjang,txt_penilaian_umum,txt_pengembangan_diri,txt_hasil;
    Button back,delete;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_hasil_perhitungan);
        Intent intent = getIntent();// mengambil nilai id dari kelas detail data

        id = intent.getStringExtra(Koneksi2.URL_ID);

        // deklarasikan
        txt_id =(TextView)findViewById(R.id.view_id);
        txt_nama =(TextView)findViewById(R.id.view_nama);
        txt_jabatan =(TextView)findViewById(R.id.view_jabatan);
        txt_kehadiran =(TextView)findViewById(R.id.view_kehadiran);
        txt_penunhjang =(TextView)findViewById(R.id.view_penunjang);
        txt_penilaian_umum =(TextView)findViewById(R.id.view_p_umum);
        txt_pengembangan_diri =(TextView)findViewById(R.id.view_p_diri);
        txt_hasil =(TextView)findViewById(R.id.view_hasil);

        txt_id.setText(id);

        delete= (Button)findViewById(R.id.btn_hasil_delete);
        back = (Button)findViewById(R.id.btn_hasil_back);
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
        getData();
    }

    // Buat Method GetData untuk ambil data diserver
    private void getData(){
        class getData extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Detail_Hasil.this,"Proses Data...","Wait...",false,false);
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
                String s = rh.sendGetRequestParam(Koneksi2.URL_GET_ID,id);
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
            JSONArray result = jsonObject.getJSONArray(Koneksi2.TAG_JSON_ARRAY);
            JSONObject jo = result.getJSONObject(0);
            // Data berdasarkan di Tabel Database
            String nama = jo.getString(Koneksi2.TAG_NAMA);
            String jabatan =jo.getString(Koneksi2.TAG_JABATAN);
            String kehadiran = jo.getString(Koneksi2.TAG_KEHADIRAN);
            String penunjang = jo.getString(Koneksi2.TAG_PENUNJANG);
            String penilaian_umum = jo.getString(Koneksi2.TAG_PENILAIAN_UMUM);
            String pengembangan_diri = jo.getString(Koneksi2.TAG_PENGEMBANGAN_DIRI);
            String hasil = jo.getString(Koneksi2.TAG_HASIL);
// Tampilkan setiap data JSON format kedalam setiap EditText
            txt_nama.setText(nama);
            txt_jabatan.setText(jabatan);
            txt_kehadiran.setText(kehadiran);
            txt_penunhjang.setText(penunjang);
            txt_penilaian_umum.setText(penilaian_umum);
            txt_pengembangan_diri.setText(pengembangan_diri);
            txt_hasil.setText(hasil);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                        delete.setBackgroundColor(Color.WHITE);
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
                loading = ProgressDialog.show(Detail_Hasil.this, "Delete Data...", "Wait...", false, false);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Detail_Hasil.this, "Terhapus..", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Detail_Hasil.this, List_Hasil.class));

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Koneksi2.URL_DELETE_EMP,id);
                return s;
            }
        }

        DeleteData de = new DeleteData();
        de.execute();
    }

    @Override
    public void onClick(View v) {
        if(v==back){
            back.setBackgroundColor(Color.BLUE);
            finish(); startActivity(new Intent(Detail_Hasil.this,Menu_utama.class));
        }else if(v==delete){
            delete.setBackgroundColor(Color.BLUE);
            confirmDelete();

        }

    }
}
