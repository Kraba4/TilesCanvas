package com.example.tilescanvas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    TilesView field;
    int activeColor;
    int notActiveColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        field = findViewById(R.id.field);
        activeColor = getResources().getColor(R.color.active);
        notActiveColor = getResources().getColor(R.color.notActive);
        findViewById(R.id.button_onemode).setBackgroundColor(notActiveColor);
        findViewById(R.id.button_help).setBackgroundColor(notActiveColor);
    }
    public void onClickHelp(View view){
        field.isHelp = !field.isHelp;
        field.invalidate();
    }
    public void onClickOneMode(View view){
        Button button = (Button) view;
        field.isOneMode = !field.isOneMode;
        if(field.isOneMode){
            button.setBackgroundColor(activeColor);
            button.setText("One mode(Activated)");
        }else{
            button.setBackgroundColor(notActiveColor);
            button.setText("One mode");
        }
    }
}