package com.ayyash.recfonenum.makansiang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ayyash.recfonenum.ConfigUmum;
import com.ayyash.recfonenum.R;
import com.ayyash.recfonenum.SarapanActivity;

import java.util.HashMap;
import java.util.Map;

public class KonfirmasiMakanSiang extends AppCompatActivity {
    public static final String KEY_EMAIL = "txt_email";
    public static final String KEY_MKN = "makanan";
    public static final String KEY_UKUR = "ukuran";
    public static final String KEY_JML = "jumlah";
    public static final String KEY_PROTEIN = "protein1";
    public static final String KEY_LEMAK = "lemak1";
    public static final String KEY_KALORI = "kalori1";
    public static final String KEY_ENERGI = "energi1";
    public static final String KEY_EMAIL_RESPONDEN = "txtEmailResponden";
    public static final String KEY_ID_WAKTU_MAKAN = "idWaktuMakan";

    String email,responden, id_waktu_makan;

    private ProgressDialog progressDialog;

    Button Ya,Tidak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_makansiang);
        SharedPreferences sharedPreferences = getSharedPreferences(ConfigUmum.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(ConfigUmum.NIS_SHARED_PREF, "tidak tersedia");

        SharedPreferences spResponden = getSharedPreferences("EmailResponden", Context.MODE_PRIVATE);
        responden = spResponden.getString("EmailResponden", "");
        id_waktu_makan = "3";

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Silahkan Tunggu...");


        Ya = (Button)findViewById(R.id.btnYa);
        Ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MakanSiangActivity.class);
                startActivity(i);
                finish();
            }
        });

        Tidak = (Button)findViewById(R.id.btnTidak);
        Tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveTidak();
            }
        });


        GetDataSebelumnya(ConfigUmum.CEK_INPUT_SEBELUMNYA +"email="+responden+"&waktumakan=2");
//        System.out.println(ConfigUmum.CEK_INPUT_SEBELUMNYA +"email="+email+"&waktumakan=2");

    }

    private void SaveTidak() {
        //Toast.makeText(KonfirmasiSarapan.this, "BABBA", Toast.LENGTH_SHORT).show();





        final String txt_email = email.toString().trim();
        final String makanan = "tidak makan";
        final String jumlah = "";
        final String ukuran = "";
        final String energi1 = "";
        final String protein1 = "";
        final String lemak1 = "";
        final String kalori1 = "";
        final String txtEmailResponden = responden.toString().trim();
        final String idWaktuMakan = id_waktu_makan.toString().trim();



        StringRequest sR = new StringRequest(Request.Method.POST, ConfigUmum.URL_INSERT_MAKAN_HARIAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                        Intent i = new Intent(getApplicationContext(), MakanSiangActivity.class);
                        startActivity(i);
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_EMAIL, txt_email);
                params.put(KEY_EMAIL_RESPONDEN, txtEmailResponden);
                params.put(KEY_ID_WAKTU_MAKAN, idWaktuMakan);
                params.put(KEY_MKN, makanan);
                params.put(KEY_UKUR, ukuran);
                params.put(KEY_JML, jumlah);
                params.put(KEY_ENERGI, energi1);
                params.put(KEY_PROTEIN, protein1);
                params.put(KEY_LEMAK, lemak1);
                params.put(KEY_KALORI, kalori1);
                return params;
            }

        };
        //Toast.makeText(getApplicationContext(), txt_email + " makanan = " + makanan, Toast.LENGTH_LONG).show();
        int socketTimeout = 5000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        sR.setRetryPolicy(policy);
        requestQueue.add(sR);
    }

    public void GetData(String URL) {

        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            ;

            @Override
            public void onResponse(String response) {

                if(response.contains("3")){
                    Intent i = new Intent(getApplicationContext(),MakanSiangActivity.class);
                    startActivity(i);
                    finish();
                }else {

                }

                progressDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Gagal Konek ke server, periksa jaringan anda :(", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);
    }

    public void GetDataSebelumnya(String URL) {

        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            ;

            @Override
            public void onResponse(String response) {
                System.out.println(response);
                //Integer jml_input = Integer.valueOf(response);

                if(response.equals("1")){
                   // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    GetData(ConfigUmum.URL_SHOW_MAKAN_SIANG + responden);
                }else {
                    Toast.makeText(getApplicationContext(),"Data SELINGAN PAGI belum diisi, silakan periksa kembali!",Toast.LENGTH_SHORT).show();
                    finish();
                }

                progressDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Gagal Konek ke server, periksa jaringan anda :(", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);
    }


    @Override
    public void onBackPressed() {

    }
}
