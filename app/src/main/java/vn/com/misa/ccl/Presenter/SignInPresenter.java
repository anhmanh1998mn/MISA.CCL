package vn.com.misa.ccl.Presenter;

import android.app.Activity;

import com.facebook.CallbackManager;
import com.google.android.gms.common.api.GoogleApiClient;

import vn.com.misa.ccl.Model.SignInModel;
import vn.com.misa.ccl.View.login.ActivitySelectionOptionLogin;
import vn.com.misa.ccl.View.login.ISignInView;

/**
‐ Mục đích Class thực hiện việc giao tiếp giữa SignInModel và ActivitySelectionOptionLogin
*
‐ {@link SignInModel}
‐ {@link ActivitySelectionOptionLogin#setLoginFacebook}
*
‐ @created_by cvmanh on 01/09/2021
*/

public class SignInPresenter implements ISignInView.IViewClickListener, SignInModel.ISignInResultLogin {

    private SignInModel mSignInModel=new SignInModel(this);

    private ISignInView.IResultLogin mResulLogin;

    public SignInPresenter(ISignInView.IResultLogin mResulLogin) {
        this.mResulLogin = mResulLogin;
    }

    /**
     * Mục đích method thực hiện việc gọi đến phần xử lý signin facebook
     *
     * @param mContext instance activity hiện tại
     * @param mCallbackManager các phản hồi đăng nhập
     *
     * @created_by cvmanh on 01/11/2021
     */
    @Override
    public void onSelectionListenerWithFacebook(Activity mContext, CallbackManager mCallbackManager) {
        mSignInModel.signInWithFacebook(mContext,mCallbackManager);
    }

    @Override
    public void onSignInFacebookSuccess(String mailName) {
        mResulLogin.signInFacebookSuccessfull(mailName);
    }

    @Override
    public void onSignInFail() {
        mResulLogin.signInFail();
    }
}
