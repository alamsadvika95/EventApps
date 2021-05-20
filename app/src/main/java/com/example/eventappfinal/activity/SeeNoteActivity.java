package com.example.eventappfinal.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.eventappfinal.R;
import com.example.eventappfinal.database.Content;
import com.example.eventappfinal.database.DatabaseHelper;

public class SeeNoteActivity extends AppCompatActivity {

    ImageView back;
    EditText seeNoteDetail;
    Button deleteNotesButton;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String id, noteId, note ;
    protected Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_note);

        dbHelper = new DatabaseHelper(this);
        Intent i = getIntent();
        id = i.getStringExtra("SendId");
        seeNoteDetail = findViewById(R.id.seeNoteDetail);
        deleteNotesButton = findViewById(R.id.deleteNotesButton);
        back = findViewById(R.id.back);

        if(dbHelper.checkNote(id)>0){
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM tb_mynotes WHERE event_id = '" + id + "'", null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                cursor.moveToPosition(0);
                noteId = cursor.getString(0);
                note = cursor.getString(2);
                seeNoteDetail.setText(note);
                seeNoteDetail.setSelection(0);
            }
        }else {
            seeNoteDetail.setText("");
            seeNoteDetail.setSelection(0);
        }

        deleteNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(SeeNoteActivity.this)
                        .setTitle("Are you sure want to delete this note ?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    db = dbHelper.getWritableDatabase();
                                    String selection = Content.NOTES_ID + " LIKE ?";
                                    String[] selectionArgs = {noteId};
                                    db.delete(Content.TABLE_NOTES, selection, selectionArgs);

                                    Intent i = new Intent(SeeNoteActivity.this, MyEventActivity.class);
                                    startActivity(i);
                                    Toast.makeText(SeeNoteActivity.this,"Data was deleted",Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Toast.makeText(SeeNoteActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SeeNoteActivity.this, MyEventActivity.class);
                startActivity(i);
            }
        });
    }
}