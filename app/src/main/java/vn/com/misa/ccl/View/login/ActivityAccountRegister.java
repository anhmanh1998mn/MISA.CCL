package vn.com.misa.ccl.View.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import vn.com.misa.ccl.R;
/**
‐ Mục đích Class thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
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
     * Mục đích:
     *
     * @created_by cvmanh on 01/07/2021
     */
    private void initView() {
        tvTermOfService=findViewById(R.id.tvTermOfService);
        tvBack=findViewById(R.id.tvBack);
    }

    /**
     * Mục đích:
     *
     * @created_by cvmanh on 01/07/2021
     */
    private void onClickViewListener(){
        tvTermOfService.setOnClickListener(this);
        tvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
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
    }
}