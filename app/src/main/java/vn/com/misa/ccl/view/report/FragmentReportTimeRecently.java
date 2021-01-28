package vn.com.misa.ccl.view.report;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.presenter.FragmentReportTimeRecentlyPresenter;
import vn.com.misa.ccl.view.report.day.ActivityReportWithDay;

/**
‐ Mục đích Class thực hiện việc hiển thị doanh thu thống kê
*
‐ {@link FragmentReportTimeRecentlyPresenter}
*
‐ @created_by cvmanh on 01/28/2021
*/

public class FragmentReportTimeRecently extends Fragment implements IFragmentReportTimeRecently.IFragmentReportTimeRecentlyView,
        View.OnClickListener {

    private FragmentReportTimeRecentlyPresenter mReportTimeRecently;

    private LinearLayout llLastDay,llThisWeek,llThisDay,llThisMonth,llThisYear;

    private TextView tvTimeThisYear,tvTimeThisMonth,tvTimeThisWeek,tvTimeThisDay,tvTimeLastDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_report_time_new,container,false);

        initView(view);

        processReport();

        onViewClickListener();

        return view;
    }

    /**
     * Mục đích method thực hiện khởi tạo các view
     *
     * @param view view
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void initView(View view) {
        llLastDay=view.findViewById(R.id.llLastDay);
        llThisWeek=view.findViewById(R.id.llThisWeek);
        llThisDay=view.findViewById(R.id.llThisDay);
        llThisMonth=view.findViewById(R.id.llThisMonth);
        llThisYear=view.findViewById(R.id.llThisYear);
        tvTimeThisYear=view.findViewById(R.id.tvTimeThisYear);
        tvTimeThisMonth=view.findViewById(R.id.tvTimeThisMonth);
        tvTimeThisWeek=view.findViewById(R.id.tvTimeThisWeek);
        tvTimeThisDay=view.findViewById(R.id.tvTimeThisDay);
        tvTimeLastDay=view.findViewById(R.id.tvTimeLastDay);
    }

    /**
     * Mục đích method thực hiện những việc gọi presenter xử lý thống kê doanh thu
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void processReport(){
        mReportTimeRecently=new FragmentReportTimeRecentlyPresenter(this);
        mReportTimeRecently.processReport(getActivity());
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xử lý thống kê thành công và hiển thị lên view
     *
     * @param amountYear Doanh thu theo năm hiện tại
     * @param amountMonth Doanh thu theo tháng hiện tại
     * @param amountThisDay Doanh thu theo ngày hiện tại
     * @param amountLastDay Doanh thu theo ngày hiện tại - 1 ngày
     * @param amountThisWeek Doanh thu theo tuần hiện tại
     *
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void processReportTImeRecentlySuccess(String amountYear,String amountMonth,String amountThisDay,String amountLastDay,String amountThisWeek) {
        tvTimeThisYear.setText(amountYear);
        tvTimeThisMonth.setText(amountMonth);
        tvTimeThisDay.setText(amountThisDay);
        tvTimeLastDay.setText(amountLastDay);
        tvTimeThisWeek.setText(amountThisWeek);
    }

    private void onViewClickListener(){
        llThisDay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llThisDay:{
                Intent intent=new Intent(getContext(), ActivityReportWithDay.class);
                intent.putExtra("AMOUNT",tvTimeThisDay.getText().toString());
                startActivity(intent);
                break;
            }
        }
    }
}
