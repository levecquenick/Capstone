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
    private static final String DATABASE_NAME = "ESTA_CLASSROOM_INFO";
    private static final String TABLE_CLASSROOMS = "classrooms";
    private static final String ROOM_ID = "id";
    private static final String ROOM_NUMBER = "room_number";
    private static final String ROOM_X_POS = "x_Pos";
    private static final String ROOM_Y_POS = "y_Pos";


    private final String TABLE_WAYPOINTS = "waypoints";
    private final String WAYPOINT_ID = "waypoint_id";
    private final String WAYPOINT_NAME = "waypoint_Name";
    private final String WAYPOINT_X_POS = "point_x_pos";
    private final String WAYPOINT_Y_POS = "point_y_pos";
    private final String CONNECTED_WAYPOINTS = "points_connected";
    private final String CONNECTED_CLASSROOMS = "connected_rooms";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_CLASSROOM_TABLE = "CREATE TABLE " + TABLE_CLASSROOMS + "( " + ROOM_ID +
                " INTEGER PRIMARY KEY, " + ROOM_NUMBER + " TEXT, " + ROOM_X_POS + " TEXT, " + ROOM_Y_POS + " TEXT "+ " )";
        String CREATE_WAYPOINT_TABLE = "Create Table " + TABLE_WAYPOINTS + "( " + WAYPOINT_ID +
                " INTEGER PRIMARY KEY, " + WAYPOINT_NAME + " TEXT, " + WAYPOINT_X_POS + " TEXT, " +
                WAYPOINT_Y_POS + " TEXT, " + CONNECTED_WAYPOINTS + " TEXT, " + CONNECTED_CLASSROOMS + " TEXT " + " )";
        db.execSQL(CREATE_CLASSROOM_TABLE);
        db.execSQL(CREATE_WAYPOINT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSROOMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WAYPOINTS);
// Creating tables again
        onCreate(db);
    }

    public void addClassroom(classroom classroom){
        System.out.println("ADD CLASSROOM");
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen()) System.out.println("DB is open.");
        else System.out.println("DB is not open.");
        int a = 0;
        a++;

        ContentValues values = new ContentValues();
        values.put(ROOM_NUMBER, classroom.getRoomNum());
        values.put(ROOM_X_POS, classroom.getXPosition());
        values.put(ROOM_Y_POS, classroom.getYPosition());

        db.insert(TABLE_CLASSROOMS,null, values);
        db.close();
    }

    public classroom getClass(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CLASSROOMS, new String[]{ROOM_ID, ROOM_NUMBER, ROOM_X_POS, ROOM_Y_POS},
                ROOM_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        //this might cause some probs just so ya know
        classroom Class = new classroom(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), cursor.getString(2), Integer.parseInt(cursor.getString(3)));
        return Class;
    }

    public List<classroom> getAllClassrooms(){
        List<classroom> classList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CLASSROOMS;

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

    public void addWaypoint(wayPoint point){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(WAYPOINT_ID, point.getID());
        values.put(WAYPOINT_NAME, point.getWaypointName());
        values.put(WAYPOINT_X_POS, point.getXPos());
        values.put(WAYPOINT_Y_POS, point.getYPos());

        db.insert(TABLE_WAYPOINTS, null, values);
    }

    public wayPoint getWayPoint(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WAYPOINTS, new String[]{WAYPOINT_ID, WAYPOINT_NAME, WAYPOINT_X_POS, WAYPOINT_Y_POS},
                WAYPOINT_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        //this might cause some probs just so ya know
        wayPoint point = new wayPoint(cursor.getFloat(0), cursor.getFloat(1), cursor.getInt(2), cursor.getString(3));
        return point;
    }


    public List<wayPoint> getAllWaypoints(){
        List<wayPoint> wayPoints = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_WAYPOINTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                wayPoint wayPoint = new wayPoint();
                wayPoint.setId((Integer.parseInt(cursor.getString(0))));
                wayPoint.setWaypointName(cursor.getString(1));
                wayPoint.setxPos(cursor.getFloat(2));
                wayPoint.setyPos(cursor.getFloat(3));

               /* wayPoint.setId(cursor.getInt((cursor.getColumnIndex(WAYPOINT_ID))));
                wayPoint.setWaypointName((cursor.getString(cursor.getColumnIndex(WAYPOINT_NAME))));
                wayPoint.setxPos((cursor.getFloat(cursor.getColumnIndex(WAYPOINT_X_POS))));
                wayPoint.setyPos((cursor.getFloat(cursor.getColumnIndex(WAYPOINT_Y_POS))));
            */
               wayPoints.add(wayPoint);
            }while(cursor.moveToNext());
        }
        return wayPoints;
    }

    public void deleteWaypoint(wayPoint point){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WAYPOINTS, WAYPOINT_ID + "=?", new String[] {String.valueOf(point.getID())} );
        db.close();
    }



}
