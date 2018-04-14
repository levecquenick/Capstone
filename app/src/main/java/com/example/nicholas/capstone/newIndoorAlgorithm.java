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
     */
    public double locationLat;
    public double locationLong;
    public wayPoint[] connected;

    public wayPoint(double lat, double longitude, wayPoint[] connectedPoints ){
        locationLat = lat;
        locationLong = longitude;
        connected = connectedPoints;
    }
}

/*
Class that will handle the edges
that connect each waypoint
to each other
 */
class Edge{
    /*
    constructor
    takes in 2 waypoints and the distance between them
     */
    public double distance;
    public wayPoint pointA;
    public wayPoint pointB;

    public Edge(wayPoint point1, wayPoint point2, double distance){
        distance = distance;
        pointA = point1;
        pointB = point2;

    }
}
