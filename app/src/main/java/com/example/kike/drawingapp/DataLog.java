package com.example.kike.drawingapp;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;

/**
 * Created by Kike on 10/10/2016.
 */
public class DataLog {
    FileOutputStream outputStream;
    String filename;

    public DataLog(String name){
        filename=name;
        //File file = new File(context.getFilesDir(), filename);
    }

    public void writeData(String data){
        try {
         //   outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setFilename(String name){
        filename=name;
    }
}
