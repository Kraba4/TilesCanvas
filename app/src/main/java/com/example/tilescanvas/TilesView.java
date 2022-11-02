package com.example.tilescanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.shape.InterpolateOnScrollPositionChangeHelper;

import java.util.ArrayList;
import java.util.Random;

public class TilesView extends View {
    int darkColor;
    int darkColorA;
    int brightColor;
    int brightColorA;
    int brightColorCounter;
    boolean cells[][] = new boolean[4][4];
    int cellSize;
    int ia;
    public boolean isHelp;
    boolean isOneMode;
    Paint paintDark;Paint paintBright;Paint alertText;Paint paintDarkA;Paint paintBrightA;

    public TilesView(Context context) {
        super(context);
    }

    public TilesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        darkColor = getResources().getColor(R.color.teal_700);
        brightColor = getResources().getColor(R.color.purple_200);
        darkColorA = getResources().getColor(R.color.teal_700a);
        brightColorA = getResources().getColor(R.color.purple_200a);
        Random r = new Random();
        brightColorCounter = 0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                cells[i][j] = r.nextBoolean();
                if(cells[i][j]){
                    brightColorCounter++;
                }
            }
        }
        paintDark = new Paint();
        paintDark.setColor(darkColor);
        paintBright = new Paint();
        paintBright.setColor(brightColor);
        paintDarkA = new Paint();
        paintDarkA.setColor(darkColorA);
        paintBrightA = new Paint();
        paintBrightA.setColor(brightColorA);
        alertText = new Paint();
        alertText.setAntiAlias(true);
        alertText.setColor(getResources().getColor(R.color.black));
        alertText.setStyle(Paint.Style.FILL);
        alertText.setTextSize(50);
        alertText.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(brightColorCounter==0 || brightColorCounter == cells.length * cells[0].length) {
        }else {
            ia = 100;
        }
        cellSize = canvas.getWidth() / 4;
        int offset = 5;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(cells[i][j]){
                    if((ia/6/4==i && ia/6%4==j) ){
                        canvas.drawRect(j* cellSize + offset, i* cellSize + offset,
                                (j+1)* cellSize - offset, (i+1)* cellSize - offset, paintBrightA );
                    }else{
                        canvas.drawRect(j* cellSize + offset, i* cellSize + offset,
                                (j+1)* cellSize - offset, (i+1)* cellSize - offset, paintBright );
                    }
                }else{
                    if(ia/6/4==i && ia/6%4==j){
                        canvas.drawRect(j* cellSize + offset, i* cellSize + offset,
                                (j+1)* cellSize - offset, (i+1)* cellSize - offset, paintDarkA );
                    }else {
                        canvas.drawRect(j * cellSize + offset, i * cellSize + offset,
                                (j + 1) * cellSize - offset, (i + 1) * cellSize - offset, paintDark);
                    }
                }
            }
        }
        Random r = new Random();

        if(brightColorCounter==0 || brightColorCounter == cells.length * cells[0].length) {
            if(ia>16*6){
                ia=0;
            }else{
                ia++;
            }
            invalidate();
        }else{
            ia = 100;
            if(isHelp) {
                ArrayList<Integer> res = computeSolution();
                for (int re : res) {
                    int i = re / 4;
                    int j = re % 4;
                    canvas.drawLine(j*cellSize, (i+1)*cellSize, (j+1)*cellSize, i*cellSize, alertText);
                    canvas.drawLine(j*cellSize, (i+0.5F)*cellSize, (j+0.5F)*cellSize, i*cellSize, alertText);
                    canvas.drawLine((j+0.5F)*cellSize, (i+1)*cellSize, (j+1)*cellSize, (i+0.5F)*cellSize, alertText);

                }
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int jm = ((int)x)/cellSize;
        int im = ((int)y)/cellSize;
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(im < 4 && jm <4) {
                changeColor(im,jm);
                if(!isOneMode) {
                    for (int i = 0; i < cells.length; i++) {
                        changeColor(i, jm);
                    }
                    for (int j = 0; j < cells[0].length; j++) {
                        changeColor(im, j);
                    }
                }
            }
        }
        invalidate();
        return super.onTouchEvent(event);
    }
    private void changeColor(int i, int j){
        if (cells[i][j]) {
            cells[i][j] = false;
            brightColorCounter--;
        } else {
            cells[i][j] = true;
            brightColorCounter++;
        }
    }
    private ArrayList<Integer> computeSolution(){
        int[] linesCount = new int[cells.length];
        int[] rowsCount = new int[cells[0].length];
        int[][] cellCount = new int[cells.length][cells[0].length];
        for(int i=0;i<cells.length; i++){
            for(int j=0;j<cells[0].length;j++){
                if(cells[i][j]) {
                    linesCount[i]++;
                    rowsCount[j]++;
                    cellCount[i][j]=1;
                }
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        for(int i=0;i<cells.length; i++){
            for(int j=0;j<cells[0].length;j++) {
                if((linesCount[i] + rowsCount[j] - cellCount[i][j])%2==1){
                    result.add(i*cells[0].length + j);
                }
            }
        }
        return result;
    }
}
