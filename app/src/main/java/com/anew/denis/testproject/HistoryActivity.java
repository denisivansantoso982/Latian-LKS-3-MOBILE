package com.anew.denis.testproject;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    RelativeLayout progress;
    RecyclerView rHistory;
    Context context;
    RequestQueue queue;
    Session session;
    ArrayList<Buku> data;
    HistoryAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchField = menu.findItem(R.id.search_box);
        SearchView searchView = (SearchView) searchField.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setTitle("Riwayat Peminjaman");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        context = this;
        progress = findViewById(R.id.progress);
        rHistory = findViewById(R.id.recyclerHistory);
        queue = Volley.newRequestQueue(context);
        session = new Session(context);
        data = new ArrayList<>();
        Log.d("Session", "onCreate: " + session.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();

        data.clear();
        StringRequest request = new StringRequest(Request.Method.POST, ApiRequest.history, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("Response", "onResponse: " + response);
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        data.add(new Buku(obj.getString("kode_buku"), obj.getString("judul"), obj.getString("nama_kategori"), obj.getString("penulis"), obj.getString("penerbit"), obj.getString("tgl_pinjam"), obj.getString("tgl_kembali")));
                    }

                    adapter = new HistoryAdapter(context, data);
                    rHistory.setLayoutManager(new LinearLayoutManager(context));
                    rHistory.setAdapter(adapter);

                    progress.setVisibility(View.GONE);
                } catch (JSONException e) {
                    Log.d("Error", "onResponse: " + e.getMessage());
                    AlertDialog dialog = new AlertDialog.Builder(context).create();
                    dialog.setTitle("Error");
                    dialog.setMessage("Terjadi kesalahan!");
                    dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            progress.setVisibility(View.GONE);
                        }
                    });
                    dialog.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "onErrorResponse: " + error.getMessage());
                AlertDialog dialog = new AlertDialog.Builder(context).create();
                dialog.setTitle("Error");
                dialog.setMessage("Terjadi kesalahan!");
                dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        progress.setVisibility(View.GONE);
                    }
                });
                dialog.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("id_anggota", session.getId());

                return params;
            }
        };

        queue.add(request);
    }

    private void filter(String text) {
        ArrayList<Buku> datas = new ArrayList<>();

        for (Buku item : data){
            if (item.getJudul().toLowerCase().contains(text.toLowerCase())) {
                datas.add(item);
            }
        }

        adapter.filterList(datas);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
