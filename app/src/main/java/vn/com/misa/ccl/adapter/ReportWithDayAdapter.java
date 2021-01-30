package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.graphics.Color;
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
 * ‐ Mục đích Class thực hiện quy định cách dữ liệu được hiển thị lên view
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.report.day.ActivityReportWithDay}
 * <p>
 * ‐ @created_by cvmanh on 01/28/2021
 */

public class ReportWithDayAdapter extends RecyclerView.Adapter<ReportWithDayAdapter.ViewHolder> {

    private Activity mContext;

    private int mLayout;

    private List<OrderDetail> mListReportWithDay;

    public ReportWithDayAdapter(Activity mContext, int mLayout, List<OrderDetail> mListReportWithDay) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListReportWithDay = mListReportWithDay;
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
            switch (position) {
                case 0: {
                    holder.tvImage.getBackground().setTint((mContext.getResources().getColor(R.color.light_bluee)));
                    break;
                }
                case 1: {
                    holder.tvImage.getBackground().setTint((mContext.getResources().getColor(R.color.green_light)));
                    break;
                }
                case 2: {
                    holder.tvImage.getBackground().setTint((mContext.getResources().getColor(R.color.red)));
                    break;
                }
                case 3: {
                    holder.tvImage.getBackground().setTint((mContext.getResources().getColor(R.color.orange)));
                    break;
                }
                case 4: {
                    holder.tvImage.getBackground().setTint((mContext.getResources().getColor(R.color.dark_blue)));
                    break;
                }
                case 5: {
                    holder.tvImage.getBackground().setTint((mContext.getResources().getColor(R.color.dark_blue_face)));
                    break;
                }
                default:
                    holder.tvImage.getBackground().setTint((mContext.getResources().getColor(R.color.light_grey)));
            }

            holder.tvImage.setText(String.valueOf(position + 1));
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            holder.tvFootName.setText(mListReportWithDay.get(position).getmProduct().getmProductName());
            holder.tvFootQuantity.setText(String.valueOf(mListReportWithDay.get(position).getmQuantity()));
            holder.tvFootPrice.setText(decimalFormat.format(mListReportWithDay.get(position).getmProductPriceOut()));
            holder.tvFootUnit.setText(mListReportWithDay.get(position).getmProduct().getmUnit().getmUnitName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListReportWithDay.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvImage, tvFootName, tvFootQuantity, tvFootPrice, tvFootUnit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            try {
                tvImage = itemView.findViewById(R.id.tvImage);
                tvFootName = itemView.findViewById(R.id.tvFootName);
                tvFootQuantity = itemView.findViewById(R.id.tvFootQuantity);
                tvFootPrice = itemView.findViewById(R.id.tvFootPrice);
                tvFootUnit = itemView.findViewById(R.id.tvFootUnit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
