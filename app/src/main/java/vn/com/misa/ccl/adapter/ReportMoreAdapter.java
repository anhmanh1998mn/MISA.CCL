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

public class ReportMoreAdapter extends RecyclerView.Adapter<ReportMoreAdapter.ViewHolder> {
    private Activity mContext;

    private int mLayout;

    private List<Report> mListReport;

    public ReportMoreAdapter(Activity mContext, int mLayout, List<Report> mListReport) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListReport = mListReport;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(mLayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDayName.setText(mListReport.get(position).getDayOfWeek());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.tvTotalReport.setText(decimalFormat.format(mListReport.get(position).getTotalMoney()));
        holder.clItemReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListReport.get(position).getTotalMoney()>0){
                    Intent intent=new Intent(mContext, ActivityReportWithDay.class);
                    intent.putExtra("REPORT_TYPE",3);
                    intent.putExtra("AMOUNT_FLOAT",mListReport.get(position).getTotalMoney());
                    intent.putExtra("DAY_NAME",mListReport.get(position).getDayOfMonth());
                    intent.putExtra("DAY_OF_WEEK",mListReport.get(position).getDayOfWeek());
                    mContext.startActivity(intent);
                    return;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListReport.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDayName,tvTotalReport;

        private ConstraintLayout clItemReport;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTotalReport=itemView.findViewById(R.id.tvTotalReport);
            tvDayName=itemView.findViewById(R.id.tvDayName);
            clItemReport=itemView.findViewById(R.id.clItemReport);
        }
    }
}
