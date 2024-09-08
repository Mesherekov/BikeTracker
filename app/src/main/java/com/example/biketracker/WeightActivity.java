package com.example.biketracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class WeightActivity extends AppCompatActivity {
    BarChart barChart;
    ArrayList<BarEntry> barArray;
    DataWeight weightDataBase;
    MyDataBase myDataBase;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        barChart = findViewById(R.id.barChart1);
        weightDataBase = new DataWeight(this);
        myDataBase = new MyDataBase(this);
        SQLiteDatabase sqldb = myDataBase.getWritableDatabase();
        Cursor cursor = sqldb.query(MyDataBase.TableContacts, null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            int themeIndex = cursor.getColumnIndex(MyDataBase.Theme);
            getWindow().setStatusBarColor(cursor.getInt(themeIndex));
            getWindow().setNavigationBarColor(cursor.getInt(themeIndex));
            cursor.close();
        }
        SQLiteDatabase sqldist = weightDataBase.getWritableDatabase();
        Cursor cursorprof = sqldist.query(DataWeight.TableWeight, null, null, null, null, null, null);
        barArray = new ArrayList<BarEntry>();
        int distance = cursorprof.getColumnIndex(DataWeight.WEIGHT);
        int day = cursorprof.getColumnIndex(DataWeight.DAY);
        if(cursorprof.moveToFirst()) {
            int prevday = 0;
            for (int i = 1; i <= cursorprof.getCount(); i++) {
                if(prevday > cursorprof.getInt(day)){
                    prevday++;
                    barArray.add(new BarEntry(prevday, cursorprof.getFloat(distance)));
                }else {
                    barArray.add(new BarEntry((int) cursorprof.getInt(day), cursorprof.getFloat(distance)));
                    prevday = cursorprof.getInt(day);
                }
                cursorprof.moveToNext();
            }
        }
        cursorprof.close();
        ArrayList<String> sAxis = new ArrayList<>();
        Cursor cursorprof2 = sqldist.query(DataWeight.TableWeight, null, null, null, null, null, null);
        int distance2 = cursorprof2.getColumnIndex(DataWeight.WEIGHT);
        int day2 = cursorprof2.getColumnIndex(DataWeight.DAY);
        int month = cursorprof2.getColumnIndex(DataWeight.MONTH);
        sAxis.add(0, "");
        if(cursorprof2.moveToFirst()) {
            int prevday = 0;
            if(cursorprof2.getInt(day2)!=1){
                for (int i = 0; i < cursorprof2.getInt(day2); i++) {
                    sAxis.add(i, " ");
                }
            }
            for (int i = 1; i <= cursorprof2.getCount(); i++) {
                if(prevday > cursorprof2.getInt(day)){
                    prevday++;
                    sAxis.add(prevday, cursorprof2.getInt(day2) + "." + cursorprof2.getInt(month));
                }else {
                    sAxis.add(cursorprof2.getInt(day2), cursorprof2.getInt(day2) + "." + cursorprof2.getInt(month));
                    prevday = cursorprof2.getInt(day);
                }
                cursorprof2.moveToNext();
            }
        }
        cursorprof2.close();
        BarDataSet barDataSet = new BarDataSet(barArray, "Твой вес");
        BarData barData = new BarData(barDataSet);
        barChart.animateY(800);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(sAxis));
        barChart.setData(barData);
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        barDataSet.setColor(Color.rgb(255, 67, 0));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);
    }
}