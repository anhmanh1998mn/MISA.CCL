package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.util.Common;
import vn.com.misa.ccl.view.login.ActivitySelectionOptionLogin;
import vn.com.misa.ccl.view.order.ActivityBill;
import vn.com.misa.ccl.view.order.ActivityOrder;

import static android.content.Context.MODE_PRIVATE;

/**
 * ‐ Mục đích Class thực hiện việc quy định dữ liệu và cách hiển thị lên view
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.order.ActivityOrder}
 * ‐ {@link OrderDetail}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Activity mContext;

    private int mLayout;

    private List<OrderDetail> mListOrderDetail;

    private String ORDER_ID = "ORDER_ID";

    private String TABLE_NAME = "TABLE_NAME";

    private String TOTAL_NUMBER = "TOTAL_NUMBER";

    private String AMOUNT = "AMOUNT";

    private ICLickButtonRemove mICLickButtonRemove;

    public ICLickButtonRemove getmICLickButtonRemove() {
        return mICLickButtonRemove;
    }

    public void setmICLickButtonRemove(ICLickButtonRemove mICLickButtonRemove) {
        this.mICLickButtonRemove = mICLickButtonRemove;
    }

    public OrderAdapter(Activity mContext, int mLayout, List<OrderDetail> mListOrderDetail) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListOrderDetail = mListOrderDetail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tvListProduct.setText(mListOrderDetail.get(position).getProduct().getProductName());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            holder.tvTotalAmount.setText(decimalFormat.format((mListOrderDetail.get(position).getOrder().getTotalMoney())));
            if (mListOrderDetail.get(position).getOrder().getTableName().equals("0")) {
                holder.tvImage.getBackground().setTint(mContext.getResources().getColor(R.color.light_grey));
                holder.tvImage.setText("");
            } else {
                holder.tvImage.getBackground().setTint(Color.parseColor(mListOrderDetail.get(position).getProduct().getColor().getColorName()));
                holder.tvImage.setText(mListOrderDetail.get(position).getOrder().getTableName());
            }

            if (mListOrderDetail.get(position).getOrder().getTotalPeople() == 0) {
                holder.tvPeople.setVisibility(View.GONE);
                holder.ivPerson.setVisibility(View.GONE);
            } else {
                holder.tvPeople.setText(String.valueOf(mListOrderDetail.get(position).getOrder().getTotalPeople()));
            }

            holder.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mICLickButtonRemove.setOnClickButtoRemove(mListOrderDetail.get(position).getOrder().getOrderId());
                }
            });

            holder.tvSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences("SHOPINFOMATION", MODE_PRIVATE);
                    if (sharedPreferences.getString("SHOP_ID", "").equals("")) {
                        Intent intent = new Intent(mContext, ActivitySelectionOptionLogin.class);
                        intent.putExtra("TypeIntent", "ActivityManage");
                        mContext.startActivity(intent);
                        return;
                    }
                    Intent intent = new Intent(mContext, ActivityBill.class);
                    intent.putExtra(ORDER_ID, mListOrderDetail.get(position).getOrder().getOrderId());
                    mContext.startActivity(intent);
                }
            });

            holder.llItemOder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ActivityOrder.class);
                    intent.putExtra(ORDER_ID, mListOrderDetail.get(position).getOrder().getOrderId());
                    intent.putExtra(TABLE_NAME, mListOrderDetail.get(position).getOrder().getTableName());
                    intent.putExtra(TOTAL_NUMBER, mListOrderDetail.get(position).getOrder().getTotalPeople());
                    intent.putExtra(AMOUNT, mListOrderDetail.get(position).getOrder().getTotalMoney());
                    Common.sOrderID = mListOrderDetail.get(position).getOrder().getOrderId();
                    mContext.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListOrderDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvImage, tvPeople, tvListProduct, tvTotalAmount, tvCancel, tvSave;

        private ImageView ivPerson;

        private LinearLayout llItemOder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            try {
                tvImage = itemView.findViewById(R.id.tvImage);
                tvPeople = itemView.findViewById(R.id.tvPeople);
                tvListProduct = itemView.findViewById(R.id.tvListProduct);
                tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
                ivPerson = itemView.findViewById(R.id.ivPerson);
                tvCancel = itemView.findViewById(R.id.tvCancel);
                tvSave = itemView.findViewById(R.id.tvSave);
                llItemOder = itemView.findViewById(R.id.llItemOder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface ICLickButtonRemove {
        public void setOnClickButtoRemove(int orderID);
    }
}
