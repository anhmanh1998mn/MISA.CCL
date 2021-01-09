package vn.com.misa.ccl.View.login;
/**
‐ Mục đích Class thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
*
‐ {@link android.app.Activity#onResume}
‐ {@link onResume}
*
‐ @created_by cvmanh on 01/07/2021
*/
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

    private void initView() {
        etUserName=findViewById(R.id.etUserName);
        etPassword=findViewById(R.id.etPassword);
        ivAppInformation=findViewById(R.id.ivAppInformation);
        tvForgotPassword=findViewById(R.id.tvForgotPassword);
        ivTextClearUserName=findViewById(R.id.ivTextClearUserName);
        ivTextClearPassword=findViewById(R.id.ivTextClearPassword);
    }

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
     * Mục đích:
     *
     * @created_by cvmanh on 01/07/2021
     */
    private void onFocusEditTextListener(){
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
    }

    private void showDialogForgotPassword() {
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_forgot_password);
        dialog.closeOptionsMenu();
        dialog.setCanceledOnTouchOutside(false);
        ConstraintLayout clForgotPassword=dialog.findViewById(R.id.clForgotPassword);
        clForgotPassword.getLayoutParams().width= AndroidDeviceHelper.getWitdhScreen(this)-80;
        clForgotPassword.requestLayout();
        dialog.show();
    }
}