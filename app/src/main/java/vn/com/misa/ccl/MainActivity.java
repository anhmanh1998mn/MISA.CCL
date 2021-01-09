package vn.com.misa.ccl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.com.misa.ccl.View.Welcome.ActivityAppConfirm;
import vn.com.misa.ccl.adapter.PagerAdapter;

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

    private void initView() {
        vpAppOverview=findViewById(R.id.vpAppOverview);
        tvAppOverviewNext=findViewById(R.id.tvAppOverViewNext);
        tvCloseAppOverview=findViewById(R.id.tvCloseAppOverview);
    }

    private void initViewPager(){
        FragmentManager manager=getSupportFragmentManager();
        PagerAdapter pagerAdapter=new PagerAdapter(manager);
        vpAppOverview.setAdapter(pagerAdapter);
        vpAppOverview.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());
    }

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