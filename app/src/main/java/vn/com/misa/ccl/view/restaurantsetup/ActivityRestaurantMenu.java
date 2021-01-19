package vn.com.misa.ccl.view.restaurantsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.ProductCategoryAdapter;
import vn.com.misa.ccl.entity.ProductCategory;
import vn.com.misa.ccl.presenter.ActivityRestaurantMenuPresenter;

/**
‐ Mục đích Class thực hiện những công việc của ActivityRestaurantMenu
*
‐ {@link ActivityRestaurantMenuPresenter}
*
‐ @created_by cvmanh on 01/19/2021
*/

public class ActivityRestaurantMenu extends AppCompatActivity implements IActivityRestaurantMenu.IFragmentRestaurantMenuView,
        View.OnClickListener {

    private ProductCategoryAdapter mProductAdapter;

    private List<ProductCategory> mListProductCategory;

    private ActivityRestaurantMenuPresenter mRestaurantMenuPresenter;

    private RecyclerView rcvRestaurantMenu;

    private int mCategoryID;

    private TextView tvBack;

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
            rcvRestaurantMenu =findViewById(R.id.rcvShopType);
            tvBack=findViewById(R.id.tvBack);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận mã loại cửa hàng từ ActivityRestaurantType
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void receiveCategoryID() {
        Intent intent=getIntent();
        mCategoryID=intent.getIntExtra("ab",-1);
    }

    /**
     * Mục đích method thực hiện việc gọi hàm xử lý lấy dữ liệu danh sách sản phẩm từ ActivityRestaurantMenuPresenter
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void loadListProduct() {
        mRestaurantMenuPresenter =new ActivityRestaurantMenuPresenter(this);
        mRestaurantMenuPresenter.loadListProduct(this,mCategoryID);
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách sản phẩm và hiển thị danh sách lên recycleView
     *
     * @param listProductCategory Danh sách sản phẩm
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListProductSuccess(List<ProductCategory> listProductCategory) {
        mListProductCategory=listProductCategory;
        mProductAdapter=new ProductCategoryAdapter(this,R.layout.item_restaurant_menu,mListProductCategory);
        rcvRestaurantMenu.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rcvRestaurantMenu.setAdapter(mProductAdapter);
        mProductAdapter.notifyDataSetChanged();
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
        tvBack.setOnClickListener(this);
    }

    /**
     * Mục đích method thực hiện việc xử lý các công việc khi click view
     *
     * @param view
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvBack:{
                finish();
                break;
            }
        }
    }
}