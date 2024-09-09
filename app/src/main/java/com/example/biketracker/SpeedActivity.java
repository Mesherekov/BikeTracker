package com.example.biketracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;
public class SpeedActivity extends AppCompatActivity implements LocationListener {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch  sw_time, sw_theme;
    private boolean clicked = false;
    TextView tv_speed, maxspeed, maxspeed2, distancetext, distpurtext, alld, yourstatistics;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch swmetr;
    NumberPicker numberPicker;
    EditText username, yourweight;
    String der;
    SharedPreferences spref;
    boolean isfirst = false;
    SQLiteDatabase sqldist;
    ConstraintLayout constraintLayout, profcon;
    boolean resetdist;
    private Animation rotateOpen;
    private Animation rotateClose;
    ProgressBar progressBar;
    private Animation fromBottom;

    private Animation toBottom;
    ImageButton stop, start, reset, delname, delspeed, penciledit, orange, blue, black, green;
    Chronometer chronometer;
    MediaPlayer mp3, mp4;
    ImageView road, moon, timer;
    float speed, mspeeed, maxspeednum;
    Button setdist, enterweight;
    ImageButton alldist,  weight;
    FloatingActionButton addbtn, setbtn, profile;
    MyDataBase myDataBase;
    DataBaseProfile dataBaseProfile;
    DistanceDataBase distanceDataBase;
    boolean setdb = false;
    boolean setspeed;
    String nameofspeed;
    float savespeed;
    int distance;
    Location lastlocation;
    int purposedist;
    final String SAVED_TEXT = "saved_text";
    SensorManager msensormanager = null;
    Sensor stepsensor;
    int totalsteps = 0;
    int previewsTotalSteps = 0;
    Calendar calendar;
    SQLiteDatabase sqlprof;
    boolean setclip = true;
    long timeWhenStopped = 0;
    DataWeight dataWeight;
    int savecol = 0;
    @SuppressLint({"MissingInflatedId", "ObsoleteSdkInt", "SetTextI18n", "WrongViewCast", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spees);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        loadbool();
        tv_speed = findViewById(R.id.textView);
        maxspeed = findViewById(R.id.textView2);
        setdist = findViewById(R.id.setdist);
        alld = findViewById(R.id.textView3);
        penciledit = findViewById(R.id.penciledit);
        distpurtext = findViewById(R.id.distpurtext);
        road = findViewById(R.id.road);
        moon = findViewById(R.id.moon);
        timer = findViewById(R.id.timer);
        profcon = findViewById(R.id.profcont);
        orange = findViewById(R.id.orangetheme);
        blue = findViewById(R.id.bluetheme);
        black = findViewById(R.id.blacktheme);
        green = findViewById(R.id.greentheme);
        constraintLayout = findViewById(R.id.contraint);
        yourstatistics = findViewById(R.id.yourstatic);
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        reset = findViewById(R.id.reset);
        sw_time = findViewById(R.id.swtime);
        numberPicker = findViewById(R.id.Numberofsteps);
        username = findViewById(R.id.username);
        maxspeed2 = findViewById(R.id.maxspeed2);
        sw_theme = findViewById(R.id.darktheme);
        chronometer = findViewById(R.id.simpleChronometer);
        addbtn = findViewById(R.id.addbtn);
        setbtn = findViewById(R.id.settingsbtn);
        profile = findViewById(R.id.profile);
        delname = findViewById(R.id.deletename);
        delspeed = findViewById(R.id.deletespeed);
        distancetext = findViewById(R.id.distance);
        weight = findViewById(R.id.weight);
        alldist = findViewById(R.id.alldistance);
        maxspeednum = 0;
        progressBar = findViewById(R.id.progressBar);
        swmetr = findViewById(R.id.switch2);
        yourweight = findViewById(R.id.enterweight);
        enterweight = findViewById(R.id.okweight);
        startService(new Intent(this, Backround.class));
        numberPicker.setMaxValue(29);
        numberPicker.setMinValue(1);
        myDataBase = new MyDataBase(this);
        SQLiteDatabase sqldb = myDataBase.getWritableDatabase();

        orange.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                mp4.start();
                @SuppressLint("ResourceType") int colorFrom = savecol;
                int colorTo = Color.rgb(255, 67, 0);
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(400); // milliseconds
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        getWindow().setStatusBarColor((int) animator.getAnimatedValue());
                        getWindow().setNavigationBarColor((int) animator.getAnimatedValue());
                        addbtn.setBackgroundTintList(ColorStateList.valueOf((int) animator.getAnimatedValue()));
                    }

                });
                colorAnimation.start();
                //getWindow().setStatusBarColor(Color.rgb(255, 67, 0));
                //getWindow().setNavigationBarColor(Color.rgb(255, 67, 0));
                savecol = Color.rgb(255, 67, 0);
                ContentValues contentValues = new ContentValues();
                contentValues.put(MyDataBase.Theme, savecol);
                int upcount = sqldb.update(DataBaseProfile.TableUserContacts, contentValues, DataBaseProfile.ID + "= ?", new String[] {"1"});
            }
        });


        blue.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                mp4.start();
                @SuppressLint("ResourceType") int colorFrom = savecol;
                int colorTo = Color.rgb(8, 0, 206);
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, Color.rgb(8, 0, 206));
                colorAnimation.setDuration(400); // milliseconds
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        getWindow().setStatusBarColor((int) animator.getAnimatedValue());
                        getWindow().setNavigationBarColor((int) animator.getAnimatedValue());
                        addbtn.setBackgroundTintList(ColorStateList.valueOf((int) animator.getAnimatedValue()));
                    }

                });
                colorAnimation.start();
                //getWindow().setStatusBarColor(Color.rgb(8, 0, 206));
                //getWindow().setNavigationBarColor(Color.rgb(8, 0, 206));
                savecol = Color.rgb(8, 0, 206);
                ContentValues contentValues = new ContentValues();
                contentValues.put(MyDataBase.Theme, savecol);
                int upcount = sqldb.update(DataBaseProfile.TableUserContacts, contentValues, DataBaseProfile.ID + "= ?", new String[] {"1"});
            }
        });
        black.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                mp4.start();
                @SuppressLint("ResourceType") int colorFrom = savecol;
                int colorTo = Color.BLACK;
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(400); // milliseconds
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        getWindow().setStatusBarColor((int) animator.getAnimatedValue());
                        getWindow().setNavigationBarColor((int) animator.getAnimatedValue());
                        addbtn.setBackgroundTintList(ColorStateList.valueOf((int) animator.getAnimatedValue()));
                    }

                });
                colorAnimation.start();
                //getWindow().setStatusBarColor(Color.BLACK);
                //getWindow().setNavigationBarColor(Color.BLACK);
                savecol = Color.BLACK;
                ContentValues contentValues = new ContentValues();
                contentValues.put(MyDataBase.Theme, savecol);
                int upcount = sqldb.update(DataBaseProfile.TableUserContacts, contentValues, DataBaseProfile.ID + "= ?", new String[] {"1"});
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                mp4.start();
                @SuppressLint("ResourceType") int colorFrom = savecol;
                int colorTo = Color.rgb(0, 174, 93);
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(400); // milliseconds
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        getWindow().setStatusBarColor((int) animator.getAnimatedValue());
                        getWindow().setNavigationBarColor((int) animator.getAnimatedValue());
                        addbtn.setBackgroundTintList(ColorStateList.valueOf((int) animator.getAnimatedValue()));
                    }

                });
                colorAnimation.start();
                //getWindow().setStatusBarColor(Color.rgb(0, 174, 93));
                //getWindow().setNavigationBarColor(Color.rgb( 0, 174, 93));
                savecol = Color.rgb( 0, 174, 93);
                ContentValues contentValues = new ContentValues();
                contentValues.put(MyDataBase.Theme, savecol);
                int upcount = sqldb.update(DataBaseProfile.TableUserContacts, contentValues, DataBaseProfile.ID + "= ?", new String[] {"1"});
            }
        });
        numberPicker.setDisplayedValues( new String[] {"2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000", "11000", "12000", "13000", "14000", "15000", "16000",
                "17000", "18000", "19000", "20000", "21000", "22000", "23000", "24000", "25000", "26000", "27000", "28000", "29000", "30000"
        } );

        calendar = Calendar.getInstance();
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 100);
        }

        msensormanager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepsensor = msensormanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        der = "User name";
        mp3 = MediaPlayer.create(this, R.raw.kasset2);
        mp4 = MediaPlayer.create(this, R.raw.komp);
        dataWeight = new DataWeight(this);
        SQLiteDatabase sqlweigt = dataWeight.getWritableDatabase();
        dataBaseProfile = new DataBaseProfile(this);
        distanceDataBase = new DistanceDataBase(this);
        sqlprof = dataBaseProfile.getWritableDatabase();
        sqldist = distanceDataBase.getWritableDatabase();
        try{
            yourstatistics.setText("Всего преодолели: " + getAllDistance()+ " метров");
        }catch (Exception ex){
            yourstatistics.setText("Всего преодолели: 0 метров");
        }
        try {
            Cursor cursordist = sqldist.query(DistanceDataBase.TableDistance, null, null, null, null, null, null);
            int distance2 = cursordist.getColumnIndex(DistanceDataBase.DISTANCE);
            int day = cursordist.getColumnIndex(DistanceDataBase.DAY);
            int month = cursordist.getColumnIndex(DistanceDataBase.MONTH);
            int year = cursordist.getColumnIndex(DistanceDataBase.YEAR);
            if(cursordist.moveToLast()) {
                if (cursordist.getInt(day) == calendar.get(Calendar.DAY_OF_MONTH) && cursordist.getInt(month) == calendar.get(Calendar.MONTH) + 1 && cursordist.getInt(year) == calendar.get(Calendar.YEAR)) {
                    distance += cursordist.getInt(distance2);
                    distancetext.setText(String.valueOf(distance));
                    progressBar.setProgress(distance);
                }
            }
            cursordist.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        if(!isfirst){
            setclip = false;
            chronometer.setVisibility(View.INVISIBLE);
            stop.setVisibility(View.INVISIBLE);
            start.setVisibility(View.INVISIBLE);
            reset.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            distancetext.setVisibility(View.INVISIBLE);
            maxspeed.setVisibility(View.INVISIBLE);
            tv_speed.setVisibility(View.INVISIBLE);
            numberPicker.setVisibility(View.VISIBLE);
            setdist.setVisibility(View.VISIBLE);
            ContentValues contentValues = new ContentValues();
            contentValues.put(MyDataBase.Theme, Color.rgb(255, 67, 0));
            sqldb.insert(MyDataBase.TableContacts, null, contentValues);
            contentValues.put(MyDataBase.Name, "metr");
            contentValues.put(MyDataBase.Bool, "true");
            sqldb.insert(MyDataBase.TableContacts, null, contentValues);
            contentValues.put(MyDataBase.Name, "theme");
            contentValues.put(MyDataBase.Bool, "false");
            sqldb.insert(MyDataBase.TableContacts, null, contentValues);
            contentValues.put(MyDataBase.Name, "spe");
            contentValues.put(MyDataBase.Bool, "false");
            sqldb.insert(MyDataBase.TableContacts, null, contentValues);
            ContentValues contentValuesprof = new ContentValues();
            contentValuesprof.put(DataBaseProfile.Name, "User");
            contentValuesprof.put(DataBaseProfile.Speed, 0.0);
            contentValuesprof.put(DataBaseProfile.Speedname, " km/h");
            contentValuesprof.put(DataBaseProfile.PurposeDistance, 0);
            contentValuesprof.put(DataBaseProfile.Booldist, "true");
            contentValuesprof.put(DataBaseProfile.Tooldist, 0);
            sqlprof.insert(DataBaseProfile.TableUserContacts, null, contentValuesprof);
        }

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (!isSQLEMPTY(sqlweigt, DataWeight.TableWeight)) {
                        yourweight.setVisibility(View.VISIBLE);
                        enterweight.setVisibility(View.VISIBLE);
                        profcon.setVisibility(View.INVISIBLE);
                    } else {
                        try {
                        Cursor curweight = sqlweigt.query(DataWeight.TableWeight, null, null, null, null, null, null);
                        int dayweight = curweight.getColumnIndex(DataWeight.DAY);
                        int monthweight = curweight.getColumnIndex(DataWeight.MONTH);
                        int yearweight = curweight.getColumnIndex(DataWeight.YEAR);
                        curweight.moveToLast();
                        if (curweight.getInt(dayweight) == calendar.get(Calendar.DAY_OF_MONTH) && curweight.getInt(monthweight) == calendar.get(Calendar.MONTH) + 1 && curweight.getInt(yearweight) == calendar.get(Calendar.YEAR)) {
                            Intent intent = new Intent(SpeedActivity.this, WeightActivity.class);
                            startActivity(intent);
                        } else {
                            yourweight.setVisibility(View.VISIBLE);
                            enterweight.setVisibility(View.VISIBLE);
                            profcon.setVisibility(View.INVISIBLE);
                        }
                        curweight.close();
                        mp4.start();
                    }catch (Exception ex){
                            Toast.makeText(SpeedActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });
        enterweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ContentValues contentweight = new ContentValues();
                    contentweight.put(DataWeight.DAY, calendar.get(Calendar.DAY_OF_MONTH));
                    contentweight.put(DistanceDataBase.MONTH, calendar.get(Calendar.MONTH) + 1);
                    contentweight.put(DistanceDataBase.YEAR, calendar.get(Calendar.YEAR));
                    contentweight.put(DataWeight.WEIGHT, Float.parseFloat(yourweight.getText().toString()));
                    sqlweigt.insert(DataWeight.TableWeight, null, contentweight);
                    yourweight.setVisibility(View.INVISIBLE);
                    enterweight.setVisibility(View.INVISIBLE);
                    maxspeed2.setVisibility(View.VISIBLE);
                    username.setVisibility(View.VISIBLE);
                    delname.setVisibility(View.VISIBLE);
                    delspeed.setVisibility(View.VISIBLE);
                    distpurtext.setVisibility(View.VISIBLE);
                    penciledit.setVisibility(View.VISIBLE);
                    alldist.setVisibility(View.VISIBLE);
                    alld.setVisibility(View.VISIBLE);
                    weight.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(SpeedActivity.this, WeightActivity.class);
                    startActivity(intent);
                } catch (Exception exception){
                    Toast.makeText(SpeedActivity.this, "Введите число", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(!isSQLEMPTY(sqldist, DistanceDataBase.TableDistance)){
            ContentValues contentValuesdist = new ContentValues();
            contentValuesdist.put(DistanceDataBase.DAY, calendar.get(Calendar.DAY_OF_MONTH));
            contentValuesdist.put(DistanceDataBase.MONTH, calendar.get(Calendar.MONTH) + 1);
            contentValuesdist.put(DistanceDataBase.YEAR, calendar.get(Calendar.YEAR));
            contentValuesdist.put(DistanceDataBase.DISTANCE, 0.0);
            sqldist.insert(DistanceDataBase.TableDistance, null, contentValuesdist);
        }


        setdist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isfirst = true;
                setclip = true;
                savebool();
                purposedist = Integer.parseInt(String.valueOf(numberPicker.getDisplayedValues()[numberPicker.getValue()-1]));;
                chronometer.setVisibility(View.VISIBLE);
                stop.setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);
                reset.setVisibility(View.VISIBLE);
                distancetext.setVisibility(View.VISIBLE);
                maxspeed.setVisibility(View.VISIBLE);
                tv_speed.setVisibility(View.VISIBLE);
                numberPicker.setVisibility(View.INVISIBLE);
                setdist.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put(DataBaseProfile.PurposeDistance, purposedist);
                progressBar.setMax(Integer.parseInt(String.valueOf(numberPicker.getDisplayedValues()[numberPicker.getValue()-1])));
                distpurtext.setText("Целевая дистанция: " + Integer.toString(purposedist));
                int upcount = sqlprof.update(DataBaseProfile.TableUserContacts, contentValues2, DataBaseProfile.ID + "= ?", new String[] {"1"});

            }
        });


        Cursor cursor = sqldb.query(MyDataBase.TableContacts, null, null, null, null, null, null);


        if(cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(MyDataBase.ID);
            int nameIndex = cursor.getColumnIndex(MyDataBase.Name);
            int boolIndex = cursor.getColumnIndex(MyDataBase.Bool);
            int themeIndex = cursor.getColumnIndex(MyDataBase.Theme);
            savecol = cursor.getInt(themeIndex);
            getWindow().setStatusBarColor(savecol);
            getWindow().setNavigationBarColor(savecol);
            addbtn.setBackgroundTintList(ColorStateList.valueOf(savecol));
            swmetr.setChecked(Boolean.parseBoolean(cursor.getString(boolIndex)));
            cursor.moveToNext();
            sw_theme.setChecked(Boolean.parseBoolean(cursor.getString(boolIndex)));
            if(sw_theme.isChecked()){
                setDarkTheme();
            }
            else{
                setLightTheme();
            }
            cursor.moveToNext();
            sw_time.setChecked(Boolean.parseBoolean(cursor.getString(boolIndex)));
            cursor.close();
        }

        Cursor cursorprof = sqlprof.query(DataBaseProfile.TableUserContacts, null, null, null, null, null, null);
        if(cursorprof.moveToFirst()){
            int idIndex = cursorprof.getColumnIndex(DataBaseProfile.ID);
            int nameIndex = cursorprof.getColumnIndex(DataBaseProfile.Name);
            int speedIndex = cursorprof.getColumnIndex(DataBaseProfile.Speed);
            int purdist = cursorprof.getColumnIndex(DataBaseProfile.PurposeDistance);
            int speednameIndex = cursorprof.getColumnIndex(DataBaseProfile.Speedname);
            int booldistIndex = cursorprof.getColumnIndex(DataBaseProfile.Booldist);
            resetdist = Boolean.parseBoolean(cursorprof.getString(booldistIndex));
            distpurtext.setText("Целевая дистанция: " + Integer.toString(cursorprof.getInt(purdist)));
            progressBar.setMax(cursorprof.getInt(purdist));
            try {
                progressBar.setProgress(distance);
            }catch (Exception ex){

            }
            username.setText(cursorprof.getString(nameIndex));
            maxspeed2.setText("Максимальная скорость: " + Float.toString(cursorprof.getFloat(speedIndex)) + cursorprof.getString(speednameIndex));
            savespeed = cursorprof.getFloat(speedIndex);
            cursorprof.close();
        }
        final String[] sped = new String[1];
        final boolean[] setb = {false};
        final boolean[] setb2 = {false};

        addbtn.setOnClickListener(view -> {
            onAddButtonClicked();
            mp4.start();
        });
        chronometer.setOnChronometerTickListener(chronometer -> {
            long elapsedMillis = (SystemClock.elapsedRealtime() - chronometer.getBase());
        });
        swmetr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpeedActivity.this.updateSpeed(null);
                mp3.start();
                if(swmetr.isChecked()){
                    SQLUpdate("1", sqldb, "true");
                } else SQLUpdate("1", sqldb, "false");
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profile.getVisibility() == View.VISIBLE) {
                        if (setclip) {
                            if (!setb2[0]) {

                                profcon.setVisibility(View.VISIBLE);

                                chronometer.setVisibility(View.INVISIBLE);
                                stop.setVisibility(View.INVISIBLE);
                                start.setVisibility(View.INVISIBLE);
                                reset.setVisibility(View.INVISIBLE);
                                distancetext.setVisibility(View.INVISIBLE);
                                maxspeed.setVisibility(View.INVISIBLE);
                                tv_speed.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                constraintLayout.setVisibility(View.INVISIBLE);
                                setb[0] = false;
                                mp4.start();
                                setb2[0] = true;
                            } else {
                                profcon.setVisibility(View.INVISIBLE);
                                chronometer.setVisibility(View.VISIBLE);
                                stop.setVisibility(View.VISIBLE);
                                start.setVisibility(View.VISIBLE);
                                reset.setVisibility(View.VISIBLE);
                                distancetext.setVisibility(View.VISIBLE);
                                maxspeed.setVisibility(View.VISIBLE);
                                tv_speed.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.VISIBLE);
                                sped[0] = maxspeed2.getText().toString();

                                der = username.getText().toString();
                                mp4.start();
                                setb2[0] = false;
                            }
                            SQLUSERUPDATE("1", sqlprof, username.getText().toString());
                        }

                }
            }
        });
        penciledit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setclip = false;
                profcon.setVisibility(View.INVISIBLE);
                sped[0] = maxspeed2.getText().toString();
                der = username.getText().toString();
                mp4.start();
                setb[0] = false;
                numberPicker.setVisibility(View.VISIBLE);
                setdist.setVisibility(View.VISIBLE);
                chronometer.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.INVISIBLE);
                start.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.INVISIBLE);
                distancetext.setVisibility(View.INVISIBLE);
                maxspeed.setVisibility(View.INVISIBLE);
                tv_speed.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                weight.setVisibility(View.INVISIBLE);
                alldist.setVisibility(View.INVISIBLE);
                alld.setVisibility(View.INVISIBLE);
            }
        });
        maxspeed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SQLSpeedUPDATE("1", sqlprof, maxspeednum);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        alldist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpeedActivity.this, BarDistanceActivity.class);
                startActivity(intent);
                mp4.start();
            }
        });
        setbtn.setOnClickListener(view -> {
            if (setbtn.getVisibility() == View.VISIBLE) {
                    if (setclip) {
                        if (!setb[0]) {
                            chronometer.setVisibility(View.INVISIBLE);
                            stop.setVisibility(View.INVISIBLE);
                            start.setVisibility(View.INVISIBLE);
                            reset.setVisibility(View.INVISIBLE);
                            distancetext.setVisibility(View.INVISIBLE);
                            maxspeed.setVisibility(View.INVISIBLE);
                            tv_speed.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            constraintLayout.setVisibility(View.VISIBLE);
                            profcon.setVisibility(View.INVISIBLE);
                            mp4.start();
                            setb[0] = true;
                            setb2[0] = false;
                        } else {
                            chronometer.setVisibility(View.VISIBLE);
                            stop.setVisibility(View.VISIBLE);
                            start.setVisibility(View.VISIBLE);
                            reset.setVisibility(View.VISIBLE);
                            distancetext.setVisibility(View.VISIBLE);
                            maxspeed.setVisibility(View.VISIBLE);
                            tv_speed.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            constraintLayout.setVisibility(View.INVISIBLE);
                            mp4.start();
                            setb[0] = false;
                        }
                    }

            }
        });
        start.setOnClickListener(view -> {
            chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            chronometer.start();
            mp4.start();
        });
        stop.setOnClickListener(view -> {
            timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
            mp4.start();
        });
        reset.setOnClickListener(view -> {
            chronometer.setBase(SystemClock.elapsedRealtime());
            timeWhenStopped = 0;
            mp4.start();
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
            doStuff();
        }
        this.updateSpeed(null);

        sw_theme.setOnCheckedChangeListener((compoundButton, b) -> {
            mp3.start();
            if(sw_theme.isChecked())
            SQLUpdate("2", sqldb, "true");
            else SQLUpdate("2",  sqldb, "false");
            if (sw_theme.isChecked()) setDarkTheme();
            else setLightTheme();
        });
        delname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLUSERUPDATE("1", sqlprof, "Username");
                username.setText("Username");
                mp4.start();
            }
        });
        delspeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLSpeedUPDATE("1", sqlprof, 0.0f);
                maxspeednum = 0.0f;
                maxspeed2.setText("Максимальная скорость: " + maxspeednum + nameofspeed);
                mp4.start();
            }
        });
        sw_time.setOnCheckedChangeListener((compoundButton, b) -> {
            if (sw_time.isChecked())
            SQLUpdate("3",  sqldb, "true");
            else SQLUpdate("3",  sqldb, "false");
            mp3.start();
        });
        if (sw_time.isChecked() && speed > 0) {
            chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            chronometer.start();
        }
        if (sw_time.isChecked() && speed == 0) {
            timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
        }

    }


    private void onAddButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }

    private void setVisibility(boolean clicked) {
        if (!clicked) {
            profile.setVisibility(View.VISIBLE);
            setbtn.setVisibility(View.VISIBLE);
        } else {
            profile.setVisibility(View.INVISIBLE);
            setbtn.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation(boolean clicked) {
        if (!clicked) {
            profile.startAnimation(fromBottom);
            setbtn.startAnimation(fromBottom);
            addbtn.startAnimation(rotateOpen);
        } else {
            profile.startAnimation(toBottom);
            setbtn.startAnimation(toBottom);
            addbtn.startAnimation(rotateClose);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        int textcolor;
        if (sw_theme.isChecked()){
            textcolor = R.color.white;
            delspeed.setBackgroundColor(ContextCompat.getColor(this, R.color.Solid2));
            delname.setBackgroundColor(ContextCompat.getColor(this, R.color.Solid2));
            penciledit.setBackgroundColor(ContextCompat.getColor(this, R.color.Solid2));
            road.setColorFilter(ContextCompat.getColor(this, R.color.White));
            moon.setColorFilter(ContextCompat.getColor(this, R.color.White));
            timer.setColorFilter(ContextCompat.getColor(this, R.color.White));
            delspeed.setColorFilter(ContextCompat.getColor(this, R.color.White));
            delname.setColorFilter(ContextCompat.getColor(this, R.color.White));
            penciledit.setColorFilter(ContextCompat.getColor(this, R.color.White));
            stop.setColorFilter(ContextCompat.getColor(this, R.color.White));
            start.setColorFilter(ContextCompat.getColor(this, R.color.White));
            reset.setColorFilter(ContextCompat.getColor(this, R.color.White));

        } else{
            textcolor = R.color.black;
            delspeed.setBackgroundColor(ContextCompat.getColor(this, R.color.WhiteSmoke));
            delname.setBackgroundColor(ContextCompat.getColor(this, R.color.WhiteSmoke));
            penciledit.setBackgroundColor(ContextCompat.getColor(this, R.color.WhiteSmoke));
            road.setColorFilter(ContextCompat.getColor(this, R.color.IconBase));
            moon.setColorFilter(ContextCompat.getColor(this, R.color.IconBase));
            timer.setColorFilter(ContextCompat.getColor(this, R.color.IconBase));
            delname.setColorFilter(ContextCompat.getColor(this, R.color.IconBase));
            delspeed.setColorFilter(ContextCompat.getColor(this, R.color.IconBase));
            penciledit.setColorFilter(ContextCompat.getColor(this, R.color.IconBase));
            stop.setColorFilter(ContextCompat.getColor(this, R.color.black));
            start.setColorFilter(ContextCompat.getColor(this, R.color.black));
            reset.setColorFilter(ContextCompat.getColor(this, R.color.black));
        }
        chronometer.setTextColor(ContextCompat.getColor(this, textcolor));
        tv_speed.setTextColor(ContextCompat.getColor(this, textcolor));
        maxspeed.setTextColor(ContextCompat.getColor(this, textcolor));
        maxspeed2.setTextColor(ContextCompat.getColor(this, textcolor));
        username.setTextColor(ContextCompat.getColor(this, textcolor));
        distpurtext.setTextColor(ContextCompat.getColor(this, textcolor));
        sw_time.setTextColor(ContextCompat.getColor(this, textcolor));
        sw_theme.setTextColor(ContextCompat.getColor(this, textcolor));
        swmetr.setTextColor(ContextCompat.getColor(this, textcolor));
        distancetext.setTextColor(ContextCompat.getColor(this, textcolor));
        yourstatistics.setTextColor(ContextCompat.getColor(this, textcolor));
    }



    protected void onPause(){
        super.onPause();

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location != null) {
            CLocation myLocation = new CLocation(location, this.useMetricUnits());
            this.updateSpeed(myLocation);
        }
        if(location.hasSpeed() && lastlocation != null) {
            distance += lastlocation.distanceTo(location);
            Cursor cursor3 = sqldist.query(DistanceDataBase.TableDistance, null, null, null, null, null, null);
            cursor3.moveToFirst();
            int day = cursor3.getColumnIndex(DistanceDataBase.DAY);
            int month = cursor3.getColumnIndex(DistanceDataBase.MONTH);
            int year = cursor3.getColumnIndex(DistanceDataBase.YEAR);
            cursor3.moveToLast();
            if(cursor3.getInt(day) == calendar.get(Calendar.DAY_OF_MONTH) && cursor3.getInt(month) == calendar.get(Calendar.MONTH)+1 && cursor3.getInt(year) == calendar.get(Calendar.YEAR)){
                ContentValues contentValues = new ContentValues();
                contentValues.put(DistanceDataBase.DISTANCE, distance);
                int upcount = sqldist.update(DistanceDataBase.TableDistance, contentValues, DistanceDataBase.ID + "= ?" , new String[] {Integer.toString(cursor3.getCount())});
            } else{
                ContentValues contentValues = new ContentValues();
                contentValues.put(DistanceDataBase.DAY, calendar.get(Calendar.DAY_OF_MONTH));
                contentValues.put(DistanceDataBase.MONTH, calendar.get(Calendar.MONTH)+1);
                contentValues.put(DistanceDataBase.YEAR, calendar.get(Calendar.YEAR));
                contentValues.put(DistanceDataBase.DISTANCE, 0);
                sqldist.insert(DistanceDataBase.TableDistance, null, contentValues);
                cursor3.moveToNext();

            }
            progressBar.setProgress(distance);
            cursor3.close();
            distancetext.setText(String.valueOf(distance));
        }
        lastlocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    private void setLightTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void setDarkTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @SuppressLint("MissingPermission")
    private void doStuff() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            //Определяет интервал между обновлением GPS
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 1, this);
        }
        Toast.makeText(this, "Waiting for GPS connection", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    private void updateSpeed(CLocation location) {
        float nCurrentSpeed = 0;

        if (location != null) {
            location.setUseMetricUnits(this.useMetricUnits());
            nCurrentSpeed = location.getSpeed();
        }
        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US, "%5.1f", nCurrentSpeed);
        String strCurrentSpeed = fmt.toString();
        strCurrentSpeed = strCurrentSpeed.replace(" ", "0");

        if (this.useMetricUnits()) {
            tv_speed.setText(strCurrentSpeed + " km/h");
            if (mspeeed < Float.parseFloat(strCurrentSpeed)) {
                mspeeed = Float.parseFloat(strCurrentSpeed);
                maxspeed.setText("Максимальная скорость: " + mspeeed + " km/h");
                if(mspeeed>maxspeednum){
                    maxspeednum = mspeeed;
                    if(savespeed<mspeeed) {
                        nameofspeed = " km/h";
                        maxspeed2.setText("Максимальная скорость: " + maxspeednum + " km/h");
                    }
                    setspeed = true;
                } else setspeed = false;
                speed = nCurrentSpeed;
            }
        } else {
            tv_speed.setText(strCurrentSpeed + " miles/h");
            if (mspeeed < Float.parseFloat(strCurrentSpeed)) {
                mspeeed = Float.parseFloat(strCurrentSpeed);
                maxspeed.setText("Максимальная скорость: " + mspeeed + " miles/h");
                if(mspeeed>maxspeednum){
                    maxspeednum = mspeeed;
                    if(savespeed<mspeeed) {
                        nameofspeed = " miles/h";
                        maxspeed2.setText("Максимальная скорость: " + maxspeednum + " miles/h");
                    }
                    setspeed = true;
                } else setspeed = false;
                assert location != null;
                if(location.hasSpeed() && lastlocation != null) {
                    distance += lastlocation.distanceTo(location);
                    Toast.makeText(this, Integer.toString(distance), Toast.LENGTH_SHORT).show();
                }
                lastlocation = location;

                speed = nCurrentSpeed;
            }
        }

    }


    private boolean useMetricUnits() {
        return swmetr.isChecked();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doStuff();
            } else {
                finish();
            }
        }
    }
    void SQLUpdate(String id,  SQLiteDatabase db, String word){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBase.Bool, word);
        int upcount = db.update(MyDataBase.TableContacts, contentValues, MyDataBase.ID + "= ?", new String[] {id});
    }
    void SQLUSERUPDATE(String id, SQLiteDatabase db, String word){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseProfile.Name, word);
        int upcount = db.update(DataBaseProfile.TableUserContacts, contentValues, DataBaseProfile.ID + "= ?", new String[] {id});
    }
    void SQLSpeedUPDATE(String id, SQLiteDatabase db, Float speed){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseProfile.Speed, speed);
        contentValues.put(DataBaseProfile.Speedname, nameofspeed);
        int upcount = db.update(DataBaseProfile.TableUserContacts, contentValues, DataBaseProfile.ID + "= ?", new String[] {id});
    }
    void loadbool(){
        spref = getPreferences(MODE_PRIVATE);
        isfirst = spref.getBoolean(SAVED_TEXT, false);
    }
    void savebool(){
        spref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = spref.edit();
        editor.putBoolean(SAVED_TEXT, isfirst);
        editor.apply();
    }
    public boolean isSQLEMPTY(SQLiteDatabase db, String nameofdata){
        Cursor cursor = db.query(nameofdata, null, null, null, null, null, null);
        boolean checkdata;
        if(cursor.getCount() == 0){
            checkdata =  false;
        } else checkdata = true;
        cursor.close();
        return checkdata;
    }
    public int getAllDistance(){
        SQLiteDatabase sq = distanceDataBase.getWritableDatabase();
        int distance = 0;
        Cursor cursor = sq.query(DistanceDataBase.TableDistance, null, null, null, null, null, null);
        int distdata = cursor.getColumnIndex(DistanceDataBase.DISTANCE);
        if (cursor.moveToFirst()) {
            for (int i = 1; i <= cursor.getCount(); i++) {
                distance += cursor.getInt(distdata);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return distance;
    }
}