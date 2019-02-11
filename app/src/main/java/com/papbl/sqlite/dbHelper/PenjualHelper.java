package com.papbl.sqlite.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.papbl.sqlite.models.PenjualModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.papbl.sqlite.dbHelper.DbContract.PenjualColumns.EMAIL;
import static com.papbl.sqlite.dbHelper.DbContract.PenjualColumns.NAMA;
import static com.papbl.sqlite.dbHelper.DbContract.PenjualColumns.NOTELP;
import static com.papbl.sqlite.dbHelper.DbContract.TABLE_PENJUAL;

public class PenjualHelper {
    Context context;
    DbHelper dbHelper;
    SQLiteDatabase db;
    ContentValues contentValues = new ContentValues();

    public PenjualHelper(Context context) {
        this.context = context;
    }

    public PenjualHelper open() throws SQLException {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<PenjualModel> selectAll() {
        ArrayList<PenjualModel> arr = new ArrayList<>();
        PenjualModel PenjualModel;
        Cursor cursor = db.query(TABLE_PENJUAL, null, null, null, null, null,_ID + " ASC", null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                PenjualModel = new PenjualModel();
                PenjualModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                PenjualModel.setNama(cursor.getString(cursor.getColumnIndexOrThrow(NAMA)));
                PenjualModel.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(EMAIL)));
                PenjualModel.setNoTelp(cursor.getString(cursor.getColumnIndexOrThrow(NOTELP)));
                arr.add(PenjualModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arr;
    }

    public ArrayList<PenjualModel> search(CharSequence query) {
        ArrayList<PenjualModel> arr = new ArrayList<>();
        PenjualModel penjualModel;
//        String selection = "SELECT * FROM " + TABLE_BARANG + " WHERE " + PENJUAL + " = '" + namaPenjual + "'";
//        Cursor cursor = db.rawQuery(selection, null);
//        String selection = NAMA + " LIKE '%" + query + "%'";
//        Cursor cursor = db.query(TABLE_PENJUAL, null, selection, null, null, null,_ID + " ASC", null);
        String selection = "SELECT * FROM " + TABLE_PENJUAL + " WHERE " + NAMA + " LIKE '%" + query + "%'";
        Cursor cursor = db.rawQuery(selection, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                penjualModel = new PenjualModel();
                penjualModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                penjualModel.setNama(cursor.getString(cursor.getColumnIndexOrThrow(NAMA)));
                penjualModel.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(EMAIL)));
                penjualModel.setNoTelp(cursor.getString(cursor.getColumnIndexOrThrow(NOTELP)));
                arr.add(penjualModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arr;
    }

    public long insert(PenjualModel PenjualModel){
        contentValues.put(NAMA, PenjualModel.getNama());
        contentValues.put(EMAIL, PenjualModel.getEmail());
        contentValues.put(NOTELP, PenjualModel.getNoTelp());
        return db.insert(TABLE_PENJUAL, null, contentValues);
    }

    public int update(PenjualModel PenjualModel){
        contentValues.put(NAMA, PenjualModel.getNama());
        contentValues.put(EMAIL, PenjualModel.getEmail());
        contentValues.put(NOTELP, PenjualModel.getNoTelp());
        return db.update(TABLE_PENJUAL, contentValues, _ID + "= '" + PenjualModel.getId() + "'", null);
    }

    public int delete(int penjualId){
        return db.delete(TABLE_PENJUAL, _ID + "='" + penjualId + "'", null);
    }
}
