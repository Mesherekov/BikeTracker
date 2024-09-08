package com.example.biketracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseProfile extends SQLiteOpenHelper {
    public static final int VERSION_DATABASE = 12;
    public static final String DataBaseName = "userdb";
    public static final String TableUserContacts = "contacts";

    public static final String ID = "id";
    public static final String Name = "name";
    public static final String Speed = "speed";
    public static final String Speedname = "speedname";
    public static final String PurposeDistance = "purposedistance";
    public static final String Booldist = "booldist";
    public static final String Tooldist = "tooldist";

    public DataBaseProfile(@Nullable Context context) {
        super(context, DataBaseName, null, VERSION_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TableUserContacts + "(" + ID + " INTEGER PRIMARY KEY," + Name + " TEXT," + Speed + " REAL,"+ Speedname + " TEXT," + PurposeDistance + " INTEGER," + Booldist + " TEXT," + Tooldist +
                " INTEGER" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TableUserContacts);
        onCreate(db);
    }
}
