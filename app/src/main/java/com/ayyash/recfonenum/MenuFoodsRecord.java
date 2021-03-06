package com.ayyash.recfonenum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ayyash.recfonenum.makanmalam.KonfirmasiMakanMalam;
import com.ayyash.recfonenum.makansiang.KonfirmasiMakanSiang;
import com.ayyash.recfonenum.selinganmalam.KonfirmasiSelinganMalam;
import com.ayyash.recfonenum.selinganpagi.KonfirmasiSelinganSarapan;
import com.ayyash.recfonenum.selingansiang.KonfirmasiSelinganSiang;

public class MenuFoodsRecord extends AppCompatActivity {

    Button sPagi,selinganPagi, makanSiang,selinganSiang,makanMalam,selinganMalam,perbandinganAsupan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu_food_record);
        sPagi = (Button)findViewById(R.id.btnsarapanpagi);
        selinganPagi= (Button)findViewById(R.id.selinganPagi);
        makanSiang = (Button)findViewById(R.id.makanSiang);
        selinganSiang =(Button)findViewById(R.id.seliSiang);
        makanMalam = (Button)findViewById(R.id.makanMalam);
        selinganMalam = (Button)findViewById(R.id.selinganMala);
        perbandinganAsupan = (Button)findViewById(R.id.btnAsupan);


        //selinganPagi.setEnabled(false);


         /* Top toolbar */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.logo_atas);
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);


        //makan pagi
        sPagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuFoodsRecord.this, KonfirmasiSarapan.class);
                startActivity(i);
//                finish();
            }
        });


        selinganPagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(MenuFoodsRecord.this, KonfirmasiSelinganSarapan.class);
                    startActivity(i);
//                    finish();

            }
        });

        makanSiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuFoodsRecord.this, KonfirmasiMakanSiang.class);
                startActivity(i);
//                finish();

            }
        });

        selinganSiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuFoodsRecord.this, KonfirmasiSelinganSiang.class);
                startActivity(i);
//                finish();

            }
        });
        makanMalam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuFoodsRecord.this, KonfirmasiMakanMalam.class);
                startActivity(i);
//                finish();

            }
        });
        selinganMalam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuFoodsRecord.this, KonfirmasiSelinganMalam.class);
                startActivity(i);
//                finish();

            }
        });

        perbandinganAsupan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuFoodsRecord.this, PerbandinganAsupan.class);
                startActivity(i);
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


    private void help(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Petunjuk");
       // builder.setMessage("Anda diminta menuliskan jenis dan jumlah yang makanan dan minuman yang dikonsumsi selama 24 jam HARI INI (sejak bangun tidur hingga tidur lagi)");
       // (Html.fromHtml("Hello "+"<b>"+"World"+"</b>"));
        builder.setMessage(Html.fromHtml("Anda diminta menuliskan jenis dan jumlah "+"<b>"+ "makanan dan minuman" + "</b>"+" yang dikonsumsi"+"<b>"+" " +
                "selama 24 jam HARI INI (sejak bangun tidur hingga tidur lagi)"+ "</b>"+"<br><br><br>"+"<u>"+"Cara pengisian :"+"</u>"+"<br>"+
                "<b>"+"Pilih jenis makanan dan minuman yang dikonsumsi, kemudian isikan jumlah yg dikonsumsi sesuai porsi yg trsedia pd gambar"+"</b>"+
                "<br><br><br>"+"<p style='text-align:justify'>Setelah anda mengisi ke 6 aktifitas harian dari pagi sampai dengan selingan malam , jangan lupa juga mengisi <strong style='color:red;'>PERBANDINGAN ASUPAN MAKAN</strong> pada tombol hijau yang berada paling bawah.</p>"+
                ""));

        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.LEFT);
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(MenuFoodsRecord.this);

        // Setting Dialog Title
        alertDialog.setTitle("Konfirmasi");
        // Setting Dialog Message
        alertDialog.setMessage("Apakah Anda yakin sudah memasukan semua menu sarapan Anda?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.i);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                // Write your code here to invoke YES event
                Intent i = new Intent(MenuFoodsRecord.this, MainMenu.class);
                startActivity(i);
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


                        editor.putBoolean(ConfigUmum.LOGGEDIN_SHARED_PREF, false);


                        editor.putString(ConfigUmum.NIS_SHARED_PREF, "");

                        editor.commit();
                        //clear sp IP


                        Intent intent = new Intent(MenuFoodsRecord.this, Login.class);
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
