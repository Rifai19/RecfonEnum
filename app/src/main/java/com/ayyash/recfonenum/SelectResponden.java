package com.ayyash.recfonenum;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SelectResponden extends AppCompatActivity {

    public static final String KEY_EMAIL = "txtEmailResponden";
    public static final String KEY_ENUM = "txtEmailEnum";

    Button mulai;
    EditText emailResponden;

    boolean ada=false;
    String email;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_responden);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardepan);
        toolbar.setNavigationIcon(R.drawable.logo_atas);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Silahkan Tunggu...");

        emailResponden = (EditText)findViewById(R.id.txtEmailResponden);

        SharedPreferences sharedPreferences = getSharedPreferences(ConfigUmum.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(ConfigUmum.NIS_SHARED_PREF, "tidak tersedia");

        mulai = (Button) findViewById(R.id.buttonAktif);
        mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResponden();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }



    private void getResponden(){
        progressDialog.show();
        final String txtEmailResponden = emailResponden.getText().toString().trim();
        final String txtEmailEnum = email.toString().trim();

//        Toast.makeText(SelectResponden.this, "hai: "+txtEmailResponden ,Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,ConfigUmum.URL_GET_RESPONDEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        if (response.equalsIgnoreCase("Sukses")) {

                            SharedPreferences sharedPreferences = SelectResponden.this.getSharedPreferences("EmailResponden", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean("SelectedResponden", true);
                            editor.putString("EmailResponden", txtEmailResponden);

                            editor.commit();

                            Intent i = new Intent(SelectResponden.this, MainMenu.class);
                            startActivity(i);
                            finish();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(SelectResponden.this, response, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("aaa", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_EMAIL, txtEmailResponden);
                params.put(KEY_ENUM, txtEmailEnum);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void logout() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Anda akan logout dari aplikasi?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        SharedPreferences preferences = getSharedPreferences(ConfigUmum.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putBoolean(ConfigUmum.LOGGEDIN_SHARED_PREF, false);
                        editor.putString(ConfigUmum.NIS_SHARED_PREF, "");

                        editor.commit();
                        //clear sp IP

                        Intent intent = new Intent(SelectResponden.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("Batal",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sp = getSharedPreferences("EmailResponden", MODE_WORLD_READABLE);
        ada =  sp.getBoolean("SelectedResponden", false);


        if (ada) {
            Intent i = new Intent(getApplicationContext(),MainMenu.class);
            startActivity(i);
        }

    }






    @Override
    public void onBackPressed() {
        finish();
    }
}