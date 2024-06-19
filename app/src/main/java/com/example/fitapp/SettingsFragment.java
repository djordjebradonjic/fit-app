package com.example.fitapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;


public class SettingsFragment extends Fragment {

    private Switch notificationSwitch;
    ImageButton btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ImageButton btnBack = view.findViewById(R.id.btnBack);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
        notificationSwitch = view.findViewById(R.id.notification_switch);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    enableNotifications();
                } else {
                    disableNotifications();
                }
            }
        });


        return view;
    }

    private void enableNotifications() {

        Toast.makeText(getContext(), "Notifikacije su omoguceene", Toast.LENGTH_SHORT).show();
    }

    private void disableNotifications() {

        Toast.makeText(getContext(), "Notifikacije su onemogucene", Toast.LENGTH_SHORT).show();
    }
}