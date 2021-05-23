package com.example.eventappfinal.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.eventappfinal.R;
import com.example.eventappfinal.database.DatabaseHelper;
import com.example.eventappfinal.session.SessionManager;
import java.util.Calendar;
import java.util.HashMap;

public class UpdateMyEventActivity extends AppCompatActivity {

    EditText updateNameText, updateDateText, updateContentText, updateDurationText, updateCapacityText, updateDescription;
    Spinner updateCategorySpinner;
    ImageView back;
    Button updateEventButton;
    SQLiteDatabase db;
    SessionManager session;
    String SendId, updateCategory, sDate, email;
    String nameEvent, updateCategoryCursor, updateDateCursor, updateContentCursor, updateDurationCursor, updateCapacityCursor, updateDescriptionCursor;
    DatabaseHelper dbHelper;
    protected Cursor cursor;
    private DatePickerDialog dpDate;
    Calendar newCalendar = Calendar.getInstance();
    String setName, setContent,setDate , setDuration, setCapacity, setDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_my_event);

        Intent i = getIntent();
        SendId = i.getStringExtra("SendId");
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);

        TextView updateEventHeadText = findViewById(R.id.updateEventHeadText);

        updateNameText= findViewById(R.id.updateNameText);
        updateCategorySpinner = findViewById(R.id.updateCategorySpinner);
        updateDateText = findViewById(R.id.updateDateText);
        updateContentText = findViewById(R.id.updateContentText);
        updateDurationText = findViewById(R.id.updateDurationText);
        updateCapacityText = findViewById(R.id.updateCapacityText);
        updateDescription = findViewById(R.id.updateDescription);
        updateEventButton = findViewById(R.id.updateEventButton);
        back = findViewById(R.id.back);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_event WHERE id = '" + SendId + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            nameEvent = cursor.getString(1);
            updateCategoryCursor = cursor.getString(2);
            updateDateCursor = cursor.getString(3);
            updateContentCursor =cursor.getString(4);
            updateDurationCursor =cursor.getString(5);
            updateCapacityCursor =cursor.getString(6);
            updateDescriptionCursor =cursor.getString(7);

        }

        updateNameText.setText(nameEvent);

        final String[] categoryList = {"One Time Event", "Weekly Event", "Monthly Event", "Seminar"};
        ArrayAdapter<CharSequence> adapterCategory = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, categoryList);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        updateCategorySpinner.setAdapter(adapterCategory);

        if (updateCategoryCursor.equalsIgnoreCase("One Time Event")){
            updateCategorySpinner.setSelection(0);
        }else if (updateCategoryCursor.equalsIgnoreCase("Weekly Event")){
            updateCategorySpinner.setSelection(1);
        }else if (updateCategoryCursor.equalsIgnoreCase("Monthly Event")){
            updateCategorySpinner.setSelection(2);
        }else{
            updateCategorySpinner.setSelection(3);
        }

        updateDateText.setText(updateDateCursor);
        updateContentText.setText(updateContentCursor);
        updateDurationText.setText(updateDurationCursor);
        updateCapacityText.setText(updateCapacityCursor);
        updateDescription.setText(updateDescriptionCursor);


        updateCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        updateDateText.setInputType(InputType.TYPE_NULL);
        updateDateText.requestFocus();
        setDateTimeField();

        updateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setName = updateNameText.getText().toString().trim();
                setDate =  updateDateText.getText().toString().trim();
                setContent = updateContentText.getText().toString().trim();
                setDuration = updateDurationText.getText().toString().trim();
                setCapacity = updateCapacityText.getText().toString().trim();
                setDescription = updateDescription.getText().toString().trim();

                if(setName!=null && setContent!=null && setDuration!=null && setCapacity!=null && setDate!=null && updateCategory!=null)
                {
                    AlertDialog dialog = new AlertDialog.Builder(UpdateMyEventActivity.this)
                            .setTitle("Are you sure to edit this event ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        long check = dbHelper.updateEvent(SendId, setName, updateCategory, setDate, setContent, setDuration, setCapacity, setDescription, email);

                                        if (check>0) {
                                            Toast.makeText(UpdateMyEventActivity.this, "Register event was successfully", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(UpdateMyEventActivity.this, MyEventActivity.class);
                                            startActivity(i);
                                        }

                                    }catch (Exception e){
                                        Toast.makeText(UpdateMyEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            })
                            .setNegativeButton("No", null)
                            .create();
                    dialog.show();
                }
                else
                {
                    Toast.makeText(UpdateMyEventActivity.this, "Please complete the form !", Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateMyEventActivity.this, DetailMyEventActivity.class);
                i.putExtra("SendId", SendId );
                startActivity(i);
            }
        });

    }

    private void setDateTimeField() {
        updateDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpDate.show();
            }
        });

        dpDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String[] month = {"January", "February", "March", "April", "May",
                        "June", "July", "August", "September", "October", "November", "December"};
                sDate = dayOfMonth + " " + month[monthOfYear] + " " + year;
                updateDateText.setText(sDate);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}