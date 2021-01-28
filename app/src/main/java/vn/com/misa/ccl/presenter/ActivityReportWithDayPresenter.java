package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.model.ActivityReportWithDayModel;
import vn.com.misa.ccl.view.report.day.IActivityReportWithDay;

public class ActivityReportWithDayPresenter implements IActivityReportWithDay.IActivityReportWithDayPresemter,
        ActivityReportWithDayModel.IActivityReportWithDayModel {

    private IActivityReportWithDay.IActivityReportWithDayView mIActivityReportWithDayView;

    public ActivityReportWithDayPresenter(IActivityReportWithDay.IActivityReportWithDayView mIActivityReportWithDayView) {
        this.mIActivityReportWithDayView = mIActivityReportWithDayView;
    }

    private ActivityReportWithDayModel mActivityReportWithDayModel=new ActivityReportWithDayModel(this);

    @Override
    public void getListProductThisDay(Activity activity) {
        mActivityReportWithDayModel.getListProductThisDay(activity);
    }

    @Override
    public void getListproductReportSuccess(List<OrderDetail> listProductReport) {
        mIActivityReportWithDayView.getListProductThisDaySuccess(listProductReport);
    }

    @Override
    public void onFailed() {
        mIActivityReportWithDayView.onFailed();
    }
}
