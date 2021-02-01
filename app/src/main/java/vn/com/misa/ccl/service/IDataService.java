package vn.com.misa.ccl.service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * ‐ Mục đích Class thực hiện việc định nghĩa các phương thức thao tác với server
 * <p>
 * ‐ {@link APIRetrofitClient}
 * ‐ {@link APIService}
 * <p>
 * ‐ @created_by cvmanh on 01/31/2021
 */

public interface IDataService {

    /**
     * Mục đích method thực hiện việc gọi api kiểm tra việc tồn tại tài khoản đã đăng ký
     *
     * @param userName tên tài khoản
     * @created_by cvmanh on 01/31/2021
     */
    @FormUrlEncoded
    @POST("checkRegister.php")
    Call<String> checkRegisterUser(@Field("UserName") String userName);

    /**
     * Mục đích method thực hiện việc gọi api đăng ký tài khoản
     *
     * @param userName tên tài khoản
     * @param password tên mật khẩu
     * @created_by cvmanh on 01/31/2021
     */
    @FormUrlEncoded
    @POST("doRegister.php")
    Call<String> doRegisterUser(@Field("UserName") String userName, @Field("Password") String password);

    /**
     * Mục đích method thực hiện việc gọi api đăng nhập vào ứng dụng
     *
     * @param userName tên tài khoản
     * @param password tên mật khẩu
     * @created_by cvmanh on 01/31/2021
     */
    @FormUrlEncoded
    @POST("loginApp.php")
    Call<String> doLoginApp(@Field("UserName") String userName, @Field("Password") String password);
}
