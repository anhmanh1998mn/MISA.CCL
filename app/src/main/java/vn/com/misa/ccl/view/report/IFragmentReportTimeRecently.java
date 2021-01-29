package vn.com.misa.ccl.view.report;

import android.app.Activity;

public interface IFragmentReportTimeRecently {

    public interface IFragmentReportTimeRecentlyPrsenter{
        public void processReport(Activity activity);
    }

    public interface IFragmentReportTimeRecentlyView{
        public void processReportTImeRecentlySuccess(String amountYear,String amountMonth,String amountThisDay,String amountLastDay,String amountThisWeek,float totalMoneyThisDay,float totalMoneyLastDay);

    }
}
