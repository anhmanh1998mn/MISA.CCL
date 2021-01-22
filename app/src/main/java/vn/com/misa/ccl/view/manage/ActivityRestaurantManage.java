package vn.com.misa.ccl.view.manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.SettingAdapter;
import vn.com.misa.ccl.entity.Setting;
import vn.com.misa.ccl.presenter.ActivityRestaurantManagePresenter;
import vn.com.misa.ccl.view.order.ActivityOrder;
import vn.com.misa.ccl.view.order.FragmentListOrder;

public class ActivityRestaurantManage extends AppCompatActivity implements View.OnClickListener, IActivityRestaurantManage.IActivityManageView {

    private Toolbar tbRestaurantManage;

    private DrawerLayout dlRestaurantManage;

    private TextView tvMenu,tvAdd;

    private ImageView ivUser;

    private RecyclerView rcvMenu;

    private ActivityRestaurantManagePresenter mActivityManage;

    private SettingAdapter mSettingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_manage);

        initActionbar();

        initView();

        clickViewListener();

        addFragment(new FragmentListOrder());

        loadListSetting();
    }

    private void initView() {
        dlRestaurantManage=findViewById(R.id.dlRestaurantManage);
        tvMenu=findViewById(R.id.tvMenu);
        ivUser=findViewById(R.id.ivUser);
        ivUser.setClipToOutline(true);
        rcvMenu=findViewById(R.id.rcvMenu);
        tvAdd=findViewById(R.id.tvAdd);
    }

    private void initActionbar() {
        tbRestaurantManage=findViewById(R.id.tbRestaurantManage);
        setSupportActionBar(tbRestaurantManage);
    }

    private void clickViewListener(){
        tvMenu.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvMenu:{
                dlRestaurantManage.openDrawer(GravityCompat.START);
                break;
            }
            case R.id.tvAdd:{
                startActivity(new Intent(this, ActivityOrder.class));
            }
        }
    }

    private void addFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frmRestaurantManage,fragment);
        fragmentTransaction.commit();
    }

    private void loadListSetting(){
        mActivityManage=new ActivityRestaurantManagePresenter(this);
        mActivityManage.getListSetting(this);
    }

    @Override
    public void getListSettingSuccess(List<Setting> listSetting) {
        mSettingAdapter=new SettingAdapter(this,R.layout.item_setting,listSetting);
        rcvMenu.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rcvMenu.setAdapter(mSettingAdapter);
        mSettingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {

    }
}