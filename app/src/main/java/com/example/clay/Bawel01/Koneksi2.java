package com.example.clay.Bawel01;

/**
 * Created by CLAY on 29/06/2016.
 */
public class Koneksi2 {
    //http://bawel01.netai.net/
    // Kita buatkan URL untuk Input data, sama kan dengan letak anda taruh script PHP
    //public static final String URL_ADD="http://bawel01.netai.net/create.php";
    // public static final String URL_DELETE_EMP="http://192.168.43.176/crud/karyawan/delete.php?id=";
// lempar data ke codding php dengan metod  = POST http://bawel01.comxa.com/perhitungan/read.php
    public static final String URL_ADD="http://bawel01.comxa.com/perhitungan/create.php";
    public static final String URL_GET_ALL = "http://bawel01.comxa.com/perhitungan/read.php";
    public static final String URL_GET_ID="http://bawel01.comxa.com/perhitungan/get_data.php?id=";
    // Link Untuk Hapus Data
    public static final String URL_DELETE_EMP="http://bawel01.comxa.com/perhitungan/delete.php?id=";

    // Field yang digunakan untuk dikirimkan ke Database, sesuaikan saja dengan Field di Tabel Mahasiswa
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_TANGGAL = "tanggal";
    public static final String KEY_EMP_ID_KARYAWAN = "id_karyawan";
    public static final String KEY_EMP_NAMA = "nama";
    public static final String KEY_EMP_JABATAN = "jabatan";
    public static final String KEY_EMP_KEHADIRAN = "kehadiran";
    public static final String KEY_EMP_PENUNJANG = "penunjang";
    public static final String KEY_EMP_PENILAIAN_UMUM = "penilaian_umum";
    public static final String KEY_EMP_PENGEMBANGAN_DIRI = "pengembangan_diri";
    public static final String KEY_EMP_HASIL = "hasil";

    // Tags Ini kita samakan nantinya untuk pengambila data JSON
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG__ID_KARYAWAN = "id_karyawan";
    public static final String TAG__TANGGAL = "tanggal";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_JABATAN = "jabatan";
    public static final String TAG_KEHADIRAN = "kehadiran";
    public static final String TAG_PENUNJANG = "penunjang";
    public static final String TAG_PENILAIAN_UMUM = "penilaian_umum";
    public static final String TAG_PENGEMBANGAN_DIRI = "pengembangan_diri";
    public static final String TAG_HASIL = "hasil";

    public static final String URL_ID = "emp_id";
}
