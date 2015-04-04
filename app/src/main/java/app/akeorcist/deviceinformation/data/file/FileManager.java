package app.akeorcist.deviceinformation.data.file;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import app.akeorcist.deviceinformation.utility.StringUtils;

/**
 * Created by Akexorcist on 3/9/15 AD.
 */
public class FileManager {

    public static boolean writeInternalFile(Context context, String fileName, String data) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String readInternalFile(Context context, String fileName) {
        try {
            InputStreamReader isr = new InputStreamReader(context.openFileInput(fileName));
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static boolean isDataCached(Context context, String fingerprint) {
        String path = context.getFilesDir() + "/" +  StringUtils.wrapUrl(fingerprint) + ".json";
        File file = new File(path);
        return file.exists();
    }

}
