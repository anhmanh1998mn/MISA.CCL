package vn.com.misa.ccl.view.login;

import android.app.Activity;

import com.facebook.CallbackManager;

public interface ISignInView {

    public interface IViewClickListener{
        public void onSelectionListenerWithFacebook(Activity mContext, CallbackManager mCallbackManager);

    }

    public interface IResultLogin{

        public void signInFail();

        public void signInFacebookSuccessfull(String mailName);

    }
}
