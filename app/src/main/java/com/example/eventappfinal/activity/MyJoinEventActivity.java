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
import java.util.ArrayList;
import java.util.HashMap;
import com.example.eventappfinal.R;
import com.example.eventappfinal.adapter.JoinEventAdapter;
import com.example.eventappfinal.database.DatabaseHelper;
import com.example.eventappfinal.session.SessionManager;


public class MyJoinEventActivity extends AppCompatActivity {

    ImageView back;
    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private JoinEventAdapter joinEventAdapter;
    private RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this);
    private ArrayList nameList;
    private ArrayList contentList;
    private ArrayList idList;
    private ArrayList categoryList;
    private ArrayList dateList;
    private String emailProfile;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_join_event);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        emailProfile = user.get(SessionManager.KEY_EMAIL);

        nameList = new ArrayList<>();
        contentList = new ArrayList<>();
        idList = new ArrayList<>();
        categoryList = new ArrayList<>();
        dateList = new ArrayList<>();

        dbHelper =new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerJoinEvent);
        back = findViewById(R.id.back);
        getData();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        joinEventAdapter = new JoinEventAdapter(MyJoinEventActivity.this, nameList, contentList, idList,categoryList,dateList);
        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(joinEventAdapter);
        //Membuat Underline pada Setiap Item Didalam List
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyJoinEventActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
    protected void getData() {
        SQLiteDatabase ReadData = dbHelper.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT tb_event.name, tb_event.content, tb_join.id, tb_event.email, tb_event.date FROM tb_event INNER JOIN tb_join ON tb_event.id = tb_join.IdEvent WHERE tb_join.email = '"+ emailProfile +"' ", null);
        cursor.moveToFirst();
        for (int count = 0; count < cursor.getCount(); count++) {

            cursor.moveToPosition(count);

            nameList.add(cursor.getString(0));
            contentList.add(cursor.getString(1));
            idList.add(cursor.getString(2));
            categoryList.add(cursor.getString(3));
            dateList.add(cursor.getString(4));
        }
    }
}