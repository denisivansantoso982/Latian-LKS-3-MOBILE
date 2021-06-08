package com.anew.denis.testproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences preferences;
    private Context context;

    public Session(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setSession(String id, String nama, String nik, String alamat, String tgl_daftar, String username, String password){
        preferences.edit().putString("id", id).commit();
        preferences.edit().putString("nama", nama).commit();
        preferences.edit().putString("username", username).commit();
        preferences.edit().putString("password", password).commit();
        preferences.edit().putString("nik", nik).commit();
        preferences.edit().putString("alamat", alamat).commit();
        preferences.edit().putString("tgl_daftar", tgl_daftar).commit();
    }

    public String getId() {
        return preferences.getString("id", "");
    }

    public String getNama() {
        return preferences.getString("nama", "");
    }

    public String getUsername() {
        return preferences.getString("username", "");
    }

    public String getPassword() {
        return preferences.getString("password", "");
    }

    public String getAlamat() {
        return preferences.getString("alamat", "");
    }

    public String getNik() {
        return preferences.getString("nik", "");
    }

    public void clearSession(){
        preferences.edit().clear().commit();
    }
}
