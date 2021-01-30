package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.entity.Report;
import vn.com.misa.ccl.model.FragmentMainReportModel;
import vn.com.misa.ccl.view.report.IFragmmentMainReport;

/**
 * ‐ Mục đích Class thực hiện việc luân chuyển dữ liệu giữa FragmentMainReport và FragmentMainReportModel
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.report.FragmentMainReport}
 * ‐ {@link FragmentMainReportModel}
 * <p>
 * ‐ @created_by cvmanh on 01/30/2021
 */

public class FragmentMainReportPresenter implements IFragmmentMainReport.IFragmentMainReportPresenter,
        FragmentMainReportModel.IFragmentMainReportModel {

    private IFragmmentMainReport.IFragmentMainReportView mIFragmentMainReportView;

    public FragmentMainReportPresenter(IFragmmentMainReport.IFragmentMainReportView mIFragmentMainReportView) {
        this.mIFragmentMainReportView = mIFragmentMainReportView;
    }

    private FragmentMainReportModel mFragmentMainReportModel = new FragmentMainReportModel(this);

    /**
     * Mục đích method thực hiện việc gọi model xử lý thống kê theo khoảng thời gian
     *
     * @param activity instance activity
     * @param startDay ngày bắt đầu thống kê
     * @param endDay   ngày kết thúc thống kê
     * @created_by cvmanh on 01/30/2021
     */
    @Override
    public void getListProductReportPeriod(Activity activity, String startDay, String endDay) {
        mFragmentMainReportModel.getListProductReportPeriod(activity, startDay, endDay);
    }

    /**
     * Mục đích method thực hiện việc gọi model thống kê theo tuần
     *
     * @param activity  instance activity
     * @param typeClick loại button click. typeCLick=ThisWeek: thống kê theo tuần hiện tại
     *                  Ngược lại, thống kê theo tuần trước đó
     * @created_by cvmanh on 01/30/2021
     */
    @Override
    public void getReportLineChart(Activity activity, String typeClick) {
        mFragmentMainReportModel.getReportLineChart(activity, typeClick);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý thống kê theo tháng
     *
     * @param activity  instance activity
     * @param typeClick loại button click. nếu typeClick=ThisMonth: thống kê theo tháng hiện tại
     *                  Ngược lại, thống kê theo tháng trước
     * @created_by cvmanh on 01/30/2021
     */
    @Override
    public void getReportLineChartWithMonth(Activity activity, String typeClick) {
        mFragmentMainReportModel.getReportLineChartWithMonth(activity, typeClick);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý thống kê theo năm
     *
     * @param activity  instance activity
     * @param typeClick loại button click. nêu typeClick=ThisYear: thống kê theo năm hiện tại
     *                  Ngược lại, thống kê theo năm trước
     * @created_by cvmanh on 01/30/2021
     */
    @Override
    public void getReportLineChartWithYear(Activity activity, String typeClick) {
        mFragmentMainReportModel.getReportLineChartWithYear(activity, typeClick);
    }

    /**
     * Mục đích method thực hiện nhận kết quả thốn kê theo khoảng thời gian thành công và trả kết quả về view
     *
     * @param listReportProduct Danh sách sản phẩm thống kê theo tuần
     * @param sumAllMoney       tổng doanh thu theo tuần
     * @created_by cvmanh on 01/30/2021
     */
    @Override
    public void getListReportWithPeroid(List<OrderDetail> listReportProduct, float sumAllMoney) {
        mIFragmentMainReportView.getListProductReportWithPeroid(listReportProduct, sumAllMoney);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả thống kê theo tuần thành công
     * Và trả kết quả thống kê về view
     *
     * @param listReportWeek Danh sách thống kê theo tuần
     * @created_by cvmanh on 01/30/2021
     */
    @Override
    public void getReportTimeWeekSuccess(List<Report> listReportWeek) {
        mIFragmentMainReportView.getReportTimeWeekSuccess(listReportWeek);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả thống kê theo tháng thành công
     * Và trả kết quả thống kê về view
     *
     * @param listReportWeek Danh sách thống kê theo tháng
     * @created_by cvmanh on 01/30/2021
     */
    @Override
    public void getReportTimeMonthSuccess(List<Report> listReportWeek) {
        mIFragmentMainReportView.getReportTimeMonthSuccess(listReportWeek);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả thống kê theo năm thành công
     * Và trả kết quả thống kê về view
     *
     * @param listReportWeek Danh sách thống kê theo năm
     * @created_by cvmanh on 01/30/2021
     */
    @Override
    public void getReportTimeYearSuccess(List<Report> listReportWeek) {
        mIFragmentMainReportView.getReportTimeYearSuccess(listReportWeek);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả thống kê với dữ liệu rỗng
     * Và trả kết quả thống kê về view
     *
     * @created_by cvmanh on 01/30/2021
     */
    @Override
    public void getReportDataNull() {
        mIFragmentMainReportView.getReportDataNull();
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xử lý thống kê thất bại
     * Và trả kết quả thống kê về view
     *
     * @created_by cvmanh on 01/30/2021
     */
    @Override
    public void onFailed() {
        mIFragmentMainReportView.onFailed();
    }
}
