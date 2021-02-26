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
 * ‐ Mục đích Class thực hiện việc hiển thị doanh thu thống kê
 * <p>
 * ‐ {@link FragmentReportTimeRecentlyPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/28/2021
 */

public class FragmentReportTimeRecently extends Fragment implements IFragmentReportTimeRecently.IFragmentReportTimeRecentlyView,
        View.OnClickListener {

    private FragmentReportTimeRecentlyPresenter mReportTimeRecently;

    private LinearLayout llLastDay, llThisWeek, llThisDay, llThisMonth, llThisYear;

    private TextView tvTimeThisYear, tvTimeThisMonth, tvTimeThisWeek, tvTimeThisDay, tvTimeLastDay;

    private float mTotalMoneyThisDay, mTotalMoneyLastDay;

    private IOnSelectionViewListener mIOnSelectionViewListener;

    public IOnSelectionViewListener getmIOnSelectionViewListener() {
        return mIOnSelectionViewListener;
    }

    public void setmIOnSelectionViewListener(IOnSelectionViewListener mIOnSelectionViewListener) {
        this.mIOnSelectionViewListener = mIOnSelectionViewListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_time_new, container, false);

        initView(view);

        processReport();

        onViewClickListener();

        return view;
    }

    /**
     * Mục đích method thực hiện khởi tạo các view
     *
     * @param view view
     * @created_by cvmanh on 01/28/2021
     */
    private void initView(View view) {
        try {
            llLastDay = view.findViewById(R.id.llLastDay);
            llThisWeek = view.findViewById(R.id.llThisWeek);
            llThisDay = view.findViewById(R.id.llThisDay);
            llThisMonth = view.findViewById(R.id.llThisMonth);
            llThisYear = view.findViewById(R.id.llThisYear);
            tvTimeThisYear = view.findViewById(R.id.tvTimeThisYear);
            tvTimeThisMonth = view.findViewById(R.id.tvTimeThisMonth);
            tvTimeThisWeek = view.findViewById(R.id.tvTimeThisWeek);
            tvTimeThisDay = view.findViewById(R.id.tvTimeThisDay);
            tvTimeLastDay = view.findViewById(R.id.tvTimeLastDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện những việc gọi presenter xử lý thống kê doanh thu
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void processReport() {

        try {
            mReportTimeRecently = new FragmentReportTimeRecentlyPresenter(this);
            mReportTimeRecently.processReport(getActivity());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xử lý thống kê thành công và hiển thị lên view
     *
     * @param amountYear     Doanh thu theo năm hiện tại
     * @param amountMonth    Doanh thu theo tháng hiện tại
     * @param amountThisDay  Doanh thu theo ngày hiện tại
     * @param amountLastDay  Doanh thu theo ngày hiện tại - 1 ngày
     * @param amountThisWeek Doanh thu theo tuần hiện tại
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void processReportTImeRecentlySuccess(String amountYear, String amountMonth, String amountThisDay, String amountLastDay, String amountThisWeek,
                                                 float totalMoneyThisDay, float totalMoneyLastDay) {

        try {
            tvTimeThisYear.setText(amountYear);
            tvTimeThisMonth.setText(amountMonth);
            tvTimeThisDay.setText(amountThisDay);
            tvTimeLastDay.setText(amountLastDay);
            tvTimeThisWeek.setText(amountThisWeek);
            mTotalMoneyThisDay = totalMoneyThisDay;
            mTotalMoneyLastDay = totalMoneyLastDay;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Mục đích method thực hiện việc lắng nghe xự kiện click view
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void onViewClickListener() {
        try {
            llThisDay.setOnClickListener(this);
            llLastDay.setOnClickListener(this);
            llThisWeek.setOnClickListener(this);
            llThisMonth.setOnClickListener(this);
            llThisYear.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện các công việc tương ứng với view click
     *
     * @param view view
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.llThisDay: {
                    if(!tvTimeThisDay.getText().toString().trim().equals("")){
                        Intent intent = new Intent(getContext(), ActivityReportWithDay.class);
                        intent.putExtra("AMOUNT", tvTimeThisDay.getText().toString());
                        intent.putExtra("AMOUNT_FLOAT", mTotalMoneyThisDay);
                        intent.putExtra("REPORT_TYPE", 2);//2: thống kê theo ngày hiện tại
                        startActivity(intent);
                    }
                    break;
                }
                case R.id.llLastDay: {
                    if(!tvTimeLastDay.getText().toString().trim().equals("")){
                        Intent intent = new Intent(getContext(), ActivityReportWithDay.class);
                        intent.putExtra("AMOUNT", tvTimeLastDay.getText().toString());
                        intent.putExtra("AMOUNT_FLOAT", mTotalMoneyLastDay);
                        intent.putExtra("REPORT_TYPE", 1);//1: thống kê theo ngày hiện tại -1 ngày
                        startActivity(intent);
                    }
                    break;
                }
                case R.id.llThisWeek: {
                    mIOnSelectionViewListener.onSelectThisWeekListener("ThisWeek");
                    break;
                }
                case R.id.llThisMonth: {
                    mIOnSelectionViewListener.onSelectThisWeekListener("ThisMonth");
                    break;
                }
                case R.id.llThisYear: {
                    mIOnSelectionViewListener.onSelectThisWeekListener("ThisYear");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IOnSelectionViewListener {
        public void onSelectThisWeekListener(String typeClick);
    }
}
