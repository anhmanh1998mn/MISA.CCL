package vn.com.misa.ccl.view.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.MenuAdapter;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.presenter.ActivityOrderPresenter;
import vn.com.misa.ccl.util.AndroidDeviceHelper;
import vn.com.misa.ccl.view.manage.ActivityRestaurantManage;

/**
 * ‐ Mục đích Class thực hiện những công việc tạo mới order
 * <p>
 * ‐ {@link ActivityOrderPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class ActivityOrder extends AppCompatActivity implements IActivityOrder.IActivityOrderView,
        MenuAdapter.ITotalAmount, View.OnClickListener {

    private ActivityOrderPresenter mActivityOrderPresenter;

    private Toolbar tbOrder;

    private RecyclerView rcvListFood;

    private MenuAdapter mMenuAdapter;

    private List<Product> mListProduct;

    private Dialog dlgOrderDialog;

    private String mResultSelect;

    private Button btnMoney;

    private int mClickButtonSave = 1;

    private int mClickButtonMoney = 2;

    private String ORDER_ID = "ORDER_ID";

    private int mOrderID;

    private List<OrderDetail> mListOrderDetail;

    private TextView tvTotalAmount, tvSave, tvSelectTable, tvPeopeNumber, tvSuccess, tvDialogTittle,
            tvResult, tvItemDown, tvItemUp, tvItemClear, tvItemSeven, tvItemEight, tvItemNine, tvItemFour,
            tvItemFive, tvItemSix, tvItemOne, tvItemTwo, tvItemThree, tvItemZero, tvSuccesss, tvBack, tvNext;

    private ImageView ivBackKeyboard;

    private float mAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();

        getActionClick();

//        getListMenu();

        onViewClickListener();
    }

    /**
     * Mục đích method thực hiện việc kiểm tra màn hình nào trước đó đã click sang activity hiện tại,
     * Nếu mã order=-1: click button + và thêm món
     * Nếu mã order != -1: click item order
     *
     * @created_by cvmanh on 01/26/2021
     */
    private void getActionClick() {
        try {
            Intent intent = getIntent();
            mOrderID = intent.getIntExtra("ORDER_ID", -1);
            if (intent.getIntExtra("ORDER_ID", -1) == -1) {
                getListMenu();
                return;
            }
            tvSelectTable.setText(intent.getStringExtra("TABLE_NAME"));
            tvPeopeNumber.setText(String.valueOf(intent.getIntExtra("TOTAL_NUMBER", -1)));
            mAmount = intent.getFloatExtra("AMOUNT", -1);
            tvTotalAmount.setText(String.valueOf(mAmount));
            getListMenu();
            getListOrderDetailWithOrderID();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void initView() {
        try {
            tbOrder = findViewById(R.id.tbOrder);
            setSupportActionBar(tbOrder);
            rcvListFood = findViewById(R.id.rcvListFood);
            tvTotalAmount = findViewById(R.id.tvTotalAmount);
            tvSave = findViewById(R.id.tvSave);
            tvSelectTable = findViewById(R.id.tvSelectTable);
            tvPeopeNumber = findViewById(R.id.tvPeopeNumber);
            btnMoney = findViewById(R.id.btnMoney);
            tvBack = findViewById(R.id.tvBack);
            tvNext = findViewById(R.id.tvNext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện gọi presenter xử lý lấy danh sách menu
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void getListMenu() {
        try {
            mActivityOrderPresenter = new ActivityOrderPresenter(this);
            mActivityOrderPresenter.getListMenu(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lấy danh sách chi tiết order theo mã order
     *
     * @created_by cvmanh on 01/26/2021
     */
    private void getListOrderDetailWithOrderID() {
        try {
            mActivityOrderPresenter = new ActivityOrderPresenter(this);
            mActivityOrderPresenter.getListOrderDetailWithOrderID(this, mOrderID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận danh sách product và hiển thị lên recycleView
     *
     * @param listProduct Danh sách product
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getListMenuSuccess(List<Product> listProduct) {
        try {
            mListProduct = listProduct;
            mMenuAdapter = new MenuAdapter(this, R.layout.item_select_food, mListProduct);
            rcvListFood.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rcvListFood.setAdapter(mMenuAdapter);
            mMenuAdapter.notifyDataSetChanged();
            mMenuAdapter.setmITotalAmount(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý thao tác trên máy tính và hiển thị lên view
     *
     * @param result Kết quả thao tác trên máy tính
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getResultCaculateSuccess(String result) {
        try {
            tvResult.setText(result);
            mResultSelect = result;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả thêm order thành công và chuyển sang activity khác
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void addNewOrderSuccess() {
        try {
            startActivity(new Intent(this, ActivityRestaurantManage.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả thêm mưới order và gửi mã order đến activity khác
     *
     * @param orderID mã order
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void addNewOrderTwoSuccess(int orderID) {
        try {
            Intent intent = new Intent(this, ActivityBill.class);
            intent.putExtra(ORDER_ID, orderID);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận danh sách orderDetail từ presenter
     *
     * @param listOrderDetail Danh sách orderDetail
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void getListOrderDetailWithOrderID(List<OrderDetail> listOrderDetail) {
        try {
            mListOrderDetail = listOrderDetail;
            mActivityOrderPresenter = new ActivityOrderPresenter(this);
            mActivityOrderPresenter.checkQuantityProductItem(mListOrderDetail, mListProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả cập nhật thông tin order khi nhấn button lưu và xử lý kết quả
     *
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void updateOrderSaveSuccess() {
        try {
            startActivity(new Intent(this, ActivityRestaurantManage.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả cập nhật thông tin order khi nhấn button thu tiền và xử lý kết quả
     *
     * @param orderID Mã order
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void updateOrderMoneySuccess(int orderID) {
        try {
            Intent intent = new Intent(this, ActivityBill.class);
            intent.putExtra(ORDER_ID, orderID);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả gán số lượng từ listOrderDetail vào listProduct
     *
     * @param listPrduct Danh sách sản phẩm
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void checkQuantityOrderClickSuccess(List<Product> listPrduct) {
        try {
            mListProduct = listPrduct;
            mMenuAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý thất bại và hiện thị thông báo
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void onFailed() {
        Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý tổng tiền order
     *
     * @param totalAmount Tổng tiền order đã định dạng tiền tệ
     * @param totalMoney  Tổng tiền order chưa được định dạng tiền tệ
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void processTotalAmount(String totalAmount, float totalMoney) {
        try {
            tvTotalAmount.setText(totalAmount);
            mAmount = totalMoney;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getItemSelect() {
    }

    /**
     * Mục đích method thực hiện việc lắng nghe xự kiện click view từ người dùng
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void onViewClickListener() {
        try {
            tvSave.setOnClickListener(this);
            tvPeopeNumber.setOnClickListener(this);
            tvSelectTable.setOnClickListener(this);
            btnMoney.setOnClickListener(this);
            tvBack.setOnClickListener(this);
            tvNext.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện xử lý công việc theo view click
     *
     * @param view view
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.tvSave: {
                    mActivityOrderPresenter = new ActivityOrderPresenter(this);
                    if (mOrderID == -1) {
                        mActivityOrderPresenter.addNewOrder(this, mListProduct, tvSelectTable.getText().toString(), tvPeopeNumber.getText().toString(), mAmount, mClickButtonSave);
                        return;
                    }
                    mActivityOrderPresenter.updateOrder(this, mOrderID, mListProduct, mClickButtonSave, tvSelectTable.getText().toString(),
                            tvPeopeNumber.getText().toString(), mAmount);
                    break;
                }
                case R.id.btnMoney: {
                    if (mOrderID == -1) {
                        mActivityOrderPresenter = new ActivityOrderPresenter(this);
                        mActivityOrderPresenter.addNewOrder(this, mListProduct, tvSelectTable.getText().toString(), tvPeopeNumber.getText().toString(), mAmount, mClickButtonMoney);
                        return;
                    }
                    mActivityOrderPresenter.updateOrder(this, mOrderID, mListProduct, mClickButtonMoney
                            , tvSelectTable.getText().toString(),
                            tvPeopeNumber.getText().toString(), mAmount);
                    break;
                }
                case R.id.tvSelectTable: {
                    showDialogOrderInfomation();
                    break;
                }
                case R.id.tvPeopeNumber: {
                    showDialogOrderInfomation();
                    tvSuccesss.setVisibility(View.VISIBLE);
                    tvSuccess.setVisibility(View.GONE);
                    tvDialogTittle.setText(getResources().getString(R.string.enter_people_name));
                    break;
                }
                case R.id.ivBackKeyboard: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator), tvResult.getText().toString());
                    break;
                }
                case R.id.tvItemDown: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_decrease), tvResult.getText().toString());
                    break;
                }
                case R.id.tvItemUp: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_increase), tvResult.getText().toString());
                    break;
                }
                case R.id.tvItemClear: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_clear), tvResult.getText().toString());
                    break;
                }
                case R.id.tvItemSeven: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_7), tvResult.getText().toString());
                    break;
                }
                case R.id.tvItemEight: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_8), tvResult.getText().toString());
                    break;
                }
                case R.id.tvItemNine: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_9), tvResult.getText().toString());
                    break;
                }
                case R.id.tvItemFour: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_4), tvResult.getText().toString());
                    break;
                }
                case R.id.tvItemFive: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_5), tvResult.getText().toString());
                    break;
                }
                case R.id.tvItemSix: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_6), tvResult.getText().toString());
                    break;
                }
                case R.id.tvItemOne: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_1), tvResult.getText().toString());
                    break;
                }
                case R.id.tvItemTwo: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_2), tvResult.getText().toString());
                    break;
                }
                case R.id.tvItemThree: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_3), tvResult.getText().toString());
                    break;
                }
                case R.id.tvItemZero: {
                    mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_0), tvResult.getText().toString());
                    break;
                }
                case R.id.tvSuccess: {
                    dlgOrderDialog.dismiss();
                    tvSelectTable.setText(mResultSelect);
                    break;
                }
                case R.id.tvSuccesss: {
                    dlgOrderDialog.dismiss();
                    tvPeopeNumber.setText(mResultSelect);
                    break;
                }
                case R.id.tvBack: {
                    finish();
                    break;
                }
                case R.id.tvNext: {
                    if (mOrderID == -1) {
                        mActivityOrderPresenter = new ActivityOrderPresenter(this);
                        mActivityOrderPresenter.addNewOrder(this, mListProduct, tvSelectTable.getText().toString(), tvPeopeNumber.getText().toString(), mAmount, mClickButtonMoney);
                        return;
                    }
                    mActivityOrderPresenter.updateOrder(this, mOrderID, mListProduct, mClickButtonMoney
                            , tvSelectTable.getText().toString(),
                            tvPeopeNumber.getText().toString(), mAmount);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc hiển thị dialog chọn bàn và chọn số người
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void showDialogOrderInfomation() {
        try {
            dlgOrderDialog = new Dialog(this);
            dlgOrderDialog.setContentView(R.layout.dialog_order_infomation);
            dlgOrderDialog.show();
            ConstraintLayout clDialogOrder = dlgOrderDialog.findViewById(R.id.clDialogOrder);
            clDialogOrder.getLayoutParams().width = AndroidDeviceHelper.getWitdhScreen(this) - 100;
            clDialogOrder.getLayoutParams().height = AndroidDeviceHelper.getHeightScreen(this) * 8 / 15;
            clDialogOrder.requestLayout();
            tvSuccess = dlgOrderDialog.findViewById(R.id.tvSuccess);
            tvDialogTittle = dlgOrderDialog.findViewById(R.id.tvResultCaculate);
            tvResult = dlgOrderDialog.findViewById(R.id.tvResult);
            ivBackKeyboard = dlgOrderDialog.findViewById(R.id.ivBackKeyboard);
            tvItemDown = dlgOrderDialog.findViewById(R.id.tvItemDown);
            tvItemUp = dlgOrderDialog.findViewById(R.id.tvItemUp);
            tvItemClear = dlgOrderDialog.findViewById(R.id.tvItemClear);
            tvItemSeven = dlgOrderDialog.findViewById(R.id.tvItemSeven);
            tvItemEight = dlgOrderDialog.findViewById(R.id.tvItemEight);
            tvItemNine = dlgOrderDialog.findViewById(R.id.tvItemNine);
            tvItemFour = dlgOrderDialog.findViewById(R.id.tvItemFour);
            tvItemFive = dlgOrderDialog.findViewById(R.id.tvItemFive);
            tvItemSix = dlgOrderDialog.findViewById(R.id.tvItemSix);
            tvItemOne = dlgOrderDialog.findViewById(R.id.tvItemOne);
            tvItemTwo = dlgOrderDialog.findViewById(R.id.tvItemTwo);
            tvItemThree = dlgOrderDialog.findViewById(R.id.tvItemThree);
            tvItemZero = dlgOrderDialog.findViewById(R.id.tvItemZero);
            tvSuccesss = dlgOrderDialog.findViewById(R.id.tvSuccesss);
            mActivityOrderPresenter = new ActivityOrderPresenter(this);
            onViewDialogClickListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lắng nghe xự kiện click view trong dialog
     *
     * @created_by cvmanh on 01/26/2021
     */
    private void onViewDialogClickListener() {
        try {
            ivBackKeyboard.setOnClickListener(this);
            tvItemDown.setOnClickListener(this);
            tvItemUp.setOnClickListener(this);
            tvItemClear.setOnClickListener(this);
            tvItemSeven.setOnClickListener(this);
            tvItemEight.setOnClickListener(this);
            tvItemNine.setOnClickListener(this);
            tvItemFour.setOnClickListener(this);
            tvItemFive.setOnClickListener(this);
            tvItemSix.setOnClickListener(this);
            tvItemOne.setOnClickListener(this);
            tvItemTwo.setOnClickListener(this);
            tvItemThree.setOnClickListener(this);
            tvItemZero.setOnClickListener(this);
            tvSuccess.setOnClickListener(this);
            tvSuccesss.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}