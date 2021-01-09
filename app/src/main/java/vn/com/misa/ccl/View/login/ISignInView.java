package vn.com.misa.ccl.View.login;

import android.app.Activity;

import com.facebook.CallbackManager;
import com.google.android.gms.common.api.GoogleApiClient;

public interface ISignInView {

    public interface IViewClickListener{
        public void onSelectionListenerWithFacebook(Activity mContext, CallbackManager mCallbackManager);

    }

    public interface IResultLogin{

        public void signInFail();

        public void signInFacebookSuccessfull(String mailName);

    }
}
