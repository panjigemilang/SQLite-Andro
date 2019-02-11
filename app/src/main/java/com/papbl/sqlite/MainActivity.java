package com.papbl.sqlite;

import android.app.SearchManager;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;
import android.support.v7.widget.SearchView.OnQueryTextListener;

import com.papbl.sqlite.dbHelper.PenjualHelper;
import com.papbl.sqlite.models.PenjualModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
    private AdapterPenjual mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton floatingActionButton;
    private PenjualHelper penjualHelper;
    private PenjualModel penjualModel;
    private EditText nama, email, notelp, search;
    private Button yes, no;
    private ArrayList<PenjualModel> dataset;
    private SearchView search_btn;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycleView);
        floatingActionButton = findViewById(R.id.add_btn);
        search = findViewById(R.id.search);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        penjualHelper = new PenjualHelper(this);

//        Menampilkan data pada recycleview
        penjualHelper.open();
        dataset = penjualHelper.selectAll();
        mAdapter = new AdapterPenjual(dataset, this, this);
        penjualHelper.close();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

//        Add new View untuk dialog
        View addView = LayoutInflater.from(this).inflate(R.layout.activity_add_new, null);
        builder.setView(addView);

//        Binding add new View
        nama = addView.findViewById(R.id.nama_penjual);
        email = addView.findViewById(R.id.email);
        notelp = addView.findViewById(R.id.no_telp);
        yes = addView.findViewById(R.id.yes);
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
                penjualHelper.open();
                penjualModel = new PenjualModel(nama.getText().toString(), email.getText().toString(), notelp.getText().toString());
                penjualHelper.insert(penjualModel);
                penjualHelper.close();
                mAdapter.notifyDataSetChanged();
                alertDialog.cancel();
                Toast.makeText(MainActivity.this, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                onResume();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("TEXT", toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                penjualHelper.open();
                if (s.length() != 0){
                    dataset = penjualHelper.search(s);
                    mAdapter.changeAdapter(dataset);
                    Log.d("TEST AJHAHAA", s.toString());
                } else {
                    Log.d("TEST abis", s.toString());
                    onResume();
                }
                penjualHelper.close();
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("TEXT", toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        penjualHelper.open();
        mAdapter = new AdapterPenjual(penjualHelper.selectAll(), this, MainActivity.this);
        penjualHelper.close();
        mRecyclerView.setAdapter(mAdapter);
    }
}



//PUNYA TEMEN


//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                penjualHelper.open();
//                if (s.length() != 0){
//                    mAdapter = new AdapterPenjual(penjualHelper.search(s), MainActivity.this);
//                    mRecyclerView.setAdapter(mAdapter);
//                } else {
//                    penjualHelper.close();
//                    onResume();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });