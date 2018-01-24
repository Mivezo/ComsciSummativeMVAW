package com.comsci.michelaustin.comscisummative;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by Evelina Vezarov on 2017-11-14.
 */

public class FileIO {

    /**
     * This method writes a string to a txt file. If there was no previous txt file,
     * it will be created under the fileName. Any other text in the file will be overwritten.
     * @param data Data to be written in txt file
     * @param fileName Name of the txt file
     * @param context Application Context
     */
    public static void writeFile(String data, String fileName,Context context) {

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));

            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    /**
     * This method reads a line from a specific file.
     * @param context ApplicationContext
     * @param name Name of the txt File
     * @return line from file
     */
    public static String readFromFile(Context context, String name) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(name);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return null;
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            return null;
        }

        return ret;
    }

    /**
     * This method writes a string to a txt file.
     * Instead of overriding the text, it appends the string on to the existing
     * @param data Data to be written in txt file
     * @param fileName Name of the txt file
     * @param context Application Context
     */
    public static void appendLineFile(String data, String fileName,Context context) {

        try {
            String separator = System.getProperty("line.separator");

            PrintWriter outputStreamWriter = new PrintWriter(context.openFileOutput(fileName, Context.MODE_APPEND));

            outputStreamWriter.append(separator);//appends line
            outputStreamWriter.write(data);
            outputStreamWriter.flush();
            outputStreamWriter.close();

        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }




}