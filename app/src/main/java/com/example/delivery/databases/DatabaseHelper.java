package com.example.delivery.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.delivery.templates.Mark;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final String TAG = "DatabaseMark";

    private Context mContext;
    private boolean moveToFirst = false;
    private static final int DATABASE_VERSION = 1;

    //#######################################################################  CREATE DB
    private static final String DATABASE_NAME = "Marks.db";

    //#######################################################################  TABLE NAME
    private static final String TABLE_MARKS = "marks_table";

    //#######################################################################  COLUMN NAMES
    private static final String COLUMN_MARK_ID = "mark_id";
    private static final String COLUMN_NAME = "mark_name";
    private static final String COLUMN_ADDRESS = "mark_address";
    private static final String COLUMN_NUMBERS = "mark_numbers";
    private static final String COLUMN_DELIVER_TIME = "mark_deliver";
    private static final String COLUMN_INTERVAL_TIME = "mark_interval";
    private static final String COLUMN_DESTINATION = "mark_destination";
    private static final String COLUMN_TYPE = "mark_type";
    private static final String COLUMN_CURRENT_MARK_INDEX = "current_mark";


    //################################################################################################ TABLE SQL QUERY

    //Product Table
    private String CREATE_MARKS_TABLE = " CREATE TABLE " + TABLE_MARKS + "("
            + COLUMN_MARK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT, "
            + COLUMN_ADDRESS + " TEXT, "
            + COLUMN_NUMBERS + " TEXT, "
            + COLUMN_DELIVER_TIME + " TEXT, "
            + COLUMN_INTERVAL_TIME + " TEXT, "
            + COLUMN_DESTINATION + " TEXT, "
            + COLUMN_TYPE + " INTEGER, "
            + COLUMN_CURRENT_MARK_INDEX + ")";

    //#################################################################################################  DROP TABLE SQL QUERY
    private String DROP_MARKS_TABLE = "DROP TABLE IF EXISTS " + TABLE_MARKS;

    //#################################################################################################  CONSTRUCTOR
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MARKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_MARKS_TABLE);
        onCreate(db);
    }

    //----------------------------------------------------------------------------------------------------MARKS
    //----------------------------------------------------------------------------------------------------ADD MARK
    public void addMark(Mark mark) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, mark.getName());
        values.put(COLUMN_ADDRESS, mark.getAddress());
        values.put(COLUMN_NUMBERS, mark.getNumbers());
        values.put(COLUMN_DELIVER_TIME, mark.getTimeToDeliver());
        values.put(COLUMN_INTERVAL_TIME, mark.getTimeInterval());
        values.put(COLUMN_DESTINATION, mark.getDestination());
        values.put(COLUMN_TYPE, mark.getType());

        //Insert Row
        db.insert(TABLE_MARKS, null, values);
        Log.d(TAG, "---------------------------Added " + mark.getName() + " MARK");
        db.close();
    }


    //--------------------------------------------------------------------------------------------------READ MARKS DATA
    public ArrayList<Mark> readMarksData() {

        ArrayList<Mark> marks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        int typeOneCount = 0;
        int typeTwoCount = 0;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MARKS, null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Mark mark = new Mark(
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NUMBERS)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DELIVER_TIME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_INTERVAL_TIME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE))
            );
            marks.add(mark);
            if (cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE)) == 1){
                typeOneCount++;
//                currentMarkIndex(1,cursor.getInt(cursor.getColumnIndex(COLUMN_MARK_ID)));
            }
            if (cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE) )== 2)typeTwoCount++;


            Log.d(TAG, "MARK Name: " + mark.getName() + ", Mark Type = " + mark.getType());
            cursor.moveToNext();

        }
        cursor.close();
        db.close();
        if (typeOneCount == 0 && typeTwoCount > 0){
            marks.get(0).setType(1);
            Log.d(TAG, "---------------------------DB Mark 0 type changed to 1");
        }
        Log.d(TAG, "---------------------------Read All MARKS Data handled");
        return marks;


    }

    //----------------------------------------------------------------------------------------------------DROP TYPES
    public void dropTypes(){
        SQLiteDatabase db = this.getWritableDatabase();

        int count = getColumnItemCount();
        ContentValues cv = new ContentValues();

        for (int i = 0; i < count; i++) {
            String rowId = String.valueOf(i);
            cv.put(COLUMN_TYPE, 2);
            db.update(TABLE_MARKS, cv, COLUMN_TYPE + "= ?", new String[] {rowId});
        }

        db.close();

    }

    //-------------------------------------------------------------------------------------------------------UPDATE TYPE
    public void updateType(int id, int type){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_MARKS + " SET " + COLUMN_TYPE + " = " + type + " WHERE " +  COLUMN_MARK_ID +"=" + id);
        db.close();
    }

//-------------------------------------------------------------------------------------------------------- CHECK TABLE EMPTY
    public boolean checkTableEmpty() {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_MARK_ID};
        Cursor cursor = db.query(TABLE_MARKS,
                columns,
                null,
                null,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;

    }
//---------------------------------------------------------------------------------- COLUMN ITEM COUNT
    public int getColumnItemCount() {

        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM " + TABLE_MARKS;
        Cursor cursor = getReadableDatabase().rawQuery(sql,null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    //---------------------------------------------------------------------------------------------------- CURRENT COLUMN INDEX

    public void currentMarkIndex(int action, int position) {
        SQLiteDatabase db = this.getWritableDatabase();

        switch (action) {
            case 0:
                ContentValues values = new ContentValues();
                values.put(COLUMN_CURRENT_MARK_INDEX, position);
                db.insert(TABLE_MARKS, null, values);
                db.close();
                Log.d(TAG, "-----------------------------------------------------------------Current Index Added");
                break;
            case 1:
                db.execSQL("UPDATE " + TABLE_MARKS + " SET " + COLUMN_CURRENT_MARK_INDEX + " = " + position + " WHERE " + COLUMN_MARK_ID + "=" + 1);
                Log.d(TAG, "-----------------------------------------------------------------Current Index Updated");
                break;
        }
    }
    //---------------------------------------------------------------------------------------------------GET CURRENT
    public int getCurrentMark(){
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MARKS, null);

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MARKS + " WHERE " + COLUMN_MARK_ID + " =" + 1, null);

        cursor.moveToFirst();
        int current = cursor.getInt(cursor.getColumnIndex(COLUMN_CURRENT_MARK_INDEX));
        return current;


    }


}
