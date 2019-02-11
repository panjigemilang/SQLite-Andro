package com.papbl.sqlite.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.papbl.sqlite.models.BarangModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.papbl.sqlite.dbHelper.DbContract.BarangColumns.KATEGORI;
import static com.papbl.sqlite.dbHelper.DbContract.BarangColumns.NAMAE;
import static com.papbl.sqlite.dbHelper.DbContract.BarangColumns.PENJUAL;
import static com.papbl.sqlite.dbHelper.DbContract.BarangColumns.STOCK;
import static com.papbl.sqlite.dbHelper.DbContract.TABLE_BARANG;
import static com.papbl.sqlite.dbHelper.DbContract.TABLE_PENJUAL;

public class BarangHelper {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    ContentValues contentValues = new ContentValues();

    public BarangHelper(Context context) {
        this.context = context;
    }

    public BarangHelper open() throws SQLException {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<BarangModel> selectAll(String namaPenjual) {
        ArrayList<BarangModel> arr = new ArrayList<>();
        BarangModel barangModel;
        String selection = "SELECT * FROM " + TABLE_BARANG + " WHERE " + PENJUAL + " = '" + namaPenjual + "'";
        Cursor cursor = db.rawQuery(selection, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                barangModel = new BarangModel();
                barangModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                barangModel.setNama(cursor.getString(cursor.getColumnIndexOrThrow(NAMAE)));
                barangModel.setKategori(cursor.getString(cursor.getColumnIndexOrThrow(KATEGORI)));
                barangModel.setStock(cursor.getInt(cursor.getColumnIndexOrThrow(STOCK)));
                barangModel.setPenjual(cursor.getString(cursor.getColumnIndexOrThrow(PENJUAL)));
                arr.add(barangModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arr;
    }

    public long insert(BarangModel barangModel){
        contentValues.put(NAMAE, barangModel.getNama());
        contentValues.put(KATEGORI, barangModel.getKategori());
        contentValues.put(STOCK, barangModel.getStock());
        contentValues.put(PENJUAL, barangModel.getPenjual());
        return db.insert(TABLE_BARANG, null, contentValues);
    }

    public int update(BarangModel barangModel){
        contentValues.put(NAMAE, barangModel.getNama());
        contentValues.put(KATEGORI, barangModel.getKategori());
        contentValues.put(STOCK, barangModel.getStock());
        Log.d("CEK ID", barangModel.getId() + "");
        return db.update(TABLE_BARANG, contentValues, _ID + "= '" + barangModel.getId() + "'", null);
    }

    public int delete(BarangModel barangModel){
        return db.delete(TABLE_BARANG, _ID + "= '" + barangModel.getId() + "'", null);
    }
}













/**
 * Created by Komang Candra Brata on 2/13/2018.
 * Edited by Panji Gemilang on 2/5/2019.
 */
