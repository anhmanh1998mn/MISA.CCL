package vn.com.misa.ccl.view.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.presenter.ActivityLoginPresenter;
import vn.com.misa.ccl.util.Common;
import vn.com.misa.ccl.util.NetworkConnection;
import vn.com.misa.ccl.view.manage.ActivityRestaurantManage;
import vn.com.misa.ccl.view.restaurantsetup.ActivityAppInformation;
import vn.com.misa.ccl.util.AndroidDeviceHelper;
import vn.com.misa.ccl.view.restaurantsetup.ActivityRestaurantType;

/**
 * ‐ Mục đích Class thực hiện việc đăng nhập bằng tài khoản đã được đăng ký
 * <p>
 * ‐ @created_by cvmanh on 01/07/2021
 */

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener, IActivityLogin.IActivityLoginView {

    private EditText etUserName, etPassword;

    private ImageView ivAppInformation;

    private TextView tvForgotPassword;

    private ImageView ivTextClearUserName, ivTextClearPassword, ivBack, ivClose;

    private Dialog dlgForgotPassword;

    private Button btnLogin;

    private ActivityLoginPresenter mActivityLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        onClickViewListener();

        onFocusEditTextListener();
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/11/2021
     */
    private void initView() {
        try {
            etUserName = findViewById(R.id.etUserName);
            etPassword = findViewById(R.id.etPassword);
            ivAppInformation = findViewById(R.id.ivAppInformation);
            tvForgotPassword = findViewById(R.id.tvForgotPassword);
            ivTextClearUserName = findViewById(R.id.ivTextClearUserName);
            ivTextClearPassword = findViewById(R.id.ivTextClearPassword);
            ivBack = findViewById(R.id.ivBack);
            btnLogin = findViewById(R.id.btnLogin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lắng nghe các sự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/11/2021
     */
    private void onClickViewListener() {
        try {
            ivAppInformation.setOnClickListener(this);
            tvForgotPassword.setOnClickListener(this);
            ivBack.setOnClickListener(this);
            btnLogin.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý các yêu cầu khi người dùng click
     *
     * @param view
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.ivAppInformation: {
                    startActivity(new Intent(this, ActivityAppInformation.class));
                    break;
                }
                case R.id.tvForgotPassword: {
                    showDialogForgotPassword();
                    break;
                }
                case R.id.ivBack: {
                    finish();
                    break;
                }
                case R.id.ivClose: {
                    dlgForgotPassword.dismiss();
                    break;
                }
                case R.id.btnLogin: {
                    doLoginApp();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doLoginApp() throws NoSuchAlgorithmException {
        mActivityLoginPresenter = new ActivityLoginPresenter(this);
        mActivityLoginPresenter.doLoginApp(etUserName.getText().toString(), etPassword.getText().toString());
    }

    /**
     * Mục đích method thực hiện việc show, hide icon clear text khi có sự kiện focus vào view
     *
     * @created_by cvmanh on 01/07/2021
     */
    private void onFocusEditTextListener() {
        try {
            etUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    ivTextClearUserName.setVisibility(View.VISIBLE);
                    ivTextClearPassword.setVisibility(View.GONE);
                }
            });
            etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    ivTextClearUserName.setVisibility(View.GONE);
                    ivTextClearPassword.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc hiển thị dialog quên mật khẩu
     *
     * @created_by cvmanh on 01/11/2021
     */
    private void showDialogForgotPassword() {
        try {
            dlgForgotPassword = new Dialog(this);
            dlgForgotPassword.setContentView(R.layout.dialog_forgot_password);
            dlgForgotPassword.closeOptionsMenu();
            dlgForgotPassword.setCanceledOnTouchOutside(false);
            ConstraintLayout clForgotPassword = dlgForgotPassword.findViewById(R.id.clForgotPassword);
            clForgotPassword.getLayoutParams().width = AndroidDeviceHelper.getWitdhScreen(this) - 80;
            clForgotPassword.requestLayout();
            dlgForgotPassword.show();
            ivClose = dlgForgotPassword.findViewById(R.id.ivClose);
            ivClose.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả đăng nhập thành công từ presenter và hiện thông báo
     *
     * @created_by cvmanh on 01/31/2021
     */
    @Override
    public void loginSuccess(int shopID) {
        Toast.makeText(this, getResources().getString(R.string.login_success), Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this, ActivityRestaurantType.class));
        startActivity(new Intent(this, ActivityRestaurantManage.class));
        SharedPreferences sharedPreferences = getSharedPreferences("SHOPINFOMATION", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SHOP_ID", String.valueOf(shopID));
        editor.putString("SHOP_NAME", etUserName.getText().toString());
        editor.commit();

        checkSyncData(shopID);

        finish();
    }

    /**
     * Mục đích method thực hiện việc kiểm tra trến server đã tồn tại dữ liệu với id của tài khoản đăng nhập
     *
     * @param shopID mã cửa hàng đăng nhập
     * @created_by cvmanh on 02/03/2021
     */
    private void checkSyncData(int shopID) {
        if (NetworkConnection.checkNetworkConnection(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mActivityLoginPresenter = new ActivityLoginPresenter(ActivityLogin.this);
                    mActivityLoginPresenter.checkSyncData(shopID, ActivityLogin.this);
                }
            }).start();
            return;
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả đăng nhập thất bại từ presenter và hiện thông báo
     *
     * @created_by cvmanh on 01/31/2021
     */
    @Override
    public void onFailed() {
        etUserName.requestFocus();
        Toast.makeText(this, getResources().getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }
}