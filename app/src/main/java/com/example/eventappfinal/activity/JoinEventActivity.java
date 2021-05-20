package com.example.eventappfinal.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.eventappfinal.R;
import com.example.eventappfinal.database.DatabaseHelper;
import com.example.eventappfinal.session.SessionManager;
import java.util.HashMap;

public class JoinEventActivity extends AppCompatActivity {

    Button btnJoin;
    TextView joinID, joinEmail;
    protected Cursor cursor, cursor2;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    SessionManager session;
    String nameJoin, categoryJoin,creatorJoin,dateJoin, nameUser;
    String email, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);
        Intent i = getIntent();
        id = i.getStringExtra("SendId");

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_event WHERE id = '" + id + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            nameJoin = cursor.getString(1);
            categoryJoin= cursor.getString(2);
            creatorJoin  = cursor.getString(8);
            dateJoin = cursor.getString(3);
        }


        TextView nameJoinEvent =findViewById(R.id.nameJoinEvent);
        TextView categoryJoinEvent=  findViewById(R.id.categoryJoinEvent);
        TextView creatorJoinEvent= findViewById(R.id.creatorJoinEvent);
        TextView dateJoinEvent = findViewById(R.id.dateJoinEvent);
        TextView nameUserEvent = findViewById(R.id.nameUserEvent);

        nameJoinEvent.setText(nameJoin);
        categoryJoinEvent.setText(categoryJoin);
        creatorJoinEvent.setText(creatorJoin);
        dateJoinEvent.setText(dateJoin);
        nameUserEvent.setText(email);


        Button joinToMain = findViewById(R.id.joinToMain);
        joinToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JoinEventActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}