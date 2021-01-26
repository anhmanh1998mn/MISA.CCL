package vn.com.misa.ccl.model;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ‐ Mục đích Class thực hiện việc xử lý đăng nhập với facebook
 * <p>
 * ‐ {@link vn.com.misa.ccl.presenter.SignInPresenter#onSelectionListenerWithFacebook}
 * <p>
 * ‐ @created_by cvmanh on 01/09/2021
 */

public class SignInModel extends FragmentActivity {

    private ISignInResultLogin mSignInResulLogin;

    public SignInModel(ISignInResultLogin mSignInResulLogin) {
        this.mSignInResulLogin = mSignInResulLogin;
    }

    /**
     * Mục đích method thực hiện việc đăng nhập facebook, yêu cầu lấy tên gmail và trả về kết quả
     *
     * @param mContext         class hiện tại
     * @param mCallbackManager Phản hồi việc đăng nhập
     * @created_by cvmanh on 01/10/2021
     */
    public void signInWithFacebook(Activity mContext, CallbackManager mCallbackManager) {
        try {
            LoginManager.getInstance().registerCallback(mCallbackManager,// đăng ký gọi lại, phản hồi kết quả đăng nhập
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    try {
                                        mSignInResulLogin.onSignInFacebookSuccess(object.getString("email"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            Bundle parameters = new Bundle(); // gửi dữ liệu lên server
                            parameters.putString("fields", "email");
                            graphRequest.setParameters(parameters);
                            graphRequest.executeAsync();

                        }

                        @Override
                        public void onCancel() {
                            mSignInResulLogin.onSignInFail();
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            mSignInResulLogin.onSignInFail();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ISignInResultLogin {

        public void onSignInFacebookSuccess(String mailName);

        public void onSignInFail();
    }
}
