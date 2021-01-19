package vn.com.misa.ccl.view.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.view.login.ActivitySelectionOptionLogin;
import vn.com.misa.ccl.view.restaurantsetup.ActivityRestaurantType;

/**
‐ Mục đích Class thực hiện việc thông báo đăng nhập hoặc tiếp tục truy cập vào ứng dụng
*
‐ @created_by cvmanh on 01/11/2021
*/

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

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/11/2021
     */
    private void initView() {
        try {
            ivBack=findViewById(R.id.ivBack);
            btnLoginNext=findViewById(R.id.btnLoginNext);
            tvAppOverViewNext=findViewById(R.id.tvAppOverViewNext);
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
        ivBack.setOnClickListener(this);
        btnLoginNext.setOnClickListener(this);
        tvAppOverViewNext.setOnClickListener(this);
    }

    /**
     * Mục đích method thực hiện việc xử lý các công việc khi người dùng click
     *
     * @param view view được click
     *
     * @created_by cvmanh on 01/11/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()){
                case R.id.ivBack:{
                    finish();
                    break;
                }
                case R.id.btnLoginNext:{
                    startActivity(new Intent(this, ActivityRestaurantType.class));
                    break;
                }
                case R.id.tvAppOverViewNext:{
                    startActivity(new Intent(this, ActivitySelectionOptionLogin.class));
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}