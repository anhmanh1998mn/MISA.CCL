package vn.com.misa.ccl.view.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.presenter.FragmentReportTimeRecentlyPresenter;

public class FragmentReportTimeRecently extends Fragment {

    private FragmentReportTimeRecentlyPresenter mReportTimeRecently;

    private LinearLayout llLastDay,llThisWeek,llThisDay,llThisMonth,llThisYear;

    private TextView tvTimeThisYear,tvTimeThisMonth,tvTimeThisWeek,tvTimeThisDay,tvTimeLastDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_report_time_new,container,false);

        initView(view);

        processReport();

        return view;
    }

    private void initView(View view) {
        llLastDay=view.findViewById(R.id.llLastDay);
        llThisWeek=view.findViewById(R.id.llThisWeek);
        llThisDay=view.findViewById(R.id.llThisDay);
        llThisMonth=view.findViewById(R.id.llThisMonth);
        llThisYear=view.findViewById(R.id.llThisYear);
        tvTimeThisYear=view.findViewById(R.id.tvTimeThisYear);
        tvTimeThisMonth=view.findViewById(R.id.tvTimeThisMonth);
        tvTimeThisWeek=view.findViewById(R.id.tvTimeThisWeek);
        tvTimeThisDay=view.findViewById(R.id.tvTimeThisDay);
        tvTimeLastDay=view.findViewById(R.id.tvTimeLastDay);
    }

    private void processReport(){
        mReportTimeRecently=new FragmentReportTimeRecentlyPresenter();
        mReportTimeRecently.processReport(getActivity());
    }
}
