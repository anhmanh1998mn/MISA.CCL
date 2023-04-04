package vn.com.misa.ccl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.com.misa.ccl.view.welcome.ActivityAppConfirm;
import vn.com.misa.ccl.adapter.PagerAdapter;

/**
 * ‐ Mục đích Class thực hiện việc chứa các màn hình giới thiệu ứng dụng
 * <p> 123
 * ‐ @created_by cvmanh on 01/10/2021
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vpAppOverview;

    private TextView tvAppOverviewNext, tvCloseAppOverview;

    private final int TOTAL_FRAGMENT_OVERVIEW = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initViewPager();

        onClickListenerView();

    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/10/2021
     */
    private void initView() {
        try {
            vpAppOverview = findViewById(R.id.vpAppOverview);
            tvAppOverviewNext = findViewById(R.id.tvAppOverViewNext);
            tvCloseAppOverview = findViewById(R.id.tvCloseAppOverview);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo view pager
     *
     * @created_by cvmanh on 01/10/2021
     */
    private void initViewPager() {
        try {
            FragmentManager manager = getSupportFragmentManager();
            PagerAdapter pagerAdapter = new PagerAdapter(manager);
            vpAppOverview.setAdapter(pagerAdapter);
            vpAppOverview.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lắng nghe sự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/10/2021
     */
    private void onClickListenerView() {
        try {
            tvAppOverviewNext.setOnClickListener(this);
            tvCloseAppOverview.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý các công việc khi người dùng click
     *
     * @param view
     * @created_by cvmanh on 01/11/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.tvAppOverViewNext: {
                    if (vpAppOverview.getCurrentItem() == TOTAL_FRAGMENT_OVERVIEW) {
                        startActivity(new Intent(this, ActivityAppConfirm.class));
                    }
                    vpAppOverview.setCurrentItem(vpAppOverview.getCurrentItem() + 1, true);
                    break;
                }
                case R.id.tvCloseAppOverview: {
                    startActivity(new Intent(this, ActivityAppConfirm.class));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}