package com.papbl.sqlite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.papbl.sqlite.dbHelper.BarangHelper;
import com.papbl.sqlite.models.BarangModel;

import java.util.ArrayList;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.MyViewHolder> {
    private ArrayList<BarangModel> barangModels;
    private BarangActivity barangActivity;
    private BarangHelper barangHelper;
    private Context context;
    private Button yes, no;
    private EditText nama, kategori, stok;
    private TextView textView;

    public AdapterBarang(ArrayList<BarangModel> barangModels, Context context, BarangActivity barangActivity){
        this.barangModels = barangModels;
        this.context = context;
        this.barangActivity = barangActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_card, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.kiri.setText(barangModels.get(i).getNama());
        myViewHolder.kanan_atas.setText(barangModels.get(i).getKategori());
        myViewHolder.kanan_bawah.setText(String.valueOf(barangModels.get(i).getStock()));

//        Dialog update new
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View updateView = LayoutInflater.from(context).inflate(R.layout.activity_add_new, null);
        builder.setView(updateView);

        alertDialog = builder.create();

//        Binding var
        textView = updateView.findViewById(R.id.textView);
        nama = updateView.findViewById(R.id.nama_penjual);
        kategori = updateView.findViewById(R.id.email);
        stok = updateView.findViewById(R.id.no_telp);
        yes = updateView.findViewById(R.id.yes);
        no = updateView.findViewById(R.id.no_btn);

//        Set text alert
        textView.setText("Update data");
        nama.setHint("Nama barang");
        nama.setText(barangModels.get(i).getNama());
        kategori.setHint("Kategori");
        kategori.setText(barangModels.get(i).getKategori());
        stok.setHint("Stok");
        stok.setText(barangModels.get(i).getStock() + "");

//        Onclick Function
        myViewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarangModel bm = barangModels.get(i);
                bm.setNama("bmbang");
                Log.d("UPDATE NAMA", nama.getText().toString());
                bm.setKategori(kategori.getText().toString());
                bm.setStock(Integer.parseInt(stok.getText().toString()));
                barangHelper = new BarangHelper(context);
                barangHelper.open();
                barangHelper.update(bm);
                barangHelper.close();
                Toast.makeText(context, "Data berhasil diupdate!", Toast.LENGTH_SHORT).show();
                alertDialog.cancel();
                barangActivity.onResume();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                Log.d("CANCEL", "BERHASIL CANCEL");
            }
        });

        myViewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarangHelper barangHelper = new BarangHelper(context);
                barangHelper.open();
                barangHelper.delete(barangModels.get(i));
                barangHelper.close();
                Toast.makeText(context, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
                barangActivity.onResume();
            }
        });
    }

    @Override
    public int getItemCount() {
        return barangModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView kiri, kanan_atas, kanan_bawah, update, del;

        public MyViewHolder(View v){
            super(v);
            kiri = v.findViewById(R.id.text_kiri);
            kanan_atas = v.findViewById(R.id.text_kanan_atas);
            kanan_bawah = v.findViewById(R.id.text_kanan_bawah);
            update = v.findViewById(R.id.update_btn);
            del = v.findViewById(R.id.del_btn);
        }
    }
}
