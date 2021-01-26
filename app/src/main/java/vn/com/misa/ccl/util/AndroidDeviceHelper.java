package vn.com.misa.ccl.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * ‐ Mục đích Class thực hiện việc lấy kích thước màn hình thiết bị
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.login.ActivityLogin#showDialogForgotPassword}
 * <p>
 * ‐ @created_by cvmanh on 01/07/2021
 */

public class AndroidDeviceHelper {

    /**
     * Mục đích method thực hiện việc lấy kích thước chiều rộng màn hình thiết bị
     *
     * @param context instance của activity hiện tại
     * @return trả về kích thước chiều rộng màn hình thiết bị
     * @created_by cvmanh on 01/11/2021
     */
    public static int getWitdhScreen(Context context) {
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            return point.x;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Mục đích method thực hiện việc lấy kích thước chiều dài màn hình thiết bị
     *
     * @param context instance của activity hiện tại
     * @return trả về kích thước chiều dài màn hình thiết bị
     * @created_by cvmanh on 01/11/2021
     */
    public static int getHeightScreen(Context context) {
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            return point.y;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
