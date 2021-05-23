package com.example.eventappfinal.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventappfinal.R;
import com.example.eventappfinal.adapter.AdapterEventAvaibility;
import com.example.eventappfinal.database.DatabaseHelper;
import com.example.eventappfinal.session.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class OneTimeEventFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private AdapterEventAvaibility adapterEventAvaibility;
    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getActivity());
    private ArrayList nameList;
    private ArrayList contentList;
    private ArrayList idList;
    private ArrayList emailList;
    private ArrayList dateList;

    private String emailProfile;

    public OneTimeEventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_one_time_event, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

        SessionManager sessionManager = new SessionManager(requireActivity());
        HashMap<String, String> user = sessionManager.getUserDetails();
        emailProfile = user.get(SessionManager.KEY_EMAIL);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                nameList = new ArrayList<>();
                contentList = new ArrayList<>();
                idList = new ArrayList<>();
                emailList = new ArrayList<>();
                dateList = new ArrayList<>();

                dbHelper = new DatabaseHelper(getActivity().getBaseContext());
                recyclerView = view.findViewById(R.id.recyclerOneTime);
                getData();
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                adapterEventAvaibility = new AdapterEventAvaibility(OneTimeEventFragment.this, nameList, contentList, idList,emailList,dateList);
                recyclerView.setAdapter(adapterEventAvaibility);
                DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL);
                itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.line));
                recyclerView.addItemDecoration(itemDecoration);
            }
        });
        nameList = new ArrayList<>();
        contentList = new ArrayList<>();
        idList = new ArrayList<>();
        emailList = new ArrayList<>();
        dateList = new ArrayList<>();

        dbHelper =new DatabaseHelper(getActivity().getBaseContext());
        recyclerView = view.findViewById(R.id.recyclerOneTime);
        getData();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapterEventAvaibility = new AdapterEventAvaibility(OneTimeEventFragment.this, nameList, contentList, idList,emailList,dateList);
        recyclerView.setAdapter(adapterEventAvaibility);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
        return view;
    }

    protected void getData() {
        SQLiteDatabase ReadData = dbHelper.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT * FROM  tb_event WHERE category = 'One Time Event' AND email NOT LIKE '"+ emailProfile +"' ", null);
        cursor.moveToFirst();
        for (int count = 0; count < cursor.getCount(); count++) {
            cursor.moveToPosition(count);
            nameList.add(cursor.getString(1));
            contentList.add(cursor.getString(4));
            idList.add(cursor.getString(0));
            emailList.add(cursor.getString(8));
            dateList.add(cursor.getString(3));
        }
    }
}