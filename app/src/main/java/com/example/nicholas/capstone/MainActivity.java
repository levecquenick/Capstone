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


import java.util.ArrayList;
import java.util.HashSet;


public class MainActivity extends AppCompatActivity {
    /*
    public wayPoint basementPoint1 = new wayPoint(66,540);
    public wayPoint basementPoint2 = new wayPoint(340, 540);
    public wayPoint basementPoint3 = new wayPoint(340, 130);

    classroom room01 = new classroom(66, 540, "room01");
    classroom room02 = new classroom(66, 445, "room08");
    classroom room03 = new classroom(160, 540, "room18");
    classroom room04 = new classroom(160, 540, "room09");
    classroom room05 = new classroom(275, 540, "room15");
    classroom room06 = new classroom(340, 495, "room25");
    classroom room07 = new classroom(340, 400, "room26");
    classroom room08 = new classroom(340, 400, "room29");
    classroom room09 = new classroom(340, 305, "room30");
    classroom room10 = new classroom(340, 275, "room32");
    classroom room11 = new classroom(340, 205, "room34");
    classroom room12 = new classroom(340, 540, "room17");
    classroom room13 = new classroom(375, 130, "room40");
    ArrayList<wayPoint> basementWaypoints = new ArrayList<wayPoint>();
    public ArrayList<wayPoint> wayPointRoute = new ArrayList<wayPoint>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Draw draw = new Draw(this);
        setContentView(draw);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            draw.setBackground(getResources().getDrawable(R.drawable.capture));
        }
        basementWaypoints.add(basementPoint1);
        basementWaypoints.add(basementPoint2);
        basementWaypoints.add(basementPoint3);
        basementPoint1.addConnectedClassroom(room01);
        basementPoint1.addConnectedClassroom(room02);
        basementPoint1.addConnectedClassroom(room03);
        basementPoint1.addConnectedClassroom(room04);
        basementPoint1.addConnectedClassroom(room05);
        basementPoint2.addConnectedClassroom(room06);
        basementPoint2.addConnectedClassroom(room07);
        basementPoint2.addConnectedClassroom(room08);
        basementPoint2.addConnectedClassroom(room09);
        basementPoint2.addConnectedClassroom(room10);
        basementPoint2.addConnectedClassroom(room11);
        basementPoint2.addConnectedClassroom(room12);
        basementPoint3.addConnectedClassroom(room13);

        basementPoint3.addConnectedWaypoint(basementPoint2);
        basementPoint2.addConnectedWaypoint(basementPoint1);
        basementPoint2.addConnectedWaypoint(basementPoint3);
        basementPoint1.addConnectedWaypoint(basementPoint2);


    }

    public class Draw extends View {

        /**
         * Paint to draw circles

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
            findWayPointRoute(basementPoint2,room13);

            for(int i = 0; i<wayPointRoute.size()-1; i++){
                canv.drawLine(wayPointRoute.get(i).getXPos(),wayPointRoute.get(i).getYPos(),
                        wayPointRoute.get(i+1).getXPos(),wayPointRoute.get(i+1).getYPos(),mLinePaint);
            }
            canv.drawLine(wayPointRoute.get(wayPointRoute.size()-1).getXPos(),wayPointRoute.get(wayPointRoute.size()-1).getYPos(),
                    room13.getXPosition(),room13.getYPosition(),mLinePaint);


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

*/

}