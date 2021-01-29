package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.model.FragmentMainReportModel;
import vn.com.misa.ccl.view.report.IFragmmentMainReport;

public class FragmentMainReportPresenter implements IFragmmentMainReport.IFragmentMainReportPresenter,
        FragmentMainReportModel.IFragmentMainReportModel {

    private IFragmmentMainReport.IFragmentMainReportView mIFragmentMainReportView;

    public FragmentMainReportPresenter(IFragmmentMainReport.IFragmentMainReportView mIFragmentMainReportView) {
        this.mIFragmentMainReportView = mIFragmentMainReportView;
    }

    private FragmentMainReportModel mFragmentMainReportModel=new FragmentMainReportModel(this);
    @Override
    public void getListProductReportPeriod(Activity activity, String startDay, String endDay) {
        mFragmentMainReportModel.getListProductReportPeriod(activity,startDay,endDay);
    }

    @Override
    public void getReportLineChart(Activity activity) {
        mFragmentMainReportModel.getReportLineChart(activity);
    }

    @Override
    public void getListReportWithPeroid(List<OrderDetail> listReportProduct, float sumAllMoney) {
        mIFragmentMainReportView.getListProductReportWithPeroid(listReportProduct,sumAllMoney);
    }

    @Override
    public void onFailed() {
        mIFragmentMainReportView.onFailed();
    }
}
