package com.example.eventappfinal.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eventappfinal.R;
import com.example.eventappfinal.session.SessionManager;

public class MainActivity extends AppCompatActivity {
    SessionManager session;
    Button buttonSignOut;
    CardView menuProfile, menuCreateEvent, menuMyEvent, menuAvailableEvent, menuMyNotes, menuAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        menuProfile = findViewById(R.id.menuProfile);
        menuCreateEvent = findViewById(R.id.menuCreateEvent);
        menuMyEvent = findViewById(R.id.menuMyEvent);
        menuAvailableEvent = findViewById(R.id.menuAvailableEvent);
        menuMyNotes = findViewById(R.id.menuMyNotes);
        menuAbout = findViewById(R.id.menuAbout);
        buttonSignOut = findViewById(R.id.buttonSignOut);

        menuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
        menuCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddEventActivity.class);
                startActivity(i);
            }
        });
        menuMyEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MyEventActivity.class);
                startActivity(i);
            }
        });
        menuAvailableEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AvaibilityActivity.class);
                startActivity(i);
            }
        });
        menuMyNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(i);
            }
        });
        menuAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(i);
            }
        });

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Are you sure to sign out ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                session.logoutUser();
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();
                dialog.show();
            }
        });


    }
}
