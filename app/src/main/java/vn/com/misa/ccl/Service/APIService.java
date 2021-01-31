package vn.com.misa.ccl.Service;

import android.app.Activity;

/**
 * ‐ Mục đích Class thực hiện việc kết nối retrofit với server
 * <p>
 * ‐ {@link APIRetrofitClient}
 * ‐ {@link IDataService}
 * <p>
 * ‐ @created_by cvmanh on 01/31/2021
 */

public class APIService {

    private static String baseURL = "http://192.168.1.188:8080/MISATestUser/";

    /**
     * Mục đích method thực hiện việc kết nối retrofit với server
     *
     * @return trả về kết nối thành công
     * @created_by cvmanh on 01/31/2021
     */
    public static IDataService getService() {
        return APIRetrofitClient.getClient(baseURL).create(IDataService.class);
    }
}
