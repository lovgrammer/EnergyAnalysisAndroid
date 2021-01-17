package kr.ac.snu.energyanalysisapp;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class StatusDataCollector {

    private static final String TAG = "StatusDataCollector";
    public static String ex_id = "";

    public static boolean running = true;

    public static void clear() {
        deleteFile("/sdcard/Download/framedrop.csv");
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        boolean deleted = file.delete();
    }
    
    public static void saveEnergy(Context context, int voltage, int current) {
        String content = "" + System.currentTimeMillis() + "," + voltage + "," + current + "\n";
        writeToFile(context, content, "energy.csv", "time,voltage,current\n");
    }

    private static void writeToFile(Context context, String content, String fileName, String firstLine) {
        if (!running)
            return;

        FileWriter fileWriter = null;
        String path = Environment.getExternalStorageDirectory().getPath();
        boolean notFound = false;

        File file = new File(path + "/" + fileName);
        if (!file.exists()) {
            Log.i("StatusDataCollector", "Not found");
            notFound = true;
        }
        try {
            fileWriter = new FileWriter(file, true);
            if (notFound)
                fileWriter.append(firstLine);
            fileWriter.append(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
