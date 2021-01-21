package vn.com.misa.ccl.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.com.misa.ccl.R;

/**
‐ Mục đích Class thực hiện những việc đăng ký tài khoản mới
*
*
‐ @created_by cvmanh on 01/07/2021
*/

public class ActivityAccountRegister extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTermOfService;

    private TextView tvBack;

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
            tvTermOfService=findViewById(R.id.tvTermOfService);
            tvBack=findViewById(R.id.tvBack);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích của method thực hiện việc lắng nghe các sự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/07/2021
     */
    private void onClickViewListener(){
        try {
            tvTermOfService.setOnClickListener(this);
            tvBack.setOnClickListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý các yêu cầu khi click
     *
     * @param view
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()){
                case R.id.tvTermOfService:{
                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("https://register.cukcuk.vn/LicenseAgrement.htm"));
                    startActivity(intent);
                    break;
                }
                case R.id.tvBack:{
                    finish();
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}