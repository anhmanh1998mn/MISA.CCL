package vn.com.misa.ccl.view.login;

import java.security.NoSuchAlgorithmException;

public interface IActivityAccountRegister {

    public interface IActivityAccountRegisterPresenter {
        public void doRegisterAccount(String userName, String passWord) throws NoSuchAlgorithmException;
    }

    public interface IActivityAccountRegisterView {

        public void registerAccountSuccess();

        public void onFailed();
    }
}
