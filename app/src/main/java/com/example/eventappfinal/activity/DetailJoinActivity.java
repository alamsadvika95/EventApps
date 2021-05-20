package com.example.eventappfinal.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import com.example.eventappfinal.R;
import com.example.eventappfinal.database.DatabaseHelper;
import com.example.eventappfinal.session.SessionManager;

public class DetailJoinActivity extends AppCompatActivity {

    Button joinEventButtton;
    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    SessionManager session;
    String name, content, creator, duration, capacity, date, description;
    String email, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_join);

        joinEventButtton = findViewById(R.id.joinEventButtton);
        dbHelper = new DatabaseHelper(this);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);

        Intent i = getIntent();
        id = i.getStringExtra("SendId");

        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_event WHERE id = '" + id + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            name = cursor.getString(1);
            content = cursor.getString(4);
            creator = cursor.getString(8);
            duration= cursor.getString(5);
            capacity= cursor.getString(6);
            date= cursor.getString(3);
            description = cursor.getString(7);
        }

        TextView joinNameDetail = findViewById(R.id.joinNameDetail);
        TextView contentJoinDetail = findViewById(R.id.contentJoinDetail);
        TextView creatorJoinDetail = findViewById(R.id.creatorJoinDetail);
        TextView durationJoinDetail = findViewById(R.id.durationJoinDetail);
        TextView capacityJoinDetail = findViewById(R.id.capacityJoinDetail);
        TextView dateJoinDetail = findViewById(R.id.dateJoinDetail);
        TextView descriptionJoinDetail = findViewById(R.id.descriptionJoinDetail);
        ImageView back = findViewById(R.id.back);

        joinNameDetail.setText(name);
        contentJoinDetail.setText(content);
        creatorJoinDetail.setText(creator);
        durationJoinDetail.setText(duration);
        capacityJoinDetail.setText(capacity);
        dateJoinDetail.setText(date);
        descriptionJoinDetail.setText(description);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailJoinActivity.this, MyJoinEventActivity.class);
                startActivity(i);
            }
        });

    }
}