package com.example.nicholas.capstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class estabrooke_nav_start_screen extends AppCompatActivity {
    /*
    This was supposed to be a start screen for the estabrooke navigation algorithm
    I didnt get time for it so i have to leave it blank
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estabrooke_nav_start_screen);

        EditText startLoc = findViewById(R.id.editText4);
        EditText endLoc = findViewById(R.id.editText5);
        Button direct = findViewById(R.id.button4);

    }
}
