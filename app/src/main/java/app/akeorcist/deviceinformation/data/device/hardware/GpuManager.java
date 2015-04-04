package app.akeorcist.deviceinformation.data.device.hardware;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Akexorcist on 2/27/15 AD.
 */

public class GpuManager {
    public static class Renderer implements GLSurfaceView.Renderer {
        SurfaceListener listener;

        public Renderer(SurfaceListener listener) {
            this.listener = listener;
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            if (listener != null)
                listener.onSurfaceCreated(gl, config);
        }

        public void onSurfaceChanged(GL10 gl, int w, int h) {
        }

        public void onDrawFrame(GL10 gl) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        }

        public String dividing(String str) {
            if (str.charAt(str.length() - 1) == '\n'
                    || str.charAt(str.length() - 1) == ' ') {
                str = str.substring(0, str.length() - 1);
            }
            String s = str.replace(" ", "\n• ");
            return s;
        }

        public interface SurfaceListener {
            public void onSurfaceCreated(GL10 gl, EGLConfig config);
        }
    }

    public static float getOpenGLVersion(Activity activity) {
        ActivityManager activityManager = (ActivityManager)activity.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        int version = configurationInfo.reqGlEsVersion;
        return getMajorVersion(version) + getMinorVersion(version);
    }

    private static int getMajorVersion(int glEsVersion) {
        return ((glEsVersion & 0xffff0000) >> 16);
    }

    private static float getMinorVersion(int glEsVersion) {
        return (float)(Integer.parseInt(Integer.toHexString(glEsVersion).replace("0x", "")) % 10) / 10;
    }
}