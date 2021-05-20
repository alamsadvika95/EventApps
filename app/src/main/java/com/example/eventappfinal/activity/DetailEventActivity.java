package com.example.eventappfinal.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventappfinal.R;
import com.example.eventappfinal.adapter.AlertDialogManager;
import com.example.eventappfinal.database.DatabaseHelper;
import com.example.eventappfinal.session.SessionManager;

import java.util.HashMap;

public class DetailEventActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_detail_event);
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

        TextView eventNameDetail = findViewById(R.id.eventNameDetail);
        TextView contentEventDetail = findViewById(R.id.contentEventDetail);
        TextView creatorEventDetail = findViewById(R.id.creatorEventDetail);
        TextView durationEventDetail = findViewById(R.id.descriptionEventDetail);
        TextView capacityEventDetail = findViewById(R.id.capacityEventDetail);
        TextView dateEventDetail = findViewById(R.id.dateEventDetail);
        TextView descriptionEventDetail = findViewById(R.id.descriptionEventDetail);

        eventNameDetail.setText(name);
        contentEventDetail.setText(content);
        creatorEventDetail.setText(creator);
        durationEventDetail.setText(duration);
        capacityEventDetail.setText(capacity);
        dateEventDetail.setText(date);
        descriptionEventDetail.setText(description);

        joinEventButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(DetailEventActivity.this)
                        .setTitle("Are you sure to join this event ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db = dbHelper.getReadableDatabase();
                                Cursor mCursor = db.rawQuery("SELECT * FROM tb_join WHERE idEvent=? AND email =?", new String[]{id, email});
                                mCursor.moveToFirst();
                                if(mCursor.getCount() > 0){
                                    AlertDialog dialog1 = new AlertDialog.Builder(DetailEventActivity.this)
                                            .setTitle("You already join this event, do you want to go to Dashboard?")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent i = new Intent(DetailEventActivity.this, MainActivity.class);
                                                    startActivity(i);
                                                }
                                            })
                                            .setNegativeButton("No", null)
                                            .create();
                                    dialog1.show();
                                }else{
                                    try {
                                        SQLiteDatabase create = dbHelper.getWritableDatabase();
                                        ContentValues values =new ContentValues();

                                        values.put("idEvent", id);
                                        values.put("email", email);

                                        create.insert("tb_join",null, values);

                                        Intent i = new Intent(DetailEventActivity.this, JoinEventActivity.class);
                                        i.putExtra("SendId",id);
                                        i.putExtra("sendEmail", email);
                                        startActivity(i);

                                    }catch (Exception e){
                                        Toast.makeText(DetailEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();
                dialog.show();

            }
        });

    }

}