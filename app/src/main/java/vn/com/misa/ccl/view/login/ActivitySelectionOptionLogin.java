package vn.com.misa.ccl.view.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;

import vn.com.misa.ccl.presenter.SignInPresenter;
import vn.com.misa.ccl.R;

/**
‐ Mục đích Class thực hiện show các lựa chọn đăng nhập vào ứng dụng
*
‐ {@link SignInPresenter}
*
‐ @created_by cvmanh on 01/11/2021
*/

public class ActivitySelectionOptionLogin extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener ,ISignInView.IResultLogin{

    private ImageView ivBack;

    private TextView tvRegisterAccount;

    private Button btnLoginNext;

    private CallbackManager mCallbackManager;

    private GoogleApiClient mGoogleApiClient;

    private int mSignIn =001;

    private SignInPresenter mSignInPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_option_login);

        initView();

        onCLickViewListener();

        setLoginFacebook();

        setLoginGoogle();

    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/11/2021
     */
    private void initView() {
        try {
            ivBack = findViewById(R.id.ivBack);
            tvRegisterAccount = findViewById(R.id.tvRegisterAccount);
            btnLoginNext = findViewById(R.id.btnLoginNext);
//        btnLoginFacebook=findViewById(R.id.btnLoginFacebook);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lắng nghe các sự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/11/2021
     */
    private void onCLickViewListener() {
        try {
            ivBack.setOnClickListener(this);
            tvRegisterAccount.setOnClickListener(this);
            btnLoginNext.setOnClickListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý các công việc khi người dúng click
     *
     * @param view View được click
     *
     * @created_by cvmanh on 01/11/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }
                case R.id.tvRegisterAccount: {
                    startActivity(new Intent(this, ActivityAccountRegister.class));
                    break;
                }
                case R.id.btnLoginNext: {
                    startActivity(new Intent(this, ActivityLogin.class));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc trả về kết quả callback
     *
     * @param requestCode giá trị trả về
     * @param  resultCode kết quả trả về đúng
     * @param data dữ liệu
     *
     * @created_by cvmanh on 01/11/2021
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

            if(requestCode== mSignIn){
                GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích:
     *
     * @created_by cvmanh on 01/07/2021
     */
    private void setLoginFacebook() {
        try {
            mSignInPresenter=new SignInPresenter(this);
            mCallbackManager = CallbackManager.Factory.create();// xử lý các phản hồi đăng nhập
            mSignInPresenter.onSelectionListenerWithFacebook(this,mCallbackManager);
            Button btnLoginFacebook =findViewById(R.id.btnLoginFacebook);
            btnLoginFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LoginManager.getInstance().logInWithReadPermissions(ActivitySelectionOptionLogin.this, Arrays.asList("public_profile", "email")); // xin quyền lấy email
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc đăng nhập với google
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void setLoginGoogle(){
        try {
            //Yêu cầu người dùng cung cấp thông tin cơ bản: email, tên, hình ảnh
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            //Kết nối với google api client
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this,this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                    .build();
            SignInButton btnLoginGoogle = findViewById(R.id.btnLoginGoogle);
            btnLoginGoogle.setSize(SignInButton.SIZE_STANDARD);
            btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Mục đích method thực hiện việc đăng nhập với google
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void signIn() {
        try {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, mSignIn);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * Mục đích: Nếu đăng nhập thành công thì lấy tên email
     *
     * @created_by cvmanh on 01/08/2021
     */
    private void handleSignInResult(GoogleSignInResult result){
        try {
            if(result.isSuccess()){
                GoogleSignInAccount accountProfile=result.getSignInAccount();
                Toast.makeText(this, accountProfile.getEmail().toString(), Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả khi đăng nhập thất bại
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void signInFail() {
        Toast.makeText(this, "Sign-in Failed", Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method thực hiện việc nhận và xử lý dữ liệu khi đăng nhập thành
     *
     * @param mailName Tên gmail đăng nhập thành công
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void signInFacebookSuccessfull(String mailName) {
        Toast.makeText(this, mailName, Toast.LENGTH_SHORT).show();
    }
}