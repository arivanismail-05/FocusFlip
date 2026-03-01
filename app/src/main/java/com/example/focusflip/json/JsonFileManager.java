package com.example.focusflip.json;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

// Reads and writes the raw JSON file only
public class JsonFileManager {
    private static final String FILE_NAME = "focusflip_history.json";

    // Write JSON string to file
    public static void writeToFile(Context context, String jsonContent) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(jsonContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read JSON string from file
    public static String readFromFile(Context context) {
        try (FileInputStream fis = context.openFileInput(FILE_NAME)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            return new String(buffer);
        } catch (IOException e) {
            return "[]";
        }
    }
}
