package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.view.order.ActivityBill;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Activity mContext;

    private int mLayout;

    private List<OrderDetail> mListOrderDetail;

    private String ORDER_ID="ORDER_ID";

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
        holder.tvListProduct.setText(mListOrderDetail.get(position).getmProduct().getmProductName());
//        holder.tvImage.getBackground().setTint(Color.parseColor(mListOrderDetail.get(position).getmProduct().getmColor().getColorName()));
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.tvTotalAmount.setText(decimalFormat.format((mListOrderDetail.get(position).getmOrder().getTotalMoney())));
        if(mListOrderDetail.get(position).getmOrder().getTableName().equals("0")){
            holder.tvImage.getBackground().setTint(mContext.getResources().getColor(R.color.light_grey));
            holder.tvImage.setText("");
        }else {
            holder.tvImage.getBackground().setTint(Color.parseColor(mListOrderDetail.get(position).getmProduct().getmColor().getColorName()));
            holder.tvImage.setText(mListOrderDetail.get(position).getmOrder().getTableName());
        }

        if(mListOrderDetail.get(position).getmOrder().getTotalPeople()==0){
            holder.tvPeople.setVisibility(View.GONE);
            holder.ivPerson.setVisibility(View.GONE);
        }else {
            holder.tvPeople.setText(String.valueOf(mListOrderDetail.get(position).getmOrder().getTotalPeople()));
        }

        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mICLickButtonRemove.setOnClickButtoRemove(mListOrderDetail.get(position).getmOrder().getOrderId());
            }
        });

        holder.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ActivityBill.class);
                intent.putExtra(ORDER_ID,mListOrderDetail.get(position).getmOrder().getOrderId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListOrderDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvImage, tvPeople, tvListProduct, tvTotalAmount,tvCancel,tvSave;

        private ImageView ivPerson;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvImage = itemView.findViewById(R.id.tvImage);
            tvPeople = itemView.findViewById(R.id.tvPeople);
            tvListProduct = itemView.findViewById(R.id.tvListProduct);
            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
            ivPerson=itemView.findViewById(R.id.ivPerson);
            tvCancel=itemView.findViewById(R.id.tvCancel);
            tvSave=itemView.findViewById(R.id.tvSave);
        }
    }

    public interface ICLickButtonRemove{
        public void setOnClickButtoRemove(int orderID);
    }
}
