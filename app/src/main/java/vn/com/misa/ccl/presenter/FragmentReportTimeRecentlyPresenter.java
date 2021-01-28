package vn.com.misa.ccl.presenter;

import android.app.Activity;

import vn.com.misa.ccl.model.FragmentReportTimeRecentlyModel;
import vn.com.misa.ccl.view.report.IFragmentReportTimeRecently;

/**
 * ‐ Mục đích Class thực hiện việc luân chuyển dữ liệu giữa FragmentReportTimeRecently và FragmentReportTimeRecentlyModel
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.report.FragmentReportTimeRecently}
 * ‐ {@link FragmentReportTimeRecentlyModel}
 * <p>
 * ‐ @created_by cvmanh on 01/28/2021
 */

public class FragmentReportTimeRecentlyPresenter implements IFragmentReportTimeRecently.IFragmentReportTimeRecentlyPrsenter,
        FragmentReportTimeRecentlyModel.IFragmentReportTimeRecentlyModel {
    private IFragmentReportTimeRecently.IFragmentReportTimeRecentlyView mIFragmentReportTimeRecentlyView;

    public FragmentReportTimeRecentlyPresenter(IFragmentReportTimeRecently.IFragmentReportTimeRecentlyView mIFragmentReportTimeRecentlyView) {
        this.mIFragmentReportTimeRecentlyView = mIFragmentReportTimeRecentlyView;
    }

    private FragmentReportTimeRecentlyModel mReportTimeRecentlyModel = new FragmentReportTimeRecentlyModel(this);

    /**
     * Mục đích method thực hiện việc gọi model xử lý thống kê doanh thu
     *
     * @param activity Instance activity
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void processReport(Activity activity) {
        mReportTimeRecentlyModel.processReport(activity);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xử lý thống kê thành công và gửi về view
     *
     * @param amountYear     Doanh thu theo năm hiện tại
     * @param amountMonth    Doanh thu theo tháng hiện tại
     * @param amountThisDay  Doanh thu theo ngày hiện tại
     * @param amountLastDay  Doanh thu theo ngày hiện tại - 1 ngày
     * @param amountThisWeek Doanh thu theo tuần hiện tại
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void processReportTimeRecentlySuccess(String amountYear, String amountMonth,
                                                 String amountThisDay, String amountLastDay, String amountThisWeek) {
        mIFragmentReportTimeRecentlyView.processReportTImeRecentlySuccess(amountYear, amountMonth,
                amountThisDay, amountLastDay, amountThisWeek);
    }
}
