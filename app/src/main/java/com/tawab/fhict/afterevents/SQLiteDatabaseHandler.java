package com.tawab.fhict.afterevents;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "AfterEvents";

    // Contacts table name
    private static final String TABLE_AFTERS = "Afters";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAAM = "after_naam";
    private static final String KEY_SOORT = "after_soort";

/*    private static final String KEY_PLAATS = "after_plaats";
    private static final String KEY_INFO = "after_info";*/

    private static final String KEY_LATITUDE = "after_lat";
    private static final String KEY_LONGITUTDE = "after_lon";


    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


/*

    public SQLiteDatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteDatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
*/


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_AFTERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAAM + " TEXT,"
                + KEY_SOORT + " TEXT,"
                + KEY_LATITUDE + " REAL,"
                + KEY_LONGITUTDE + " REAL"
                + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // nieuwe afters handmatig toevoegen
    public void addAfters(Afters after) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAAM, after.get_afterNaam());
        values.put(KEY_SOORT, after.get_afterSoort());
        values.put(KEY_LATITUDE, after.get_lat());
        values.put(KEY_LONGITUTDE, after.get_lon());

        db.insert(TABLE_AFTERS, null, values);
        db.close();
    }



    // Alle afters ophalen
    public List<Afters> getAllAfters() {
        List<Afters> AfterList = new ArrayList<Afters>();
        String selectQuery = "SELECT  * FROM " + TABLE_AFTERS;

        GoogleMaps.aftiesArray.clear();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Afters after = new Afters();
                after.set_afterid(Integer.parseInt(cursor.getString(0)));
                after.set_afterNaam(cursor.getString(1));
                after.set_afterSoort(cursor.getString(2));
                after.set_lat(Double.parseDouble(cursor.getString(3)));
                after.set_lon(Double.parseDouble(cursor.getString(4)));

                String name = cursor.getString(1) +"\n"+ cursor.getString(2);
                GoogleMaps.aftiesArray.add(name);


                AfterList.add(after);
            } while (cursor.moveToNext());
        }

        return AfterList;
    }



    public void deleteAfter(int afterId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_AFTERS, KEY_ID + " = ?", new String[] { String.valueOf(afterId) });
        db.close();
    }

    // Delete afters (by after id)
/*    public void deleteAfter(Afters after) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_AFTERS, KEY_ID + " = ?",
                new String[] { String.valueOf(after.get_afterid()) });
        db.close();
    }*/

    // Count aantal afters
    public int getAftersCount() {
        String countQuery = "SELECT * FROM " + TABLE_AFTERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }



}
