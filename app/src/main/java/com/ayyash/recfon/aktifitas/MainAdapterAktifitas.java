package com.ayyash.recfon.aktifitas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ayyash.recfon.ConfigUmum;
import com.ayyash.recfon.ItemObject;
import com.ayyash.recfon.MainHolder;
import com.ayyash.recfon.R;

import java.util.List;

/**
 * Created by Isfahani on 30-Jul-16.
 */
public class MainAdapterAktifitas extends RecyclerView.Adapter<MainHolder> {

    ProgressDialog progressDialog;



    public List<ItemObjectAktifitas.ObjectBelajar.Results> resultsList;
    public Context context;

    public MainAdapterAktifitas(Context context, List<ItemObjectAktifitas.ObjectBelajar.Results> resultsList) {
        this.context = context;
        this.resultsList = resultsList;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_aktifitas, null);
        MainHolder mainHolder = new MainHolder(view);
        return mainHolder;
    }




    public void DeleteData(String Url) {

        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {;
                    @Override
                    public void onResponse(String response) {
                        Log.d("uye", response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("uye", error.toString());

            }
        });
        queue.add(stringRequest);
    }


    @Override
    public void onBindViewHolder(MainHolder holder, final int position) {
        holder.txt_name.setText("Nama Makanan : "+resultsList.get(position).activity);
        holder.txt_office.setText("Durasi : "+resultsList.get(position).durasi);


//        final String nama_makanan =resultsList.get(position).activity;
//        final String idd = resultsList.get(position).id;

        holder.cardview_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
//                progressDialog = new ProgressDialog(context);
//                progressDialog.setCancelable(false);
//                progressDialog.setMessage("Silahkan Tunggu...");


//                DeleteData(ConfigUmum.URL_DELETE_PAGI+idd);
//                // Intent i = new Intent(context, Pengalih.class);
//                //  view.getContext().startActivity(i);
//
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setTitle("Konfirmasi");
//                builder.setMessage("Apakah anda yakin ingin menghapus\n" +
//                        nama_makanan + " ?");
//                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        DeleteData(ConfigUmum.URL_DELETE_PAGI+idd);
////                        dialog.dismiss();
//
//                        //   Intent i = new Intent(context, SarapanActivity.class);
//
//                        //
//                        //  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
//                        //view.getContext().startActivity(i);
//
//
//                        Activity activity = (Activity)view.getContext();
//                        activity.finish();
//                        view.getContext().startActivity(activity.getIntent());
//
//                    }
//                });
//                builder.setNegativeButton("Batalkan", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        dialog.dismiss();
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();
//
//
//                //   Toast.makeText(context,"ID nya: "+resultsList.get(position).nama_makanan, Toast.LENGTH_LONG).show();
//
//
            }
        });
    }



    @Override
    public int getItemCount() {
        return this.resultsList.size();
    }
}
