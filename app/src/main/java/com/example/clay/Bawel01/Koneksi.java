package com.example.clay.Bawel01;

/**
 * Created by CLAY on 29/06/2016.
 */
public class Koneksi {
//http://bawel01.netai.net/
    // Kita buatkan URL untuk Input data, sama kan dengan letak anda taruh script PHP
    //public static final String URL_ADD="http://bawel01.netai.net/create.php";
    // public static final String URL_DELETE_EMP="http://192.168.43.176/crud/karyawan/delete.php?id=";
// lempar data ke codding php dengan metod  = POST
    public static final String URL_ADD="http://bawel01.comxa.com/karyawan/create.php";
    public static final String URL_GET_ALL = "http://bawel01.comxa.com/karyawan/read.php";
    public static final String URL_GET_ID="http://bawel01.comxa.com/karyawan/get_data.php?id=";
    // Link untuk Update data
    public static final String URL_UPDATE_EMP="http://bawel01.comxa.com/karyawan/update.php";
    // Link Untuk Hapus Data
    public static final String URL_DELETE_EMP="http://bawel01.comxa.com/karyawan/delete.php?id=";
    // pencarian data karyawan http://bawel01.comxa.com/karyawan/search.php
    public static final String URL_SEARCH_EMP="http://bawel01.comxa.com/karyawan/search.php=";

    // Field yang digunakan untuk dikirimkan ke Database, sesuaikan saja dengan Field di Tabel Mahasiswa
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAMA = "nama";
    public static final String KEY_EMP_ALAMAT = "alamat";
    public static final String KEY_EMP_JABATAN = "jabatan";
    public static final String KEY_EMP_PENDIDIKAN = "pendidikan";
    public static final String KEY_EMP_JENDER = "jender";

    // Tags Ini kita samakan nantinya untuk pengambila data JSON
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_ALAMAT = "alamat";
    public static final String TAG_JABATAN = "jabatan";
    public static final String TAG_PENDIDIKAN = "pendidikan";
    public static final String TAG_JENDER = "jender";

    public static final String URL_ID = "emp_id";
}
