package vn.com.misa.ccl.presenter;

import android.app.Activity;

import vn.com.misa.ccl.model.FragmentReportTimeRecentlyModel;
import vn.com.misa.ccl.view.report.IFragmentReportTimeRecently;

public class FragmentReportTimeRecentlyPresenter implements IFragmentReportTimeRecently.IFragmentReportTimeRecentlyPrsenter {
    private FragmentReportTimeRecentlyModel mReportTimeRecentlyModel=new FragmentReportTimeRecentlyModel();

    @Override
    public void processReport(Activity activity) {
        mReportTimeRecentlyModel.processReport(activity);
    }
}
