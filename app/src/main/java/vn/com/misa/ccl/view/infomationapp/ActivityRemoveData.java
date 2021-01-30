package vn.com.misa.ccl.view.infomationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import vn.com.misa.ccl.R;

/**
 * ‐ Mục đích Class thực hiện việc xóa dữ liệu trên máy và lấy dữ liệu mới nhất từ server
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.manage.ActivityRestaurantManage}
 * <p>
 * ‐ @created_by cvmanh on 01/31/2021
 */

public class ActivityRemoveData extends AppCompatActivity {

    private TextView tvBack, tvSettingName, tvComfirmRemove, tvSyncDate;

    private ImageView ivSyncNow;

    private Button btnSyncNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_data);

        initView();

        receiveIntent();

        try {
            tvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/31/2021
     */
    private void initView() {
        try {
            tvBack = findViewById(R.id.tvBack);
            ivSyncNow = findViewById(R.id.ivSyncNow);
            btnSyncNow = findViewById(R.id.btnSyncNow);
            tvSettingName = findViewById(R.id.tvSettingName);
            tvComfirmRemove = findViewById(R.id.tvComfirmRemove);
            tvSyncDate = findViewById(R.id.tvSyncDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận dữ liệu truyền tới
     *
     * @created_by cvmanh on 01/31/2021
     */
    private void receiveIntent() {
        try {
            Intent intent = getIntent();
            if (intent.getIntExtra("SETTING_INTENT", -1) == 1) {
                ivSyncNow.setVisibility(View.GONE);
                btnSyncNow.setVisibility(View.GONE);
                tvSettingName.setText(getResources().getString(R.string.setting));
                tvComfirmRemove.setVisibility(View.VISIBLE);
                tvSyncDate.setVisibility(View.GONE);
                return;
            }
            ivSyncNow.setVisibility(View.VISIBLE);
            btnSyncNow.setVisibility(View.VISIBLE);
            tvSettingName.setText(getResources().getString(R.string.synchronize));
            tvComfirmRemove.setVisibility(View.GONE);
            tvSyncDate.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}