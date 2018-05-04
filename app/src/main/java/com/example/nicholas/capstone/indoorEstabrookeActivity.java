package com.example.nicholas.capstone;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import android.graphics.Path;
import android.util.Log;
import java.util.List;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

/*
 This class is designed to test the mapping algorithm for Estabrooke hall

 the pathing algorithm is tested and works for Estabrooke hall but
 since I have the pathing algorithm working in IndoorBoardmanActivity.java
 I figure it would be redundant to do it in this class too.
 */
public class indoorEstabrookeActivity extends AppCompatActivity {
    public ArrayList<wayPoint> wayPointRoute = new ArrayList<wayPoint>();
    public List<wayPoint> points;
    public List<classroom> classes;
    public Draw draw;
    public DBHandler db;

    //bottom wall pixel locations used for mapping functions
    public int botCornerLeftPixY = 10;
    public int botCornerRightPixX = 844;
    public int topCornerLeftPixY = 359;

    // wall geolocation data used for mapping functions
    public double topLeftLat = 44.89648;
    public double botRightLat = 44.89612;
    public double botRightLng = 68.67022;
    public double botLeftLat = 44.89662;
    public double botLeftLng = 68.66972;


    //these are just a test
    double xCord = 68.66983;
    double yCord = 44.89647;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        draw = new Draw(this);
        setContentView(draw);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            draw.setBackground(getResources().getDrawable(R.drawable.estabrooke_first_floor));
        }


        db = new DBHandler(this);

        //Adding all the classrooms to the database
        db.addClassroom(new classroom(20,60,"room152",152));
        db.addClassroom(new classroom(80,60,"room157", 157));
        db.addClassroom(new classroom(70,110,"room153", 153));
        db.addClassroom(new classroom(70,175,"room151", 151));
        db.addClassroom(new classroom(70,210,"room148", 148));
        db.addClassroom(new classroom(70,265,"room146", 146));
        db.addClassroom(new classroom(70,283,"room145", 145));
        db.addClassroom(new classroom(130,310,"room143", 143));
        db.addClassroom(new classroom(155,310,"room141", 141));
        db.addClassroom(new classroom(193,310,"room138", 138));
        db.addClassroom(new classroom(225,310,"room136", 136));
        db.addClassroom(new classroom(225,310,"room137", 137));
        db.addClassroom(new classroom(395,265,"room130", 130));
        db.addClassroom(new classroom(700,310,"room115", 115));
        db.addClassroom(new classroom(700,310,"room113", 113));
        db.addClassroom(new classroom(700,262,"room118", 118));
        db.addClassroom(new classroom(785,262,"room111", 111));
        db.addClassroom(new classroom(785,200,"room109", 109));
        db.addClassroom(new classroom(785,175,"room108", 108));
        db.addClassroom(new classroom(785,94,"room103", 103));
        db.addClassroom(new classroom(785,80,"room104", 104));
        db.addClassroom(new classroom(785,44,"room102", 102));
        db.addClassroom(new classroom(785,44,"room101", 101));

        //adding the waypoints to the database
        db.addWaypoint(new wayPoint(70, 110, 1, "point1"));
        db.addWaypoint(new wayPoint(70, 250, 2, "point2"));
        db.addWaypoint(new wayPoint(170, 310, 3, "point3"));
        db.addWaypoint(new wayPoint(205, 310, 4, "point4"));
        db.addWaypoint(new wayPoint(245, 310, 5, "point5"));
        db.addWaypoint(new wayPoint(610, 310, 6, "point6"));
        db.addWaypoint(new wayPoint(650, 310, 7, "point7"));
        db.addWaypoint(new wayPoint(700, 310, 8, "point8"));
        db.addWaypoint(new wayPoint(700, 262, 9, "point9"));
        db.addWaypoint(new wayPoint(785, 262, 10, "point10"));
        db.addWaypoint(new wayPoint(785, 112, 11, "point11"));

        //creating a list of all the classrooms
        classes = (db.getAllClassrooms());
        //list of all the waypoints
        points = (db.getAllWaypoints());


        //int xPixel;



        System.out.println(lngCoordinateToPixel(xCord));



        //connecting the waypoints
        //this one checks the X pos and Y pos of a waypoint against all the X pos and Y pos of the waypoints
        //if the X or Y pos match of the classroom x and y pos then it is a connected waypoint
        for(int i = 0; i<points.size()-1; i++){
            if(points.get(i).getID() == 2)
                points.get(i).addConnectedWaypoint(points.get(i+1));
            if(((points.get(i).getXPos() == points.get(i+1).getXPos())|| (points.get(i).getYPos() ==
                        points.get(i+1).getYPos()))){
                    points.get(i).addConnectedWaypoint(points.get(i+1));

            }
        }




        //connecting the classrooms to a waypoint
        //this one checks the X pos and Y pos of a waypoint against all the X pos and Y pos of the classrooms
        //if the X or Y pos match of the classroom x and y pos then it is a connected classroom
        for(int x = 0; x < points.size(); x++) {
            for (int i = 0; i < classes.size(); i++) {
                if (points.get(x).getXPos() == classes.get(i).posX ||
                        points.get(x).getYPos() == classes.get(i).posY){

                }
            }
        }




    }
    /*
    class that handles the drawing on the image
     */
    public class Draw extends View {

        /**
         * Paint to draw circles
         */
        private Paint mCirclePaint;
        Paint strokePaint;
        Paint mLinePaint;

        Draw(final Context ct) {
            super(ct);
            init(ct);
        }

        private void init(final Context ct) {
            mCirclePaint = new Paint(3);
            mCirclePaint.setColor(Color.argb(100, 50, 205, 50));
            mCirclePaint.setStrokeWidth(10);
            mCirclePaint.setStyle(Paint.Style.FILL);

            strokePaint = new Paint();
            strokePaint.setColor(Color.argb(100, 100, 0, 0));
            strokePaint.setStyle(Paint.Style.FILL);
            strokePaint.setStrokeWidth(10);

            mLinePaint = new Paint();
            mLinePaint.setColor(Color.rgb(0, 0, 100));
            mLinePaint.setStrokeWidth(10);
            mLinePaint.setStyle(Paint.Style.FILL);

        }

        public void onDraw(final Canvas canv) {

            canv.drawPoint(lngCoordinateToPixel(xCord),latCoordinateToPixel(yCord), mLinePaint);

    }
    /*
    indoor pathing algorithm
     */
    boolean a = false;
    public void findWayPointRoute( wayPoint start, classroom end) {
        while (!a) {
            //adding the start node to the final routing algorithm
            wayPointRoute.add(start);
            //searches through the connected classrooms of the current waypoint
            for (int x = 0; x < start.getConnectedRooms().size(); x++) {
                //if the destination is connected to the current waypoint
                if (start.getConnectedRooms().get(x).equals(end)) {
                    wayPointRoute.add(start);
                    a = true;

                }
            }
            //iterates through all connected points
            for (int x = 0; x < start.getConnectedPoints().size(); x++) {
                //looks through the connected rooms of each waypoint connected to the start node
                for (int i = 0; i < start.getConnectedPoints().get(x).getConnectedRooms().size(); i++) {
                    //if the destination is connected to the waypoint that is conected to the start (yeah its confusing)
                    if (start.getConnectedPoints().get(x).getConnectedRooms().get(i).equals(end)) {
                        //adds that waypoint to the final route
                        wayPointRoute.add(start.getConnectedPoints().get(x));
                        a = true;
                    }
                }
            }
        }
    }


    }

    //Mapping function to mapp the longitude of an object to an x coordinate
    public int lngCoordinateToPixel(double xCoordinate){
        double xCoord = xCoordinate;
        //get the coordinate width of a building
        double geoWidth = botRightLng - botLeftLng;
        //find the pixel width of the building
        double pixelWidth = botCornerRightPixX - botCornerLeftPixY;

        //mapping equation
        int xPixel = (int)(pixelWidth*(xCoord-botLeftLng)/geoWidth);


        //# of pixels between edge of image and the left wall of the building because I didn't have
        //the geolocation of the edge of the image I only had the location of the wall so it needs to
        //be offset to get an acurate location on the image
        xPixel += 20;

        return xPixel;

    }


    //function to mapp a latitude coordinate to a y value
    public int latCoordinateToPixel(double yCoordinate){
        double yCoord = yCoordinate;

        //get the coordinate height
        double geoHeight = botLeftLat - topLeftLat;
        //get the pixel height
        double pixelHeight = topCornerLeftPixY - botCornerLeftPixY;

        //mapping equation to convert a longitude point to a pixel value
        int yPixel = (int)(pixelHeight*(1-((yCoord - botRightLat)/(geoHeight))/100));

        //# of pixels between edge of image and the bottom wall of the building because I didn't have
        //the geolocation of the edge of the image I only had the location of the wall so it needs to
        //be offset to get an acurate location on the image
        yPixel -= 31;
        return yPixel;
    }


}