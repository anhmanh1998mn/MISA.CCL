package vn.com.misa.ccl.view.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.MenuAdapter;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.presenter.ActivityOrderPresenter;

public class ActivityOrder extends AppCompatActivity implements IActivityOrder.IActivityOrderView,
        MenuAdapter.ITotalAmount {

    private ActivityOrderPresenter mActivityOrderPresenter;

    private Toolbar tbOrder;

    private RecyclerView rcvListFood;

    private MenuAdapter mMenuAdapter;

    private List<Product> mListProduct;

    private TextView tvTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();

        getListMenu();

    }

    private void initView() {
        tbOrder=findViewById(R.id.tbOrder);
        setSupportActionBar(tbOrder);
        rcvListFood=findViewById(R.id.rcvListFood);
        tvTotalAmount=findViewById(R.id.tvTotalAmount);
    }

    private void getListMenu(){
        mActivityOrderPresenter=new ActivityOrderPresenter(this);
        mActivityOrderPresenter.getListMenu(this);
    }

    @Override
    public void getListMenuSuccess(List<Product> listProduct) {
        mListProduct=listProduct;
        mMenuAdapter=new MenuAdapter(this,R.layout.item_select_food,mListProduct);
        rcvListFood.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rcvListFood.setAdapter(mMenuAdapter);
        mMenuAdapter.notifyDataSetChanged();
        mMenuAdapter.setmITotalAmount(this);
    }

    @Override
    public void onFailed() {
        Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void processTotalAmount(String totalAmount) {
        tvTotalAmount.setText(totalAmount);
    }

    @Override
    public void getQuantity(int quantity) {
    }
}