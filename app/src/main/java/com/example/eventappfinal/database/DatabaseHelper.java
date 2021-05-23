package com.example.eventappfinal.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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

        final String create_tb_notes = "CREATE TABLE " + Content.TABLE_NOTES +
                "(" + Content.NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Content.NOTES_EVENT_ID + " TEXT NOT NULL, "
                + Content.NOTES_DESCRIPTION + " TEXT NOT NULL, "
                + Content.NOTES_EMAIL + " TEXT NOT NULL)";

        final String create_tb_event = "CREATE TABLE " + Content.TABLE_EVENT +
                "(" + Content.EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Content.EVENT_NAME + " TEXT NOT NULL, "
                + Content.EVENT_CATEGORY + " TEXT NOT NULL, "
                + Content.EVENT_DATE + " TEXT NOT NULL, "
                + Content.EVENT_CONTENT + " TEXT NOT NULL, "
                + Content.EVENT_DURATION + " TEXT NOT NULL, "
                + Content.EVENT_CAPACITY + " TEXT NOT NULL, "
                + Content.EVENT_DESCRIPTION + " TEXT NOT NULL, "
                + Content.EVENT_EMAIL + " TEXT NOT NULL)";
        final String create_tb_join = "CREATE TABLE " + Content.TABLE_JOIN +
                "(" + Content.JOIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Content.JOIN_ID_EVENT + " TEXT NOT NULL, "
                + Content.JOIN_EMAIL + " TEXT NOT NULL)";
        db.execSQL(create_tb_user);
        db.execSQL(create_tb_notes);
        db.execSQL(create_tb_event);
        db.execSQL(create_tb_join);
        db.execSQL(Content.insert1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Content.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + Content.TABLE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS " + Content.TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + Content.TABLE_JOIN);
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
    public long updateProfile ( String email, String name, String major, String description, String foto){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Content.USER_NAME, name);
        cv.put(Content.USER_MAJOR, major);
        cv.put(Content.USER_DESCRIPTION, description);
        cv.put(Content.USER_FOTO, foto);

        long result = db.update(Content.TABLE_USER,cv,"username=?",new String[]{email});

        return result;
    }
    public long addEvent(String name, String category, String date, String content, String duration, String capacity, String description, String email ){
        SQLiteDatabase create = getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put(Content.EVENT_NAME, name);
        values.put(Content.EVENT_CATEGORY, category);
        values.put(Content.EVENT_DATE, date );
        values.put(Content.EVENT_CONTENT, content);
        values.put(Content.EVENT_DURATION, duration);
        values.put(Content.EVENT_CAPACITY, capacity);
        values.put(Content.EVENT_DESCRIPTION, description);
        values.put(Content.EVENT_EMAIL, email);

        long result = create.insert(Content.TABLE_EVENT,null, values);
        return result;
    }

    public long updateEvent(String id, String name, String category, String date, String content, String duration, String capacity, String description, String email){
        SQLiteDatabase create = getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put(Content.EVENT_NAME, name);
        values.put(Content.EVENT_CATEGORY, category);
        values.put(Content.EVENT_DATE, date );
        values.put(Content.EVENT_CONTENT, content);
        values.put(Content.EVENT_DURATION, duration);
        values.put(Content.EVENT_CAPACITY, capacity);
        values.put(Content.EVENT_DESCRIPTION, description);
        values.put(Content.EVENT_EMAIL, email);

        long result = create.update(Content.TABLE_EVENT,values,"id=?",new String[]{id});
        return result;
    }

    public long checkNote ( String idEvent){
        long result;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_mynotes WHERE event_id = '" + idEvent + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            result = 1;
        }else
            result = 0;

        return result;
    }
}
