package vn.com.misa.ccl.view.restaurantsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.CategoryAdapter;
import vn.com.misa.ccl.entity.Category;
import vn.com.misa.ccl.presenter.ActivityRestaurantTypePresenter;

/**
 * ‐ Mục đích Class thực hiện việc chứa fragmentShopMenu, fragmentShopType
 * <p>
 * ‐ @created_by cvmanh on 01/11/2021
 */

public class ActivityRestaurantType extends AppCompatActivity implements View.OnClickListener, IActivityRestaurantType.IActivityRestaurantTypeView,
        CategoryAdapter.IItemClickListener{

    private Toolbar tbShopSetup;

    private TextView tvBack, tvNext;

    private RecyclerView rcvRestaurantType;

    private Button btnNext;

    private ActivityRestaurantTypePresenter mActivityRestaurantTypePresenter;

    private CategoryAdapter mCategoryAdapter;

    private List<Category> mListCategory;

    private int mCategoryID;

    private int CATEGORY_ID=1;

    private String CATEGORY_INTENT="ab";

    private String  PRODUCT_ID="PRODUCT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_type);

        initActionBar();

        initView();

        onClickViewListener();

        loadListType();

    }

    /**
     * Mục đích method thực hiện việc khởi tạo actionbar
     *
     * @created_by cvmanh on 01/09/2021
     */
    private void initActionBar() {
        try {
            tbShopSetup = findViewById(R.id.tbShopType);
            setSupportActionBar(tbShopSetup);
            btnNext=findViewById(R.id.btnNext);
            rcvRestaurantType =findViewById(R.id.rcvShopType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/09/2021
     */
    private void initView() {
        try {
            tvNext = findViewById(R.id.tvNext);
            tvBack=findViewById(R.id.tvBack);
            btnNext=findViewById(R.id.btnNext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lắng nghe các sự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/09/2021
     */
    private void onClickViewListener() {
        try {
            tvBack.setOnClickListener(this);
            tvNext.setOnClickListener(this);
            btnNext.setOnClickListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện xử lý các công việc khi người dùng click
     *
     * @param view các view được click
     * @created_by cvmanh on 01/11/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.tvBack: {
                    finish();
                    break;
                }
                case R.id.tvNext: {
                    Intent intent=new Intent(this,ActivityRestaurantMenu.class);
                    if(mCategoryID<CATEGORY_ID){
                        intent.putExtra(CATEGORY_INTENT,CATEGORY_ID);
                        intent.putExtra(PRODUCT_ID,-1);
                    }else {
                        intent.putExtra(CATEGORY_INTENT,mCategoryID);
                        intent.putExtra(PRODUCT_ID,-1);
                    }
                    startActivity(intent);
                    break;
                }
                case R.id.btnNext:{
                    Intent intent=new Intent(this,ActivityRestaurantMenu.class);
                    if(mCategoryID<CATEGORY_ID){
                        intent.putExtra(CATEGORY_INTENT,CATEGORY_ID);
                        intent.putExtra(PRODUCT_ID,-1);
                    }else {
                        intent.putExtra(CATEGORY_INTENT,mCategoryID);
                        intent.putExtra(PRODUCT_ID,-1);
                    }
                    startActivity(intent);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc gọi presenter lấy dữ liệu danh sách loại cửa hàng
     *
     * @created_by cvmanh on 01/18/2021
     */
    private void loadListType() {
        try {
            mActivityRestaurantTypePresenter =new ActivityRestaurantTypePresenter(this);
            mActivityRestaurantTypePresenter.loadListShopType(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách loại cửa hàng và hiển thị lên recycleView
     *
     * @param listCategory Danh sách loại cửa hàng
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListRestaurantTypeSuccess(List<Category> listCategory) {
        try {
            mListCategory=listCategory;
            mCategoryAdapter=new CategoryAdapter(this,R.layout.item_restaurant_type,mListCategory);
            rcvRestaurantType.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
            rcvRestaurantType.setAdapter(mCategoryAdapter);
            mCategoryAdapter.notifyDataSetChanged();
            mCategoryAdapter.setmIItemClickListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả thất bại khi lấy dữ liệu danh sách
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onFailed() {

    }

    /**
     * Mục đích method thực hiện nhận mã loại cửa hàng
     *
     * @param position Mã loại cửa hàng
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onClickListener(int position) {
        try {
            mCategoryID=position;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}