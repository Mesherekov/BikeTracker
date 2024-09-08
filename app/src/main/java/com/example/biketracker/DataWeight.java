package com.example.biketracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataWeight extends SQLiteOpenHelper {
    public static final int VERSION_DATABASE =  8;
    public static final String WeightName = "weightdb";
    public static final String TableWeight = "weightdistance";

    public static final String ID = "id";
    public static final String DAY = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String WEIGHT = "weight";

    DataWeight(@Nullable Context context){
        super(context, WeightName, null, VERSION_DATABASE);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TableWeight + "(" + ID + " INTEGER PRIMARY KEY," + DAY + " INTEGER," + MONTH + " INTEGER," + YEAR + " INTEGER," + WEIGHT + " Real" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TableWeight);
        onCreate(db);
    }
}
