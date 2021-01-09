package vn.com.misa.ccl.Model;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;
/**
‐ Mục đích Class thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
*
‐ {@link Activity#onResume}
‐ {@link onResume}
*
‐ @created_by cvmanh on 01/09/2021
*/
public class SignInModel extends FragmentActivity {

    private ISignInResultLogin mSignInResulLogin;

    public SignInModel(ISignInResultLogin mSignInResulLogin) {
        this.mSignInResulLogin = mSignInResulLogin;
    }

    public void signInWithFacebook(Activity mContext,CallbackManager mCallbackManager){
//        mCallbackManager = CallbackManager.Factory.create();// xử lý các phản hồi đăng nhập

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
    }

    public interface ISignInResultLogin{

        public void onSignInFacebookSuccess(String mailName);

        public void onSignInFail();
    }
}
