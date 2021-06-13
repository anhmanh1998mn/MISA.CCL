package vn.com.misa.ccl.view.restaurantsetup;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.CaculateAdapter;
import vn.com.misa.ccl.adapter.ColorAdapter;
import vn.com.misa.ccl.adapter.ProductImageAdapter;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.entity.ProductCategory;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.presenter.ActivityFoodUpdatePresenter;
import vn.com.misa.ccl.util.AndroidDeviceHelper;
import vn.com.misa.ccl.util.NetworkConnection;

/**
 * ‐ Mục đích Class thực hiện các công việc trong ActivityFoodUpdate
 * <p>
 * ‐ @created_by cvmanh on 01/19/2021
 */

public class ActivityFoodUpdate extends AppCompatActivity implements View.OnClickListener,
        IActivityFoodUpdate.IActivityFoodUpdateView, ColorAdapter.IColorSelection,
        ProductImageAdapter.IOnClickItemListener, CaculateAdapter.IResultClickItem {

    private EditText etFoodName;

    private TextView tvFoodPrice, tvFoodUnit, tvBack, tvPriceEnter, tvSetupName, tvCloseFoodImage,
            tvColorClose, tvNext, tvUpdate;

    private LinearLayout llFoodPrice;

    private ImageView ivFoodImage, ivColor, ivClose, ivCloseDialogConfirmRemoveItem;

    private ProductCategory mProductCategory;

    private CardView cvImage, cvColor;

    private RecyclerView rcvListColor, rcvListImage, rcvCaculating;

    private ActivityFoodUpdatePresenter mActivityFoodUpdatePresenter;

    private List<vn.com.misa.ccl.entity.Color> mListColor;

    private ColorAdapter mColorAdapter;

    private Dialog dlgColor, dlgImage, dlgCaculate;

    private ProductImageAdapter mProductImageAdapter;

    private List<ProductImage> mListProductImage;

    private List<String> mListCaculate;

    private CaculateAdapter mCacucateAdapter;

    private Button btnDelete, btnSave, btnAddnewProduct, btnUpdate;

    private int mProductID, mColorID = 1, mImageID = 1, mUnitID = 1;

    private float mPriceOut;

    private Product mProduct;

    private String mTypeIntent, mProductName, mKeyColor, mUnitName;

    private CheckBox cbStopSell;

    private byte[] mProductImage;

    private Dialog dlgConfirmRemoveItemMenu;

    private TextView tvRemoveConfirm, tvNoConfirmRemove, tvAppName, tvNotifiRemove;

    private LinearLayout llRemoveItemMenu;

    private ConstraintLayout clConfirmRemove;

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
            Intent intent = getIntent();
            mTypeIntent = intent.getStringExtra("TypeIntent");
            if (mTypeIntent.equals("Setup")) {
                mProductCategory = (ProductCategory) intent.getSerializableExtra("Object");
                mProductID = mProductCategory.getProduct().getProductID();
                mImageID = mProductCategory.getProduct().getProductImage().getProductImageID();
                mColorID = mProductCategory.getProduct().getColor().getColorID();
                mUnitID = mProductCategory.getProduct().getUnit().getUnitID();
                mPriceOut = mProductCategory.getProduct().getProductPrice();
                etFoodName.setText(mProductCategory.getProduct().getProductName());
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                tvFoodPrice.setText(String.valueOf(decimalFormat.format(mProductCategory.getProduct().getProductPrice())));
                tvFoodUnit.setText(mProductCategory.getProduct().getUnit().getUnitName());
                ivFoodImage.setImageBitmap(BitmapFactory.decodeByteArray(mProductCategory.getProduct().getProductImage().getImage(),
                        0, mProductCategory.getProduct().getProductImage().getImage().length));
                cvImage.getBackground().setTint(Color.parseColor(mProductCategory.getProduct().getColor().getColorName()));
                cvColor.getBackground().setTint(Color.parseColor(mProductCategory.getProduct().getColor().getColorName()));
                cbStopSell.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.GONE);
                tvUpdate.setVisibility(View.GONE);
                tvNext.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
                mProductImage = mProductCategory.getProduct().getProductImage().getImage();
                mKeyColor = mProductCategory.getProduct().getColor().getColorName();

            } else if (mTypeIntent.equals("Menu")) {
                mProduct = (Product) intent.getSerializableExtra("Object");
                mProductID = mProduct.getProductID();
                etFoodName.setText(mProduct.getProductName());
                mProductName = mProduct.getProductName();
                etFoodName.setTag(mProductName);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                tvFoodPrice.setText(String.valueOf(decimalFormat.format(mProduct.getProductPrice())));
                mPriceOut = mProduct.getProductPrice();
                tvFoodPrice.setTag(mPriceOut);
                mImageID = mProduct.getProductImage().getProductImageID();
                mColorID = mProduct.getColor().getColorID();
                mProductImage = mProduct.getProductImage().getImage();
                mKeyColor = mProduct.getColor().getColorName();
                mUnitID = mProduct.getUnit().getUnitID();
                btnUpdate.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.GONE);
                tvUpdate.setVisibility(View.VISIBLE);
                tvNext.setVisibility(View.GONE);
                if (mProduct.getProductStatus() == 2) {
                    cbStopSell.setChecked(true);
                } else {
                    cbStopSell.setChecked(false);
                }
                tvFoodUnit.setText(mProduct.getUnit().getUnitName());
                ivFoodImage.setImageBitmap(BitmapFactory.decodeByteArray(mProduct.getProductImage().getImage(),
                        0, mProduct.getProductImage().getImage().length));
                cvImage.getBackground().setTint(Color.parseColor(mProduct.getColor().getColorName()));
                cvColor.getBackground().setTint(Color.parseColor(mProduct.getColor().getColorName()));
            } else {
                btnDelete.setVisibility(View.GONE);
                btnSave.setVisibility(View.GONE);
                btnAddnewProduct.setVisibility(View.VISIBLE);
                tvSetupName.setText(getResources().getString(R.string.add_new_product));
                btnUpdate.setVisibility(View.GONE);
            }
        } catch (Exception e) {
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
            SharedPreferences sharedPreferences = getSharedPreferences("UnitSelection", MODE_PRIVATE);
            if (sharedPreferences != null) {
                tvFoodUnit.setText(sharedPreferences.getString("UNIT_NAME", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void initView() {
        try {
            llFoodPrice = findViewById(R.id.llFoodPrice);
            etFoodName = findViewById(R.id.etFoodName);
            tvFoodPrice = findViewById(R.id.tvPrice);
            tvFoodUnit = findViewById(R.id.tvUnit);
            ivFoodImage = findViewById(R.id.ivImage);
            cvImage = findViewById(R.id.cvImage);
            cvColor = findViewById(R.id.cvColor);
            tvBack = findViewById(R.id.tvBack);
            ivColor = findViewById(R.id.ivColor);
            btnDelete = findViewById(R.id.btnDelete);
            btnSave = findViewById(R.id.btnSave);
            tvSetupName = findViewById(R.id.tvSetupName);
            btnAddnewProduct = findViewById(R.id.btnAddnewProduct);
            tvNext = findViewById(R.id.tvNext);
            cbStopSell = findViewById(R.id.cbStopSell);
            btnUpdate = findViewById(R.id.btnUpdate);
            tvUpdate = findViewById(R.id.tvUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lắng nghe sự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void onViewCLickListener() {
        try {
            tvBack.setOnClickListener(this);
            ivColor.setOnClickListener(this);
            ivFoodImage.setOnClickListener(this);
            tvFoodUnit.setOnClickListener(this);
            tvFoodPrice.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
            btnSave.setOnClickListener(this);
            btnAddnewProduct.setOnClickListener(this);
            tvNext.setOnClickListener(this);
            btnUpdate.setOnClickListener(this);
            tvUpdate.setOnClickListener(this);
            llFoodPrice.setOnClickListener(v -> {
                Toast.makeText(this, "CLICKED", Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện các công việc tương ứng khi view được click
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
                case R.id.ivColor: {
                    showDialogListColor();
                    break;
                }
                case R.id.ivImage: {
                    showDialogImage();
                    break;
                }
                case R.id.tvUnit: {
                    startActivity(new Intent(this, ActivityUnit.class));
                    break;
                }
                case R.id.llFoodPrice: {
                    showDialogCaculating();
                    break;
                }
                case R.id.btnDelete: {
                    mActivityFoodUpdatePresenter = new ActivityFoodUpdatePresenter(this);
                    if (mTypeIntent.equals("Setup")) {
                        mActivityFoodUpdatePresenter.removeItemProduct(mProductID);
                        return;
                    }
                    showDialogConfirmRemoveIntemMenu();
//                    mActivityFoodUpdatePresenter.deleteItemProductMenu(this, mProductID);
                    break;
                }
                case R.id.btnSave: {
                    updateProductInfomation();
                    break;
                }
                case R.id.btnAddnewProduct: {
                    addNewProductInfomation();
                    break;
                }
                case R.id.ivClose: {
                    dlgCaculate.dismiss();
                    break;
                }
                case R.id.tvCloseFoodImage: {
                    dlgImage.dismiss();
                    break;
                }
                case R.id.tvColorClose: {
                    dlgColor.dismiss();
                    break;
                }
                case R.id.tvNext: {
                    updateProductInfomation();
                    break;
                }
                case R.id.btnUpdate: {
                    updateMenu();
                    break;
                }
                case R.id.tvUpdate: {
                    updateMenu();
                    break;
                }
                case R.id.tvRemoveConfirm: {
                    mActivityFoodUpdatePresenter.deleteItemProductMenu(this, mProductID);
                    break;
                }
                case R.id.tvNoConfirmRemove: {
                    dlgConfirmRemoveItemMenu.dismiss();
                    break;
                }
                case R.id.ivCloseDialogConfirmRemoveItem: {
                    dlgConfirmRemoveItemMenu.dismiss();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện hiển thị dialog xác nhận xóa món ăn trong thực đơn
     * Xử lý theo view click: có, không
     *
     * @created_by cvmanh on 02/09/2021
     */
    private void showDialogConfirmRemoveIntemMenu() {
        try {
            dlgConfirmRemoveItemMenu = new Dialog(this);
            dlgConfirmRemoveItemMenu.setCanceledOnTouchOutside(true);
            dlgConfirmRemoveItemMenu.setContentView(R.layout.dialog_remove_item);
            dlgConfirmRemoveItemMenu.show();
            initDialogViewRemoveItemMenu();
            setWidthForDialogRemoveItem();
            onViewDialogRemoveItemMenuListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo view trong dialog xác nhận xóa
     *
     * @created_by cvmanh on 02/09/2021
     */
    private void initDialogViewRemoveItemMenu() {
        try {
            clConfirmRemove = dlgConfirmRemoveItemMenu.findViewById(R.id.clConfirmRemove);
            tvRemoveConfirm = dlgConfirmRemoveItemMenu.findViewById(R.id.tvRemoveConfirm);
            tvNotifiRemove = dlgConfirmRemoveItemMenu.findViewById(R.id.tvNotifiRemove);
            tvNotifiRemove.setText(getResources().getString(R.string.remove_confirm_menu) + " " + mProductName + " không?");
            tvNoConfirmRemove = dlgConfirmRemoveItemMenu.findViewById(R.id.tvNoConfirmRemove);
            ivCloseDialogConfirmRemoveItem = dlgConfirmRemoveItemMenu.findViewById(R.id.ivCloseDialogConfirmRemoveItem);
            tvAppName = dlgConfirmRemoveItemMenu.findViewById(R.id.tvAppName);
            llRemoveItemMenu = dlgConfirmRemoveItemMenu.findViewById(R.id.llRemoveItemMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc cài đặt chiều dai cho dialog xác nhận xóa
     *
     * @created_by cvmanh on 02/09/2021
     */
    private void setWidthForDialogRemoveItem() {
        try {
            llRemoveItemMenu.getLayoutParams().width = AndroidDeviceHelper.getWitdhScreen(this) - 100;
            llRemoveItemMenu.requestLayout();
            clConfirmRemove.getLayoutParams().width = AndroidDeviceHelper.getWitdhScreen(this) - 100;
            clConfirmRemove.requestLayout();
            tvAppName.getLayoutParams().width = AndroidDeviceHelper.getWitdhScreen(this) - 100;
            tvAppName.requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lắng nghe xự kiên click view dialog xác nhận xóa từ người dùng
     *
     * @created_by cvmanh on 02/09/2021
     */
    private void onViewDialogRemoveItemMenuListener() {
        try {
            tvRemoveConfirm.setOnClickListener(this);
            tvNoConfirmRemove.setOnClickListener(this);
            ivCloseDialogConfirmRemoveItem.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện gọi presenter xử lý cập nhật thông tin sản phẩm trong menu đã setup
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void updateMenu() {
        try {
            mActivityFoodUpdatePresenter = new ActivityFoodUpdatePresenter(this);
            if (cbStopSell.isChecked()) {
                mActivityFoodUpdatePresenter.stopSellProduct(this, mProductID);
                return;
            }
            mActivityFoodUpdatePresenter.updateItemProductMenu(this, mProductID, etFoodName.getText().toString(),
                    mPriceOut, mImageID, mUnitID, mColorID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc gọi presenter xử lý thêm mới thông tin sản phẩm
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void addNewProductInfomation() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("UnitSelection", MODE_PRIVATE);
            if (sharedPreferences != null) {
                mUnitID = (sharedPreferences.getInt("UNIT_ID", -1));
            } else {
                mUnitID = 1;
            }
            mActivityFoodUpdatePresenter = new ActivityFoodUpdatePresenter(this);
            mActivityFoodUpdatePresenter.addNewFoodMenu(this, etFoodName.getText().toString(),
                    mPriceOut, mImageID, mUnitID, mColorID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện gọi presenter xử lý cập nhật thông tin sản phẩm setup menu
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void updateProductInfomation() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("UnitSelection", MODE_PRIVATE);
            if (sharedPreferences != null) {
                mUnitID = (sharedPreferences.getInt("UNIT_ID", -1));
            }
            mActivityFoodUpdatePresenter = new ActivityFoodUpdatePresenter(this);
//            if (cbStopSell.isChecked()) {
//                mActivityFoodUpdatePresenter.stopSellProduct(this, mProductID);
//                return;
//            }
            switch (mTypeIntent) {
                case "Setup": {
                    mActivityFoodUpdatePresenter.updateItemProduct(mProductID, etFoodName.getText().toString().trim(),
                            mPriceOut, mImageID, mUnitID, mColorID, mProductImage, mKeyColor);
                    break;
                }
                default: {
                    addNewProductInfomation();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc hiển thị máy tính
     *
     * @created_by cvmanh on 01/26/2021
     */
    private void showDialogCaculating() {
        try {
            dlgCaculate = new Dialog(this);
            dlgCaculate.setContentView(R.layout.dialog_caculating);
            dlgCaculate.setCanceledOnTouchOutside(false);
            dlgCaculate.show();
            FrameLayout frmPrice = dlgCaculate.findViewById(R.id.frmPrice);
            frmPrice.getLayoutParams().width = AndroidDeviceHelper.getWitdhScreen(this) - 10;
            frmPrice.requestLayout();
            rcvCaculating = dlgCaculate.findViewById(R.id.rcvCaculating);
            rcvCaculating.getLayoutParams().height = AndroidDeviceHelper.getHeightScreen(this) * 10 / 25;
            rcvCaculating.getLayoutParams().width = AndroidDeviceHelper.getWitdhScreen(this) - 150;
            rcvCaculating.requestLayout();
            mActivityFoodUpdatePresenter = new ActivityFoodUpdatePresenter(this);
            mActivityFoodUpdatePresenter.loadCaculating(this);
            tvPriceEnter = dlgCaculate.findViewById(R.id.tvPriceEnter);
            ivClose = dlgCaculate.findViewById(R.id.ivClose);
            ivClose.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo dialog list color và gọi đến mà xử lý lấy dữ liệu danh sách màu
     *
     * @created_by cvmanh on 01/19/2021
     */
    private void showDialogListColor() {
        try {
            dlgColor = new Dialog(this);
            dlgColor.setContentView(R.layout.dialog_list_color);
            dlgColor.setCanceledOnTouchOutside(false);
            dlgColor.show();
            rcvListColor = dlgColor.findViewById(R.id.rcvListColor);
            LinearLayout llListColor = dlgColor.findViewById(R.id.llListColor);
            llListColor.getLayoutParams().width = AndroidDeviceHelper.getWitdhScreen(this) - 10;
            llListColor.requestLayout();
            mActivityFoodUpdatePresenter = new ActivityFoodUpdatePresenter(this);
            mActivityFoodUpdatePresenter.loadListColor(this);
            tvColorClose = dlgColor.findViewById(R.id.tvColorClose);
            tvColorClose.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách màu và hiển thị lên recycleView
     *
     * @param listColor Danh sách màu
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListColorSuccess(List<vn.com.misa.ccl.entity.Color> listColor) {
        try {
            mListColor = listColor;
            mColorAdapter = new ColorAdapter(this, R.layout.item_color, mListColor);
            rcvListColor.setLayoutManager(new GridLayoutManager(this, 4));
            rcvListColor.setAdapter(mColorAdapter);
            mColorAdapter.notifyDataSetChanged();
            mColorAdapter.setmIColorSelection(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách hình ảnh và hiển thị lên recycleView
     *
     * @param listProductImage Danh sách hình ảnh
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListProductImageSuccess(List<ProductImage> listProductImage) {
        try {
            mListProductImage = listProductImage;
            mProductImageAdapter = new ProductImageAdapter(this, R.layout.item_product_image, mListProductImage);
            rcvListImage.setLayoutManager(new GridLayoutManager(this, 5));
            rcvListImage.setAdapter(mProductImageAdapter);
            mProductImageAdapter.setmClickItemListener(this);
            mProductImageAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận dữ liệu và hiển thị lên máy tính
     *
     * @param listCaculate Danh sách dữ liệu hiển thị trên máy tính
     * @created_by cvmanh on 01/21/2021
     */
    @Override
    public void loadCaculatingSuccess(List<String> listCaculate) {
        try {
            mListCaculate = listCaculate;
            mCacucateAdapter = new CaculateAdapter(this, R.layout.item_caculate, mListCaculate);
            rcvCaculating.setLayoutManager(new GridLayoutManager(this, 4));
            rcvCaculating.setAdapter(mCacucateAdapter);
            mCacucateAdapter.notifyDataSetChanged();
            mCacucateAdapter.setmIResultClickItem(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả tính toán và hiển thị lên view
     *
     * @param resulText Kết quả thực hiện xử phí phép tính
     * @created_by cvmanh on 01/21/2021
     */
    @Override
    public void processCaculatorSuccess(String resulText) {
        try {
            tvPriceEnter.setText(resulText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý click button success
     *
     * @param resultText Kết quả xử lý
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void processEnterSuccess(String resultText) {
        try {
            mPriceOut = Float.parseFloat(resultText);
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            tvFoodPrice.setText(decimalFormat.format(Integer.parseInt(resultText)));
            dlgCaculate.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xóa product thành công
     *
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void removeProductItemSuccess() {
        finish();
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả sửa thông tin product thành công
     *
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void updateItemProductSuccess() {
        finish();
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xóa đồ trong menu thành công và hiện thông báo cho người dùng
     *
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void deleteItemProductMenuSuccess() {
        try {
            deleteItemProductMenuOnServer();
            dlgConfirmRemoveItemMenu.dismiss();
            finish();
            Toast.makeText(this, getResources().getString(R.string.process_success), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc gọi presenter xử lý xóa sản phẩm menu trên server
     *
     * @created_by cvmanh on 02/05/2021
     */
    private void deleteItemProductMenuOnServer() {
        try {
            if (NetworkConnection.checkNetworkConnection(this)) {
                mActivityFoodUpdatePresenter = new ActivityFoodUpdatePresenter(this);
                SharedPreferences sharedPreferences = getSharedPreferences("SHOPINFOMATION", MODE_PRIVATE);
                int shopID = Integer.parseInt(sharedPreferences.getString("SHOP_ID", ""));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mActivityFoodUpdatePresenter.deleteItemProductMenuOnServer(mProductID, shopID);
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả sửa đồ trong menu thành công và hiện thông báo cho người dùng
     *
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void updateItemProductMenuSuccess() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("SHOPINFOMATION", MODE_PRIVATE);
            int shopID = Integer.parseInt(sharedPreferences.getString("SHOP_ID", ""));
            updateItemProductOnServer(etFoodName.getText().toString(), mPriceOut, mImageID, mUnitID, mColorID, shopID, mProductID);
            finish();
            Toast.makeText(this, getResources().getString(R.string.process_success), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc gọi presenter xử lý việc cập nhật thông tin sản phẩm trên server
     *
     * @param productName tên sản phẩm
     * @param priceOut    giá sản phẩm
     * @param imageID     mã ảnh
     * @param unitID      mã đơn vị
     * @param colorID     mã màu
     * @param shopID      mã cửa hàng
     * @param productID   mã sản phẩm
     * @created_by cvmanh on 02/05/2021
     */
    private void updateItemProductOnServer(String productName, float priceOut, int imageID, int unitID, int colorID, int shopID, int productID) {

        try {
            if (NetworkConnection.checkNetworkConnection(this)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mActivityFoodUpdatePresenter = new ActivityFoodUpdatePresenter(ActivityFoodUpdate.this);
                        mActivityFoodUpdatePresenter.updateItemProductOnServer(productName, priceOut, imageID,
                                unitID, colorID, shopID, productID);
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc thêm mới món ăn thành công và hiện thông báo cho người dùng
     *
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void addNewFoodMenuSuccess() {
        finish();
        Toast.makeText(this, getResources().getString(R.string.process_success), Toast.LENGTH_SHORT).show();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận thông báo xóa sản phẩm thất bại nếu sản phẩm đó đã tồn tại trong đơn hàng
     *
     * @created_by cvmanh on 02/18/2021
     */
    @Override
    public void onDeleteProductFailed() {
        try {
            dlgConfirmRemoveItemMenu.dismiss();
            Toast toast = Toast.makeText(this, "Món <" + etFoodName.getText().toString() + "> " + getResources().getString(R.string.exists_product), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 166);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận mã màu khi chọn màu từ dialog
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onClickListener(String keyColor, int colorID) {
        try {
            mColorID = colorID;
            mKeyColor = keyColor;
            dlgColor.dismiss();
            cvImage.getBackground().setTint(Color.parseColor(keyColor));
            cvColor.getBackground().setTint(Color.parseColor(keyColor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện những việc hiển thị dialog danh sách hình ảnh và gọi hàm xử lý lấy danh sách hình ảnh
     *
     * @return giải thích hàm này trả về
     * @created_by cvmanh on 01/19/2021
     */
    private void showDialogImage() {
        try {
            dlgImage = new Dialog(this);
            dlgImage.setContentView(R.layout.dialog_product_image);
            dlgImage.setCanceledOnTouchOutside(false);
            dlgImage.show();
            rcvListImage = dlgImage.findViewById(R.id.rcvListImage);
            LinearLayout llListImage = dlgImage.findViewById(R.id.llListImage);
            llListImage.getLayoutParams().width = AndroidDeviceHelper.getWitdhScreen(this) - 10;
            llListImage.requestLayout();
            mActivityFoodUpdatePresenter = new ActivityFoodUpdatePresenter(this);
            mActivityFoodUpdatePresenter.loadProductImage(this);
            tvCloseFoodImage = dlgImage.findViewById(R.id.tvCloseFoodImage);
            tvCloseFoodImage.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận hình ảnh khi người dùng chọn ảnh từ dialog danh sách hình ảnh
     *
     * @param productImage Kiểu hình ảnh
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void selectionItem(byte[] productImage, int imageID) {
        try {
            mImageID = imageID;
            mProductImage = productImage;
            dlgImage.dismiss();
            ivFoodImage.setImageBitmap(BitmapFactory.decodeByteArray(productImage,
                    0, productImage.length));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận giá trị button người dùng click và gọi hàm xử lý các nút nhấn
     *
     * @param nameClick Giá trị button mà người dùng click
     * @created_by cvmanh on 01/21/2021
     */
    @Override
    public void resultClickItem(String nameClick) {
        try {
            mActivityFoodUpdatePresenter = new ActivityFoodUpdatePresenter(this);
            mActivityFoodUpdatePresenter.processCaculator(this, tvPriceEnter.getText().toString().trim(), nameClick);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}