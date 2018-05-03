package com.example.nicholas.capstone;

/**
 * Created by Nicholas on 3/8/2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ESTA_WAYPOINT_INFO";
    private static final String TABLE_CLASSROOMS = "waypoints";
    private static final String ROOM_ID = "id";
    private static final String ROOM_NUMBER = "number";
    private static final String ROOM_X_POS = "x_Pos";
    private static final String ROOM_Y_POS = "y_Pos";
    private ArrayList<wayPoint> CONNECTED_POINTS = new ArrayList<>();

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_CONTACTS_TABLE = "CREATE TABLE" + TABLE_CLASSROOMS + "(" + ROOM_ID + "INTEGER PRIMARY KEY" + ROOM_NUMBER + "TEXT" + ROOM_X_POS + "TEXT" + ROOM_Y_POS + "TEXT"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSROOMS);
// Creating tables again
        onCreate(db);
    }

    public void addClassroom(classroom classroom){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROOM_NUMBER, classroom.getRoomNum());
        values.put(ROOM_X_POS, classroom.getXPosition());
        values.put(ROOM_Y_POS, classroom.getYPosition());

        db.insert(TABLE_CLASSROOMS, null, values);
        db.close();
    }

    public classroom getClass(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CLASSROOMS, new String[]{ROOM_ID, ROOM_NUMBER, ROOM_X_POS, ROOM_Y_POS},
                ROOM_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        //this might cause some probs just so ya know
        classroom Class = new classroom(Integer.parseInt(cursor.getString(0)), cursor.getFloat(1), cursor.getString(2), cursor.getInt(3));
        return Class;
    }

    public List<classroom> getAllClassrooms(){
        List<classroom> classList = new ArrayList<>();

        String selectQuery = "SELECT * FROM" + TABLE_CLASSROOMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                classroom Class = new classroom();
                Class.setRoomId((Integer.parseInt(cursor.getString(0))));
                Class.setRoomNum(cursor.getString(1));
                Class.setPosX(cursor.getFloat(2));
                Class.setPosY(cursor.getFloat(3));

                classList.add(Class);
            }
            while (cursor.moveToNext());
        }
        return classList;
    }

    public void deleteClassroom(classroom Class){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLASSROOMS, ROOM_ID + "=?", new String[] {String.valueOf(Class.getRoomNum())} );
        db.close();
    }

}
