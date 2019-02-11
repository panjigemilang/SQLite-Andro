package com.papbl.sqlite.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.provider.BaseColumns._ID;
import static com.papbl.sqlite.dbHelper.DbContract.BarangColumns.KATEGORI;
import static com.papbl.sqlite.dbHelper.DbContract.BarangColumns.NAMAE;
import static com.papbl.sqlite.dbHelper.DbContract.BarangColumns.PENJUAL;
import static com.papbl.sqlite.dbHelper.DbContract.BarangColumns.STOCK;
import static com.papbl.sqlite.dbHelper.DbContract.PenjualColumns.EMAIL;
import static com.papbl.sqlite.dbHelper.DbContract.PenjualColumns.NAMA;
import static com.papbl.sqlite.dbHelper.DbContract.PenjualColumns.NOTELP;
import static com.papbl.sqlite.dbHelper.DbContract.TABLE_PENJUAL;
import static com.papbl.sqlite.dbHelper.DbContract.TABLE_BARANG;

public class DbHelper extends SQLiteOpenHelper {
    SQLiteDatabase db = getWritableDatabase();
    private static final String TAG = DbHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbKomputer";

    public static String CREATE_TABLE_PENJUAL = "create table " +
            TABLE_PENJUAL + " (" +
            _ID +" integer primary key autoincrement, " +
            NAMA + " text not null, " +
            EMAIL +" text not null, " +
            NOTELP + " text not null);";

    public static String CREATE_TABLE_BARANG = "create table " +
            TABLE_BARANG + " (" +
            _ID +" integer primary key autoincrement, " +
            NAMAE + " text not null, " +
            KATEGORI + " text not null, " +
            STOCK + " integer not null, " +
            PENJUAL + " text not null);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Construct DatabaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_BARANG);
        db.execSQL(CREATE_TABLE_PENJUAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BARANG);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PENJUAL);
        onCreate(db);
    }
}
