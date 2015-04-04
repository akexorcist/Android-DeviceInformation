package app.akeorcist.deviceinformation.data.device.hardware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */
public class MemoryManager {
    public static String getHeapGrowthLimit() {
        try {
            Process proc = Runtime.getRuntime().exec("getprop dalvik.vm.heapgrowthlimit");
            InputStream is = proc.getInputStream();
            String size = getStringFromInputStream(is);
            if(!size.equals("\n"))
                return size.trim();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    public static String getHeapSize() {
        try {
            Process proc = Runtime.getRuntime().exec("getprop dalvik.vm.heapsize");
            InputStream is = proc.getInputStream();
            String size = getStringFromInputStream(is);
            if(!size.equals("\n"))
                return size.trim();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    public static String getHeapStartSize() {
        try {
            Process proc = Runtime.getRuntime().exec("getprop dalvik.vm.heapstartsize");
            InputStream is = proc.getInputStream();
            String size = getStringFromInputStream(is);
            if(!size.equals("\n"))
                return size.trim();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    public static String getMemory() {
        String memory = getTotalMemory().replace("MemTotal:", "").replace(" ", "").replace("kB", "");
        if(!memory.equals("Unknown")) {
            float mem = Float.parseFloat(memory) / 1000f;
            return String.format("%.3f", mem) + " MB";
        } else {
            return "Unknown";
        }
    }

    public static String getTotalMemory() {
        try {
            Process proc = Runtime.getRuntime().exec("cat /proc/meminfo");
            InputStream is = proc.getInputStream();
            String[] listMemory = getStringFromInputStream(is).split("\n");
            for(int i = 0 ; i < listMemory.length ; i++) {
                if(listMemory[i].contains("MemTotal"))
                    return listMemory[i];
            }
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
