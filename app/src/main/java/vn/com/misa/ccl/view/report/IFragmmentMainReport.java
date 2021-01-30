package vn.com.misa.ccl.view.report;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.entity.Report;

public interface IFragmmentMainReport {
    public interface IFragmentMainReportPresenter{
        public void getListProductReportPeriod(Activity activity, String startDay, String endDay);

        public void getReportLineChart(Activity activity,String typeClick);

        public void getReportLineChartWithMonth(Activity activity, String typeClick);

        public void getReportLineChartWithYear(Activity activity, String typeClick);
    }

    public interface IFragmentMainReportView{
        public void getListProductReportWithPeroid(List<OrderDetail> listProductReport, float sumAllMoney);

        public void getReportTimeWeekSuccess(List<Report> listReportWeek);

        public void getReportTimeMonthSuccess(List<Report> listReportWeek);

        public void getReportTimeYearSuccess(List<Report> listReportWeek);

        public void getReportDataNull();

        public void onFailed();
    }
}
