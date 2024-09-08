package com.example.biketracker;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    private LottieAnimationView animationView;
    MediaPlayer mplayer;
    MyDataBase myDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animationView = findViewById(R.id.animationView);
        mplayer = MediaPlayer.create(this, R.raw.bikecycle);
        try {
            myDataBase = new MyDataBase(this);
            SQLiteDatabase sqldb = myDataBase.getWritableDatabase();
            Cursor cursor = sqldb.query(MyDataBase.TableContacts, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int themeIndex = cursor.getColumnIndex(MyDataBase.Theme);
                getWindow().setStatusBarColor(cursor.getInt(themeIndex));
                getWindow().setNavigationBarColor(cursor.getInt(themeIndex));
                cursor.close();
            }
        } catch (Exception ex){
            Log.e("Sql", ex.getMessage());
        }
        mplayer.start();
        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SpeedActivity.class);
                mplayer.stop();
                startActivity(intent);
                finish();
            }
        });
    }
}