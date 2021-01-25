package vn.com.misa.ccl.view.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.BillAdapter;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.presenter.ActivityBillPresenter;
import vn.com.misa.ccl.util.AndroidDeviceHelper;

public class ActivityBill extends AppCompatActivity implements IActivityBill.IActivityBillView, View.OnClickListener {

    private Toolbar mHeaderBill;

    private String ORDER_ID="ORDER_ID";

    private TextView tvOrderID,tvTableName,tvCreatedAt,tvTotal,tvMoneyIn,tvMoneyOut,tvDialogTittle;

    private RecyclerView rcvListOrderInfomation;

    private Button btnSuccess;

    private BillAdapter mBillAdapter;

    private List<OrderDetail> mListOrderDetail;

    private ActivityBillPresenter mActivityBillPresenter;

    private Dialog mBillCaculate;

    private ConstraintLayout clBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        initActionBar();

        initView();

        getOrderDetail();

        onViewClickListener();
    }

    private void initActionBar() {
        mHeaderBill=findViewById(R.id.tbBill);
        setSupportActionBar(mHeaderBill);
    }

    private void initView(){
        tvOrderID=findViewById(R.id.tvOrderID);
        tvTableName=findViewById(R.id.tvTableName);
        tvCreatedAt=findViewById(R.id.tvCreatedAt);
        rcvListOrderInfomation=findViewById(R.id.rcvListOrderInfomation);
        tvTotal=findViewById(R.id.tvTotal);
        tvMoneyIn=findViewById(R.id.tvMoneyIn);
        tvMoneyOut=findViewById(R.id.tvMoneyOut);
        btnSuccess=findViewById(R.id.btnSuccess);
    }

    private void getOrderDetail(){
        Intent intent=getIntent();
//        Log.d("bbb",intent.getIntExtra(ORDER_ID,-1)+"");
        mActivityBillPresenter=new ActivityBillPresenter(this);
        mActivityBillPresenter.getOrderDetail(this,intent.getIntExtra(ORDER_ID,-1));
    }

    @Override
    public void getOrderDetailSuccess(List<OrderDetail> listOrderDetail) {
        mListOrderDetail=listOrderDetail;
        mBillAdapter=new BillAdapter(this,R.layout.item_bill,mListOrderDetail);
        rcvListOrderInfomation.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rcvListOrderInfomation.setAdapter(mBillAdapter);
        mBillAdapter.notifyDataSetChanged();
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tvTotal.setText(decimalFormat.format(mListOrderDetail.get(0).getmOrder().getTotalMoney()));
        tvTableName.setText(mListOrderDetail.get(0).getmOrder().getTableName());
        tvOrderID.setText(getResources().getString(R.string.number)+"  "+ mListOrderDetail.get(0).getmOrder().getOrderId());
        tvCreatedAt.setText(mListOrderDetail.get(0).getmOrder().getCreatedAt());
    }

    @Override
    public void onFailed() {
        Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    private void onViewClickListener(){
        tvMoneyIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvMoneyIn:{
                showBillCaculate();
                break;
            }
        }
    }

    private void showBillCaculate() {
        mBillCaculate=new Dialog(this);
        mBillCaculate.setContentView(R.layout.dialog_bill_caculate);
        mBillCaculate.show();
        initViewDialog();
        clBill.getLayoutParams().width= AndroidDeviceHelper.getWitdhScreen(this)-100;
        clBill.getLayoutParams().height= AndroidDeviceHelper.getHeightScreen(this)*10/18;
        clBill.requestLayout();
    }

    private void initViewDialog() {
        tvDialogTittle=mBillCaculate.findViewById(R.id.tvDialogTittle);
        clBill=mBillCaculate.findViewById(R.id.clBill);
    }
}