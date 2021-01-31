package vn.com.misa.ccl.view.login;

import java.security.NoSuchAlgorithmException;

public interface IActivityLogin {

    public interface IActivityLoginPresenter{
        public void doLoginApp(String username,String password) throws NoSuchAlgorithmException;
    }

    public interface IActivityLoginView{
        public void loginSuccess();

        public void onFailed();
    }
}
