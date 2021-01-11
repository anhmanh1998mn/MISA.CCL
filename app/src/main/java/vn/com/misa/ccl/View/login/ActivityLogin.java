package vn.com.misa.ccl.View.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.View.ShopSetup.ActivityAppInformation;
import vn.com.misa.ccl.util.AndroidDeviceHelper;

/**
 ‐ Mục đích Class thực hiện việc đăng nhập bằng tài khoản đã được đăng ký
 *
 ‐ @created_by cvmanh on 01/07/2021
 */

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText etUserName,etPassword;

    private ImageView ivAppInformation;

    private TextView tvForgotPassword;

    private ImageView ivTextClearUserName,ivTextClearPassword;

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
            etUserName=findViewById(R.id.etUserName);
            etPassword=findViewById(R.id.etPassword);
            ivAppInformation=findViewById(R.id.ivAppInformation);
            tvForgotPassword=findViewById(R.id.tvForgotPassword);
            ivTextClearUserName=findViewById(R.id.ivTextClearUserName);
            ivTextClearPassword=findViewById(R.id.ivTextClearPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lắng nghe các sự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/11/2021
     */
    private void onClickViewListener(){
        ivAppInformation.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivAppInformation:{
                startActivity(new Intent(this, ActivityAppInformation.class));
                break;
            }
            case R.id.tvForgotPassword:{
                showDialogForgotPassword();
                break;
            }
        }
    }


    /**
     * Mục đích method thực hiện việc show, hide icon clear text khi có sự kiện focus vào view
     *
     * @created_by cvmanh on 01/07/2021
     */
    private void onFocusEditTextListener(){
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
        }catch (Exception e){
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
            Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.dialog_forgot_password);
            dialog.closeOptionsMenu();
            dialog.setCanceledOnTouchOutside(false);
            ConstraintLayout clForgotPassword=dialog.findViewById(R.id.clForgotPassword);
            clForgotPassword.getLayoutParams().width= AndroidDeviceHelper.getWitdhScreen(this)-80;
            clForgotPassword.requestLayout();
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}