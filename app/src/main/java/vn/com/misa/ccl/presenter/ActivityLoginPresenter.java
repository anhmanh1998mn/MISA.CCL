package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.security.NoSuchAlgorithmException;

import vn.com.misa.ccl.model.ActivityLoginModel;
import vn.com.misa.ccl.view.login.IActivityLogin;

/**
 * ‐ Mục đích Class thực hiện việc luân chuyển dữ liệu giữa ActivityLogin và ActivityLoginModel
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.login.ActivityLogin}
 * ‐ {@link ActivityLoginModel}
 * <p>
 * ‐ @created_by cvmanh on 01/31/2021
 */

public class ActivityLoginPresenter implements IActivityLogin.IActivityLoginPresenter, ActivityLoginModel.IActivityLoginModel {

    private IActivityLogin.IActivityLoginView mIActivityLoginView;

    public ActivityLoginPresenter(IActivityLogin.IActivityLoginView mIActivityLoginView) {
        this.mIActivityLoginView = mIActivityLoginView;
    }

    private ActivityLoginModel mActivityLoginModel = new ActivityLoginModel(this);

    /**
     * Mục đích method thực hiện việc gọi model xử lý đăng nhập vào ứng dụng
     *
     * @param username tên tài khoản
     * @param password tên mật khẩu
     * @created_by cvmanh on 01/31/2021
     */
    @Override
    public void doLoginApp(String username, String password) throws NoSuchAlgorithmException {
        mActivityLoginModel.doLoginApp(username, password);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả đăng nhập thành công và trả kết quả về view
     *
     * @created_by cvmanh on 01/31/2021
     */
    @Override
    public void loginSuccess(int shopID) {
        mIActivityLoginView.loginSuccess(shopID);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả đăng nhập thất bại và trả kết quả về view
     *
     * @created_by cvmanh on 01/31/2021
     */
    @Override
    public void onFailed() {
        mIActivityLoginView.onFailed();
    }
}
