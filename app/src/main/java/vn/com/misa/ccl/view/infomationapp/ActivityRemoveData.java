package vn.com.misa.ccl.view.infomationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.util.NetworkConnection;

/**
 * ‐ Mục đích Class thực hiện việc xóa dữ liệu trên máy và lấy dữ liệu mới nhất từ server
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.manage.ActivityRestaurantManage}
 * <p>
 * ‐ @created_by cvmanh on 01/31/2021
 */

public class ActivityRemoveData extends AppCompatActivity implements View.OnClickListener {

    private TextView tvBack, tvSettingName, tvComfirmRemove, tvSyncDate;

    private ImageView ivSyncNow;

    private Button btnSyncNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_data);

        initView();

        receiveIntent();

        onViewClickListener();

    }

    /**
     * Mục đích method thực hiện việc lắng nghe xự kiện click từ ngừi dùng
     *
     * @return giải thích hàm này trả về
     * @created_by cvmanh on 02/02/2021
     */
    private void onViewClickListener() {
        tvComfirmRemove.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        btnSyncNow.setOnClickListener(this);
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

    /**
     * Mục đích method thực hiện việc xử lý các công việc theo view click
     *
     * @param view view
     * @created_by cvmanh on 02/02/2021
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvBack: {
                finish();
                break;
            }
            case R.id.tvComfirmRemove: {
                if (NetworkConnection.checkNetworkConnection(this)) {
                    return;
                }
                Toast.makeText(this, getResources().getString(R.string.error_network_disconnected), Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btnSyncNow: {
                if (NetworkConnection.checkNetworkConnection(this)) {
                    return;
                }
                Toast.makeText(this, getResources().getString(R.string.error_network_disconnected), Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}