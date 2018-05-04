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
The purpose of this class is to test the pathing algorithm in the basement of boardman hall
 */
public class IndoorBoardmanActivity extends AppCompatActivity {

    DBHandler db = new DBHandler(this);
    public Draw draw;
    public ArrayList<wayPoint> wayPointRoute = new ArrayList<>();

    //list to hold all the waypoints
    public List<wayPoint> points = new ArrayList<>();
    //list to hold all the classrooms
    public List<classroom> classes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Draw draw = new Draw(this);
        setContentView(draw);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            draw.setBackground(getResources().getDrawable(R.drawable.capture));
        }

        //making all the waypoints and adding them to the database
        db.addWaypoint(new wayPoint(66, 540, 1, "point1"));
        db.addWaypoint(new wayPoint(340, 540, 2, "point2"));
        db.addWaypoint(new wayPoint(340, 130, 2, "point3"));

        //making all the classrooms and adding them to the database
        db.addClassroom(new classroom(66,  540, "room1", 1));
        db.addClassroom(new classroom(66,  445, "room8", 2));
        db.addClassroom(new classroom(160, 540, "room18", 3));
        db.addClassroom(new classroom(160, 540, "room9", 4));
        db.addClassroom(new classroom(275, 495, "room15", 5));
        db.addClassroom(new classroom(340, 400, "room25", 6));
        db.addClassroom(new classroom(340, 400, "room26", 7));
        db.addClassroom(new classroom(340, 540, "room29", 8));
        db.addClassroom(new classroom(340, 305, "room30", 9));
        db.addClassroom(new classroom(340, 275, "room32", 10));
        db.addClassroom(new classroom(340, 205, "room34", 11));
        db.addClassroom(new classroom(340, 540, "room17", 12));
        db.addClassroom(new classroom(375, 130, "room40", 13));

        // add all the classrooms to a list
        classes = db.getAllClassrooms();
        //adding all the waypoints to a list
        points = db.getAllWaypoints();

        //connecting the waypoints
        //this one checks the X pos and Y pos of a waypoint against all the X pos and Y pos of the waypoints
        //if the X or Y pos match of the classroom x and y pos then it is a connected waypoint
        for(int i = 0; i<points.size()-1; i++){
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

    public class Draw extends View {


         //Paint to draw circles

        private Paint mCirclePaint;
        Paint strokePaint;
        Paint mLinePaint;

 Draw(final Context ct) {
            super(ct);
            init(ct);
        }

        private void init(final Context ct) {
            // Generate bitmap used for background
            //mBitmap = BitmapFactory.decodeResource(ct.getResources(), R.drawable.abc_ic_menu_cut_mtrl_alpha);
            mCirclePaint = new Paint();
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

        //I know its bad to hard code these 2 things in but its for testing purposes
        classroom destination = new classroom(db.getClass(7).getXPosition(),db.getClass(7).getYPosition(),
                db.getClass(7).getRoomNum(), db.getClass(7).getRoomId());

        wayPoint startPoint = new wayPoint(db.getWayPoint(2).getXPos(),db.getWayPoint(2).getYPos(),
                db.getWayPoint(2).getID(), db.getWayPoint(2).getWaypointName());

            findWayPointRoute(startPoint,destination);

            for(int i = 0; i<wayPointRoute.size()-1; i++){
                canv.drawLine(wayPointRoute.get(i).getXPos(),wayPointRoute.get(i).getYPos(),
                        wayPointRoute.get(i+1).getXPos(),wayPointRoute.get(i+1).getYPos(),mLinePaint);
            }
            canv.drawLine(wayPointRoute.get(wayPointRoute.size()-1).getXPos(),wayPointRoute.get(wayPointRoute.size()-1).getYPos(),
                    destination.getXPosition(),destination.getYPosition(),mLinePaint);


        }



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