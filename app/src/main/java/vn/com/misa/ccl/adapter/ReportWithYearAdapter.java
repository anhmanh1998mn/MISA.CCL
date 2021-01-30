package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.Report;
import vn.com.misa.ccl.view.report.day.ActivityReportWithDay;

/**
 * ‐ Mục đích Class thực hiện khởi tạo, quy định dữ liệu và cách thức hiển thị trên view
 * <p>
 * ‐ {@link ActivityReportWithDay}
 * <p>
 * ‐ @created_by cvmanh on 01/30/2021
 */

public class ReportWithYearAdapter extends RecyclerView.Adapter<ReportWithYearAdapter.ViewHolder> {

    private Activity mContext;

    private int mLayout;

    private List<Report> mListReport;

    public ReportWithYearAdapter(Activity mContext, int mLayout, List<Report> mListReport) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListReport = mListReport;
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
            holder.tvDayName.setText("Tháng " + mListReport.get(position).getDayOfWeek());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            holder.tvTotalReport.setText(decimalFormat.format(mListReport.get(position).getTotalMoney()));
            holder.clItemReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ActivityReportWithDay.class);
                    intent.putExtra("REPORT_TYPE", 4);// 4: item thống kê theo năm
                    intent.putExtra("AMOUNT_FLOAT", mListReport.get(position).getTotalMoney());
                    intent.putExtra("YEAR_NAME", mListReport.get(position).getDayOfMonth());
                    intent.putExtra("MONTH_NAME", mListReport.get(position).getDayOfWeek());
                    mContext.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListReport.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDayName, tvTotalReport;

        private ConstraintLayout clItemReport;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            try {
                tvTotalReport = itemView.findViewById(R.id.tvTotalReport);
                tvDayName = itemView.findViewById(R.id.tvDayName);
                clItemReport = itemView.findViewById(R.id.clItemReport);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
