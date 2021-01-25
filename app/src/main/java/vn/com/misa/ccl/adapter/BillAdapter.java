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
        View view= LayoutInflater.from(mContext).inflate(mlayout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.tvProductName.setText(mListOrderDetail.get(position).getmProduct().getmProductName());
        holder.tvAmount.setText(String.valueOf(mListOrderDetail.get(position).getmQuantity()));
        holder.tvUnitPrice.setText(decimalFormat.format(mListOrderDetail.get(position).getmProduct().getmProductPrice()));
        holder.tvTotalMoney.setText(decimalFormat.format(mListOrderDetail.get(position).getmQuantity()*mListOrderDetail.get(position).getmProduct().getmProductPrice()));

    }


    @Override
    public int getItemCount() {
        return mListOrderDetail.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView tvProductName,tvAmount,tvUnitPrice,tvTotalMoney;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tvProductName=itemView.findViewById(R.id.tvProductName);
            tvAmount=itemView.findViewById(R.id.tvAmount);
            tvUnitPrice=itemView.findViewById(R.id.tvUnitPrice);
            tvTotalMoney=itemView.findViewById(R.id.tvTotalMoney);
        }
    }
}
