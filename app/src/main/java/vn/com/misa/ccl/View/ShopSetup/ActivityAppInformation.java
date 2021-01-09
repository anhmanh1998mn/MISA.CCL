package vn.com.misa.ccl.View.ShopSetup;
/**
‐ Mục đích Class thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
*
‐ {@link android.app.Activity#onResume}
‐ {@link onResume}
*
‐ @created_by cvmanh on 01/07/2021
*/
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.com.misa.ccl.R;

public class ActivityAppInformation extends AppCompatActivity implements View.OnClickListener {

    private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_information);

        initView();

        onClickViewListener();
    }

    private void initView() {
        tvBack=findViewById(R.id.tvBack);
    }

    private void onClickViewListener(){
        tvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvBack:{
                finish();
                break;
            }
        }
    }
}