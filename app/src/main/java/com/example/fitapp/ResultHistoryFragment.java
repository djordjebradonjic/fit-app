package com.example.fitapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;


public class ResultHistoryFragment extends Fragment {

    ROOMStepsDatabase stepDB;
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_result_history, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        stepDB = Room.databaseBuilder( requireContext(),ROOMStepsDatabase.class,"StepDB").build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<ROOMStep> steps_list = stepDB.getStepsCRUD().getAllSteps();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new MyAdapter(steps_list);
                        recyclerView.setAdapter(adapter);
                        StringBuilder sb= new StringBuilder();
                        for(ROOMStep s : steps_list){
                            sb.append("Date  : " + s.getDate() + "  Steps made: " +   s.getSteps());
                            sb.append("\n");
                        }
                        String finalData = sb.toString();

                        Toast.makeText(getActivity(),finalData,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
        return  view;
    }
}