package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class WaterConsumerActivity extends AppCompatActivity {


    TextView tvTotalConsumed;
    Button btnAddWater, btnSetReminder;
    int waterConsumed = 0;
    int glass=0;
    private RadioGroup radioGroup;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_consumer);

        tvTotalConsumed = findViewById(R.id.tvTotalWater);
         btnAddWater = findViewById(R.id.btnAddWater);
         btnSetReminder = findViewById(R.id.btnSetReminder);







        radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Proveravamo koji RadioButton je čekiran
                if(checkedId ==  R.id.rb150) {
                    // Akcije koje se izvršavaju ako je radioButton1 čekiran
                    Toast.makeText(WaterConsumerActivity.this, "Option 1 checked", Toast.LENGTH_SHORT).show();
                    glass = 150;
                }else if (checkedId == R.id.rb200){
                    glass = 200;
                        // Akcije koje se izvršavaju ako je radioButton2 čekiran
                        Toast.makeText(WaterConsumerActivity.this, "Option 2 checked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAddWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterConsumed += glass; // Pretpostavljamo da je jedna čaša vode 250ml
            tvTotalConsumed.setText("Popijena voda: " + waterConsumed + "ml");

            }
        });

        btnSetReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReminder(  );
            }
        });
    }

    private void setReminder() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 1); // Podsetnik se aktivira za 1 minut od sada
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceive.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "Reminder set!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("waterConsumed", waterConsumed);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
         waterConsumed = savedInstanceState.getInt("waterConsumed", 0);

    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    }

