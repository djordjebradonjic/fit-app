package com.example.fitapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {

    Button btnLogout;
    TextView tvCurrentUser;
    FirebaseAuth auth;
    FirebaseUser user;

    Button stepCounter;
    Button btnWaterConsumer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         inflater.inflate(R.layout.fragment_home, container, false);
          View view = inflater.inflate(R.layout.fragment_home, container, false);
        stepCounter = view.findViewById(R.id.btnStepCounterActivity);
        tvCurrentUser = view.findViewById(R.id.tvcurrentUser);
        stepCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StepCounterActivity.class);
                startActivity(intent);

            }
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);

        } else {
            tvCurrentUser.setText(user.getEmail());
        }
        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });


        btnWaterConsumer = view.findViewById(R.id.btnWaterCounsmer);
        btnWaterConsumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WaterConsumerActivity.class);
                startActivity(intent);
            }
        });
    return view ;
    }
}