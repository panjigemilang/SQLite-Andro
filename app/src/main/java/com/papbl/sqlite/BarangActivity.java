package com.papbl.sqlite;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.papbl.sqlite.dbHelper.BarangHelper;
import com.papbl.sqlite.models.BarangModel;

public class BarangActivity extends AppCompatActivity {
    private String namaPenjual;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton floatingActionButton;
    private BarangHelper barangHelper;
    private EditText nama, kategori, stok;
    private Button yes, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);
        mRecyclerView = findViewById(R.id.recyclerview2);
        floatingActionButton = findViewById(R.id.add_btn_barang);

//        Get ID from Penjual
        namaPenjual = getIntent().getStringExtra("namaPenjual");
        Log.d("namaPenjual", "nama penjual cuy " + namaPenjual);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        barangHelper = new BarangHelper(this);

//        Menampilkan data pada recycleview
        barangHelper.open();
        mAdapter = new AdapterBarang(barangHelper.selectAll(namaPenjual), this, this);
        barangHelper.close();
        mRecyclerView.setAdapter(mAdapter);

//        Add new View untuk dialog
        View addView = LayoutInflater.from(this).inflate(R.layout.activitiy_add_new_barang, null);
        builder.setView(addView);

//        Binding add new View
        nama = addView.findViewById(R.id.nama_barang);
        kategori = addView.findViewById(R.id.kategori);
        stok = addView.findViewById(R.id.stok);
        yes = addView.findViewById(R.id.yes_add_barang);
        no = addView.findViewById(R.id.no_btn);
        
        alertDialog = builder.create();

//        onClick function
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barangHelper.open();
                BarangModel barangModel = new BarangModel(nama.getText().toString(), kategori.getText().toString(), Integer.parseInt(stok.getText().toString()), namaPenjual);
                barangHelper.insert(barangModel);
                barangHelper.close();
                alertDialog.cancel();
                Toast.makeText(BarangActivity.this, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                onResume();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        barangHelper.open();
        mAdapter = new AdapterBarang(barangHelper.selectAll(namaPenjual), this, BarangActivity.this);
        barangHelper.close();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
