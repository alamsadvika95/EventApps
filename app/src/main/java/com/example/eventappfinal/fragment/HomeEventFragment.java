package com.example.eventappfinal.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.eventappfinal.R;
import com.example.eventappfinal.activity.MainActivity;

public class HomeEventFragment extends Fragment {

    ImageView back;

    public HomeEventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_home_event, container, false);

        back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplication(), MainActivity.class);
                startActivity(i);
            }
        });
        return view;

    }
}