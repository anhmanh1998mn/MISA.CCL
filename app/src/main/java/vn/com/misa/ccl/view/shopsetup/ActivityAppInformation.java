package vn.com.misa.ccl.view.shopsetup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.com.misa.ccl.R;

/**
 ‐ Mục đích Class thực hiện việc hiển thị thông tin điều khoản sử dụng ứng dụng
 *
 ‐ @created_by cvmanh on 01/07/2021
 */

public class ActivityAppInformation extends AppCompatActivity implements View.OnClickListener {

    private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_information);

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
            tvBack=findViewById(R.id.tvBack);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lắng nghe các xự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/11/2021
     */
    private void onClickViewListener(){
        tvBack.setOnClickListener(this);
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
        switch (view.getId()){
            case R.id.tvBack:{
                finish();
                break;
            }
        }
    }
}