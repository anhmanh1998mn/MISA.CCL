package vn.com.misa.ccl.view.restaurantsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.ProductCategoryAdapter;
import vn.com.misa.ccl.entity.ProductCategory;
import vn.com.misa.ccl.presenter.ActivityRestaurantMenuPresenter;
import vn.com.misa.ccl.util.AndroidDeviceHelper;
import vn.com.misa.ccl.util.Common;
import vn.com.misa.ccl.view.manage.ActivityRestaurantManage;

/**
 * ‐ Mục đích Class thực hiện những công việc của ActivityRestaurantMenu
 * <p>
 * ‐ {@link ActivityRestaurantMenuPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/19/2021
 */

public class ActivityRestaurantMenu extends AppCompatActivity implements IActivityRestaurantMenu.IFragmentRestaurantMenuView,
        View.OnClickListener {

    private ProductCategoryAdapter mProductAdapter;

    private List<ProductCategory> mListProductCategory;

    private ActivityRestaurantMenuPresenter mRestaurantMenuPresenter;

    private RecyclerView rcvRestaurantMenu;

    private int mCategoryID;

    private TextView tvBack, tvSuccess, tvSuccessNext;

    private Button btnSuccess;

    private Dialog dialogSetupSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        initView();

        receiveCategoryID();

        loadListProduct();

        clickViewListener();

    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void initView() {
        try {
            rcvRestaurantMenu = findViewById(R.id.rcvShopType);
            tvBack = findViewById(R.id.tvBack);
            tvSuccess = findViewById(R.id.tvSuccess);
            btnSuccess = findViewById(R.id.btnSuccess);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận mã loại cửa hàng từ ActivityRestaurantType
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void receiveCategoryID() {
        try {
            Intent intent = getIntent();
            mCategoryID = intent.getIntExtra("CATEGORY_ID", -1);
            Common.CATEGORY_ID = mCategoryID;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc gọi hàm xử lý lấy dữ liệu danh sách sản phẩm từ ActivityRestaurantMenuPresenter
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void loadListProduct() {
        try {
            mRestaurantMenuPresenter = new ActivityRestaurantMenuPresenter(this);
            mRestaurantMenuPresenter.loadListProduct(this, Common.CATEGORY_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách sản phẩm và hiển thị danh sách lên recycleView
     *
     * @param listProductCategory Danh sách sản phẩm
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListProductSuccess(List<ProductCategory> listProductCategory) {
        try {
            mListProductCategory = listProductCategory;
            mProductAdapter = new ProductCategoryAdapter(this, R.layout.item_restaurant_menu, mListProductCategory);
            rcvRestaurantMenu.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rcvRestaurantMenu.setAdapter(mProductAdapter);
            mProductAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả khởi tạo menu thành công và tắt dialog
     *
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void initMenuSuccess() {
        try {
            dialogSetupSuccess.dismiss();
            startActivity(new Intent(this, ActivityRestaurantManage.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Mục đích method thực hiện việc nhận kết quả lấy dữ liệu thất bại và xử lý
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onFailed() {
    }

    /**
     * Mục đích method thực hiện click view
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void clickViewListener() {
        try {
            tvBack.setOnClickListener(this);
            tvSuccess.setOnClickListener(this);
            btnSuccess.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý các công việc khi click view
     *
     * @param view
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.tvBack: {
                    finish();
                    break;
                }
                case R.id.tvSuccess: {
                    showDialogSetupSuccess();
                    break;
                }
                case R.id.btnSuccess: {
                    showDialogSetupSuccess();
                    break;
                }
                case R.id.tvSuccessNext: {
                    mRestaurantMenuPresenter = new ActivityRestaurantMenuPresenter(this);
                    mRestaurantMenuPresenter.initMenu(this, mListProductCategory);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc hiển thị dialog thông báo khởi tạo menu thành công
     *
     * @created_by cvmanh on 01/26/2021
     */
    private void showDialogSetupSuccess() {
        try {
            dialogSetupSuccess = new Dialog(this);
            dialogSetupSuccess.setContentView(R.layout.dialog_setup_success);
            dialogSetupSuccess.setCanceledOnTouchOutside(false);
            dialogSetupSuccess.show();
            tvSuccessNext = dialogSetupSuccess.findViewById(R.id.tvSuccessNext);
            ConstraintLayout clSuccess = dialogSetupSuccess.findViewById(R.id.clSuccess);
            clSuccess.getLayoutParams().width = AndroidDeviceHelper.getWitdhScreen(this) - 100;
            clSuccess.requestLayout();
            tvSuccessNext.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc hiển thị lại danh sách menu khi người dùng xóa item
     *
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    protected void onRestart() {
        mProductAdapter.notifyDataSetChanged();
        super.onRestart();
    }

    /**
     * Mục đích method thực hiện việc cập nhật lại adapter
     *
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    protected void onResume() {
        mProductAdapter.notifyDataSetChanged();
        super.onResume();
    }
}