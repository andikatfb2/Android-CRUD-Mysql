package com.example.clay.Bawel01;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by CLAY on 10/07/2016.
 */
public class Perhitungan extends Activity implements View.OnClickListener  {
   EditText edt_id_karyawan,edt_nama,edt_jabatan,edt_kehadiran,edt_penunjang,edt_penilaian_umum,edt_pengembangan_diri,edt_hasil;
    Button btn_proccess,btn_save,btn_back;
    String id;
    Date dt = new Date();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String tgl =df.format(dt);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perhitungan);
        // pengambilan nilai dari list
        Intent intent = getIntent();
         id = intent.getStringExtra(Koneksi.URL_ID);

        // deklarasikan edit text nya dulu ya gaes...
        edt_id_karyawan =(EditText)findViewById(R.id.edit_perhitunagn_id);
        edt_nama =(EditText)findViewById(R.id.edit_perhitunagn_nama);
        edt_jabatan =(EditText)findViewById(R.id.edit_perhitunagn_jabatan);
        edt_kehadiran =(EditText)findViewById(R.id.edit_perhitunagn_kehadiran);
        edt_penunjang =(EditText)findViewById(R.id.edit_perhitunagn_penunjang);
        edt_penilaian_umum =(EditText)findViewById(R.id.edit_perhitunagn_penilaianUmum);
        edt_pengembangan_diri =(EditText)findViewById(R.id.edit_perhitunagn_pengembanganDiri);
        edt_hasil =(EditText)findViewById(R.id.edit_perhitunagn_hasil);
        // letakan nilai id di kolom juga sebagai parameter
        edt_id_karyawan.setText(id);
        btn_back =(Button)findViewById(R.id.btn_perhitungan_kembali);
        btn_proccess =(Button)findViewById(R.id.btn_perhitungan_proses);
        btn_save =(Button)findViewById(R.id.btn_perhitungan_simpan);
        btn_back.setOnClickListener(this);
        btn_proccess.setOnClickListener(this);
        btn_save.setOnClickListener(this);


        getData();
    }
    // Buat Method GetData untuk ambil data diserver
    private void getData(){
        class getData extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Perhitungan.this,"Proses Data...","Wait...",false,false);
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
            String jabatan =jo.getString(Koneksi.TAG_JABATAN);
