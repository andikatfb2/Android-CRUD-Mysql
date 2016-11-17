package com.example.clay.Bawel01;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by CLAY on 13/07/2016.
 */
public class Login extends Activity implements View.OnClickListener{
   Button login,exit;
    EditText user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        user = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);

        login = (Button)findViewById(R.id.button_login);
        exit = (Button)findViewById(R.id.button_login_exit);
        login.setOnClickListener(this);
        exit.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v==login){
            final String username = user.getText().toString().trim();
            final  String password = pass.getText().toString().trim();
            login.setBackgroundColor(Color.BLUE);
            if(user.getText().length()>0&&pass.getText().length()>0){
                if(username.equals("heri")&&password.equals("123456")){
                    finish();  startActivity(new Intent(Login.this,Menu_utama.class));
                }else {
                    Toast.makeText(this,"Username atau Password Anda Salah.",Toast.LENGTH_LONG).show();
                    user.requestFocus(); user.setText(null); pass.setText(null);
                    login.setBackgroundColor(Color.TRANSPARENT);
                }

                }else {
                Toast.makeText(this,"Masukan Uername dan Passwor.",Toast.LENGTH_LONG).show();
                user.requestFocus(); user.setText(null); pass.setText(null);
                login.setBackgroundColor(Color.TRANSPARENT);
            }
            }if (v==exit){
            exit.setBackgroundColor(Color.BLUE);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Keluar.?");

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
