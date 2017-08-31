package com.example.ss89068.feedback;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "feedbackManagerNew";

    // Contacts table name
    private static final String TABLE_REGISTRATION = "feedbackplusDomains";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "feedback";
    private static final String KEY_TEXT = "feedbackInText";
    private static final String KEY_DOMAIN = "Domain" ;


    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_REGISTRATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " INTEGER," + KEY_TEXT + " VARCHAR," +  KEY_DOMAIN + " VARCHAR" + ")";
     db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATION);

        // Create tables again
        onCreate(db);
    }

    void add(int starFeedback, Editable textFeedback, String spinnerValue) {

        SQLiteDatabase db = this.getWritableDatabase();


   ContentValues values = new ContentValues();
        values.put(KEY_NAME, starFeedback);
        values.put(KEY_TEXT, String.valueOf(textFeedback));
        values.put(KEY_DOMAIN, spinnerValue);
        // Inserting Row
        db.insert(TABLE_REGISTRATION, null, values);
        db.close();
    }

    List<String> get()
    {
        List<String> feed = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REGISTRATION  ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                feed.add(cursor.getString(1));
                feed.add(cursor.getString(2));
                feed.add(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        // return contact list
        return feed;
    }



  /*  int getDisLikeCount()
    {
        List<String> racfIds = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_REGISTRATION+" WHERE feedback=0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return  cursor.getCount();
    }*/
}