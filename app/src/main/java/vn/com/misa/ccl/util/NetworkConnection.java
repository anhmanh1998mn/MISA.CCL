package vn.com.misa.ccl.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ‐ Mục đích Class thực hiện việc xây dựng các hàm xử lý chung
 * <p>
 * ‐ @created_by cvmanh on 02/02/2021
 */

public class NetworkConnection {

    /**
     * Mục đích method thực hiện việc kiểm tra kết nối mạng
     *
     * @param context context
     * @return trả về kiểu boolean: true: có kết nối mạng, False: không có kết nối mạng
     * @created_by cvmanh on 02/02/2021
     */
    public static boolean checkNetworkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
