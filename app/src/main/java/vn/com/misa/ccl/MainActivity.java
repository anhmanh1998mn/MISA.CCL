package vn.com.misa.ccl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.com.misa.ccl.View.Welcome.ActivityAppConfirm;
import vn.com.misa.ccl.adapter.PagerAdapter;
/**
‐ Mục đích Class thực hiện việc chứa các màn hình giới thiệu ứng dụng
*
‐ @created_by cvmanh on 01/10/2021
*/
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vpAppOverview;

    private TextView tvAppOverviewNext,tvCloseAppOverview;

    private int mTotalFragmentAppOverview=4;

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
        vpAppOverview=findViewById(R.id.vpAppOverview);
        tvAppOverviewNext=findViewById(R.id.tvAppOverViewNext);
        tvCloseAppOverview=findViewById(R.id.tvCloseAppOverview);
    }

    /**
     * Mục đích method thực hiện việc khởi tạo view pager
     *
     *
     * @created_by cvmanh on 01/10/2021
     */
    private void initViewPager(){
        FragmentManager manager=getSupportFragmentManager();
        PagerAdapter pagerAdapter=new PagerAdapter(manager);
        vpAppOverview.setAdapter(pagerAdapter);
        vpAppOverview.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());
    }

    /**
     * Mục đích method thực hiện việc lắng nghe sự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/10/2021
     */
    private void onClickListenerView(){
        tvAppOverviewNext.setOnClickListener(this);
        tvCloseAppOverview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAppOverViewNext:{
                if(vpAppOverview.getCurrentItem()==mTotalFragmentAppOverview){
                    startActivity(new Intent(this, ActivityAppConfirm.class));
                }
                vpAppOverview.setCurrentItem(vpAppOverview.getCurrentItem()+1,true);
                break;
            }
            case R.id.tvCloseAppOverview:{
                startActivity(new Intent(this, ActivityAppConfirm.class));
                break;
            }
        }
    }
}