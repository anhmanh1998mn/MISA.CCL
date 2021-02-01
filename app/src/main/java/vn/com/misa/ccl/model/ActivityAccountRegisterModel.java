package vn.com.misa.ccl.model;

import android.app.Activity;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.misa.ccl.Service.APIService;
import vn.com.misa.ccl.Service.IDataService;

/**
 * ‐ Mục đích Class thực hiện việc thực hiện các cộng việc được nhận yêu cầu từ ActivityAccountRegisterPresenter
 * <p>
 * ‐ {@link vn.com.misa.ccl.presenter.ActivityAccountPresenter}
 * ‐ {@link APIService}
 * <p>
 * ‐ @created_by cvmanh on 01/31/2021
 */

public class ActivityAccountRegisterModel {

    private IActivityRegisterModel mIActivityRegisterModel;

    private int MAX_LENGHT_TEXT = 6;

    public ActivityAccountRegisterModel(IActivityRegisterModel mIActivityRegisterModel) {
        this.mIActivityRegisterModel = mIActivityRegisterModel;
    }

    /**
     * Mục đích method thực hiện việc việc kiểm tra xem tài khoản dã được đăng ký chưa và
     * xử lý đăng ký tài khoản đăng nhập
     *
     * @param userName tên tài khoản
     * @param passWord tên mật khẩu
     * @created_by cvmanh on 01/31/2021
     */
    public void doRegisterAccount(String userName, String passWord) {
        try {
            if (userName.equals("") || passWord.equals("") || userName.length() < MAX_LENGHT_TEXT || passWord.length() < MAX_LENGHT_TEXT) {
                mIActivityRegisterModel.onFailed();
                return;
            }
            IDataService dataService = APIService.getService();
            Call<String> callback = dataService.checkRegisterUser(userName);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body().toString().trim().equals("exist")) {
                        mIActivityRegisterModel.onFailed();
                        return;
                    }
                    try {
                        registerAccount(userName, passWord);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Mục đích method thực hiện việc đăng ký tài khoản nếu tài khoản chưa được đăng ký
     *
     * @param userName tên tài khoản
     * @param passWord tên mật khẩu
     * @created_by cvmanh on 01/31/2021
     */
    private void registerAccount(String userName, String passWord) throws NoSuchAlgorithmException {
        try {
            IDataService dataService = APIService.getService();
            Call<String> callback = dataService.doRegisterUser(userName, encodeMD5(passWord));
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body().toString().trim().equals("Success")) {
                        mIActivityRegisterModel.registerAccountSuccess();
                        return;
                    }
                    mIActivityRegisterModel.onFailed();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("Err", t.toString());
                    mIActivityRegisterModel.onFailed();
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
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            BigInteger bigInteger = new BigInteger(1, messageDigest.digest());
            result = bigInteger.toString(16);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public interface IActivityRegisterModel {
        public void registerAccountSuccess();

        public void onFailed();
    }
}
