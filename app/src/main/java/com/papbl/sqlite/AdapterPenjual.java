package com.papbl.sqlite;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.papbl.sqlite.dbHelper.PenjualHelper;
import com.papbl.sqlite.models.PenjualModel;

import java.util.ArrayList;

public class AdapterPenjual extends RecyclerView.Adapter<AdapterPenjual.MyViewHolder> {
    private MainActivity mainActivity;
    private ArrayList<PenjualModel> penjualModels;
    private Context context;
    private Button yes, no;
    private EditText nama, email, notelp;
    private PenjualHelper penjualHelper;
    private Intent intent;

    public AdapterPenjual(ArrayList<PenjualModel> penjualModels, Context context, MainActivity mainActivity){
        this.penjualModels = new ArrayList<>(penjualModels);
        this.context = context;
        this.mainActivity = mainActivity;
    }

    public AdapterPenjual(ArrayList<PenjualModel> penjualModels, MainActivity mainActivity){
        this.penjualModels = penjualModels;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_card, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.kiri.setText(penjualModels.get(i).getNama());
        myViewHolder.kanan_atas.setText(penjualModels.get(i).getEmail());
        myViewHolder.kanan_bawah.setText(penjualModels.get(i).getNoTelp());
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View updateView = LayoutInflater.from(context).inflate(R.layout.update_new_layout, null);
        builder.setView(updateView);

        alertDialog = builder.create();

        nama = updateView.findViewById(R.id.nama_penjual_update);
        email = updateView.findViewById(R.id.email_update);
        notelp = updateView.findViewById(R.id.no_telp_update);
        yes = updateView.findViewById(R.id.yes_update);
        no = updateView.findViewById(R.id.no_btn_update);

//        Onclick Function
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), BarangActivity.class);
                intent.putExtra("namaPenjual", penjualModels.get(i).getNama());
                v.getContext().startActivity(intent);
            }
        });

        myViewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        penjualHelper = new PenjualHelper(context);
                        penjualHelper.open();
                        PenjualModel penjualModel = new PenjualModel(penjualModels.get(i).getId(), nama.getText().toString(), email.getText().toString(), notelp.getText().toString());
                        penjualHelper.update(penjualModel);
                        penjualHelper.close();
                        Toast.makeText(context, "Data berhasil diupdate!", Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();
                        mainActivity.onResume();
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
            }
        });

        myViewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penjualHelper = new PenjualHelper(context);
                penjualHelper.open();
                penjualHelper.delete(penjualModels.get(i).getId());
                penjualHelper.close();
                Toast.makeText(context, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
                mainActivity.onResume();
            }
        });

    }

    @Override
    public int getItemCount() {
        return penjualModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView kiri, kanan_atas, kanan_bawah, update, del;
        private CardView cardView;

        public MyViewHolder(View v){
            super(v);
            kiri = v.findViewById(R.id.text_kiri);
            kanan_atas = v.findViewById(R.id.text_kanan_atas);
            kanan_bawah = v.findViewById(R.id.text_kanan_bawah);
            update = v.findViewById(R.id.update_btn);
            del = v.findViewById(R.id.del_btn);
            cardView = v.findViewById(R.id.main_card);
        }
    }

    public void changeAdapter(ArrayList<PenjualModel> penjualModels)
    {
        this.penjualModels = penjualModels;

        this.notifyDataSetChanged();
    }
}
