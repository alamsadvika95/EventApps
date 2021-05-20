package com.example.eventappfinal.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.eventappfinal.R;
import com.example.eventappfinal.database.DatabaseHelper;
import com.example.eventappfinal.session.SessionManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.HashMap;

public class UpdateProfileActivity extends AppCompatActivity {

    Button updateProfileButton, btnOpen;
    EditText updateName, updateMajor, updateDescription;
    ImageView back;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    SessionManager session;
    protected Cursor cursor;
    String email, name, major, description, foto;
    Uri resultUri;
    CircleImageView newImageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        dbHelper = new DatabaseHelper(UpdateProfileActivity.this);
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

        //init
        updateName = findViewById(R.id.updateName);
        updateMajor = findViewById(R.id.updateMajor);
        updateDescription = findViewById(R.id.updateDescription);
        updateProfileButton = findViewById(R.id.updateProfileButton);
        back = findViewById(R.id.back);
        btnOpen = findViewById(R.id.btnOpen);
        newImageProfile = findViewById(R.id.newImageProfile);


        //set values
        updateName.setText(name);
        updateMajor.setText(major);
        updateDescription.setText(description);
        newImageProfile.setImageURI(Uri.parse(foto));

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(UpdateProfileActivity.this)
                        .setTitle("Are you sure want to change your profile information ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    setUpdateData();
                                }catch (Exception e){
                                    Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();
                dialog.show();
            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(UpdateProfileActivity.this);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }

    private void setUpdateData() {

        String name =updateName.getText().toString().trim();
        String major = updateMajor.getText().toString().trim();
        String description = updateDescription.getText().toString().trim();
        String foto = String.valueOf(resultUri);

        dbHelper.updateProfile(email, name, major, description, foto);

        Toast.makeText(UpdateProfileActivity.this, "Update Profile Successfully", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(UpdateProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {
            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                resultUri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                        , 0);
            } else {
                startCrop(imageuri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                newImageProfile.setImageURI(result.getUri());
                resultUri = result.getUri();
            }
        }
    }

    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(3, 4)
                .start(this);
        resultUri = imageuri;
    }


}