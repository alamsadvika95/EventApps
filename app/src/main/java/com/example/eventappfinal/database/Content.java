package com.example.eventappfinal.database;

public class Content {
    public static final String DATABASE_NAME = "db_event";
    public static final int DATABASE_VERSION = 1;

    //user table
    public static final String TABLE_USER = "tb_user";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";
    public static final String USER_MAJOR = "major";
    public static final String USER_DESCRIPTION = "description";
    public static final String USER_FOTO = "foto";

    //note table
    public static final String TABLE_NOTES="tb_mynotes";
    public static final String NOTES_ID="id";
    public static final String NOTES_TITLE="title";
    public static final String NOTES_DESCRIPTION="description";
    public static final String NOTES_EMAIL="email";

    //event table
    public static final String TABLE_EVENT="tb_event";
    public static final String EVENT_ID="id";
    public static final String EVENT_NAME="name";
    public static final String EVENT_CATEGORY="category";
    public static final String EVENT_DATE="date";
    public static final String EVENT_CONTENT="content";
    public static final String EVENT_DURATION="duration";
    public static final String EVENT_CAPACITY="capacity";
    public static final String EVENT_DESCRIPTION="description";
    public static final String EVENT_EMAIL="email";

    //join table
    public static final String TABLE_JOIN="tb_join";
    public static final String JOIN_ID="id";
    public static final String JOIN_ID_EVENT="IdEvent";
    public static final String JOIN_EMAIL="email";
}
