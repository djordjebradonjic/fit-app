package com.example.fitapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "ROOMStep")
public class ROOMStep {

    @ColumnInfo(name = "ROOMStep_id")
           @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "steps")
    int  steps;

    @Ignore
    public ROOMStep(){}

    public  ROOMStep(String date, int steps){
        this.date=date;
        this.steps= steps;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }
}
