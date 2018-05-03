package com.example.nicholas.capstone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.google.android.gms.maps.model.PolylineOptions;
import android.graphics.Path;
import android.util.Log;
import java.util.List;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashSet;


public class indoorEstabrookeActivity extends AppCompatActivity {
    public ArrayList<wayPoint> wayPointRoute = new ArrayList<wayPoint>();
    public List<wayPoint> points;
    public List<classroom> classes;
    public DBHandler db;
    /*ArrayList<wayPoint> estabrookeWaypoints = new ArrayList<>();
    ArrayList<classroom> classList = new ArrayList<>();
    wayPoint estabrookePoint1 = new wayPoint(70,10);
    wayPoint estabrookePoint2 = new wayPoint(70,250);
    wayPoint estabrookePoint3 = new wayPoint(170,310);
    wayPoint estabrookePoint4 = new wayPoint(205,310);
    wayPoint estabrookePoint5 = new wayPoint(245,310);
    wayPoint estabrookePoint6 = new wayPoint(610,310);
    wayPoint estabrookePoint7 = new wayPoint(650,310);
    wayPoint estabrookePoint8 = new wayPoint(700,310);
    wayPoint estabrookePoint9 = new wayPoint(700,262);
    wayPoint estabrookePoint10 = new wayPoint(785,262);
    wayPoint estabrookePoint11 = new wayPoint(785,110);
    classroom room01 = new classroom(20, 60, "room152");
    classroom room02 = new classroom(80, 60, "room157");
    classroom room03 = new classroom(70, 110, "room153");
    classroom room04 = new classroom(70, 175, "room151");
    classroom room05 = new classroom(70, 210, "room148");
    classroom room06 = new classroom(70, 265, "room146");
    classroom room07 = new classroom(70, 283, "room145");
    classroom room08 = new classroom(130, 310, "room143");
    classroom room09 = new classroom(155, 310, "room141");
    classroom room10 = new classroom(193, 310, "room138");
    classroom room11 = new classroom(225, 310, "room136");
    classroom room12 = new classroom(225, 310, "room137");
    classroom room13 = new classroom(395,265 , "room130");
    classroom room14 = new classroom(700,310 , "room115");
    classroom room15 = new classroom(700,310 , "room113");
    classroom room16 = new classroom(700,262, "room118");
    classroom room17 = new classroom(785,262, "room111");
    classroom room18 = new classroom(785,200, "room109");
    classroom room19 = new classroom(785,175, "room108");
    classroom room20 = new classroom(785,94, "room103");
    classroom room21 = new classroom(785,80, "room104");
    classroom room22 = new classroom(785,44, "room102");
    classroom room23 = new classroom(785,44, "room101");
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Draw draw = new Draw(this);
        setContentView(draw);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            draw.setBackground(getResources().getDrawable(R.drawable.estabrooke_first_floor));
        }

        db = new DBHandler(this);

        //Adding all the classrooms to the database
        //classroom Classroom = new classroom(20,60,"room152", 152);
        db.addClassroom(new classroom(20,60,"152",152));
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
        db.addWaypoint(new wayPoint(785, 110, 11, "point11"));

        classes = (db.getAllClassrooms());

        points = (db.getAllWaypoints());




        /*for(int i = 0; i<points.size()-1; i++){
            for(int x = 1; x<points.size(); x++){
                if((points.get(i).getXPos() == points.get(x).getXPos()) ||
                        (points.get(i).getYPos() == points.get(x).getYPos())){
                    points.get(i).addConnectedWaypoint(points.get(x));
                }
            }
        }

        for(int i = 0; i < points.size(); i++) {
            for (int x = 0; x < classes.size(); x++) {
                if (points.get(x).getXPos() == classes.get(i).posX ||
                        points.get(x).getYPos() == classes.get(i).posY){

                }
            }
        }

        points.get(0).addConnectedClassroom(classes.get(0));
        points.get(0).addConnectedClassroom(classes.get(1));




        /*estabrookeWaypoints.add(estabrookePoint1);
        estabrookeWaypoints.add(estabrookePoint2);
        estabrookeWaypoints.add(estabrookePoint3);
        estabrookeWaypoints.add(estabrookePoint4);
        estabrookeWaypoints.add(estabrookePoint5);
        estabrookeWaypoints.add(estabrookePoint6);
        estabrookeWaypoints.add(estabrookePoint7);
        estabrookeWaypoints.add(estabrookePoint8);
        estabrookeWaypoints.add(estabrookePoint9);
        estabrookeWaypoints.add(estabrookePoint10);
        estabrookeWaypoints.add(estabrookePoint11);

        classList.add(room01);
        classList.add(room02);
        classList.add(room03);
        classList.add(room04);
        classList.add(room05);
        classList.add(room06);
        classList.add(room07);
        classList.add(room08);
        classList.add(room09);
        classList.add(room10);
        classList.add(room11);
        classList.add(room12);
        classList.add(room13);
        classList.add(room14);
        classList.add(room15);
        classList.add(room16);
        classList.add(room17);
        classList.add(room18);
        classList.add(room19);
        classList.add(room20);
        classList.add(room21);
        classList.add(room22);
        classList.add(room23);
*
        for(int i = 0; i<estabrookeWaypoints.size()-1; i++){
            if(estabrookeWaypoints.get(i).getXPos() == estabrookeWaypoints.get(i+1).getXPos()
                    || estabrookeWaypoints.get(i).getYPos() == estabrookeWaypoints.get(i+1).getYPos() ){
                estabrookeWaypoints.get(i).addConnectedWaypoint(estabrookeWaypoints.get(i+1));
            }
        }
        for(int x = 0; x < estabrookeWaypoints.size(); x++) {
            for (int i = 0; i < classList.size(); i++) {
                if (estabrookeWaypoints.get(x).getXPos() == classList.get(i).posX ||
                        estabrookeWaypoints.get(x).getYPos() == classList.get(i).posY){

                }
            }
        }

*/
        //db.addWaypoint(new wayPoint(70, 110, 1, "point1"));
    }

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

            /*findWayPointRoute(basementPoint2,room13);

            for(int i = 0; i<wayPointRoute.size()-1; i++){
                canv.drawLine(wayPointRoute.get(i).getXPos(),wayPointRoute.get(i).getYPos(),
                        wayPointRoute.get(i+1).getXPos(),wayPointRoute.get(i+1).getYPos(),mLinePaint);
            }
            canv.drawLine(wayPointRoute.get(wayPointRoute.size()-1).getXPos(),wayPointRoute.get(wayPointRoute.size()-1).getYPos(),
                    room13.getXPosition(),room13.getYPosition(),mLinePaint);
*/          //top left corner
            canv.drawPoint(10, 10, mLinePaint);
            //top right corner
            canv.drawPoint(10, 359, mLinePaint);
            //bottom left
            canv.drawPoint(844, 10, mLinePaint);
            //bottom right
            canv.drawPoint(844, 359, mLinePaint);


            for(int x = 0; x<classes.size(); x++){
                canv.drawPoint(classes.get(x).getXPosition(),classes.get(x).getYPosition(),mLinePaint);

            }
            for(int x = 0; x<points.size(); x++){
                canv.drawPoint(points.get(x).getXPos(),points.get(x).getYPos(),mLinePaint);

            }




            /*canv.drawPoint(70, 110, strokePaint);
            canv.drawPoint(70, 250, strokePaint);
            canv.drawPoint(170, 310, strokePaint);
            canv.drawPoint(205, 310, strokePaint);
            canv.drawPoint(245, 310, strokePaint);
            canv.drawPoint(610, 310, strokePaint);
            canv.drawPoint(650, 310, strokePaint);

            canv.drawPoint(700, 310, strokePaint);
            canv.drawPoint(700, 262, strokePaint);
            canv.drawPoint(785, 262, strokePaint);
            canv.drawPoint(785, 110, strokePaint);

            //room 152
            canv.drawPoint(20, 60, mLinePaint);
            //room 157
            canv.drawPoint(80, 60, mLinePaint);
            //room 153
            canv.drawPoint(70, 110, mLinePaint);
            //room 151
            canv.drawPoint(70, 175, mLinePaint);
            //room 148
            canv.drawPoint(70, 210, mLinePaint);
            //room 146
            canv.drawPoint(70, 265, mLinePaint);
            //room 145
            canv.drawPoint(70, 283, mLinePaint);
            //room 143
            canv.drawPoint(130, 310, mLinePaint);
            //room 141
            canv.drawPoint(155, 310, mLinePaint);
            //room 138
            canv.drawPoint(193, 310, mLinePaint);
            //room 136
            canv.drawPoint(225, 310, mLinePaint);
            //room 137 its the same as 136 cuz they're so close together
            canv.drawPoint(225, 310, mLinePaint);

            //room 130
            canv.drawPoint(395, 265, mLinePaint);

            //rooms 115 and 113
            canv.drawPoint(700, 310, mLinePaint);
            canv.drawPoint(700, 310, mLinePaint);
            //room 118
            canv.drawPoint(700, 262, mLinePaint);
            //room 111
            canv.drawPoint(785, 262, mLinePaint);
            //room 109
            canv.drawPoint(785, 200, mLinePaint);
            //room 108
            canv.drawPoint(785, 175, mLinePaint);
            //room 103
            canv.drawPoint(785, 94, mLinePaint);
            //room 104
            canv.drawPoint(785, 80, mLinePaint);
            //room 102
            canv.drawPoint(785, 44, mLinePaint);
            //room 101
            canv.drawPoint(785, 44, mLinePaint);


*/


        }



    }
    boolean a = false;
    public void findWayPointRoute( wayPoint start, classroom end) {
        while (!a) {
            wayPointRoute.add(start);

            for (int x = 0; x < start.getConnectedRooms().size(); x++) {
                if (start.getConnectedRooms().get(x).equals(end)) {
                    wayPointRoute.add(start);
                    a = true;

                }
            }

            for (int x = 0; x < start.getConnectedPoints().size(); x++) {
                for (int i = 0; i < start.getConnectedPoints().get(x).getConnectedRooms().size(); i++) {
                    if (start.getConnectedPoints().get(x).getConnectedRooms().get(i).equals(end)) {
                        wayPointRoute.add(start.getConnectedPoints().get(x));
                        a = true;
                    }
                }
            }
        }
    }

    public void findWayPointRoute2(wayPoint start, classroom end){
        //wayPointRoute.add(start);
        for(int x = 0; x<start.getConnectedRooms().size(); x++){
            if(start.getConnectedRooms().get(x).equals(end)){
                wayPointRoute.add(start);
            }
            else{
                findWayPointRoute2(start.getConnectedPoints().get(x), end);
            }
        }
    }

    public List<wayPoint> setUpWaypoints(DBHandler db){
        List<wayPoint> points = new ArrayList<>();
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
        db.addWaypoint(new wayPoint(785, 110, 11, "point11"));

        points = db.getAllWaypoints();

        return points;
    }



}