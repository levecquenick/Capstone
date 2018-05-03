package com.example.nicholas.capstone;

/**
 * Created by Nicholas on 4/11/2018.
 */
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.*;
import java.util.*;

import static java.lang.Integer.compare;
public class newIndoorAlgorithm {


}
/*
Class that will deal with the major
waypoints in each building
 */
class wayPoint{
    /*
    constructor
    Needs:
    location
    list of connected waypoints
    list of connected classrooms
     */
    public float xPos;
    public float yPos;
    public int waypointId;
    public String waypointName;
    public ArrayList<wayPoint> connectedPoints = new ArrayList<wayPoint>();
    public ArrayList<classroom> connectedRooms = new ArrayList<classroom>();
    public boolean visited = false;

    public wayPoint(float locX, float locY, int id, String name){
        xPos = locX;
        yPos = locY;
        waypointId = id;
        waypointName = name;

    }

    public wayPoint(){

    }

    public void setId(int id){
        this.waypointId = id;
    }

    public int getID(){
        return this.waypointId;
    }


    public void addConnectedWaypoint(wayPoint point){
        this.connectedPoints.add(point);
        point.connectedPoints.add(this);
    }



    public float getXPos(){
        return this.xPos;
    }
    public float getYPos(){
        return this.yPos;
    }

    public void setxPos(float xPos){
        this.xPos = xPos;
    }
    public void setyPos(float yPos){
        this.yPos = yPos;
    }

    public void setWaypointName(String name){
        this.waypointName = name;
    }

    public String getWaypointName(){
        return this.waypointName;
    }

    public ArrayList<classroom> getConnectedRooms(){
        return this.connectedRooms;
    }

    public ArrayList<wayPoint> getConnectedPoints(){
        return this.connectedPoints;
    }

    public void visit(){
        this.visited = true;
    }

    public boolean isVisited(){
        return visited;
    }

    public void addConnectedClassroom(classroom room){
        connectedRooms.add(room);
    }

    public void setConnectedRooms(ArrayList<classroom> rooms){
        this.connectedRooms = rooms;
    }

    public void setConnectedPoints(ArrayList<wayPoint> points){
        this.connectedPoints = points;
    }
}

class classroom{

    /*
    constructor
    Needs:
    location
    room number
     */
    float posX;
    float posY;
    String roomNum;
    int roomId;
    public classroom(float locX, float locY, String number, int id){
        posX = locX;
        posY = locY;
        roomNum = number;
        roomId = id;
    }

    public classroom(){

    }

    public void setRoomId(int id){
        this.roomId = id;
    }

    public int getRoomId(){
        return this.roomId;
    }
    public void setRoomNum(String number){
        this.roomNum = number;
    }

    public void setPosX(float posX){this.posX = posX;}
    public void setPosY(float posY){this.posY = posY;}

    public float getXPosition(){
        return this.posX;
    }

    public float getYPosition(){
        return this.posY;
    }

    public String getRoomNum(){return this.roomNum;}

}


