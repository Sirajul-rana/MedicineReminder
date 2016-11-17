package com.destroinc.medicinereminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rana on 22-Jul-16.
 */
public class MedicineDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Medicine.db";
    private static final String TABLE_NAME = "Medicine_table";
    private static final String MEDICINE_ID = "MEDICINE_ID";
    private static final String MEDICINE_NAME = "MEDICINE_NAME";
    private static final String MEDICINE_TIME_MORNING = "MEDICINE_TIME_MORNING";
    private static final String MEDICINE_TIME_NOON = "MEDICINE_TIME_NOON";
    private static final String MEDICINE_TIME_AFTERNOON = "MEDICINE_TIME_AFTERNOON";
    private static final String MEDICINE_TIME_NIGHT = "MEDICINE_TIME_NIGHT";
    private static final String MEDICINE_IMAGE = "MEDICINE_IMAGE";
    private static final String MEDICINE_QUANTITY = "MEDICINE_QUANTITY";
    public MedicineDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table"+TABLE_NAME+" ("+MEDICINE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+MEDICINE_NAME+" TEXT," +
                " "+MEDICINE_TIME_MORNING+" INTEGER, "+MEDICINE_TIME_NOON+" INTEGER, "+MEDICINE_TIME_AFTERNOON+" INTEGER," +
                " "+MEDICINE_TIME_NIGHT+" INTEGER, "+MEDICINE_IMAGE+" BLOB, "+MEDICINE_QUANTITY+" REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertMedicine(String name,String morning, String noon, String afternoon, String night, byte[] image, String quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEDICINE_NAME,name);
        contentValues.put(MEDICINE_TIME_MORNING, morning);
        contentValues.put(MEDICINE_TIME_NOON, noon);
        contentValues.put(MEDICINE_TIME_AFTERNOON, afternoon);
        contentValues.put(MEDICINE_TIME_NIGHT, night);
        contentValues.put(MEDICINE_IMAGE, image);
        contentValues.put(MEDICINE_QUANTITY, quantity);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
}
