package vn.com.misa.ccl.View.Welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.View.login.ActivitySelectionOptionLogin;
import vn.com.misa.ccl.View.ShopSetup.ActivityShopSetup;

public class ActivityAppConfirm extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack;

    private Button btnLoginNext;

    private TextView tvAppOverViewNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_confirm);

        initView();

        onClickViewListener();
    }

    private void initView() {
        ivBack=findViewById(R.id.ivBack);
        btnLoginNext=findViewById(R.id.btnLoginNext);
        tvAppOverViewNext=findViewById(R.id.tvAppOverViewNext);
    }

    private void onClickViewListener(){
        ivBack.setOnClickListener(this);
        btnLoginNext.setOnClickListener(this);
        tvAppOverViewNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivBack:{
                finish();
                break;
            }
            case R.id.btnLoginNext:{
                startActivity(new Intent(this, ActivityShopSetup.class));
                break;
            }
            case R.id.tvAppOverViewNext:{
                startActivity(new Intent(this, ActivitySelectionOptionLogin.class));
                break;
            }
        }
    }
}