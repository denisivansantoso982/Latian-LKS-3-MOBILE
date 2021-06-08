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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Queue;

public class BukuActivity extends AppCompatActivity {
    Context context;
    RequestQueue queue;
    ArrayList<Buku> dataBuku;
    RelativeLayout progress;
    BukuAdapter adapter;
    RecyclerView recyclerBuku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        context = this;
        getSupportActionBar().setTitle("Cari Buku");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        queue = Volley.newRequestQueue(context);
        progress = findViewById(R.id.progress);
        dataBuku = new ArrayList<>();
        recyclerBuku = findViewById(R.id.recyclerBook);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchField = menu.findItem(R.id.search_box);
        SearchView searchBox = (SearchView) searchField.getActionView();
        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    void filter(String text) {
        ArrayList<Buku> bukuArr = new ArrayList<>();

        for (Buku buku : dataBuku) {
            if (buku.getJudul().toLowerCase().contains(text.toLowerCase()))
                bukuArr.add(buku);
        }

        adapter.filterList(bukuArr);
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

    @Override
    protected void onStart() {
        super.onStart();

        dataBuku.clear();
        progress.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ApiRequest.book, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        dataBuku.add(new Buku(obj.getString("kode_buku"), obj.getString("judul"), obj.getString("nama_kategori"), obj.getString("penulis"), obj.getString("penerbit"), obj.getInt("harga"), obj.getString("deskripsi")));
                        adapter = new BukuAdapter(context, dataBuku);
                        recyclerBuku.setLayoutManager(new LinearLayoutManager(context));
                        recyclerBuku.setAdapter(adapter);

                        progress.setVisibility(View.GONE);
                    } catch (JSONException error) {
                        Log.d("Error", "onResponse: " + error.getMessage());
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
        });

        queue.add(request);
    }
}
