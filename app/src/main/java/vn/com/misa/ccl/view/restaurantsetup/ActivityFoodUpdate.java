package vn.com.misa.ccl.view.restaurantsetup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.CaculateAdapter;
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
        IActivityFoodUpdate.IActivityFoodUpdateView, ColorAdapter.IColorSelection,
        ProductImageAdapter.IOnClickItemListener, CaculateAdapter.IResultClickItem {

    private EditText etFoodName;

    private TextView tvFoodPrice,tvFoodUnit,tvBack,tvPriceEnter;

    private ImageView ivFoodImage,ivColor,ivClose;

    private ProductCategory mProductCategory;

    private CardView cvImage,cvColor;

    private RecyclerView rcvListColor,rcvListImage,rcvCaculating;

    private ActivityFoodUpdatePresenter mActivityFoodUpdatePresenter;

    private List<vn.com.misa.ccl.entity.Color> mListColor;

    private ColorAdapter mColorAdapter;

    private Dialog mDialogColor,mDialogImage;

    private ProductImageAdapter mProductImageAdapter;

    private List<ProductImage> mListProductImage;

    private List<String> mListCaculate;

    private CaculateAdapter mCacucateAdapter;

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
    private void receiveProductData() {
        try {
            Intent intent=getIntent();
            mProductCategory= (ProductCategory) intent.getSerializableExtra("Object");
            etFoodName.setText(mProductCategory.getmProduct().getmProductName());
            DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
            tvFoodPrice.setText(String.valueOf(decimalFormat.format(mProductCategory.getmProduct().getmProductPrice())));
            tvFoodUnit.setText(mProductCategory.getmProduct().getmUnit().getmUnitName());
            ivFoodImage.setImageBitmap(BitmapFactory.decodeByteArray(mProductCategory.getmProduct().getmProductImage().getmImage(),
                    0,mProductCategory.getmProduct().getmProductImage().getmImage().length));
            cvImage.getBackground().setTint(Color.parseColor(mProductCategory.getmProduct().getmColor().getColorName()));
            cvColor.getBackground().setTint(Color.parseColor(mProductCategory.getmProduct().getmColor().getColorName()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện tạo sharePreference
     *
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            SharedPreferences sharedPreferences=getSharedPreferences("UnitSelection",MODE_PRIVATE);
            if(sharedPreferences!=null){
                tvFoodUnit.setText(sharedPreferences.getString("UNIT_NAME",""));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void initView(){
        try {
            etFoodName=findViewById(R.id.etFoodName);
            tvFoodPrice=findViewById(R.id.tvPrice);
            tvFoodUnit=findViewById(R.id.tvUnit);
            ivFoodImage=findViewById(R.id.ivImage);
            cvImage=findViewById(R.id.cvImage);
            cvColor=findViewById(R.id.cvColor);
            tvBack=findViewById(R.id.tvBack);
            ivColor=findViewById(R.id.ivColor);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lắng nghe sự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void onViewCLickListener(){
        try {
            tvBack.setOnClickListener(this);
            ivColor.setOnClickListener(this);
            ivFoodImage.setOnClickListener(this);
            tvFoodUnit.setOnClickListener(this);
            tvFoodPrice.setOnClickListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }
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
        try {
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
                case R.id.tvUnit:{
                    startActivity(new Intent(this,ActivityUnit.class));
                    break;
                }
                case R.id.tvPrice:{
                    showDialogCaculating();
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc hiển thị máy tính
     *
     * @created_by cvmanh on 01/26/2021
     */
    private void showDialogCaculating(){
        try {
            Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.dialog_caculating);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            FrameLayout frmPrice=dialog.findViewById(R.id.frmPrice);
            frmPrice.getLayoutParams().width=AndroidDeviceHelper.getWitdhScreen(this)-10;
            frmPrice.requestLayout();
            rcvCaculating=dialog.findViewById(R.id.rcvCaculating);
            rcvCaculating.getLayoutParams().height=AndroidDeviceHelper.getHeightScreen(this)*10/25;
            rcvCaculating.getLayoutParams().width=AndroidDeviceHelper.getWitdhScreen(this)-150;
            rcvCaculating.requestLayout();
            mActivityFoodUpdatePresenter=new ActivityFoodUpdatePresenter(this);
            mActivityFoodUpdatePresenter.loadCaculating(this);
            tvPriceEnter=dialog.findViewById(R.id.tvPriceEnter);
            ivClose=dialog.findViewById(R.id.ivClose);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo dialog list color và gọi đến mà xử lý lấy dữ liệu danh sách màu
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void showDialogListColor(){
        try {
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
        }catch (Exception e){
            e.printStackTrace();
        }
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
        try {
            mListColor=listColor;
            mColorAdapter=new ColorAdapter(this,R.layout.item_color,mListColor);
            rcvListColor.setLayoutManager(new GridLayoutManager(this,4));
            rcvListColor.setAdapter(mColorAdapter);
            mColorAdapter.notifyDataSetChanged();
            mColorAdapter.setmIColorSelection(this);
        }catch (Exception e){
            e.printStackTrace();
        }
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
        try {
            mListProductImage=listProductImage;
            mProductImageAdapter=new ProductImageAdapter(this,R.layout.item_product_image,mListProductImage);
            rcvListImage.setLayoutManager(new GridLayoutManager(this,5));
            rcvListImage.setAdapter(mProductImageAdapter);
            mProductImageAdapter.setmClickItemListener(this);
            mProductImageAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận dữ liệu và hiển thị lên máy tính
     *
     * @param listCaculate Danh sách dữ liệu hiển thị trên máy tính
     *
     * @created_by cvmanh on 01/21/2021
     */
    @Override
    public void loadCaculatingSuccess(List<String> listCaculate) {
        try {
            mListCaculate=listCaculate;
            mCacucateAdapter=new CaculateAdapter(this,R.layout.item_caculate,mListCaculate);
            rcvCaculating.setLayoutManager(new GridLayoutManager(this,4));
            rcvCaculating.setAdapter(mCacucateAdapter);
            mCacucateAdapter.notifyDataSetChanged();
            mCacucateAdapter.setmIResultClickItem(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả tính toán và hiển thị lên view
     *
     * @param resulText Kết quả thực hiện xử phí phép tính
     *
     * @created_by cvmanh on 01/21/2021
     */
    @Override
    public void processCaculatorSuccess(String resulText) {
        try {
            tvPriceEnter.setText(resulText);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận thông báo khi không lấy được dữ liệu
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onFailed() {
        try {
            Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận mã màu khi chọn màu từ dialog
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onClickListener(String keyColor) {
        try {
            mDialogColor.dismiss();
            cvImage.getBackground().setTint(Color.parseColor(keyColor));
            cvColor.getBackground().setTint(Color.parseColor(keyColor));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện những việc hiển thị dialog danh sách hình ảnh và gọi hàm xử lý lấy danh sách hình ảnh
     *
     * @return giải thích hàm này trả về
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void showDialogImage(){
        try {
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
        }catch (Exception e){
            e.printStackTrace();
        }
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
        try {
            mDialogImage.dismiss();
            ivFoodImage.setImageBitmap(BitmapFactory.decodeByteArray(productImage,
                    0,productImage.length));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận giá trị button người dùng click và gọi hàm xử lý các nút nhấn
     *
     * @param nameClick Giá trị button mà người dùng click
     *
     * @created_by cvmanh on 01/21/2021
     */
    @Override
    public void resultClickItem(String nameClick) {
        try {
            mActivityFoodUpdatePresenter=new ActivityFoodUpdatePresenter(this);
            mActivityFoodUpdatePresenter.processCaculator(this,tvPriceEnter.getText().toString().trim(),nameClick);
        }catch (Exception e){
            e.printStackTrace();
        }
//        switch (nameClick){
//            case "C":{
//                tvPriceEnter.setText("0");
//                break;
//            }
//            case "Giảm":{
//                if(Float.parseFloat(tvPriceEnter.getText().toString().trim())<1){
//                    tvPriceEnter.setText("0");
//                    return;
//                }
//                tvPriceEnter.setText((Float.parseFloat(tvPriceEnter.getText().toString().trim())-1)+"");
//                break;
//            }
//            case "Tăng":{
//                tvPriceEnter.setText((Float.parseFloat(tvPriceEnter.getText().toString().trim())+1)+"");
//                break;
//            }
//            case "":{
//                if(tvPriceEnter.getText().toString().equals("0")||tvPriceEnter.getText().length()==1){
//                    tvPriceEnter.setText("0");
//                    return;
//                }
//                tvPriceEnter.setText(tvPriceEnter.getText().toString().substring(0,tvPriceEnter.getText().toString().length()-1));
//                break;
//            }
//            case "7":{
//                checkNumberCaculate(nameClick);
//                break;
//            }
//            case "8":{
//                checkNumberCaculate(nameClick);
//                break;
//            }
//            case "9":{
//                checkNumberCaculate(nameClick);
//                break;
//            }
//            case "-":{
//                if(tvPriceEnter.getText().toString().startsWith("0")){
//                    tvPriceEnter.setText("0");
//                    return;
//                }
//                if(tvPriceEnter.getText().toString().endsWith("-")||tvPriceEnter.getText().toString().endsWith("+")){
//                    return;
//                }
//                tvPriceEnter.append(nameClick);
//                break;
//            }
//            case "4":{
//                checkNumberCaculate(nameClick);
//                break;
//            }
//            case "5":{
//                checkNumberCaculate(nameClick);
//                break;
//            }
//            case "6":{
//                checkNumberCaculate(nameClick);
//                break;
//            }
//            case "+":{
//                if(tvPriceEnter.getText().toString().startsWith("0")){
//                    tvPriceEnter.setText("0");
//                    return;
//                }
//                if(tvPriceEnter.getText().toString().endsWith("+")||tvPriceEnter.getText().toString().endsWith("-")){
//                    return;
//                }
//                tvPriceEnter.append(nameClick);
//                break;
//            }
//            case "1":{
//                checkNumberCaculate(nameClick);
//                break;
//            }
//            case "2":{
//                checkNumberCaculate(nameClick);
//                break;
//            }
//            case "3":{
//                checkNumberCaculate(nameClick);
//                break;
//            }
//            case "±":{
//                if(tvPriceEnter.getText().toString().startsWith("-")){
//                    return;
//                }
//                tvPriceEnter.setText("-"+tvPriceEnter.getText());
//                break;
//            }
//            case "0":{
//                if(tvPriceEnter.getText().toString().startsWith("0")||tvPriceEnter.getText().toString().startsWith("-0")){
//                    tvPriceEnter.setText(nameClick);
//                    return;
//                }
//                checkNumberCaculate(nameClick);
//                break;
//            }
//            case "000":{
//                if(tvPriceEnter.getText().toString().startsWith("0")||tvPriceEnter.getText().toString().startsWith("-0")){
//                    tvPriceEnter.setText("0");
//                    return;
//                }
//                checkNumberCaculate(nameClick);
//                break;
//            }
//            case ",":{
//                tvPriceEnter.append(nameClick);
//                break;
//            }
//        }
    }
//    private void checkNumberCaculate(String nameItemClick){
//        if(tvPriceEnter.getText().toString().startsWith("0")){
//            tvPriceEnter.setText(nameItemClick);
//            return;
//        }
//        if(tvPriceEnter.getText().toString().contains("+")||tvPriceEnter.getText().toString().contains("-")){
//            tvPriceEnter.append(nameItemClick);
//        }else if(tvPriceEnter.getText().toString().length()<17){
//            tvPriceEnter.append(nameItemClick);
//            return;
//        }
//    }
}