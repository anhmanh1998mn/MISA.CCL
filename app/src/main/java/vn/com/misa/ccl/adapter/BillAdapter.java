package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.OrderDetail;

/**
 * ‐ Mục đích Class thực hiện việc quy định dữ liệu và cách hiển thị lên view
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.order.ActivityBill}
 * ‐ {@link OrderDetail}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.Viewholder> {
    private Activity mContext;

    private int mlayout;

    private List<OrderDetail> mListOrderDetail;

    public BillAdapter(Activity mContext, int mlayout, List<OrderDetail> mListOrderDetail) {
        this.mContext = mContext;
        this.mlayout = mlayout;
        this.mListOrderDetail = mListOrderDetail;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mlayout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            holder.tvProductName.setText(mListOrderDetail.get(position).getProduct().getProductName());
            holder.tvAmount.setText(String.valueOf(mListOrderDetail.get(position).getQuantity()));
            holder.tvUnitPrice.setText(decimalFormat.format(mListOrderDetail.get(position).getProductPriceOut()));
            holder.tvTotalMoney.setText(decimalFormat.format(mListOrderDetail.get(position).getQuantity() * mListOrderDetail.get(position).getProductPriceOut()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mListOrderDetail.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView tvProductName, tvAmount, tvUnitPrice, tvTotalMoney;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            try {
                tvProductName = itemView.findViewById(R.id.tvProductName);
                tvAmount = itemView.findViewById(R.id.tvAmount);
                tvUnitPrice = itemView.findViewById(R.id.tvUnitPrice);
                tvTotalMoney = itemView.findViewById(R.id.tvTotalMoney);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
