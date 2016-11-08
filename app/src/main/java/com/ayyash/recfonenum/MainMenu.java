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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ayyash.recfonenum.aktifitas.MenuAktifitas;
import com.ayyash.recfonenum.frekuensi.FrekuensiBulanan;
import com.ayyash.recfonenum.profile.ProfileUser;

import java.util.HashMap;
import java.util.Map;

public class MainMenu extends AppCompatActivity {
    public static final String KEY_EMAIL = "txtEmailResponden";
    public static final String KEY_STATUS = "status";

    Button statusGizi, makanHarian, aktifitasFisik, frekuensiMakan, btnBelumSelesai, btnSelesai;

    ProgressDialog progressDialog;
    String responden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu2);
        statusGizi = (Button) findViewById(R.id.btnsarapanpagi);
        makanHarian = (Button) findViewById(R.id.selinganPagi);
        aktifitasFisik = (Button) findViewById(R.id.makanSiang);
        frekuensiMakan = (Button) findViewById(R.id.seliSiang);

        btnBelumSelesai = (Button) findViewById(R.id.blmSelesai);
        btnSelesai = (Button) findViewById(R.id.sudahSelesai);
         /* Top toolbar */

        SharedPreferences spResponden = getSharedPreferences("EmailResponden", Context.MODE_PRIVATE);
        responden = spResponden.getString("EmailResponden", "");

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Silahkan Tunggu...");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.logo_atas);
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);


        //makan pagi
        //makan pagi
        statusGizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), StatusGizi.class);
                startActivity(i);
                finish();
            }
        });


        makanHarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainMenu.this, MenuFoodsRecord.class);
                startActivity(i);
                finish();

            }
        });

        aktifitasFisik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this, MenuAktifitas.class);
                startActivity(i);
                finish();

            }
        });

        frekuensiMakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this, FrekuensiBulanan.class);
                startActivity(i);
                finish();

            }
        });
        btnBelumSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(MainMenu.this);
            alertDialog.setTitle("Konfirmasi");
            alertDialog.setMessage("Apakah Anda yakin ingin keluar?");
            alertDialog.setIcon(R.drawable.i);

            alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp = getSharedPreferences("EmailResponden", MODE_WORLD_READABLE);
                SharedPreferences.Editor edd = sp.edit();

                edd.clear();
                edd.commit();

                Intent i = new Intent(MainMenu.this, SelectResponden.class);
                startActivity(i);
                finish();
                }
            });

            alertDialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();

            }
        });

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(MainMenu.this);
            alertDialog.setTitle("Konfirmasi");
            alertDialog.setMessage("Apakah Anda yakin survey telah selesai dilaksanakan?");
            alertDialog.setIcon(R.drawable.i);

            alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                Selesai();
                }
            });

            alertDialog.setNegativeButton("Cek Kembali", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();
            }
        });

    }


    public void Selesai(){
        progressDialog.show();
        final String txtEmailResponden = responden.toString().trim();
        final String status = "2";


        //Toast.makeText(MainMenu.this, "hai: "+txtEmailResponden ,Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,ConfigUmum.URL_UPDATE_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        if (response.equalsIgnoreCase("Sukses")) {

                            SharedPreferences sp = getSharedPreferences("EmailResponden", MODE_WORLD_READABLE);
                            SharedPreferences.Editor edd = sp.edit();

                            edd.clear();
                            edd.commit();

                            Intent i = new Intent(MainMenu.this, SelectResponden.class);
                            startActivity(i);
                            finish();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(MainMenu.this, "Email Responden tidak ditemukan", Toast.LENGTH_LONG).show();
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
                params.put(KEY_STATUS, status);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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




    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(MainMenu.this);

        // Setting Dialog Title
        alertDialog.setTitle("Konfirmasi");
        // Setting Dialog Message
        alertDialog.setMessage("Apakah Anda yakin ingin keluar?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.i);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event

                finish();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cek Kembali", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
//                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
//    }

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

                        SharedPreferences sp = getSharedPreferences("EmailResponden", MODE_WORLD_READABLE);
                        SharedPreferences.Editor edd = sp.edit();

                        edd.clear();
                        edd.commit();


                        editor.putBoolean(ConfigUmum.LOGGEDIN_SHARED_PREF, false);


                        editor.putString(ConfigUmum.NIS_SHARED_PREF, "");

                        editor.commit();
                        //clear sp IP


                        Intent intent = new Intent(MainMenu.this, Login.class);
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




}
