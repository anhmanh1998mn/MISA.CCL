package vn.com.misa.ccl.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
‐ Mục đích Class thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
*
‐ {@link Activity#onResume}
‐ {@link onResume}
*
‐ @created_by cvmanh on 01/07/2021
*/
public class AndroidDeviceHelper {

    public static int getWitdhScreen(Context context){
        try {
            WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display=windowManager.getDefaultDisplay();
            Point point=new Point();
            display.getSize(point);
            return point.x;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static int getHeightScreen(Context context){
        try {
            WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display=windowManager.getDefaultDisplay();
            Point point=new Point();
            display.getSize(point);
            return point.y;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
