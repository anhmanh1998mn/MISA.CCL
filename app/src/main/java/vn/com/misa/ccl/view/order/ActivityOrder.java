package vn.com.misa.ccl.view.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.presenter.ActivityOrderPresenter;
import vn.com.misa.ccl.util.AndroidDeviceHelper;
import vn.com.misa.ccl.view.manage.ActivityRestaurantManage;

/**
‐ Mục đích Class thực hiện những công việc tạo mới order
*
‐ {@link ActivityOrderPresenter}
*
‐ @created_by cvmanh on 01/25/2021
*/

public class ActivityOrder extends AppCompatActivity implements IActivityOrder.IActivityOrderView,
        MenuAdapter.ITotalAmount, View.OnClickListener {

    private ActivityOrderPresenter mActivityOrderPresenter;

    private Toolbar tbOrder;

    private RecyclerView rcvListFood;

    private MenuAdapter mMenuAdapter;

    private List<Product> mListProduct;

    private Dialog mOrderDialog;

    private String mResultSelect;

    private Button btnMoney;

    private int MCLICK_BUTTON_SAVE =1;

    private int MCLICK_BUTTON_MONEY=2;

    private String ORDER_ID="ORDER_ID";

    private TextView tvTotalAmount,tvSave,tvSelectTable,tvPeopeNumber, tvSuccess,tvDialogTittle,
            tvResult,tvItemDown,tvItemUp,tvItemClear,tvItemSeven,tvItemEight,tvItemNine,tvItemFour,
            tvItemFive,tvItemSix,tvItemOne,tvItemTwo,tvItemThree,tvItemZero,tvSuccesss;

    private ImageView ivBackKeyboard;

    private float mAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();

        getListMenu();

        onViewClickListener();
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void initView() {
        tbOrder=findViewById(R.id.tbOrder);
        setSupportActionBar(tbOrder);
        rcvListFood=findViewById(R.id.rcvListFood);
        tvTotalAmount=findViewById(R.id.tvTotalAmount);
        tvSave=findViewById(R.id.tvSave);
        tvSelectTable=findViewById(R.id.tvSelectTable);
        tvPeopeNumber=findViewById(R.id.tvPeopeNumber);
        btnMoney=findViewById(R.id.btnMoney);
    }

    /**
     * Mục đích method thực hiện gọi presenter xử lý lấy danh sách menu
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void getListMenu(){
        mActivityOrderPresenter=new ActivityOrderPresenter(this);
        mActivityOrderPresenter.getListMenu(this);
    }

    /**
     * Mục đích method thực hiện nhận danh sách product và hiển thị lên recycleView
     *
     * @param listProduct Danh sách product
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getListMenuSuccess(List<Product> listProduct) {
        mListProduct=listProduct;
        mMenuAdapter=new MenuAdapter(this,R.layout.item_select_food,mListProduct);
        rcvListFood.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rcvListFood.setAdapter(mMenuAdapter);
        mMenuAdapter.notifyDataSetChanged();
        mMenuAdapter.setmITotalAmount(this);
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý thao tác trên máy tính và hiển thị lên view
     *
     * @param result Kết quả thao tác trên máy tính
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getResultCaculateSuccess(String result) {
        tvResult.setText(result);
        mResultSelect=result;
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả thêm order thành công và chuyển sang activity khác
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void addNewOrderSuccess() {
        startActivity(new Intent(this, ActivityRestaurantManage.class));
    }

    /**
     * Mục đích method thực hiện nhận kết quả thêm mưới order và gửi mã order đến activity khác
     *
     * @param orderID mã order
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void addNewOrderTwoSuccess(int orderID) {
        Intent intent=new Intent(this, ActivityBill.class);
        intent.putExtra(ORDER_ID,orderID);
        startActivity(intent);
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
     * @param  totalMoney Tổng tiền order chưa được định dạng tiền tệ
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void processTotalAmount(String totalAmount,float totalMoney) {
        tvTotalAmount.setText(totalAmount);
        mAmount=totalMoney;
    }

    private void getItemSelect(){
    }

    /**
     * Mục đích method thực hiện việc lắng nghe xự kiện click view từ người dùng
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void onViewClickListener(){
        tvSave.setOnClickListener(this);
        tvPeopeNumber.setOnClickListener(this);
        tvSelectTable.setOnClickListener(this);
        btnMoney.setOnClickListener(this);
    }

    /**
     * Mục đích method thực hiện xử lý công việc theo view click
     *
     * @param view view
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvSave:{
                mActivityOrderPresenter=new ActivityOrderPresenter(this);
                mActivityOrderPresenter.addNewOrder(this,mListProduct,tvSelectTable.getText().toString(),tvPeopeNumber.getText().toString(),mAmount, MCLICK_BUTTON_SAVE);
//                for(int i=0;i<mListProduct.size();i++){
//                    if(mListProduct.get(i).getQuantity()>0){
//                        Log.d("ProductSelect",mListProduct.get(i).getmProductName()+":"+mListProduct.get(i).getQuantity());
//                    }
//                }
                break;
            }
            case R.id.btnMoney:{
                mActivityOrderPresenter=new ActivityOrderPresenter(this);
                mActivityOrderPresenter.addNewOrder(this,mListProduct,tvSelectTable.getText().toString(),tvPeopeNumber.getText().toString(),mAmount, MCLICK_BUTTON_MONEY);
//                startActivity(new Intent(this,ActivityBill.class));
//                SQLiteDatabase database= DatabaseHelper.initDatabase(this, DatabaseInfomation.DATABASE_NAME);
//                Cursor cursor=database.rawQuery("SELECT * FROM "+DatabaseInfomation.TABLE_ORDER_DETAIL+"",null);
//                Log.d("acccc",cursor.getCount()+"");
                break;
            }
            case R.id.tvSelectTable:{
                showDialogOrderInfomation();
                break;
            }
            case R.id.tvPeopeNumber:{
                showDialogOrderInfomation();
                tvSuccesss.setVisibility(View.VISIBLE);
                tvSuccess.setVisibility(View.GONE);
                tvDialogTittle.setText(getResources().getString(R.string.enter_people_name));
                break;
            }
            case R.id.ivBackKeyboard:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator),tvResult.getText().toString());
                break;
            }
            case R.id.tvItemDown:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_decrease),tvResult.getText().toString());
                break;
            }
            case R.id.tvItemUp:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_increase),tvResult.getText().toString());
                break;
            }
            case R.id.tvItemClear:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_clear),tvResult.getText().toString());
                break;
            }
            case R.id.tvItemSeven:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_7),tvResult.getText().toString());
                break;
            }
            case R.id.tvItemEight:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_8),tvResult.getText().toString());
                break;
            }
            case R.id.tvItemNine:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_9),tvResult.getText().toString());
                break;
            }
            case R.id.tvItemFour:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_4),tvResult.getText().toString());
                break;
            }
            case R.id.tvItemFive:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_5),tvResult.getText().toString());
                break;
            }
            case R.id.tvItemSix:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_6),tvResult.getText().toString());
                break;
            }
            case R.id.tvItemOne:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_1),tvResult.getText().toString());
                break;
            }
            case R.id.tvItemTwo:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_2),tvResult.getText().toString());
                break;
            }
            case R.id.tvItemThree:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_3),tvResult.getText().toString());
                break;
            }
            case R.id.tvItemZero:{
                mActivityOrderPresenter.getResultCaculate(getResources().getString(R.string.caculator_0),tvResult.getText().toString());
                break;
            }
            case R.id.tvSuccess:{
                mOrderDialog.dismiss();
                tvSelectTable.setText(mResultSelect);
                break;
            }
            case R.id.tvSuccesss:{
                mOrderDialog.dismiss();
                tvPeopeNumber.setText(mResultSelect);
                break;
            }
        }
    }

    /**
     * Mục đích method thực hiện việc hiển thị dialog chọn bàn và chọn số người
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void showDialogOrderInfomation(){
        mOrderDialog =new Dialog(this);
        mOrderDialog.setContentView(R.layout.dialog_order_infomation);
        mOrderDialog.show();
        ConstraintLayout clDialogOrder= mOrderDialog.findViewById(R.id.clDialogOrder);
        clDialogOrder.getLayoutParams().width= AndroidDeviceHelper.getWitdhScreen(this)-100;
        clDialogOrder.getLayoutParams().height= AndroidDeviceHelper.getHeightScreen(this)*8/15;
        clDialogOrder.requestLayout();
        tvSuccess = mOrderDialog.findViewById(R.id.tvSuccess);
        tvDialogTittle= mOrderDialog.findViewById(R.id.tvResultCaculate);
        tvResult= mOrderDialog.findViewById(R.id.tvResult);
        ivBackKeyboard= mOrderDialog.findViewById(R.id.ivBackKeyboard);
        tvItemDown= mOrderDialog.findViewById(R.id.tvItemDown);
        tvItemUp= mOrderDialog.findViewById(R.id.tvItemUp);
        tvItemClear= mOrderDialog.findViewById(R.id.tvItemClear);
        tvItemSeven= mOrderDialog.findViewById(R.id.tvItemSeven);
        tvItemEight= mOrderDialog.findViewById(R.id.tvItemEight);
        tvItemNine= mOrderDialog.findViewById(R.id.tvItemNine);
        tvItemFour= mOrderDialog.findViewById(R.id.tvItemFour);
        tvItemFive= mOrderDialog.findViewById(R.id.tvItemFive);
        tvItemSix= mOrderDialog.findViewById(R.id.tvItemSix);
        tvItemOne= mOrderDialog.findViewById(R.id.tvItemOne);
        tvItemTwo= mOrderDialog.findViewById(R.id.tvItemTwo);
        tvItemThree= mOrderDialog.findViewById(R.id.tvItemThree);
        tvItemZero= mOrderDialog.findViewById(R.id.tvItemZero);
        tvSuccesss=mOrderDialog.findViewById(R.id.tvSuccesss);
        mActivityOrderPresenter=new ActivityOrderPresenter(this);
        onViewDialogClickListener();
    }

    /**
     * Mục đích method thực hiện việc lắng nghe xự kiện click view trong dialog
     *
     * @created_by cvmanh on 01/26/2021
     */
    private void onViewDialogClickListener(){
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
    }
}