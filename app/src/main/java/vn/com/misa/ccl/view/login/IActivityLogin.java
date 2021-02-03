package vn.com.misa.ccl.view.login;

import android.app.Activity;

import java.security.NoSuchAlgorithmException;

public interface IActivityLogin {

    public interface IActivityLoginPresenter {
        public void doLoginApp(String username, String password) throws NoSuchAlgorithmException;

        public void checkSyncData(int shopID, Activity activity);

    }

    public interface IActivityLoginView {
        public void loginSuccess(int shopID);

        public void onFailed();
    }
}
