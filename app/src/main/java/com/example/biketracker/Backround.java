package com.example.biketracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;


public class Backround extends Service implements LocationListener {
    Location lastlocation;
    public LocationManager locationManager;
    private static final String NOTIFICATION_ID_STRING = "ForegroundServiceChannel";
    DistanceDataBase dataBase;
    Location location;
    Calendar calendar = Calendar.getInstance();

    int distance;
    private MediaPlayer player;
    Handler handler = new Handler(Looper.getMainLooper());
    public void start(Context context){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, 3000);
            }
        };
        handler.post(runnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      //  String input = intent.getStringExtra("inputExtra");

       /* Intent notifint = new Intent(this, SpeedActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifint, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_ID_STRING).setContentTitle("ForegroundService").setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent).build();
        startForeground(1, notification);*/

        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        doStuff();
    }

    @SuppressLint("MissingPermission")
    private void doStuff() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            //Определяет интервал между обновлением GPS
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 1, this);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
       /* if (location.hasSpeed() && lastlocation != null) {
            distance += lastlocation.distanceTo(location);
            dataBase = new DistanceDataBase(this);
            SQLiteDatabase sqldist = dataBase.getWritableDatabase();
            Cursor cursor3 = sqldist.query(DistanceDataBase.TableDistance, null, null, null, null, null, null);
            cursor3.moveToFirst();
            int day = cursor3.getColumnIndex(DistanceDataBase.DAY);
            int month = cursor3.getColumnIndex(DistanceDataBase.MONTH);
            int year = cursor3.getColumnIndex(DistanceDataBase.YEAR);
            cursor3.moveToLast();
            if (cursor3.getInt(day) == calendar.get(Calendar.DAY_OF_MONTH) && cursor3.getInt(month) == calendar.get(Calendar.MONTH) + 1 && cursor3.getInt(year) == calendar.get(Calendar.YEAR)) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DistanceDataBase.DISTANCE, distance);
                int upcount = sqldist.update(DistanceDataBase.TableDistance, contentValues, DistanceDataBase.ID + "= ?", new String[]{Integer.toString(cursor3.getCount())});
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DistanceDataBase.DAY, calendar.get(Calendar.DAY_OF_MONTH));
                contentValues.put(DistanceDataBase.MONTH, calendar.get(Calendar.MONTH) + 1);
                contentValues.put(DistanceDataBase.YEAR, calendar.get(Calendar.YEAR));
                contentValues.put(DistanceDataBase.DISTANCE, 0);
                sqldist.insert(DistanceDataBase.TableDistance, null, contentValues);
                cursor3.moveToNext();
            }
            Toast.makeText(this, Integer.toString(distance), Toast.LENGTH_SHORT).show();
            cursor3.close();
        }
        lastlocation = location;*/

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
