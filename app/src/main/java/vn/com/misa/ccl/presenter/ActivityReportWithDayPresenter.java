package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.model.ActivityReportWithDayModel;
import vn.com.misa.ccl.view.report.day.IActivityReportWithDay;

/**
 * ‐ Mục đích Class thực hiện việc luân chuyển dữ liệu giữa ActivityReportWithDay và ActivityReportWithDayModel
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.report.day.ActivityReportWithDay}
 * ‐ {@link ActivityReportWithDayModel}
 * <p>
 * ‐ @created_by cvmanh on 01/28/2021
 */

public class ActivityReportWithDayPresenter implements IActivityReportWithDay.IActivityReportWithDayPresemter,
        ActivityReportWithDayModel.IActivityReportWithDayModel {

    private IActivityReportWithDay.IActivityReportWithDayView mIActivityReportWithDayView;

    public ActivityReportWithDayPresenter(IActivityReportWithDay.IActivityReportWithDayView mIActivityReportWithDayView) {
        this.mIActivityReportWithDayView = mIActivityReportWithDayView;
    }

    private ActivityReportWithDayModel mActivityReportWithDayModel = new ActivityReportWithDayModel(this);

    /**
     * Mục đích method thực hiện việc gọi model xử lý lấy danh sách thống kê sản phẩm theo ngày hiện tại
     *
     * @param activity instance activity
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void getListProductThisDay(Activity activity) {
        mActivityReportWithDayModel.getListProductThisDay(activity);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý lấy danh sách thống kê sản phẩm theo ngày hiện tại - 1 ngày
     *
     * @param activity instance activity
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void getListProductReportLastDay(Activity activity) {
        mActivityReportWithDayModel.getListProductReportLastDay(activity);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả thống kê theo ngày hiện tại và gửi kết quả tới view
     *
     * @param listProductReport danh sách thống kê sản phẩm
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void getListproductReportSuccess(List<OrderDetail> listProductReport) {
        mIActivityReportWithDayView.getListProductThisDaySuccess(listProductReport);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả thống kê theo ngày hiện tại - 1 ngày và gửi kết quả tới view
     *
     * @param listProductReport danh sách thống kê sản phẩm
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void getListproductReportLastDaySuccess(List<OrderDetail> listProductReport) {
        mIActivityReportWithDayView.getListproductReportLastDaySuccess(listProductReport);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xử lý thất bại và gửi tới view
     *
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void onFailed() {
        mIActivityReportWithDayView.onFailed();
    }
}
