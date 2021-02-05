package vn.com.misa.ccl.view.manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.SettingAdapter;
import vn.com.misa.ccl.entity.Setting;
import vn.com.misa.ccl.presenter.ActivityRestaurantManagePresenter;
import vn.com.misa.ccl.view.infomationapp.ActivityApplicationInfomation;
import vn.com.misa.ccl.view.infomationapp.ActivityRemoveData;
import vn.com.misa.ccl.view.login.ActivityAccountRegister;
import vn.com.misa.ccl.view.login.ActivitySelectionOptionLogin;
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

    private TextView tvMenu, tvAdd, tvSetupName, tvAddMenu, tvVisible, tvLogin, tvRegister, tvUserInformation, tvLogoutAccount;

    private ImageView ivUser;

    private RecyclerView rcvMenu;

    private ActivityRestaurantManagePresenter mActivityManage;

    private SettingAdapter mSettingAdapter;

    private List<Setting> mListSetting;

    private ConstraintLayout clAccountLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_manage);

        initActionbar();

        initView();

        clickViewListener();

        addFragment(new FragmentListOrder());

        loadListSetting();

        checkUserLoginSuccess();

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
            tvVisible = findViewById(R.id.tvVisible);
            tvLogin = findViewById(R.id.tvLogin);
            tvRegister = findViewById(R.id.tvRegister);
            tvUserInformation = findViewById(R.id.tvUserInformation);
            tvLogoutAccount = findViewById(R.id.tvLogoutAccount);
            clAccountLogout = findViewById(R.id.clAccountLogout);
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
            tvLogin.setOnClickListener(this);
            tvRegister.setOnClickListener(this);
            tvLogoutAccount.setOnClickListener(this);
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
                case R.id.tvLogin: {
                    Intent intent = new Intent(this, ActivitySelectionOptionLogin.class);
                    intent.putExtra("TypeIntent", "ActivityManage");
                    startActivity(intent);
                    break;
                }
                case R.id.tvRegister: {
                    startActivity(new Intent(this, ActivityAccountRegister.class));
                    break;
                }
                case R.id.tvLogoutAccount: {
                    SharedPreferences sharedPreferences = getSharedPreferences("SHOPINFOMATION", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("SHOP_ID", "");
                    editor.commit();

                    checkUserLoginSuccess();
                    dlRestaurantManage.closeDrawer(GravityCompat.START);
                    addFragment(new FragmentListOrder());
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
            mListSetting = listSetting;
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
        try {
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
                    SharedPreferences sharedPreferences = getSharedPreferences("SHOPINFOMATION", MODE_PRIVATE);
                    if (sharedPreferences.getString("SHOP_ID", "").equals("")) {
                        Intent intent = new Intent(this, ActivitySelectionOptionLogin.class);
                        intent.putExtra("TypeIntent", "ActivityManage");
                        startActivity(intent);
                        return;
                    }
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
                case "Giới thiệu cho bạn": {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, (getResources().getString(R.string.share_with_fiend)));
                    intent.setType("text/plain");
                    startActivity(Intent.createChooser(intent, ""));
                    break;
                }
                case "Thông tin sản phẩm": {
                    startActivity(new Intent(this, ActivityApplicationInfomation.class));
                    break;
                }
                case "Thiết lập": {
                    Intent intent = new Intent(this, ActivityRemoveData.class);
                    intent.putExtra("SETTING_INTENT", 1);//2: thiết lập
                    startActivity(intent);
                    break;
                }
                case "Đồng bộ dữ liệu": {
                    Intent intent = new Intent(this, ActivityRemoveData.class);
                    intent.putExtra("SETTING_INTENT", 2);//2: đồng bộ dữ liệu
                    startActivity(intent);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkUserLoginSuccess() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHOPINFOMATION", MODE_PRIVATE);
        if (sharedPreferences.getString("SHOP_ID", "").equals("")) {
            tvUserInformation.setVisibility(View.GONE);
            tvLogin.setVisibility(View.VISIBLE);
            tvRegister.setVisibility(View.VISIBLE);
            clAccountLogout.setVisibility(View.GONE);
            return;
        }
        tvUserInformation.setVisibility(View.VISIBLE);
        tvLogin.setVisibility(View.GONE);
        tvRegister.setVisibility(View.GONE);
        tvUserInformation.setText(sharedPreferences.getString("SHOP_NAME", ""));
        clAccountLogout.setVisibility(View.VISIBLE);
    }
}