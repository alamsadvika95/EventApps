package com.example.eventappfinal.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, Content.DATABASE_NAME, null, Content.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String create_tb_user = "CREATE TABLE " + Content.TABLE_USER +
                "(" + Content.USER_USERNAME + " TEXT PRIMARY KEY, "
                + Content.USER_PASSWORD + " TEXT NOT NULL, "
                + Content.USER_NAME + " TEXT NOT NULL, "
                + Content.USER_MAJOR + " TEXT NOT NULL, "
                + Content.USER_DESCRIPTION + " TEXT NOT NULL, "
                + Content.USER_FOTO + " TEXT NOT NULL)";
        db.execSQL(create_tb_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Content.TABLE_USER);
        onCreate(db);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public boolean Login(String username, String password) throws SQLException {
        @SuppressLint("Recycle") Cursor mCursor = db.rawQuery("SELECT * FROM " + Content.TABLE_USER + " WHERE " + Content.USER_USERNAME + "=? AND " + Content.USER_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {
            return mCursor.getCount() > 0;
        }
        return false;
    }
}
