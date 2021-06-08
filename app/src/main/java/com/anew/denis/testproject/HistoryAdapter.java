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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    Context context;
    ArrayList<Buku> dataRiwayat;

    public HistoryAdapter(Context context, ArrayList<Buku> dataRiwayat){
       this.context = context;
       this.dataRiwayat = dataRiwayat;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);

        return new HistoryViewHolder(inflater.inflate(R.layout.history_adapter, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, final int pos) {
        holder.judul.setText(dataRiwayat.get(pos).getJudul());
        holder.kategori.setText(dataRiwayat.get(pos).getKategori());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailHistoryActivity.class);
                intent.putExtra("kode_buku", dataRiwayat.get(pos).getKode_buku());
                intent.putExtra("judul", dataRiwayat.get(pos).getJudul());
                intent.putExtra("penulis", dataRiwayat.get(pos).getPenulis());
                intent.putExtra("penerbit", dataRiwayat.get(pos).getPenerbit());
                intent.putExtra("kategori", dataRiwayat.get(pos).getKategori());
                intent.putExtra("tglPinjam", dataRiwayat.get(pos).getTglPinjam());
                intent.putExtra("tglKembali", dataRiwayat.get(pos).getTglKembali());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataRiwayat.size();
    }

    public void filterList(ArrayList<Buku> historyList) {
        dataRiwayat = historyList;
        notifyDataSetChanged();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView judul, kategori;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judul);
            kategori = itemView.findViewById(R.id.kategori);
        }
    }
}
