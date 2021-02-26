package vn.com.misa.ccl.service;

/**
 * ‐ Mục đích Class thực hiện việc kết nối retrofit với server
 * <p>
 * ‐ {@link APIRetrofitClient}
 * ‐ {@link IDataService}
 * <p>
 * ‐ @created_by cvmanh on 01/31/2021
 */

public class APIService {

    //    private static String sBaseURL = "http://192.168.1.188:8080/MISATestUser/";
//    private static String sBaseURL = "http://192.168.1.102:8080/MISATestUser/";
    private static String sBaseURL = "http://192.168.43.91:8080/MISATestUser/";

    /**
     * Mục đích method thực hiện việc kết nối retrofit với server
     *
     * @return trả về kết nối thành công
     * @created_by cvmanh on 01/31/2021
     */
    public static IDataService getService() {
        return APIRetrofitClient.getClient(sBaseURL).create(IDataService.class);
    }
}
