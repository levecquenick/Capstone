package com.example.nicholas.capstone;

/**
 * Created by Nicholas on 4/11/2018.
 */
import android.os.Build;
import android.support.annotation.RequiresApi;

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
    public ArrayList<wayPoint> connectedPoints = new ArrayList<wayPoint>();
    public ArrayList<classroom> connectedRooms = new ArrayList<classroom>();
    public boolean visited = false;

    public wayPoint(float locX, float locY){
        xPos = locX;
        yPos = locY;
    }

    public void addConnectedRoom(classroom room){
        this.connectedRooms.add(room);
    }
    public void addConnectedWaypoint(wayPoint point){
        this.connectedPoints.add(point);
    }



    public float getXPos(){
        return this.xPos;
    }
    public float getYPos(){
        return this.yPos;
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
    public classroom(float locX, float locY, String roomID){
        posX = locX;
        posY = locY;
        roomNum = roomID;
    }

    public float getXPosition(){
        return this.posX;
    }

    public float getYPosition(){
        return this.posY;
    }
}


