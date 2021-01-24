package vn.com.misa.ccl.view.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.OrderAdapter;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.presenter.FragmentListOrderPresenter;
import vn.com.misa.ccl.util.AndroidDeviceHelper;

public class FragmentListOrder extends Fragment implements View.OnClickListener, IFragmentListOrder.IFragmentListOrderView {

    private ImageView ivLogo;

    private TextView tvAddFood,tvNoOrder;

    private RecyclerView rcvOrder;

    private FragmentListOrderPresenter mFragmentListOrderPresenter;

    private List<OrderDetail> mListOrderDetail;

    private OrderAdapter mOrderAdapter;

    private LinearLayout llListOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list_order,container,false);

        initView(view);

        viewClickListener();

        getListOrder();

        checkListOrderSize();

        return view;
    }

    private void checkListOrderSize() {
        if(mListOrderDetail.size()<1){
            tvAddFood.setVisibility(View.VISIBLE);
            tvNoOrder.setVisibility(View.VISIBLE);
            ivLogo.setVisibility(View.VISIBLE);
            llListOrder.setBackgroundColor(getResources().getColor(R.color.white));
            return;
        }
        tvAddFood.setVisibility(View.GONE);
        tvNoOrder.setVisibility(View.GONE);
        ivLogo.setVisibility(View.GONE);
        llListOrder.setBackgroundColor(getResources().getColor(R.color.grey_lighh));
    }

    private void initView(View view) {
        ivLogo=view.findViewById(R.id.ivLogo);
        tvAddFood=view.findViewById(R.id.tvAddFood);
        tvNoOrder=view.findViewById(R.id.tvNoOrder);
        rcvOrder=view.findViewById(R.id.rcvOrder);
        llListOrder=view.findViewById(R.id.llListOrder);
    }

    private void viewClickListener(){
        tvAddFood.setOnClickListener(this);
    }

    private void getListOrder(){
        mFragmentListOrderPresenter=new FragmentListOrderPresenter(this);
        mFragmentListOrderPresenter.getListOrder(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAddFood:{
                startActivity(new Intent(getContext(),ActivityOrder.class));
                break;
            }
        }
    }

    @Override
    public void getListOrderSuccess(List<OrderDetail> listOrderDetail) {
        mListOrderDetail=listOrderDetail;
        mOrderAdapter=new OrderAdapter(getActivity(),R.layout.item_order,mListOrderDetail);
        rcvOrder.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        rcvOrder.setAdapter(mOrderAdapter);
        mOrderAdapter.notifyDataSetChanged();
//        tvAddFood.setVisibility(View.GONE);
//        tvNoOrder.setVisibility(View.GONE);
//        ivLogo.setVisibility(View.GONE);
//        llListOrder.setBackgroundColor(getResources().getColor(R.color.grey_lighh));
    }

    @Override
    public void onFailed() {
        Toast.makeText(getContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }
}
