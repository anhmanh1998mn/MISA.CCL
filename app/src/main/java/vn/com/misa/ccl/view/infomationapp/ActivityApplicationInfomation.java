package vn.com.misa.ccl.view.infomationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.com.misa.ccl.R;

/**
 * ‐ Mục đích Class thực hiện xem thông tin sản phẩm
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.manage.ActivityRestaurantManage}
 * <p>
 * ‐ @created_by cvmanh on 01/30/2021
 */

public class ActivityApplicationInfomation extends AppCompatActivity implements View.OnClickListener {

    private TextView tvViewMISA;

    private ImageView ivBack;

    private LinearLayout llWebsite, llSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_infomation);

        initView();

        onViewClickListener();
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/30/2021
     */
    private void initView() {
        try {
            tvViewMISA = findViewById(R.id.tvViewMISA);
            ivBack = findViewById(R.id.ivBack);
            llWebsite = findViewById(R.id.llWbsite);
            llSupport = findViewById(R.id.llSupport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lắng nghe các xự kiện click
     *
     * @created_by cvmanh on 01/30/2021
     */
    private void onViewClickListener() {
        try {
            tvViewMISA.setOnClickListener(this);
            ivBack.setOnClickListener(this);
            llWebsite.setOnClickListener(this);
            llSupport.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý công việc theo view click
     *
     * @param view view
     * @created_by cvmanh on 01/30/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.tvViewMISA: {
                    intentWebsite();
                    break;
                }
                case R.id.ivBack: {
                    finish();
                    break;
                }
                case R.id.llWbsite: {
                    intentWensiteCukCuk();
                    break;
                }
                case R.id.llSupport: {
                    intentWebsiteSupport();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc đi tới trang web hỗ trợ
     *
     * @created_by cvmanh on 01/30/2021
     */
    private void intentWebsiteSupport() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.view_support)));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc đi tới trang web cukcuk.vn
     *
     * @created_by cvmanh on 01/30/2021
     */
    private void intentWensiteCukCuk() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.view_website_cukcuk)));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc chuyển sang website www.misa.vn
     *
     * @created_by cvmanh on 01/30/2021
     */
    private void intentWebsite() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.view_misa)));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}