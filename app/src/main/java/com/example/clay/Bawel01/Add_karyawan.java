package com.example.clay.Bawel01;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class Add_karyawan extends Activity implements View.OnClickListener  {

    Button btn_save,btn_cancel;
    EditText edt_nama,edt_alamat;
    Spinner spinner,spinner_pendidikan,spinner_jabatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_nama = (EditText) findViewById(R.id.editText_nama);
        edt_alamat = (EditText) findViewById(R.id.editText_alamat);

        // Spinner nya mas ini
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner_jabatan =(Spinner)findViewById(R.id.spinner_jabatan);
        spinner_pendidikan =(Spinner)findViewById(R.id.spinner_pendidikan);

        // aksi dari tombol
        btn_save = (Button) findViewById(R.id.button_simpan);
        btn_save.setOnClickListener(this);
        btn_cancel = (Button) findViewById(R.id.button_BATALSIMPAN);
        btn_cancel.setOnClickListener(this);
         }

    private void TambahData(){
        // Ubah setiap View EditText ke tipe Data String
       // radioButton = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

        final String nama = edt_nama.getText().toString().trim();
        final String alamat = edt_alamat.getText().toString().trim();
        final String jabatan = spinner_jabatan.getSelectedItem().toString().trim();
        final String pendidikan = spinner_pendidikan.getSelectedItem().toString().trim();
        final String kelamin = spinner.getSelectedItem().toString().trim();

        // Pembuatan Class AsyncTask yang berfungsi untuk koneksi ke Database Server

        class TambahData extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Add_karyawan.this,"Proses Kirim Data...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Add_karyawan.this,nama+" Telah Tersimpan..!", Toast.LENGTH_LONG).show();
                edt_nama.setText(null); edt_alamat.setText(null);edt_nama.requestFocus();

            }
            private boolean isNetworkAvailable() {
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                // Sesuaikan bagian ini dengan field di tabel Mahasiswa
                params.put(Koneksi.KEY_EMP_NAMA,nama);
                params.put(Koneksi.KEY_EMP_ALAMAT,alamat);
                params.put(Koneksi.KEY_EMP_JABATAN,jabatan);
                params.put(Koneksi.KEY_EMP_PENDIDIKAN,pendidikan);
                params.put(Koneksi.KEY_EMP_JENDER,kelamin);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Koneksi.URL_ADD, params);
                return res;
            }
        }
        // Jadikan Class TambahData Sabagai Object Baru
        TambahData ae = new TambahData();
        ae.execute();
        }

    @Override
    public void onClick(View v) {
        if(v==btn_save){
           TambahData();

        }else if(v == btn_cancel){
            startActivity(new Intent(this,Menu_utama.class));


        }

    }

}







