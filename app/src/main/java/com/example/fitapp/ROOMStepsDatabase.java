package com.example.fitapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ROOMStep.class},version = 1)
public abstract class ROOMStepsDatabase  extends RoomDatabase {
    public abstract  StepsCRUD getStepsCRUD();
}
