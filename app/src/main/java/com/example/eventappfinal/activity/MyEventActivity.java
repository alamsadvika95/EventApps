package com.example.eventappfinal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.eventappfinal.R;
import com.example.eventappfinal.adapter.MyEventAdapter;
import com.example.eventappfinal.database.DatabaseHelper;
import com.example.eventappfinal.session.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class MyEventActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private MyEventAdapter myEventAdapter;

    private RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this);
    private ArrayList nameList;
    private ArrayList contentList;
    private ArrayList idList;
    private ArrayList categoryList;
    private ArrayList dateList;
    private ImageView back;

    private String emailProfile;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        emailProfile = user.get(SessionManager.KEY_EMAIL);

        nameList = new ArrayList<>();
        contentList = new ArrayList<>();
        idList = new ArrayList<>();
        categoryList = new ArrayList<>();
        dateList = new ArrayList<>();

        dbHelper =new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerMyEvent);
        back = findViewById(R.id.back);
        getData();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        myEventAdapter = new MyEventAdapter(MyEventActivity.this, nameList, contentList, idList,categoryList,dateList);
        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(myEventAdapter);
        //Membuat Underline pada Setiap Item Didalam List
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyEventActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    protected void getData() {
        //Mengambil Repository dengan Mode Membaca
        SQLiteDatabase ReadData = dbHelper.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT * FROM  tb_event WHERE email = '"+ emailProfile +"' ", null);

        cursor.moveToFirst();//Memulai Cursor pada Posisi Awal

        //Melooping Sesuai Dengan Jumlan Data (Count) pada cursor
        for (int count = 0; count < cursor.getCount(); count++) {

            cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir

            //Mengambil data dari sesuai kolom array
            nameList.add(cursor.getString(1));
            contentList.add(cursor.getString(4));
            idList.add(cursor.getString(0));
            categoryList.add(cursor.getString(2));
            dateList.add(cursor.getString(3));
        }
    }
}