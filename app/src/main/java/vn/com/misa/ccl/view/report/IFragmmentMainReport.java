package vn.com.misa.ccl.view.report;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;

public interface IFragmmentMainReport {
    public interface IFragmentMainReportPresenter{
        public void getListProductReportPeriod(Activity activity, String startDay, String endDay);

        public void getReportLineChart(Activity activity);
    }

    public interface IFragmentMainReportView{
        public void getListProductReportWithPeroid(List<OrderDetail> listProductReport, float sumAllMoney);

        public void onFailed();
    }
}
