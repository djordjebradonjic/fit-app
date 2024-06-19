package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

//senosor
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Year;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {


    private ExecutorService executorService;

    private TextView tvDistance;
    private TextView steps;
    private SensorManager mSensorManager = null;
    private Sensor stepSensor;

    private ProgressBar pbStepCounter;
    int totalSteps = 0;

    int stepGoal = 5000;
    TextView tvstepGoal;


    Button btnReset;
    Button btnSave;
    Button btnSetGoal;
    private int newGoal;

    private float stepLengthInMeter = 0.762f;

    ROOMStepsDatabase stepDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        loadData();
        pbStepCounter = findViewById(R.id.progressBarStep);
        steps = findViewById(R.id.tvStepsCount);
        tvDistance = findViewById(R.id.tvDistance);
        tvstepGoal = findViewById(R.id.tvStepGoal);

        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalSteps = 0;
                steps.setText("0");
                pbStepCounter.setProgress(0);
                saveData();
                tvDistance.setText("0");

            }
        });


        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }

            @Override
            public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                super.onDestructiveMigration(db);
            }
        };

        stepDB = Room.databaseBuilder(getApplicationContext(), ROOMStepsDatabase.class, "StepDB").addCallback(myCallBack).build();




        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                String date = day + "-" + month + "-" + year + "  " + hour + "h " + minute + "m " + second + "s ";
                ROOMStep step = new ROOMStep(date, totalSteps);


                executorService = Executors.newSingleThreadExecutor();

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        stepDB.getStepsCRUD().addSteps(step);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(StepCounterActivity.this, "Podaci su uspesno dodati u bazu podataka", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                });
            }
        });

        btnSetGoal = findViewById(R.id.btnSetGoal);
        btnSetGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StepCounterActivity.this);
                builder.setTitle("SET STEPS GOAL");

                final EditText input = new EditText(StepCounterActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newGoal = Integer.parseInt(input.getText().toString());
                        tvstepGoal.setText("Goal:" + String.valueOf(newGoal));
                        pbStepCounter.setMax(newGoal);
                        if (newGoal <= totalSteps)
                            tvstepGoal.setText("Steps goal ACHIEVED");
                        else {
                            tvstepGoal.setText("Goal:" + String.valueOf(newGoal));

                        }
                        stepGoal = newGoal;


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);


        pbStepCounter.setMax(stepGoal);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stepSensor == null) {
            Toast.makeText(this, "This device has no sensor", Toast.LENGTH_SHORT).show();
        } else {
            mSensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            totalSteps = totalSteps + 1;

            pbStepCounter.setProgress(totalSteps);
            steps.setText(String.valueOf(totalSteps));
            if (totalSteps >= stepGoal)
                tvstepGoal.setText("Step goal achieved");
        }
        int distance = (int) (totalSteps * stepLengthInMeter);
        tvDistance.setText("Distance : " + distance + "m");
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();


        // mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //  mSensorManager.unregisterListener(this);
        saveData();
    }


    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("stepHistory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key1", String.valueOf(totalSteps));
        editor.apply();
    }


    private void loadData() {

        SharedPreferences sharedPreferences = getSharedPreferences("stepHistory", MODE_PRIVATE);
        int savedNumber = Integer.valueOf(sharedPreferences.getString("key1", ""));
        totalSteps = savedNumber;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}