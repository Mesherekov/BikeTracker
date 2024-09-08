package com.example.biketracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDataBase extends SQLiteOpenHelper {
    public static final int VERSION_DATABASE = 35;
    public static final String DataBaseName = "contactdb";
    public static final String TableContacts = "contacts";

    public static final String ID = "id";
    public static final String Name = "name";
    public static final String Bool = "bool";
    public static final String Theme = "theme";
    public MyDataBase(@Nullable Context context) {
        super(context, DataBaseName, null, VERSION_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TableContacts + "(" + ID + " integer primary key," + Name + " text," + Bool + " text," + Theme + " integer" +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TableContacts);
        onCreate(db);
    }
}
