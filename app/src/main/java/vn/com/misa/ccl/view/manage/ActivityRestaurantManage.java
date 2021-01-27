package vn.com.misa.ccl.view.manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.SettingAdapter;
import vn.com.misa.ccl.entity.ProductCategory;
import vn.com.misa.ccl.entity.Setting;
import vn.com.misa.ccl.presenter.ActivityRestaurantManagePresenter;
import vn.com.misa.ccl.view.order.ActivityOrder;
import vn.com.misa.ccl.view.order.FragmentListOrder;
import vn.com.misa.ccl.view.report.FragmentMainReport;
import vn.com.misa.ccl.view.restaurantsetup.ActivityFoodUpdate;
import vn.com.misa.ccl.view.rmenu.FragmentMenu;

/**
 * ‐ Mục đích Class thực hiện những công việc chứa các fragment quản lý cửa hàng
 * <p>
 * ‐ {@link FragmentListOrder}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class ActivityRestaurantManage extends AppCompatActivity implements View.OnClickListener, IActivityRestaurantManage.IActivityManageView,
        SettingAdapter.IOnCLickViewListener {

    private Toolbar tbRestaurantManage;

    private DrawerLayout dlRestaurantManage;

    private TextView tvMenu, tvAdd, tvSetupName, tvAddMenu,tvVisible;

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

    /**
     * Mục đích method thực hiện những công việc khởi tạo các view
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void initView() {
        try {
            dlRestaurantManage = findViewById(R.id.dlRestaurantManage);
            tvMenu = findViewById(R.id.tvMenu);
            ivUser = findViewById(R.id.ivUser);
            ivUser.setClipToOutline(true);
            rcvMenu = findViewById(R.id.rcvMenu);
            tvAdd = findViewById(R.id.tvAdd);
            tvSetupName = findViewById(R.id.tvSetupName);
            tvAddMenu = findViewById(R.id.tvAddMenu);
            tvVisible=findViewById(R.id.tvVisible);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện những công việc khởi tạo actionbar
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void initActionbar() {
        try {
            tbRestaurantManage = findViewById(R.id.tbRestaurantManage);
            setSupportActionBar(tbRestaurantManage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện những công việc lắng nghe xự kiện click view từ người dùng
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void clickViewListener() {
        try {
            tvMenu.setOnClickListener(this);
            tvAdd.setOnClickListener(this);
            tvAddMenu.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện những công việc tương ứng với xự kiện click từ người dùng
     *
     * @param view view
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.tvMenu: {
                    dlRestaurantManage.openDrawer(GravityCompat.START);
                    break;
                }
                case R.id.tvAdd: {
                    Intent intent = new Intent(this, ActivityOrder.class);
                    intent.putExtra("ORDER_ID", -1);
                    startActivity(intent);
                    break;
                }
                case R.id.tvAddMenu: {
                    Intent intent = new Intent(this, ActivityFoodUpdate.class);
                    intent.putExtra("TypeIntent", "AddNewProduct");
                    startActivity(intent);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo fragment
     *
     * @param fragment fragment
     * @created_by cvmanh on 01/25/2021
     * @see #onCreate
     */
    private void addFragment(Fragment fragment) {
        try {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frmRestaurantManage, fragment);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc gọi presenter lấy danh sách setting
     *
     * @return giải thích hàm này trả về
     * @created_by cvmanh on 01/25/2021
     */
    private void loadListSetting() {
        try {
            mActivityManage = new ActivityRestaurantManagePresenter(this);
            mActivityManage.getListSetting(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách setting từ presenter và hiển thị lên recycleView
     *
     * @param listSetting Danh sách setting
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getListSettingSuccess(List<Setting> listSetting) {
        try {
            mSettingAdapter = new SettingAdapter(this, R.layout.item_setting, listSetting);
            rcvMenu.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rcvMenu.setAdapter(mSettingAdapter);
            mSettingAdapter.notifyDataSetChanged();
            mSettingAdapter.setmIOnCLickViewListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý lỗi và hiển thị thông báo cho người dùng
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void onFailed() {
        Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method thực hiện việc xử lý các công việc khi người dùng click vào item trong drawerLayout
     *
     * @param settingName tên item người dùng click trong drawerLayout
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void onCLickListener(String settingName) {
        switch (settingName) {
            case "Bán hàng": {
                addFragment(new FragmentListOrder());
                dlRestaurantManage.closeDrawer(GravityCompat.START);
                tvSetupName.setText(getResources().getString(R.string.sell));
                tvAddMenu.setVisibility(View.GONE);
                tvAdd.setVisibility(View.VISIBLE);
                tvVisible.setVisibility(View.GONE);
                break;
            }
            case "Thực đơn": {
                addFragment(new FragmentMenu());
                dlRestaurantManage.closeDrawer(GravityCompat.START);
                tvSetupName.setText(getResources().getString(R.string.menu));
                tvAddMenu.setVisibility(View.VISIBLE);
                tvAdd.setVisibility(View.GONE);
                tvVisible.setVisibility(View.GONE);
                break;
            }
            case "Báo cáo": {
                addFragment(new FragmentMainReport());
                dlRestaurantManage.closeDrawer(GravityCompat.START);
                tvSetupName.setText(getResources().getString(R.string.main_report));
                tvAddMenu.setVisibility(View.GONE);
                tvAdd.setVisibility(View.GONE);
                tvVisible.setVisibility(View.VISIBLE);
                break;
            }
        }
    }
}