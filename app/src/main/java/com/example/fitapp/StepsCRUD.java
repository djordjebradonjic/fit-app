package com.example.fitapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface StepsCRUD {
    @Insert
    public void addSteps(ROOMStep steps);

    @Update
    public void updateSteps(ROOMStep steps);

    @Delete
    public void deleteSteps(ROOMStep steps);

    @Query("select * from  roomStep")
    public List<ROOMStep> getAllSteps();



}