// Tampilkan setiap data JSON format kedalam kolom nama dan jabatan saja
            edt_nama.setText(nama);
            edt_jabatan.setText(jabatan);
            edt_kehadiran.requestFocus();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // prosess perhitungan
    public void Proccess(){
        // kondisi apabila ada kolom yg kosong / blm di isi maka proses tidak akan di jalankan
        if(edt_kehadiran.getText().length()>0&&edt_penunjang.getText().length()>0&&edt_penilaian_umum.getText().length()>0
                &&edt_pengembangan_diri.getText().length()>0){
           /*
           * kita deklarasikan C1-C4
           * C1 dan C2 adalah benefit dimana niai akan di bagi dengan nilai max yaitu 1
           * sedangkan C3 dan C4 adalah Cost dimana nilai minimum akan di bagi dengan nilai kriteria
           * setelah di dapat tahap yang terakhir adalah mengalikan dengan Bobot dari masing2 kriteria
           * bobot C1=0.35, C2=0.25, C3=0.2,dan C4=0.2
           * */

            // kriteria
            Double c1=0.00,c2=0.00,c3=0.00,c4=0.00;
            // bobot
            Double b_c1=0.35,b_c2=0.25,b_c3=0.2,b_c4=0.2;
            // Normalisasi yang Di inisialisasikan dengan R1-4
            Double r1=0.00,r2=0.00,r3=0.00,r4=0.00;
            // Penjumlahan kriteria dan pembobotan
            Double v=0.00;

            c1 = Double.parseDouble(edt_kehadiran.getText().toString());
            c2 = Double.parseDouble(edt_penunjang.getText().toString());
            c3 = Double.parseDouble(edt_penilaian_umum.getText().toString());
            c4 = Double.parseDouble(edt_pengembangan_diri.getText().toString());

            // kondisi apabila nilai melebihi angka max proses tidak akan di jalankan
            if(c1>1.00||c2>1.00||c3>1.00||c4>1.00){
                Toast.makeText(this,"Nilain Maximum adalah (1)\n Harap masukan nilai Desimal contoh (0.23)",Toast.LENGTH_LONG).show();
                edt_kehadiran.requestFocus();
            }else{
                // prosess perhitungan
                /*
                * untuk melakukan normalisasi dan mencari nilai Benefit-nya kita gunakan rumus sebagai berkut
                * Rii = ( Xij / max{Xij})
                * Sumber = https://dikutandi.wordpress.com/2014/02/10/contoh-kasus-dan-penerapan-metode-saw-simple-additive-weighting/
                * */
                r1=c1/1;
                r2=c2/1;
                r3=c3/1;
                r4=c4/1;

               // Semua kriteria adalah Benefit sehingga tidak ada kriteria cost can rumus Cost tidak kita Gunakan
                /*
                * untuk melakukan normalisasi dan mencari nilai Cost-nya kita gunakan rumus sebagai berkut
                *Rii = (min{Xij} /Xij)
                * Sumber = https://dikutandi.wordpress.com/2014/02/10/contoh-kasus-dan-penerapan-metode-saw-simple-additive-weighting/
                * */

                /*
                * Maka hasil Normalisasi dari setiap kriteria kita jumlahkan dan kalikan dengan bobot yang sudah di terapkan
                * Rumus =    n
                *       Vi = E .Wj.Rij
                *           j=1
                * */
                v=(r1*b_c1)+(r2*b_c2)+(r3*b_c3)+(r4*b_c4);
                edt_hasil.setText(v.toString());


            }
        }else{
            Toast.makeText(this,"Harap masukan nilai Desimal contoh (0.23)",Toast.LENGTH_LONG).show();
            edt_kehadiran.requestFocus();
        }
    }
    // Simpan data
    private void SimpanHasil(){
        // Ubah setiap View EditText ke tipe Data String
        // radioButton = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        final String id_karyawan = edt_id_karyawan.getText().toString().trim();
        final String tanggal = tgl;
        final String nama = edt_nama.getText().toString().trim();
        final String jabatan = edt_jabatan.getText().toString();
        final String kehadiran = edt_kehadiran.getText().toString().trim();
        final String penunjang = edt_penunjang.getText().toString().trim();
        final String penilaian_umum = edt_penilaian_umum.getText().toString().trim();
        final String pengembangan_diri = edt_pengembangan_diri.getText().toString().trim();
        final String hasil = edt_hasil.getText().toString().trim();
        //SELECT 	MID((tanggal),6,2)as'bulan'  FROM `tb_saw` WHERE 1

        // Pembuatan Class AsyncTask yang berfungsi untuk koneksi ke Database Server
        class SimpanHasil extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Perhitungan.this,"Proses Kirim Data...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Perhitungan.this," Tersimpan..!"+s, Toast.LENGTH_LONG).show();
                finish(); startActivity(new Intent(Perhitungan.this, Menu_utama.class));
            }
            private boolean isNetworkAvailable() {
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> perhitungan = new HashMap<>();
                // Sesuaikan bagian ini dengan field di tabel Mahasiswa
                perhitungan.put(Koneksi2.KEY_EMP_TANGGAL,tanggal);
                perhitungan.put(Koneksi2.KEY_EMP_ID_KARYAWAN,id_karyawan);
                perhitungan.put(Koneksi2.KEY_EMP_NAMA,nama);
                perhitungan.put(Koneksi2.KEY_EMP_JABATAN,jabatan);
                perhitungan.put(Koneksi2.KEY_EMP_KEHADIRAN,kehadiran);
                perhitungan.put(Koneksi2.KEY_EMP_PENUNJANG,penunjang);
                perhitungan.put(Koneksi2.KEY_EMP_PENILAIAN_UMUM,penilaian_umum);
                perhitungan.put(Koneksi2.KEY_EMP_PENGEMBANGAN_DIRI,pengembangan_diri);
                perhitungan.put(Koneksi2.KEY_EMP_HASIL,hasil);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Koneksi2.URL_ADD, perhitungan);
                return res;
            }
        }
        // Jadikan Class TambahData Sabagai Object Baru
        SimpanHasil ae = new SimpanHasil();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v==btn_back){
            finish();
            startActivity(new Intent(this, Menu_utama.class));
        }else if(v==btn_proccess){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Apakah Anda Sudah Memasukan Nilai Kriteria Dengan Benar.?");

            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // proses perhitungan akan di jalankan
                            Proccess();

                        }
                    });

            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // exit.setBackgroundColor(Color.TRANSPARENT);
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }else if(v==btn_save){
            if(edt_hasil.getText().length()>0){
                SimpanHasil();
            }else{
                Toast.makeText(this,"Anda Belum Melakukan Perhitungan",Toast.LENGTH_LONG).show();
                edt_kehadiran.requestFocus();
            }
        }

    }

}
