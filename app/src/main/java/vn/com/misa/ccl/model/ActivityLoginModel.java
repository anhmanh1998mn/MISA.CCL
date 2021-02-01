package vn.com.misa.ccl.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.misa.ccl.service.APIService;
import vn.com.misa.ccl.service.IDataService;

/**
 * ‐ Mục đích Class thực hiện việc xử lý các yêu cầu từ ActivityLoginPresenter truyền sang
 * <p>
 * ‐ {@link vn.com.misa.ccl.presenter.ActivityLoginPresenter}
 * ‐ {@link APIService}
 * <p>
 * ‐ @created_by cvmanh on 01/31/2021
 */

public class ActivityLoginModel {

    private IActivityLoginModel mIActivityLoginModel;

    public ActivityLoginModel(IActivityLoginModel mIActivityLoginModel) {
        this.mIActivityLoginModel = mIActivityLoginModel;
    }

    /**
     * Mục đích method thực hiện việc xử lý đăng nhập vào ứng dụng
     *
     * @param username tên tài khoản
     * @param password tên mật khẩu
     * @created_by cvmanh on 01/31/2021
     */
    public void doLoginApp(String username, String password) throws NoSuchAlgorithmException {
        try {
            if (username.equals("") || password.equals("")) {
                mIActivityLoginModel.onFailed();
                return;
            }
            IDataService dataService = APIService.getService();
            Call<String> callback = dataService.doLoginApp(username, encodeMD5(password));
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body().toString().trim().equals("Success")) {
                        mIActivityLoginModel.loginSuccess();
                        return;
                    }
                    mIActivityLoginModel.onFailed();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    mIActivityLoginModel.onFailed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc mã hóa MD5 mật khẩu
     *
     * @param password mật khẩu
     * @return trả về mật khẩu đã được mã hóa
     * @created_by cvmanh on 01/31/2021
     */
    private String encodeMD5(String password) throws NoSuchAlgorithmException {
        try {
            String result = "";
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            BigInteger bigInteger = new BigInteger(1, md.digest());
            result = bigInteger.toString(16);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public interface IActivityLoginModel {
        public void loginSuccess();

        public void onFailed();
    }
}
