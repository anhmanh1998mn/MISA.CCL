package vn.com.misa.ccl.view.order;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

/**
 * ‐ Mục đích Class thực hiện những công việc hiển thị danh sách các order chưa thu tiền
 * <p>
 * ‐ {@link FragmentListOrderPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/26/2021
 */

public class FragmentListOrder extends Fragment implements View.OnClickListener, IFragmentListOrder.IFragmentListOrderView,
        OrderAdapter.ICLickButtonRemove {

    private ImageView ivLogo;

    private TextView tvAddFood, tvNoOrder;

    private RecyclerView rcvOrder;

    private FragmentListOrderPresenter mFragmentListOrderPresenter;

    private List<OrderDetail> mListOrderDetail;

    private OrderAdapter mOrderAdapter;

    private LinearLayout llListOrder;

    private Dialog mDialogConfirmRemove;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_order, container, false);

        initView(view);

        viewClickListener();

        getListOrder();

        checkListOrderSize();

        return view;
    }

    @Override
    public void onResume() {
        getListOrder();

        checkListOrderSize();
        super.onResume();
    }


    /**
     * Mục đích method thực hiện việc kiể tra số lượng danh sách order để ẩn hiện các view tương ứng
     *
     * @created_by cvmanh on 01/26/2021
     */
    private void checkListOrderSize() {
        try {
            if (mListOrderDetail.size() < 1) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @param view view
     * @created_by cvmanh on 01/26/2021
     */
    private void initView(View view) {
        try {
            ivLogo = view.findViewById(R.id.ivLogo);
            tvAddFood = view.findViewById(R.id.tvAddFood);
            tvNoOrder = view.findViewById(R.id.tvNoOrder);
            rcvOrder = view.findViewById(R.id.rcvOrder);
            llListOrder = view.findViewById(R.id.llListOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lắng nghe xự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/26/2021
     */
    private void viewClickListener() {
        try {
            tvAddFood.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getListOrder() {
        try {
            mFragmentListOrderPresenter = new FragmentListOrderPresenter(this);
            mFragmentListOrderPresenter.getListOrder(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện công việc theo view click
     *
     * @param view view
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.tvAddFood: {
                    Intent intent = new Intent(getContext(), ActivityOrder.class);
                    intent.putExtra("ORDER_ID", -1);
                    startActivity(intent);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận danh sách order và hiển thị lên recycleView
     *
     * @param listOrderDetail Danh sách orderDetail
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void getListOrderSuccess(List<OrderDetail> listOrderDetail) {
        try {
            mListOrderDetail = listOrderDetail;
            mOrderAdapter = new OrderAdapter(getActivity(), R.layout.item_order, mListOrderDetail);
            rcvOrder.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            rcvOrder.setAdapter(mOrderAdapter);
            mOrderAdapter.notifyDataSetChanged();
            mOrderAdapter.setmICLickButtonRemove(this);
            checkListOrderSize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả xóa order thành công và tắt dialog
     *
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void removeOrderSuccess() {
        try {
            getListOrder();
            mDialogConfirmRemove.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả xóa order thất bại
     *
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void onFailed() {
        Toast.makeText(getContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method thực hiện việc nhân mà order từ adapter và gọi presenter xóa order
     *
     * @param orderID Mã order
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void setOnClickButtoRemove(int orderID) {
        try {
            mFragmentListOrderPresenter = new FragmentListOrderPresenter(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getResources().getString(R.string.app));
            builder.setMessage(getResources().getString(R.string.remove_confirm));
            builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mFragmentListOrderPresenter.removeItemOrder(getActivity(), orderID);
                }
            });
            mDialogConfirmRemove = builder.create();
            mDialogConfirmRemove.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
