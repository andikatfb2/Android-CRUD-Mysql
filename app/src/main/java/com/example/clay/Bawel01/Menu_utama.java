package com.example.clay.Bawel01;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by CLAY on 08/07/2016.
 */
public class Menu_utama extends Activity implements View.OnClickListener{

    ImageButton i_btn_hitung,i_btn_add,i_btn_see,i_btn_see_emp;
    Button exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama);
        i_btn_hitung =(ImageButton)findViewById(R.id.imageButton_prosess);
        i_btn_add =(ImageButton)findViewById(R.id.imageButton_add);
        i_btn_see =(ImageButton)findViewById(R.id.imageButton_hasil);
        i_btn_see_emp =(ImageButton)findViewById(R.id.imageButton_data);
        exit = (Button)findViewById(R.id.btn_exitMenu);

        //action
        i_btn_hitung.setOnClickListener(this);
        i_btn_see_emp.setOnClickListener(this);
        i_btn_see.setOnClickListener(this);
        i_btn_add.setOnClickListener(this);
        exit.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v==i_btn_add){
            startActivity(new Intent(Menu_utama.this,Add_karyawan.class));
        }else if(v==i_btn_see_emp){
            startActivity(new Intent(Menu_utama.this,List_karyawan.class));
        }else if(v==i_btn_hitung){
            startActivity(new Intent(Menu_utama.this,List_karyawan2.class));
        }else if(v==i_btn_see){
            startActivity(new Intent(Menu_utama.this,List_Hasil.class));
        }else if(v==exit){
            exit.setBackgroundColor(Color.BLUE);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Exit From Application.?");

            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            System.exit(0);

                        }
                    });

            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            exit.setBackgroundColor(Color.TRANSPARENT);
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

}
