package com.anew.denis.testproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Context context;
    RequestQueue queue;
    EditText user, pass;
    RelativeLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.context = this;
        queue = Volley.newRequestQueue(context);
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        progress = findViewById(R.id.progress);
        getSupportActionBar().hide();
    }

    public void clickButton(View view){
        if (view.getId() == R.id.btn_login) {
            if (user.getText().toString().equals("") || pass.getText().toString().equals("")){
                AlertDialog dialog = new AlertDialog.Builder(context).create();
                dialog.setTitle("Peringatan");
                dialog.setMessage("Form harus diisi!");
                dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            } else {
                progress.setVisibility(View.VISIBLE);
                StringRequest request = new StringRequest(Request.Method.POST, ApiRequest.login, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            Session session = new Session(context);
                            session.setSession(object.getString("id_anggota"), object.getString("nama"), object.getString("nik"), object.getString("alamat"), object.getString("tgl_daftar"), object.getString("username"), object.getString("password"));

                            Intent i = new Intent(LoginActivity.this, LandingPageActivity.class);
                            startActivity(i);
                            finish();
                        } catch (JSONException e) {
                            Log.d("Error", "onResponse: " + e.getMessage());
                            AlertDialog dialog = new AlertDialog.Builder(context).create();
                            dialog.setTitle("Informasi");
                            dialog.setMessage("Pengguna tidak ditemukan!");
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
                        params.put("username", user.getText().toString());
                        params.put("password", pass.getText().toString());

                        return params;
                    }
                };

                queue.add(request);
            }
        }
    }
}
