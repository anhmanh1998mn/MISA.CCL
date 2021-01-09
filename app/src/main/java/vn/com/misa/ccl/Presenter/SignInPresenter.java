package vn.com.misa.ccl.Presenter;

import android.app.Activity;

import com.facebook.CallbackManager;
import com.google.android.gms.common.api.GoogleApiClient;

import vn.com.misa.ccl.Model.SignInModel;
import vn.com.misa.ccl.View.login.ISignInView;
/**
‐ Mục đích Class thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
*
‐ {@link Activity#onResume}
‐ {@link onResume}
*
‐ @created_by cvmanh on 01/09/2021
*/
public class SignInPresenter implements ISignInView.IViewClickListener, SignInModel.ISignInResultLogin {

    private SignInModel mSignInModel=new SignInModel(this);

    private ISignInView.IResultLogin mResulLogin;

    public SignInPresenter(ISignInView.IResultLogin mResulLogin) {
        this.mResulLogin = mResulLogin;
    }

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
