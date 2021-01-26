package vn.com.misa.ccl.presenter;

import android.app.Activity;

import com.facebook.CallbackManager;

import vn.com.misa.ccl.model.SignInModel;
import vn.com.misa.ccl.view.login.ActivitySelectionOptionLogin;
import vn.com.misa.ccl.view.login.ISignInView;

/**
 * ‐ Mục đích Class thực hiện việc giao tiếp giữa SignInModel và ActivitySelectionOptionLogin
 * <p>
 * ‐ {@link SignInModel}
 * ‐ {@link ActivitySelectionOptionLogin#setLoginFacebook}
 * <p>
 * ‐ @created_by cvmanh on 01/09/2021
 */

public class SignInPresenter implements ISignInView.IViewClickListener, SignInModel.ISignInResultLogin {

    private SignInModel mSignInModel = new SignInModel(this);

    private ISignInView.IResultLogin mResulLogin;

    public SignInPresenter(ISignInView.IResultLogin mResulLogin) {
        this.mResulLogin = mResulLogin;
    }

    /**
     * Mục đích method thực hiện việc gọi đến phần xử lý signin facebook
     *
     * @param mContext         instance activity hiện tại
     * @param mCallbackManager các phản hồi đăng nhập
     * @created_by cvmanh on 01/11/2021
     */
    @Override
    public void onSelectionListenerWithFacebook(Activity mContext, CallbackManager mCallbackManager) {
        mSignInModel.signInWithFacebook(mContext, mCallbackManager);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả đăng nhập thành công và gửi tới view
     *
     * @param mailName Tên gmail đăng nhập thành công
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onSignInFacebookSuccess(String mailName) {
        mResulLogin.signInFacebookSuccessfull(mailName);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả thất bại khi đăng nhập và gửi về view
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onSignInFail() {
        mResulLogin.signInFail();
    }
}
