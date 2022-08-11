package com.example.zakahdeserved.Connection;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.zakahdeserved.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileAccessLayer {

    public static String ALL_SPINNER_FILE = "Zakah_Spinner.txt";
    public static String QUEIES_FILE = "Zakah_Queries.txt";

    public static MainActivity mainActivity;

    public static void addSpinnerToFile(String spinnerName, List<String> items) {
        File root = new File(mainActivity.getExternalCacheDir(), "ZAKAH");

        File gpxfile = null;
        FileWriter writer = null;

        try {

            if (!root.exists())
                root.mkdirs();

            gpxfile = new File(root, ALL_SPINNER_FILE);
            writer = new FileWriter(gpxfile, true);

            writer.append("\n#").append(spinnerName).append("\n");
            for (String item : items)
                writer.append(item).append("\n");

            writer.append("$$$$$\n");

            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean fillSpinner(Context context, Spinner spinner) {
        boolean success = false;

        File root = new File(mainActivity.getExternalCacheDir() + File.separator + "ZAKAH", ALL_SPINNER_FILE);
        if (!root.exists()) {
            Log.d("ZakahException", "File Zakah.txt not found");
        }
        else {
            FileInputStream inputStream = null;
            BufferedReader reader = null;
            try {
                inputStream = new FileInputStream(root);
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                String spinnerName = spinner.getResources().getResourceEntryName(spinner.getId());
                if (spinnerName.contains("1"))
                    spinnerName = spinnerName.substring(0, spinnerName.indexOf("1"));

                boolean isReachedSpinner = false;
                List<String> items = new ArrayList<>();

                while ((line = reader.readLine()) != null) {
                    if (isReachedSpinner) {

                        //إذا وصل إلى نهاية القائمة الخاصة بال Spinner
                        if (line.contains("$$$$$"))
                            break;
                        items.add(line);
                    } else if (line.trim().equals("#" + spinnerName))
                        isReachedSpinner = true;
                }

                //إذا لم يتم إيجاد هذه القائمة في الملف
                if (!isReachedSpinner)
                    Log.d("ZakahException", spinnerName + " did't have data in the Zakah.txt file");

                else {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            context, android.R.layout.simple_spinner_item, items);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    success = true;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null)
                        reader.close();
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

    public static void clearSpinnersFile() {
        File root = new File(mainActivity.getExternalCacheDir() + File.separator + "ZAKAH");
        File gpxfile = null;
        FileWriter writer = null;

        try {

            if (!root.exists())
                root.mkdirs();

            gpxfile = new File(root, ALL_SPINNER_FILE);
            writer = new FileWriter(gpxfile, false);
            writer.write("");
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean addQuery(String query) {

        boolean success = false;
        File root = new File(mainActivity.getExternalCacheDir() + File.separator + "ZAKAH");

        File gpxfile = null;
        FileWriter writer = null;
        try {

            if (!root.exists()) {
                root.mkdirs();
            }
            gpxfile = new File(root, QUEIES_FILE);
            writer = new FileWriter(gpxfile, true);
            writer.append(query).append("\n");
            writer.flush();
            writer.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public static String getAllQueries() {
        File root = new File(mainActivity.getExternalCacheDir() + File.separator + "ZAKAH", QUEIES_FILE);

        if (!root.exists()) {
            Log.d("ZakahException", "File Zakah.txt not found");
            return "";
        }

        FileInputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = new FileInputStream(root);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null)
                stringBuilder.append(line).append("\n");

            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static void clearQueriesFile() {
        File root = new File(mainActivity.getExternalCacheDir() + File.separator + "ZAKAH");
        File gpxfile = null;
        FileWriter writer = null;

        try {

            if (!root.exists())
                root.mkdirs();

            gpxfile = new File(root, QUEIES_FILE);
            writer = new FileWriter(gpxfile, false);
            writer.write("");
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
