package com.example.kike.drawingapp;

import android.app.Activity;
import android.os.Bundle;
import java.util.UUID;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    private float circle, triangle, square;
    private ImageButton currPaint, drawBtn, accBtn;
    private DrawingView drawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = (DrawingView)findViewById(R.id.drawing);
        drawBtn = (ImageButton)findViewById(R.id.draw_btn);
        drawBtn.setOnClickListener(this);

        accBtn = (ImageButton)findViewById(R.id.accuracy_btn);
        accBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view.getId()==R.id.draw_btn){ // click draw button
            //draw button clicked from shape_chooser.xml
            final Dialog shapeDialog = new Dialog(this);
            shapeDialog.setTitle("Shape:");
            shapeDialog.setContentView(R.layout.shape_chooser);
            // Shape button behaviour
            ImageButton circleBtn = (ImageButton)shapeDialog.findViewById(R.id.circle_shape);
            circleBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.drawCircle();
                    shapeDialog.dismiss();
                }
            });
            ImageButton triangleBtn = (ImageButton)shapeDialog.findViewById(R.id.triangle_shape);
            triangleBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.drawTriangle();
                    shapeDialog.dismiss();
                }
            });
            ImageButton squareBtn = (ImageButton)shapeDialog.findViewById(R.id.square_shape);
            squareBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.drawSquare();
                    shapeDialog.dismiss();
                }
            });
            /*ImageButton starBtn = (ImageButton)shapeDialog.findViewById(R.id.star_shape); //choose the button
            starBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.drawSquare(); // call the drawing
                    shapeDialog.dismiss();
                }
            });*/
            shapeDialog.show();
        }
        else if(view.getId()==R.id.accuracy_btn){ //click accuracy button
            //switch to accuracy
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Accuracy");
            alertDialog.setMessage("Accuracy: " + drawView.getAccuracy()); // accuracy message
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
}
