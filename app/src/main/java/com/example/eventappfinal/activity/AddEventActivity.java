package com.example.eventappfinal.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.eventappfinal.R;
import com.example.eventappfinal.database.DatabaseHelper;
import com.example.eventappfinal.session.SessionManager;

import java.util.Calendar;
import java.util.HashMap;

public class AddEventActivity extends AppCompatActivity {

    EditText eventNameText,eventContentText, eventDurationText, eventCapacityText, eventDescriptionText;
    Spinner eventCategorySpinner;
    Button addEventButton;
    SessionManager session;
    String email, category, sTanggal;
    ImageView back;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    private EditText etTanggal;
    private DatePickerDialog dpTanggal;
    Calendar newCalendar = Calendar.getInstance();
    String setName, setContent, setDuration, setCapacity, setDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        dbHelper = new DatabaseHelper(AddEventActivity.this);
        db = dbHelper.getReadableDatabase();
        //init
        addEventButton = findViewById(R.id.addEventButton);
        etTanggal = findViewById(R.id.eventDateText);
        eventCategorySpinner = findViewById(R.id.eventCategorySpinner);
        eventNameText = findViewById(R.id.eventNameText);
        eventContentText = findViewById(R.id.eventContentText);
        eventDurationText = findViewById(R.id.eventDurationText);
        eventCapacityText = findViewById(R.id.eventCapacityText);
        eventDescriptionText = findViewById(R.id.eventDescriptionText);
        back = findViewById(R.id.back);

        final String[] categoryList = {"One Time Event", "Weekly Event", "Monthly Event", "Seminar"};

        ArrayAdapter<CharSequence> adapterCategory = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, categoryList);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventCategorySpinner.setAdapter(adapterCategory);

        eventCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etTanggal.setInputType(InputType.TYPE_NULL);
        etTanggal.requestFocus();
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);
        setDateTimeField();

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setName = eventNameText.getText().toString().trim();
                setContent = eventContentText.getText().toString().trim();
                setDuration = eventDurationText.getText().toString().trim();
                setCapacity = eventCapacityText.getText().toString().trim();
                setDescription = eventDescriptionText.getText().toString().trim();

                if(setName!=null && setContent!=null && setDuration!=null && setCapacity!=null && sTanggal!=null && category!=null)
                {
                    AlertDialog dialog = new AlertDialog.Builder(AddEventActivity.this)
                            .setTitle("Are you sure to create this event ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        long check = dbHelper.addEvent(setName, category, sTanggal, setContent, setDuration, setCapacity, setDescription, email);
                                        if (check>0) {
                                            Toast.makeText(AddEventActivity.this, "Register event was successfully", Toast.LENGTH_LONG).show();
                                            finish();
                                        }else{
                                            Toast.makeText(AddEventActivity.this, "Register event was failed", Toast.LENGTH_LONG).show();
                                            finish();
                                        }

                                    }catch (Exception e){
                                        Toast.makeText(AddEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            })
                            .setNegativeButton("No", null)
                            .create();
                    dialog.show();
                }
                else
                {
                    Toast.makeText(AddEventActivity.this, "Please complete the form !", Toast.LENGTH_LONG).show();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddEventActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
    private void setDateTimeField() {
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpTanggal.show();
            }
        });

        dpTanggal = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String[] month = {"January", "February", "March", "April", "May",
                        "June", "July", "August", "September", "October", "November", "December"};
                sTanggal = dayOfMonth + " " + month[monthOfYear] + " " + year;
                etTanggal.setText(sTanggal);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}