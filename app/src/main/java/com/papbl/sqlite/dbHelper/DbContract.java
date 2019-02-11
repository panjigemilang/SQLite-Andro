package com.papbl.sqlite.dbHelper;

import android.provider.BaseColumns;

public class DbContract {
    static String TABLE_PENJUAL = "table_penjual";
    static String TABLE_BARANG = "table_barang";

    static final class PenjualColumns implements BaseColumns {
        static String NAMA = "nama";
        static String EMAIL = "email";
        static String NOTELP = "noTelp";
    }

    static final class BarangColumns implements BaseColumns {
        static String NAMAE = "nama";
        static String KATEGORI = "kategori";
        static String STOCK = "stock";
        static String PENJUAL = "penjual";
    }
}
