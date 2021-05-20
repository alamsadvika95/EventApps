package com.example.eventappfinal.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.eventappfinal.R;
import com.example.eventappfinal.database.Content;
import com.example.eventappfinal.database.DatabaseHelper;
import com.example.eventappfinal.session.SessionManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.HashMap;

public class AddNoteActivity extends AppCompatActivity {

    ImageView back;
    EditText addNoteDetail;
    Button addNotesButton;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    SessionManager session;
    String email, id, note, noteId;
    protected Cursor cursor;
    int check = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        dbHelper = new DatabaseHelper(this);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);

        Intent i = getIntent();
        id = i.getStringExtra("SendId");

        addNoteDetail = findViewById(R.id.addNoteDetail);
        addNotesButton = findViewById(R.id.addNotesButton);
        back = findViewById(R.id.back);

        if(dbHelper.checkNote(id)>0){
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM tb_mynotes WHERE event_id = '" + id + "'", null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                cursor.moveToPosition(0);
                noteId = cursor.getString(0);
                note = cursor.getString(2);
                addNoteDetail.setText(note);
                addNoteDetail.setSelection(0);
                check = 1;
            }
        }else {
                addNoteDetail.setText("");
                addNoteDetail.setSelection(0);
                check = 0;
            }

        addNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String description = addNoteDetail.getText().toString().trim();

                if(check == 1){
                    try {
                        SQLiteDatabase create = dbHelper.getWritableDatabase();
                        ContentValues values =new ContentValues();

                        values.put(Content.NOTES_DESCRIPTION, description);

                        create.update(Content.TABLE_NOTES,values,"id=?",new String[]{noteId});

                        Intent intent = new Intent(AddNoteActivity.this, MyEventActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }catch (Exception e){
                        Toast.makeText(AddNoteActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else{
                    try {
                        SQLiteDatabase create = dbHelper.getWritableDatabase();
                        ContentValues values =new ContentValues();

                        values.put(Content.NOTES_EVENT_ID, id);
                        values.put(Content.NOTES_DESCRIPTION, description);
                        values.put(Content.NOTES_EMAIL, email);
                        create.insert(Content.TABLE_NOTES,null, values);

                        Intent intent = new Intent(AddNoteActivity.this, MyEventActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }catch (Exception e){
                        Toast.makeText(AddNoteActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddNoteActivity.this, MyEventActivity.class);
                startActivity(i);
            }
        });
    }
}