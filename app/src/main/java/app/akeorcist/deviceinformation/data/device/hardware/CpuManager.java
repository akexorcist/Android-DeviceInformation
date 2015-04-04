package app.akeorcist.deviceinformation.data.device.hardware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class CpuManager {
    public static String getCpuInfo() {
        try {
            Process process = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStream is = process.getInputStream();
            return getStringFromInputStream(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    private static String getStringFromInputStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        try {
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
