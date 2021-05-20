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
import com.example.eventappfinal.R;
import com.example.eventappfinal.database.DatabaseHelper;

public class DetailMyEventActivity extends AppCompatActivity {
    Button updateEventButtton;
    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String name, content, category, duration, capacity, date, description;
    String id;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_my_event);

        Intent i = getIntent();
        id = i.getStringExtra("SendId");

        updateEventButtton = findViewById(R.id.updateEventButtton);
        back = findViewById(R.id.back);
        dbHelper = new DatabaseHelper(this);

        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_event WHERE id = '" + id + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            name = cursor.getString(1);
            content = cursor.getString(4);
            category = cursor.getString(2);
            duration= cursor.getString(5);
            capacity= cursor.getString(6);
            date= cursor.getString(3);
            description = cursor.getString(7);
        }

        TextView myEventNameDetail = findViewById(R.id.myEventNameDetail);
        TextView contentMyEventDetail = findViewById(R.id.contentMyEventDetail);
        TextView categoryMyEventDetail = findViewById(R.id.categoryMyEventDetail);
        TextView durationMyEventDetail = findViewById(R.id.durationMyEventDetail);
        TextView capacityMyEventDetail = findViewById(R.id.capacityMyEventDetail);
        TextView dateMyEventDetail = findViewById(R.id.dateMyEventDetail);
        TextView descriptionMyEventDetail = findViewById(R.id.descriptionMyEventDetail);

        myEventNameDetail.setText(name);
        contentMyEventDetail.setText(content);
        categoryMyEventDetail.setText(category);
        durationMyEventDetail.setText(duration);
        capacityMyEventDetail.setText(capacity);
        dateMyEventDetail.setText(date);
        descriptionMyEventDetail.setText(description);

        updateEventButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailMyEventActivity.this, UpdateMyEventActivity.class);
                i.putExtra("SendId", id);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailMyEventActivity.this, MyEventActivity.class);
                startActivity(i);
            }
        });

    }
}