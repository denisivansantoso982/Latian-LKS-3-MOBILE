package com.anew.denis.testproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.AdapterViewHolder> {
    Context context;
    ArrayList<Buku> dataBuku;

    public BukuAdapter(Context context, ArrayList<Buku> dataBuku) {
        this.context = context;
        this.dataBuku = dataBuku;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.book_adapter, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, final int i) {
        holder.judul.setText(dataBuku.get(i).getJudul());
        holder.kategori.setText(dataBuku.get(i).getKategori());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailBukuActivity.class);
                intent.putExtra("kode_buku", dataBuku.get(i).getKode_buku());
                intent.putExtra("judul", dataBuku.get(i).getJudul());
                intent.putExtra("kategori", dataBuku.get(i).getKategori());
                intent.putExtra("penerbit", dataBuku.get(i).getPenerbit());
                intent.putExtra("penulis", dataBuku.get(i).getPenulis());
                intent.putExtra("harga", dataBuku.get(i).getHarga());
                intent.putExtra("deskripsi", dataBuku.get(i).getDeskripsi());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBuku.size();
    }

    public void filterList(ArrayList<Buku> data){
        dataBuku = data;
        notifyDataSetChanged();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView kategori, judul;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judul);
            kategori = itemView.findViewById(R.id.kategori);
        }
    }
}
