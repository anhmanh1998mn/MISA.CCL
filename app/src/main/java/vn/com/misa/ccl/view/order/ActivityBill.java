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

import java.text.DecimalFormat;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.BillAdapter;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.presenter.ActivityBillPresenter;
import vn.com.misa.ccl.util.AndroidDeviceHelper;

/**
 * ‐ Mục đích Class thực hiện những công việc hiển thị chi tiết hóa đơn
 * <p>
 * ‐ {@link ActivityBillPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class ActivityBill extends AppCompatActivity implements IActivityBill.IActivityBillView, View.OnClickListener {

    private Toolbar mHeaderBill;

    private String ORDER_ID = "ORDER_ID";

    private TextView tvOrderID, tvTableName, tvCreatedAt, tvTotal, tvMoneyIn, tvMoneyOut, tvDialogTittle, tvMoneyOne,
            tvMonetTwo, tvMoneyFour, tvMonetFive;

    private RecyclerView rcvListOrderInfomation;

    private Button btnSuccess;

    private BillAdapter mBillAdapter;

    private List<OrderDetail> mListOrderDetail;

    private ActivityBillPresenter mActivityBillPresenter;

    private Dialog mBillCaculate;

    private ConstraintLayout clBill;

    private ImageView ivBackKeyboard;

    private int mOrderID;

    private TextView tvResultCaculate, tvItemSeven, tvItemEight, tvItemNine, tvItemFour, tvItemFive, tvItemSix, tvItemClear,
            tvItemOne, tvItemTwo, tvItemThree, tvItemDot, tvItemZero, tvItemZeroo, tvSuccess,tvBack,tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        initActionBar();

        initView();

        getOrderDetail();

        onViewClickListener();
    }

    /**
     * Mục đích method thực hiện những công việc khởi tạo actionBar
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void initActionBar() {
        try {
            mHeaderBill = findViewById(R.id.tbBill);
            setSupportActionBar(mHeaderBill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện những công việc khởi tạo các view
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void initView() {
        try {
            tvOrderID = findViewById(R.id.tvOrderID);
            tvTableName = findViewById(R.id.tvTableName);
            tvCreatedAt = findViewById(R.id.tvCreatedAt);
            rcvListOrderInfomation = findViewById(R.id.rcvListOrderInfomation);
            tvTotal = findViewById(R.id.tvTotal);
            tvMoneyIn = findViewById(R.id.tvMoneyIn);
            tvMoneyOut = findViewById(R.id.tvMoneyOut);
            btnSuccess = findViewById(R.id.btnSuccess);
            tvBack=findViewById(R.id.tvBack);
            tvNext=findViewById(R.id.tvNext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện những công việc lấy mã order và gọi model lấy chi tiết order theo mã order
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void getOrderDetail() {
        try {
            Intent intent = getIntent();
            mActivityBillPresenter = new ActivityBillPresenter(this);
            mActivityBillPresenter.getOrderDetail(this, intent.getIntExtra(ORDER_ID, -1));
            mOrderID = intent.getIntExtra(ORDER_ID, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện những công việc nhận danh sách orderDetail và hiển thị kết quả lên view
     *
     * @param listOrderDetail Danh sách orderDetail
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getOrderDetailSuccess(List<OrderDetail> listOrderDetail) {
        try {
            mListOrderDetail = listOrderDetail;
            mBillAdapter = new BillAdapter(this, R.layout.item_bill, mListOrderDetail);
            rcvListOrderInfomation.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rcvListOrderInfomation.setAdapter(mBillAdapter);
            mBillAdapter.notifyDataSetChanged();
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            tvTotal.setText(decimalFormat.format(mListOrderDetail.get(0).getmOrder().getTotalMoney()));
            tvTableName.setText(mListOrderDetail.get(0).getmOrder().getTableName());
            tvOrderID.setText(getResources().getString(R.string.number) + "  " + mListOrderDetail.get(0).getmOrder().getOrderId());
            tvCreatedAt.setText(mListOrderDetail.get(0).getmOrder().getCreatedAt());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý hiển thị kết quả nhập trên máy tính và hiên thị lên view
     *
     * @param result số tiền nhận từ người dùng
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void resultEnterProcessSuccess(String result) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            tvResultCaculate.setText(result);
            tvMoneyIn.setText(decimalFormat.format(Integer.parseInt(result)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý tiền thừa từ presenter và hiển thị lên view
     *
     * @param moneyOut Tiền thừa trả lại khách
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void resultMoneyOutSuccess(String moneyOut) {
        try {
            mBillCaculate.dismiss();
            tvMoneyOut.setText((moneyOut));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrderStatusSuccess() {
        try {
            startActivity(new Intent(this, ActivityOrder.class));
            finish();
            Toast.makeText(this, getResources().getString(R.string.update_status), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý thất bại và hiển thị thông báo cho người dùng
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void onFailed() {
        Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method thực hiện lắng nghe xự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void onViewClickListener() {
        try {
            tvMoneyIn.setOnClickListener(this);
            btnSuccess.setOnClickListener(this);
            tvBack.setOnClickListener(this);
            tvNext.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện xử lý công việc theo vew được click
     *
     * @param view view
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.tvMoneyIn: {
                    showBillCaculate();
                    break;
                }
                case R.id.tvMoneyOne: {
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tvMoneyIn.setText(decimalFormat.format(mListOrderDetail.get(0).getmOrder().getTotalMoney() + 10000));
                    tvMoneyOut.setText(decimalFormat.format(10000));
                    mBillCaculate.dismiss();
                    break;
                }
                case R.id.tvMonetTwo: {
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tvMoneyIn.setText(decimalFormat.format(mListOrderDetail.get(0).getmOrder().getTotalMoney() + 30000));
                    tvMoneyOut.setText(decimalFormat.format(30000));
                    mBillCaculate.dismiss();
                    break;
                }
                case R.id.tvMoneyFour: {
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tvMoneyIn.setText(decimalFormat.format(mListOrderDetail.get(0).getmOrder().getTotalMoney() + 40000));
                    tvMoneyOut.setText(decimalFormat.format(40000));
                    mBillCaculate.dismiss();
                    break;
                }
                case R.id.tvMonetFive: {
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tvMoneyIn.setText(decimalFormat.format(mListOrderDetail.get(0).getmOrder().getTotalMoney() + 50000));
                    tvMoneyOut.setText(decimalFormat.format(50000));
                    mBillCaculate.dismiss();
                    break;
                }
                case R.id.tvItemSeven: {
                    processCaculate(tvItemSeven.getText().toString());
                    break;
                }
                case R.id.tvItemEight: {
                    processCaculate(tvItemEight.getText().toString());
                    break;
                }
                case R.id.tvItemNine: {
                    processCaculate(tvItemNine.getText().toString());
                    break;
                }
                case R.id.ivBackKeyboard: {
                    processCaculate("");
                    break;
                }
                case R.id.tvItemFour: {
                    processCaculate(tvItemFour.getText().toString());
                    break;
                }
                case R.id.tvItemFive: {
                    processCaculate(tvItemFive.getText().toString());
                    break;
                }
                case R.id.tvItemSix: {
                    processCaculate(tvItemSix.getText().toString());
                    break;
                }
                case R.id.tvItemClear: {
                    processCaculate(tvItemClear.getText().toString());
                    break;
                }
                case R.id.tvItemOne: {
                    processCaculate(tvItemOne.getText().toString());
                    break;
                }
                case R.id.tvItemTwo: {
                    processCaculate(tvItemTwo.getText().toString());
                    break;
                }
                case R.id.tvItemThree: {
                    processCaculate(tvItemThree.getText().toString());
                    break;
                }
                case R.id.tvItemDot: {
                    processCaculate(tvItemDot.getText().toString());
                    break;
                }
                case R.id.tvItemZero: {
                    processCaculate(tvItemZero.getText().toString());
                    break;
                }
                case R.id.tvItemZeroo: {
                    processCaculate(tvItemZeroo.getText().toString());
                    break;
                }
                case R.id.tvSuccess: {
                    processCaculate(tvSuccess.getText().toString());
                    break;
                }
                case R.id.btnSuccess: {
                    mActivityBillPresenter = new ActivityBillPresenter(this);
                    mActivityBillPresenter.updateOrderStatus(this, mOrderID);
                    break;
                }
                case R.id.tvBack:{
                    finish();
                    break;
                }
                case R.id.tvNext:{
                    mActivityBillPresenter = new ActivityBillPresenter(this);
                    mActivityBillPresenter.updateOrderStatus(this, mOrderID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện gọi presenter xử lý kết quả thao tác trên máy tính
     *
     * @param nameClick value button click
     * @created_by cvmanh on 01/25/2021
     */
    private void processCaculate(String nameClick) {
        try {
            mActivityBillPresenter = new ActivityBillPresenter(this);
            mActivityBillPresenter.processCaculator(this, tvResultCaculate.getText().toString().trim(),
                    nameClick, mListOrderDetail.get(0).getmOrder().getTotalMoney());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện hiển thị dialog máy tính
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void showBillCaculate() {
        try {
            mBillCaculate = new Dialog(this);
            mBillCaculate.setContentView(R.layout.dialog_bill_caculate);
            mBillCaculate.show();
            initViewDialog();
            clBill.getLayoutParams().width = AndroidDeviceHelper.getWitdhScreen(this) - 100;
            clBill.getLayoutParams().height = AndroidDeviceHelper.getHeightScreen(this) * 10 / 18;
            clBill.requestLayout();
            showSuggestMoney();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view trong dialog
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void initViewDialog() {
        try {
            tvDialogTittle = mBillCaculate.findViewById(R.id.tvResultCaculate);
            clBill = mBillCaculate.findViewById(R.id.clBill);
            tvMoneyOne = mBillCaculate.findViewById(R.id.tvMoneyOne);
            tvMonetTwo = mBillCaculate.findViewById(R.id.tvMonetTwo);
            tvMoneyFour = mBillCaculate.findViewById(R.id.tvMoneyFour);
            tvMonetFive = mBillCaculate.findViewById(R.id.tvMonetFive);
            tvResultCaculate = mBillCaculate.findViewById(R.id.tvResultCaculate);
            tvItemSeven = mBillCaculate.findViewById(R.id.tvItemSeven);
            tvItemEight = mBillCaculate.findViewById(R.id.tvItemEight);
            tvItemNine = mBillCaculate.findViewById(R.id.tvItemNine);
            ivBackKeyboard = mBillCaculate.findViewById(R.id.ivBackKeyboard);
            tvItemFour = mBillCaculate.findViewById(R.id.tvItemFour);
            tvItemSix = mBillCaculate.findViewById(R.id.tvItemSix);
            tvItemFive = mBillCaculate.findViewById(R.id.tvItemFive);
            tvItemClear = mBillCaculate.findViewById(R.id.tvItemClear);
            tvItemOne = mBillCaculate.findViewById(R.id.tvItemOne);
            tvItemTwo = mBillCaculate.findViewById(R.id.tvItemTwo);
            tvItemThree = mBillCaculate.findViewById(R.id.tvItemThree);
            tvItemDot = mBillCaculate.findViewById(R.id.tvItemDot);
            tvItemZero = mBillCaculate.findViewById(R.id.tvItemZero);
            tvItemZeroo = mBillCaculate.findViewById(R.id.tvItemZeroo);
            tvSuccess = mBillCaculate.findViewById(R.id.tvSuccess);
            onViewDialogClickListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện lắng nghe xự kiện click view trong dialog
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void onViewDialogClickListener() {
        try {
            tvMoneyOne.setOnClickListener(this);
            tvMonetTwo.setOnClickListener(this);
            tvMoneyFour.setOnClickListener(this);
            tvMonetFive.setOnClickListener(this);
            tvItemSeven.setOnClickListener(this);
            tvItemEight.setOnClickListener(this);
            tvItemNine.setOnClickListener(this);
            ivBackKeyboard.setOnClickListener(this);
            tvItemFour.setOnClickListener(this);
            tvItemFive.setOnClickListener(this);
            tvItemSix.setOnClickListener(this);
            tvItemClear.setOnClickListener(this);
            tvItemOne.setOnClickListener(this);
            tvItemTwo.setOnClickListener(this);
            tvItemThree.setOnClickListener(this);
            tvItemDot.setOnClickListener(this);
            tvItemZero.setOnClickListener(this);
            tvItemZeroo.setOnClickListener(this);
            tvSuccess.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc hiển thị số tiền gợi ý
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void showSuggestMoney() {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            tvMoneyOne.setText(decimalFormat.format(mListOrderDetail.get(0).getmOrder().getTotalMoney() + 10000));
            tvMonetTwo.setText(decimalFormat.format(mListOrderDetail.get(0).getmOrder().getTotalMoney() + 30000));
            tvMoneyFour.setText(decimalFormat.format(mListOrderDetail.get(0).getmOrder().getTotalMoney() + 40000));
            tvMonetFive.setText(decimalFormat.format(mListOrderDetail.get(0).getmOrder().getTotalMoney() + 50000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}