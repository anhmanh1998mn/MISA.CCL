package vn.com.misa.ccl.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.presenter.ActivityAccountPresenter;
import vn.com.misa.ccl.view.restaurantsetup.ActivityRestaurantType;

/**
 * ‐ Mục đích Class thực hiện những việc đăng ký tài khoản mới
 * <p>
 * <p>
 * ‐ @created_by cvmanh on 01/07/2021
 */

public class ActivityAccountRegister extends AppCompatActivity implements View.OnClickListener, IActivityAccountRegister.IActivityAccountRegisterView {

    private TextView tvTermOfService;

    private TextView tvBack, tvNext;

    private EditText etUserName, etPassword;

    private Button btnRegister;

    private ActivityAccountPresenter mActivityAccountPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);

        initView();

        onClickViewListener();
    }

    /**
     * Mục đích của method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/07/2021
     */
    private void initView() {
        try {
            tvTermOfService = findViewById(R.id.tvTermOfService);
            tvBack = findViewById(R.id.tvBack);
            etPassword = findViewById(R.id.etPassword);
            etUserName = findViewById(R.id.etUserName);
            btnRegister = findViewById(R.id.btnRegister);
            tvNext = findViewById(R.id.tvNext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích của method thực hiện việc lắng nghe các sự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/07/2021
     */
    private void onClickViewListener() {
        try {
            tvTermOfService.setOnClickListener(this);
            tvBack.setOnClickListener(this);
            btnRegister.setOnClickListener(this);
            tvNext.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý các yêu cầu khi click
     *
     * @param view
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.tvTermOfService: {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://register.cukcuk.vn/LicenseAgrement.htm"));
                    startActivity(intent);
                    break;
                }
                case R.id.tvBack: {
                    finish();
                    break;
                }
                case R.id.btnRegister: {
                    doRegisterUser();
                    break;
                }
                case R.id.tvNext: {
                    doRegisterUser();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc gọi presenter xử lý đăng ký tài khoản người dùng
     *
     * @created_by cvmanh on 01/31/2021
     */
    private void doRegisterUser() throws NoSuchAlgorithmException {
        try {
            mActivityAccountPresenter = new ActivityAccountPresenter(this);
            mActivityAccountPresenter.doRegisterAccount(etUserName.getText().toString().trim(), etPassword.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả đăng ký thành công từ presenter và hiện thông báo
     *
     * @created_by cvmanh on 01/31/2021
     */
    @Override
    public void registerAccountSuccess() {
        Toast.makeText(this, getResources().getString(R.string.register_success), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ActivityLogin.class));
        finish();
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả đăng ký thất bại từ presenter và hiện thông báo
     *
     * @created_by cvmanh on 01/31/2021
     */
    @Override
    public void onFailed() {
        Toast.makeText(this, getResources().getString(R.string.register_account_error), Toast.LENGTH_LONG).show();
        etUserName.requestFocus();
    }
}