package com.example.eventappfinal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.eventappfinal.R;
import com.example.eventappfinal.database.DatabaseHelper;
import com.example.eventappfinal.session.SessionManager;
import java.util.HashMap;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    TextView profileName, profileEmail, profileMajor, profileDescription, boxEvent, boxNote;
    Button updateProfileButton;
    protected Cursor cursor, cursor2, cursor3;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    SessionManager session;
    String name, email, major, description, foto;
    Integer eventCalculation, noteCalculation;
    CircleImageView imageProfile;
    ImageView back;
    String eventDisplay, noteDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new DatabaseHelper(this);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);

        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM TB_USER WHERE username = '" + email + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            name = cursor.getString(2);
            major = cursor.getString(3);
            description = cursor.getString(4);
            foto = cursor.getString(5);
        }

        cursor2 = db.rawQuery("SELECT * FROM tb_event WHERE email = '" + email + "'", null);
        eventCalculation = cursor2.getCount();
        eventDisplay = eventCalculation + " Events";

        cursor3 = db.rawQuery("SELECT * FROM tb_mynotes WHERE email = '" + email + "'", null);
        noteCalculation = cursor3.getCount();
        noteDisplay = noteCalculation + " Notes";

        //init
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileMajor = findViewById(R.id.profileMajor);
        profileDescription = findViewById(R.id.profileDescription);
        imageProfile = findViewById(R.id.profileImage);
        boxEvent = findViewById(R.id.boxEvent);
        boxNote = findViewById(R.id.boxNote);
        updateProfileButton = findViewById(R.id.updateProfileButton);
        back = findViewById(R.id.back);

        profileName.setText(name);
        profileEmail.setText(email);
        profileMajor.setText(major);
        profileDescription.setText(description);
        imageProfile.setImageURI(Uri.parse(foto));
        boxEvent.setText(eventDisplay);
        boxNote.setText(noteDisplay);

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}