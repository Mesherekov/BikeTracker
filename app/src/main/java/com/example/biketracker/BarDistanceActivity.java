package com.example.biketracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarDistanceActivity extends AppCompatActivity {
BarChart barChart;
ArrayList<BarEntry> barArray;
DistanceDataBase distanceDataBase;
    MyDataBase myDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_distance);
        barChart = findViewById(R.id.barChart);
        myDataBase = new MyDataBase(this);
        SQLiteDatabase sqldb = myDataBase.getWritableDatabase();
        Cursor cursor = sqldb.query(MyDataBase.TableContacts, null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            int themeIndex = cursor.getColumnIndex(MyDataBase.Theme);
            getWindow().setStatusBarColor(cursor.getInt(themeIndex));
            getWindow().setNavigationBarColor(cursor.getInt(themeIndex));
            cursor.close();
        }
        distanceDataBase = new DistanceDataBase(this);
        SQLiteDatabase sqldist = distanceDataBase.getWritableDatabase();
        Cursor cursorprof = sqldist.query(DistanceDataBase.TableDistance, null, null, null, null, null, null);
        barArray = new ArrayList<BarEntry>();
        int distance = cursorprof.getColumnIndex(DistanceDataBase.DISTANCE);
        int day = cursorprof.getColumnIndex(DistanceDataBase.DAY);
        if(cursorprof.moveToFirst()) {
            int prevday = 0;
            for (int i = 1; i <= cursorprof.getCount(); i++) {
                if(prevday > cursorprof.getInt(day)){
                    prevday++;
                    barArray.add(new BarEntry(prevday, cursorprof.getInt(distance)));
                }else {
                    barArray.add(new BarEntry((int) cursorprof.getInt(day), cursorprof.getInt(distance)));
                    prevday = cursorprof.getInt(day);
                }
                cursorprof.moveToNext();
            }
        }
        cursorprof.close();
        ArrayList<String> sAxis = new ArrayList<>();
        Cursor cursorprof2 = sqldist.query(DistanceDataBase.TableDistance, null, null, null, null, null, null);
        int distance2 = cursorprof2.getColumnIndex(DistanceDataBase.DISTANCE);
        int day2 = cursorprof2.getColumnIndex(DistanceDataBase.DAY);
        int month = cursorprof2.getColumnIndex(DistanceDataBase.MONTH);
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
                    if(cursorprof2.getInt(month)>=10) {
                        sAxis.add(prevday, cursorprof2.getInt(day2) + "." + cursorprof2.getInt(month));
                    }
                    if(cursorprof2.getInt(month)<10) {
                        sAxis.add(prevday, cursorprof2.getInt(day2) + "."  + cursorprof2.getInt(month));
                    }
                }else {
                    sAxis.add(cursorprof2.getInt(day2), cursorprof2.getInt(day2) + "." + cursorprof2.getInt(month));
                    prevday = cursorprof2.getInt(day);
                }
                cursorprof2.moveToNext();
            }
        }
        cursorprof2.close();


        BarDataSet barDataSet = new BarDataSet(barArray, "Твоя дистанция");
        BarData barData = new BarData(barDataSet);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(sAxis));
        barChart.animateY(500);
        barChart.setData(barData);
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        barDataSet.setColor(Color.RED);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);
    }
    }