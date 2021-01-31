package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.security.NoSuchAlgorithmException;

import vn.com.misa.ccl.model.ActivityAccountRegisterModel;
import vn.com.misa.ccl.view.login.IActivityAccountRegister;

/**
 * ‐ Mục đích Class thực hiện việc lauan chuyển dữ liệu giữa ActivityAccountPresenter và ActivityAccount
 * <p>
 * ‐ {@link ActivityAccountRegisterModel
 * ‐ {@link vn.com.misa.ccl.view.login.ActivityAccountRegister}
 * <p>
 * ‐ @created_by cvmanh on 01/31/2021
 */

public class ActivityAccountPresenter implements IActivityAccountRegister.IActivityAccountRegisterPresenter,
        ActivityAccountRegisterModel.IActivityRegisterModel {
    private IActivityAccountRegister.IActivityAccountRegisterView mIActivityAccountRegisterView;

    public ActivityAccountPresenter(IActivityAccountRegister.IActivityAccountRegisterView mIActivityAccountRegisterView) {
        this.mIActivityAccountRegisterView = mIActivityAccountRegisterView;
    }

    private ActivityAccountRegisterModel mActivityAccountRegisterModel = new ActivityAccountRegisterModel(this);

    /**
     * Mục đích method thực hiện việc gọi model xử lý đăng ký tài khoản
     *
     * @param userName tên tài khoản
     * @param passWord tên mật khẩu
     * @created_by cvmanh on 01/31/2021
     */
    @Override
    public void doRegisterAccount(String userName, String passWord) throws NoSuchAlgorithmException {
        mActivityAccountRegisterModel.doRegisterAccount(userName, passWord);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả đăng ký tài khoản thành công và trả kết quả về view
     *
     * @created_by cvmanh on 01/31/2021
     */
    @Override
    public void registerAccountSuccess() {
        mIActivityAccountRegisterView.registerAccountSuccess();
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả đăng ký tài khoản thất bại và trả kết quả về view
     *
     * @created_by cvmanh on 01/31/2021
     */
    @Override
    public void onFailed() {
        mIActivityAccountRegisterView.onFailed();
    }
}
