package vn.com.misa.ccl.view.restaurantsetup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.ColorAdapter;
import vn.com.misa.ccl.adapter.ProductImageAdapter;
import vn.com.misa.ccl.entity.ProductCategory;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.presenter.ActivityFoodUpdatePresenter;
import vn.com.misa.ccl.util.AndroidDeviceHelper;

/**
‐ Mục đích Class thực hiện các công việc trong ActivityFoodUpdate
*
‐ @created_by cvmanh on 01/19/2021
*/

public class ActivityFoodUpdate extends AppCompatActivity implements View.OnClickListener,
        IActivityFoodUpdate.IActivityFoodUpdateView, ColorAdapter.IColorSelection, ProductImageAdapter.IOnClickItemListener {

    private EditText etFoodName;

    private TextView tvFoodPrice,tvFoodUnit,tvBack;

    private ImageView ivFoodImage,ivColor;

    private ProductCategory mProductCategory;

    private CardView cvImage,cvColor;

    private RecyclerView rcvListColor,rcvListImage;

    private ActivityFoodUpdatePresenter mActivityFoodUpdatePresenter;

    private List<vn.com.misa.ccl.entity.Color> mListColor;

    private ColorAdapter mColorAdapter;

    private Dialog mDialogColor,mDialogImage;

    private ProductImageAdapter mProductImageAdapter;

    private List<ProductImage> mListProductImage;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_update);

        initView();

        receiveProductData();

        onViewCLickListener();

    }

    /**
     * Mục đích method thực hiện việc nhận dữ liệu đối tượng món ăn được gửi từ ProductCategotyAdapter và hiển thị dữ liệu lên view
     *
     * @created_by cvmanh on 01/19/2021
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void receiveProductData() {
        Intent intent=getIntent();
        mProductCategory= (ProductCategory) intent.getSerializableExtra("Object");
        etFoodName.setText(mProductCategory.getmProduct().getmProductName());
        tvFoodPrice.setText(String.valueOf(mProductCategory.getmProduct().getmProductPrice()));
        tvFoodUnit.setText(mProductCategory.getmProduct().getmUnit().getmUnitName());
        ivFoodImage.setImageBitmap(BitmapFactory.decodeByteArray(mProductCategory.getmProduct().getmProductImage().getmImage(),
                0,mProductCategory.getmProduct().getmProductImage().getmImage().length));
        cvImage.getBackground().setTint(Color.parseColor(mProductCategory.getmProduct().getmColor().getColorName()));
        cvColor.getBackground().setTint(Color.parseColor(mProductCategory.getmProduct().getmColor().getColorName()));
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void initView(){
        etFoodName=findViewById(R.id.etFoodName);
        tvFoodPrice=findViewById(R.id.tvPrice);
        tvFoodUnit=findViewById(R.id.tvUnit);
        ivFoodImage=findViewById(R.id.ivImage);
        cvImage=findViewById(R.id.cvImage);
        cvColor=findViewById(R.id.cvColor);
        tvBack=findViewById(R.id.tvBack);
        ivColor=findViewById(R.id.ivColor);
    }

    /**
     * Mục đích method thực hiện việc lắng nghe sự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void onViewCLickListener(){
        tvBack.setOnClickListener(this);
        ivColor.setOnClickListener(this);
        ivFoodImage.setOnClickListener(this);
    }

    /**
     * Mục đích method thực hiện các công việc tương ứng khi view được click
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
            case R.id.ivColor:{
                showDialogListColor();
                break;
            }
            case R.id.ivImage:{
                showDialogImage();
                break;
            }
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo dialog list color và gọi đến mà xử lý lấy dữ liệu danh sách màu
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void showDialogListColor(){
        mDialogColor=new Dialog(this);
        mDialogColor.setContentView(R.layout.dialog_list_color);
        mDialogColor.setCanceledOnTouchOutside(false);
        mDialogColor.show();
        rcvListColor=mDialogColor.findViewById(R.id.rcvListColor);
        LinearLayout llListColor=mDialogColor.findViewById(R.id.llListColor);
        llListColor.getLayoutParams().width= AndroidDeviceHelper.getWitdhScreen(this)-10;
        llListColor.requestLayout();
        mActivityFoodUpdatePresenter=new ActivityFoodUpdatePresenter(this);
        mActivityFoodUpdatePresenter.loadListColor(this);
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách màu và hiển thị lên recycleView
     *
     * @param listColor Danh sách màu
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListColorSuccess(List<vn.com.misa.ccl.entity.Color> listColor) {
        mListColor=listColor;
        mColorAdapter=new ColorAdapter(this,R.layout.item_color,mListColor);
        rcvListColor.setLayoutManager(new GridLayoutManager(this,4));
        rcvListColor.setAdapter(mColorAdapter);
        mColorAdapter.notifyDataSetChanged();
        mColorAdapter.setmIColorSelection(this);
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách hình ảnh và hiển thị lên recycleView
     *
     * @param listProductImage Danh sách hình ảnh
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListProductImageSuccess(List<ProductImage> listProductImage) {
        mListProductImage=listProductImage;
        mProductImageAdapter=new ProductImageAdapter(this,R.layout.item_product_image,mListProductImage);
        rcvListImage.setLayoutManager(new GridLayoutManager(this,5));
        rcvListImage.setAdapter(mProductImageAdapter);
        mProductImageAdapter.setmClickItemListener(this);
        mProductImageAdapter.notifyDataSetChanged();
    }

    /**
     * Mục đích method thực hiện việc nhận thông báo khi không lấy được dữ liệu
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onFailed() {
        Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method thực hiện việc nhận mã màu khi chọn màu từ dialog
     *
     * @created_by cvmanh on 01/19/2021
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClickListener(String keyColor) {
        mDialogColor.dismiss();
        cvImage.getBackground().setTint(Color.parseColor(keyColor));
        cvColor.getBackground().setTint(Color.parseColor(keyColor));
    }

    /**
     * Mục đích method thực hiện những việc hiển thị dialog danh sách hình ảnh và gọi hàm xử lý lấy danh sách hình ảnh
     *
     * @return giải thích hàm này trả về
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void showDialogImage(){
        mDialogImage=new Dialog(this);
        mDialogImage.setContentView(R.layout.dialog_product_image);
        mDialogImage.setCanceledOnTouchOutside(false);
        mDialogImage.show();
        rcvListImage=mDialogImage.findViewById(R.id.rcvListImage);
        LinearLayout llListImage=mDialogImage.findViewById(R.id.llListImage);
        llListImage.getLayoutParams().width= AndroidDeviceHelper.getWitdhScreen(this)-10;
        llListImage.requestLayout();
        mActivityFoodUpdatePresenter=new ActivityFoodUpdatePresenter(this);
        mActivityFoodUpdatePresenter.loadProductImage(this);
    }

    /**
     * Mục đích method thực hiện việc nhận hình ảnh khi người dùng chọn ảnh từ dialog danh sách hình ảnh
     *
     * @param productImage Kiểu hình ảnh
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void selectionItem(byte[] productImage) {
        mDialogImage.dismiss();
        ivFoodImage.setImageBitmap(BitmapFactory.decodeByteArray(productImage,
                0,productImage.length));
    }
}