package vn.com.misa.ccl.view.report.day;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;

public interface IActivityReportWithDay {

    public interface IActivityReportWithDayPresemter{
        public void getListProductThisDay(Activity activity);

        public void getListProductReportLastDay(Activity activity);

        public void getReportTimeDay(Activity activity,String dayName);

        public void getReportTimeYear(Activity activity,String startDay,String endDay);

    }

    public interface IActivityReportWithDayView{
        public void getListProductThisDaySuccess(List<OrderDetail> listOrderDetail);

        public void getListProductReportLastDaySuccess(List<OrderDetail> listProductReport);

        public void getReportYearSuccess(List<OrderDetail> listReportYear);

        public void onFailed();
    }
}
