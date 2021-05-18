package com.example.eventappfinal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

import com.example.eventappfinal.R;
import com.example.eventappfinal.database.DatabaseHelper;
import com.theartofdev.edmodo.cropper.CropImage;

public class RegisterActivity extends AppCompatActivity {

    EditText registerName, registerUsername, registerPassword, registerMajor, registerDescription;
    Button registerButton, registerToLogin, btnOpen;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String setName, setUsername, setPassword, setMajor, setDescription;
    CircleImageView imageProfile;
    Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        init();

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CropImage.activity().setAspectRatio(3,4).getIntent(RegisterActivity.this);
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
                try {
                    if (setUsername.trim().length() > 0 && setPassword.trim().length() > 0 && setName.trim().length() > 0 && setMajor.trim().length() > 0 && setDescription.trim().length() > 0) {
                        saveData();
                        Toast.makeText(RegisterActivity.this, "Register successfully", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Register failed, complete the form!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode ==  Activity.RESULT_OK) {
                resultUri = result.getUri();
                Log.e("resultUri ->", String.valueOf(resultUri));
                imageProfile.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("error ->", String.valueOf(error));
            }
        }
    }

    private void init() {
        registerName = findViewById(R.id.registerName);
        registerUsername = findViewById(R.id.registerUsername);
        registerPassword = findViewById(R.id.registerPassword);
        registerMajor = findViewById(R.id.registerMajor);
        registerDescription = findViewById(R.id.registerDescription);
        imageProfile= (CircleImageView)findViewById(R.id.image_profile);
        registerButton = findViewById(R.id.registerButton);
        registerToLogin = findViewById(R.id.registerToLogin);
        btnOpen = findViewById(R.id.btnOpen);
    }

    private void setData() {
        setName = registerName.getText().toString();
        setUsername = registerUsername.getText().toString();
        setPassword = registerPassword.getText().toString();
        setMajor = registerMajor.getText().toString();
        setDescription = registerDescription.getText().toString();

    }

    private void saveData(){
        SQLiteDatabase create = dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("username", setUsername);
        values.put("password", setPassword);
        values.put("name", setName);
        values.put("major", setMajor);
        values.put("description", setDescription);
        values.put("foto", String.valueOf(resultUri));

        create.insert("tb_user",null, values);
    }
}