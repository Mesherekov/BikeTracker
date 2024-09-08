package com.example.biketracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DistanceDataBase extends SQLiteOpenHelper {
    public static final int VERSION_DATABASE = 6;
    public static final String DataBaseName = "distancedb";
    public static final String TableDistance = "usersdistance";

    public static final String ID = "id";
    public static final String DAY = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String DISTANCE = "distance";
    public DistanceDataBase(@Nullable Context context) {
        super(context, DataBaseName, null, VERSION_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TableDistance + "(" + ID + " INTEGER PRIMARY KEY," + DAY + " INTEGER," + MONTH + " INTEGER," + YEAR + " INTEGER," + DISTANCE + " INTEGER" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TableDistance);
        onCreate(db);
    }
}
