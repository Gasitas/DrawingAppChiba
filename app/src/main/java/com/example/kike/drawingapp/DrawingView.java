package com.example.kike.drawingapp;

import android.graphics.Color;
import android.graphics.drawable.shapes.Shape;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;


/**
 * Created by Kike on 9/27/2016.
 */
public class DrawingView extends View {
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;

    private enum shape{
        CIRCLE, TRIANGLE, SQUARE, STAR
    } // add new shapes

    private shape drawingShape;

    private float accumulateDistance;
    int numberPoints;

    private float xorigin, yorigin, radious;
    private float squareHeight, squareWidth;


    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();

        numberPoints=0;
        accumulateDistance=0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    //view given size
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    //draw view
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //detect user touch
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                accumulateAccuracy(touchX,touchY,xorigin,yorigin);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                 break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
    private void setupDrawing(){
        //get drawing area setup for interaction
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);

        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    public void drawCircle(){
        resetAccuracy();
        int paddingSides= canvasBitmap.getWidth()/10;
        xorigin=canvasBitmap.getWidth()/2; // Half of the width of the screen
        yorigin=canvasBitmap.getHeight()/2; //half of the height of the screen
        radious=(canvasBitmap.getWidth()-paddingSides)/2;
        drawingShape=shape.CIRCLE;
        drawCanvas.drawColor(Color.WHITE);
        drawPaint.setAlpha(30);

        drawCanvas.drawCircle(xorigin,yorigin,radious,drawPaint);
        drawPaint.setAlpha(100);
    }

    public void drawTriangle() {
        drawingShape=shape.TRIANGLE;
        drawCanvas.drawColor(Color.WHITE);
        float paddingBottom= (canvasBitmap.getHeight()/10);
        float paddingSides= canvasBitmap.getWidth()/20;
        drawPaint.setAlpha(30);
        drawCanvas.drawLine(paddingSides,canvasBitmap.getHeight()-paddingBottom,canvasBitmap.getWidth()-paddingSides,canvasBitmap.getHeight()-paddingBottom,drawPaint);
        drawCanvas.drawLine(paddingSides,canvasBitmap.getHeight()-paddingBottom,canvasBitmap.getWidth()/2,paddingBottom,drawPaint);
        drawCanvas.drawLine(canvasBitmap.getWidth()-paddingSides,canvasBitmap.getHeight()-paddingBottom,canvasBitmap.getWidth()/2,paddingBottom,drawPaint);

        drawPaint.setAlpha(100);
    }

    public void drawSquare() {
        drawingShape=shape.SQUARE;
        drawCanvas.drawColor(Color.WHITE);
        float paddingBottom= (canvasBitmap.getHeight()/10);
        float paddingSides= canvasBitmap.getWidth()/20;
        drawPaint.setAlpha(30);
        squareWidth=canvasBitmap.getWidth()-paddingSides-paddingSides;
        squareHeight=canvasBitmap.getHeight()-paddingBottom-paddingBottom;
        drawCanvas.drawRect(paddingSides,paddingBottom,canvasBitmap.getWidth()-paddingSides,canvasBitmap.getHeight()-paddingBottom,drawPaint);
        drawPaint.setAlpha(100);
    }

    private void accumulateAccuracy(float x1,float y1, float x2, float y2) {
       double distance;
        numberPoints++;
        if (drawingShape==shape.CIRCLE) {
                distance = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
                distance=distance-radious;
           accumulateDistance+=Math.abs((float)distance);
       }
        if (drawingShape==shape.SQUARE) {
            distance = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));


        }
    }

    public float getAccuracy() {
        return accumulateDistance/numberPoints;
    }

    private void resetAccuracy() {
        numberPoints=0;
        accumulateDistance=0;
    }
}
