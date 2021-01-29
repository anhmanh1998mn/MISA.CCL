package vn.com.misa.ccl.view.report;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.ReportMoreAdapter;
import vn.com.misa.ccl.adapter.ReportWithDayAdapter;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.entity.Report;
import vn.com.misa.ccl.presenter.FragmentMainReportPresenter;
import vn.com.misa.ccl.util.AndroidDeviceHelper;
import vn.com.misa.ccl.view.report.day.ActivityReportWithDay;

/**
 * ‐ Mục đích Class thực hiện việc chứa các fragment thống kê
 * <p>
 * ‐ @created_by cvmanh on 01/28/2021
 */

public class FragmentMainReport extends Fragment implements View.OnClickListener, IFragmmentMainReport.IFragmentMainReportView {

    private LinearLayout llReportHeader;

    private Dialog mDialogTimeOption,mDialogReportTimeOrther;

    private ConstraintLayout clDialogMainReport,clReportTimeOrther;

    private TextView tvTimeNew, tvTimeThisWeek, tvTimeLastWeek, tvTimeThisMonth, tvTimeLastMonth,
            tvTimeThisYear, tvTimeLastYear, tvTimeOther;

    private TextView tvStartDay,tvEndDay,tvCancel,tvAccept,tvOptionReport;

    private ReportWithDayAdapter mReportWithDayAdapter;

    private FragmentMainReportPresenter mFragmentMainReportPresenter;

    private ImageView ivTick, ivTickTwo, ivTickThree, ivTickFour, ivTickFive, ivTickSix, ivTickSeven, ivTickEight;

    private CardView cvChart;

    private RecyclerView rcvFoodReport,rcvFoodReportLineChar;

    private FrameLayout frmMainReportContent;

    private List<OrderDetail> mListProductReport;

    private PieChart pcReportFood;

    private String mAmount="";

    private float mTotalMoneyDay;

    private LinearLayout llReportNull,llReportLine;

    private ReportMoreAdapter mReportMoreAdapter;

    private List<Report> mListReport;

    private LineChart lcReportFoodLineChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_report, container, false);

        initView(view);

        onViewClickListener();

        replaceFragment(new FragmentReportTimeRecently());

        return view;
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @param view view
     * @created_by cvmanh on 01/28/2021
     */
    private void initView(View view) {
        llReportHeader = view.findViewById(R.id.llReportHeader);
        cvChart=view.findViewById(R.id.cvChart);
        rcvFoodReport=view.findViewById(R.id.rcvFoodReport);
        frmMainReportContent=view.findViewById(R.id.frmMainReportContent);
        pcReportFood=view.findViewById(R.id.pcReportFood);
        llReportNull=view.findViewById(R.id.llReportNull);
        tvOptionReport=view.findViewById(R.id.tvOptionReport);
        rcvFoodReportLineChar=view.findViewById(R.id.rcvFoodReportLineChar);
        llReportLine=view.findViewById(R.id.llReportLine);
        lcReportFoodLineChart=view.findViewById(R.id.lcReportFoodLineChart);
    }

    /**
     * Mục đích method thực hiện việc lắng nghe các xự kiện view click
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void onViewClickListener() {
        llReportHeader.setOnClickListener(this);
    }

    /**
     * Mục đích method thực hiện việc gọi xử lý các công việc theo view click
     *
     * @param view view
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llReportHeader: {
                showDialogTimeOption();
                break;
            }
            case R.id.tvTimeNew: {
                ivTick.setVisibility(View.VISIBLE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.GONE);
                rcvFoodReport.setVisibility(View.GONE);
                cvChart.setVisibility(View.GONE);
                frmMainReportContent.setVisibility(View.VISIBLE);
                llReportNull.setVisibility(View.GONE);
                llReportLine.setVisibility(View.GONE);
                tvOptionReport.setText(getResources().getString(R.string.time_new));
                mDialogTimeOption.dismiss();
                break;
            }
            case R.id.tvTimeThisWeek: {
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.VISIBLE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.GONE);
                llReportLine.setVisibility(View.VISIBLE);
                llReportNull.setVisibility(View.GONE);
                tvOptionReport.setText(getResources().getString(R.string.time_this_week));
                rcvFoodReport.setVisibility(View.GONE);
                frmMainReportContent.setVisibility(View.GONE);
                mDialogTimeOption.dismiss();
                replaceFragment(new FragmentReportTimeRecently());
                mFragmentMainReportPresenter=new FragmentMainReportPresenter(this);
                mFragmentMainReportPresenter.getReportLineChart(getActivity(),"ThisWeek");
                break;
            }
            case R.id.tvTimeLastWeek: {
                llReportLine.setVisibility(View.VISIBLE);
                llReportNull.setVisibility(View.GONE);
                rcvFoodReport.setVisibility(View.GONE);
                frmMainReportContent.setVisibility(View.GONE);
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.VISIBLE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.GONE);
                tvOptionReport.setText(getResources().getString(R.string.time_last_week));
                mDialogTimeOption.dismiss();
                mFragmentMainReportPresenter=new FragmentMainReportPresenter(this);
                mFragmentMainReportPresenter.getReportLineChart(getActivity(),"LastWeek");
                break;
            }
            case R.id.tvTimeThisMonth: {
                llReportLine.setVisibility(View.VISIBLE);
                llReportNull.setVisibility(View.GONE);
                rcvFoodReport.setVisibility(View.GONE);
                frmMainReportContent.setVisibility(View.GONE);
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.VISIBLE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.GONE);
                tvOptionReport.setText(getResources().getString(R.string.time_this_month));
                mDialogTimeOption.dismiss();
                mFragmentMainReportPresenter=new FragmentMainReportPresenter(this);
                mFragmentMainReportPresenter.getReportLineChartWithMonth(getActivity(),"ThisMonth");
                break;
            }
            case R.id.tvTimeLastMonth: {
                llReportLine.setVisibility(View.VISIBLE);
                llReportNull.setVisibility(View.GONE);
                rcvFoodReport.setVisibility(View.GONE);
                frmMainReportContent.setVisibility(View.GONE);
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.VISIBLE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.GONE);
                tvOptionReport.setText(getResources().getString(R.string.time_last_mont));
                mDialogTimeOption.dismiss();
                mFragmentMainReportPresenter=new FragmentMainReportPresenter(this);
                mFragmentMainReportPresenter.getReportLineChartWithMonth(getActivity(),"LastMonth");
                break;
            }
            case R.id.tvTimeThisYear: {
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.VISIBLE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.GONE);
                tvOptionReport.setText(getResources().getString(R.string.time_this_year));
                mDialogTimeOption.dismiss();
                break;
            }
            case R.id.tvTimeLastYear: {
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.VISIBLE);
                ivTickEight.setVisibility(View.GONE);
                tvOptionReport.setText(getResources().getString(R.string.time_last_year));
                mDialogTimeOption.dismiss();
                break;
            }
            case R.id.tvTimeOther: {
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.VISIBLE);
                mDialogTimeOption.dismiss();
                showDialogTimeOrther();
                break;
            }
            case R.id.tvStartDay:{
                showDatePickerDialog(1);
                break;
            }
            case R.id.tvEndDay:{
                showDatePickerDialog(2);
                break;
            }
            case R.id.tvCancel:{
                mDialogReportTimeOrther.dismiss();
                mDialogTimeOption.show();
                break;
            }
            case R.id.tvAccept:{
                mFragmentMainReportPresenter=new FragmentMainReportPresenter(this);
                mFragmentMainReportPresenter.getListProductReportPeriod(getActivity(),tvStartDay.getText().toString(),
                        tvEndDay.getText().toString());

                break;
            }
        }
    }

    private void showDialogTimeOrther() {
        mDialogReportTimeOrther=new Dialog(getContext());
        mDialogReportTimeOrther.setContentView(R.layout.dialog_report_time_orther);
        mDialogReportTimeOrther.show();
        mDialogReportTimeOrther.setCanceledOnTouchOutside(false);
        initViewDialogTimeOrther();
        onClickViewDialogTimeOrther();
    }

    private void initViewDialogTimeOrther() {
        clReportTimeOrther=mDialogReportTimeOrther.findViewById(R.id.clReportTimeOrther);
        clReportTimeOrther.getLayoutParams().width=AndroidDeviceHelper.getWitdhScreen(getContext())-100;
        clReportTimeOrther.requestLayout();
        tvStartDay=mDialogReportTimeOrther.findViewById(R.id.tvStartDay);
        tvEndDay=mDialogReportTimeOrther.findViewById(R.id.tvEndDay);
        tvCancel=mDialogReportTimeOrther.findViewById(R.id.tvCancel);
        tvAccept=mDialogReportTimeOrther.findViewById(R.id.tvAccept);
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DATE);
        calendar.set(year,month,day);
        tvStartDay.setText(year+"-"+"01-01");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        tvEndDay.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void onClickViewDialogTimeOrther(){
        tvStartDay.setOnClickListener(this);
        tvEndDay.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvAccept.setOnClickListener(this);
    }

    private void showDatePickerDialog(int typeClick){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                if(typeClick==1){
                    tvStartDay.setText(simpleDateFormat.format(calendar.getTime()));
                    return;
                }
                tvEndDay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();
    }

    /**
     * Mục đích method thực hiện việc hiển thị dialog lựa chọn thời gian thống kê
     *
     * @return giải thích hàm này trả về
     * @created_by cvmanh on 01/28/2021
     */
    private void showDialogTimeOption() {
        mDialogTimeOption = new Dialog(getContext());
        mDialogTimeOption.setContentView(R.layout.dialog_main_report);
        mDialogTimeOption.show();
        initViewDialog();
        onCLickViewDialogListener();
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view trong dialog
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void initViewDialog() {
        clDialogMainReport = mDialogTimeOption.findViewById(R.id.clDialogMainReport);
        clDialogMainReport.getLayoutParams().width = AndroidDeviceHelper.getWitdhScreen(getContext()) - 100;
        clDialogMainReport.requestLayout();
        tvTimeNew = mDialogTimeOption.findViewById(R.id.tvTimeNew);
        ivTick = mDialogTimeOption.findViewById(R.id.ivTick);
        tvTimeThisWeek = mDialogTimeOption.findViewById(R.id.tvTimeThisWeek);
        ivTickTwo = mDialogTimeOption.findViewById(R.id.ivTickTwo);
        tvTimeLastWeek = mDialogTimeOption.findViewById(R.id.tvTimeLastWeek);
        ivTickThree = mDialogTimeOption.findViewById(R.id.ivTickThree);
        tvTimeThisMonth = mDialogTimeOption.findViewById(R.id.tvTimeThisMonth);
        ivTickFour = mDialogTimeOption.findViewById(R.id.ivTickFour);
        tvTimeLastMonth = mDialogTimeOption.findViewById(R.id.tvTimeLastMonth);
        ivTickFive = mDialogTimeOption.findViewById(R.id.ivTickFive);
        tvTimeThisYear = mDialogTimeOption.findViewById(R.id.tvTimeThisYear);
        ivTickSix = mDialogTimeOption.findViewById(R.id.ivTickSix);
        tvTimeLastYear = mDialogTimeOption.findViewById(R.id.tvTimeLastYear);
        ivTickSeven = mDialogTimeOption.findViewById(R.id.ivTickSeven);
        tvTimeOther = mDialogTimeOption.findViewById(R.id.tvTimeOther);
        ivTickEight = mDialogTimeOption.findViewById(R.id.ivTickEight);
    }

    /**
     * Mục đích method thực hiện việc lắng nghe các xự kiện view click từ dialog
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void onCLickViewDialogListener() {
        tvTimeNew.setOnClickListener(this);
        tvTimeThisWeek.setOnClickListener(this);
        tvTimeLastWeek.setOnClickListener(this);
        tvTimeThisMonth.setOnClickListener(this);
        tvTimeLastMonth.setOnClickListener(this);
        tvTimeThisYear.setOnClickListener(this);
        tvTimeLastYear.setOnClickListener(this);
        tvTimeOther.setOnClickListener(this);
    }

    /**
     * Mục đích method thực hiện việc thay thế fragment
     *
     * @param fragment fragment
     * @created_by cvmanh on 01/28/2021
     */
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frmMainReportContent, fragment);
        transaction.commit();
    }

    @Override
    public void getListProductReportWithPeroid(List<OrderDetail> listProductReport, float sumAllMoney) {
        Log.d("mmmm",sumAllMoney+"");
        mDialogReportTimeOrther.dismiss();
        mDialogTimeOption.dismiss();
        rcvFoodReport.setVisibility(View.VISIBLE);
        cvChart.setVisibility(View.VISIBLE);
        frmMainReportContent.setVisibility(View.GONE);
        llReportNull.setVisibility(View.GONE);
        mTotalMoneyDay=sumAllMoney;
        mAmount=String.valueOf(mTotalMoneyDay);
        mListProductReport=listProductReport;
        mReportWithDayAdapter = new ReportWithDayAdapter(getActivity(), R.layout.item_report_with_day, mListProductReport);
        rcvFoodReport.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rcvFoodReport.setAdapter(mReportWithDayAdapter);
        mReportWithDayAdapter.notifyDataSetChanged();
        tvOptionReport.setText(formatDatePeroid());
        showPieChart();
    }

    @Override
    public void getReportTimeWeekSuccess(List<Report> listReportWeek) {
        mListReport=listReportWeek;
        mReportMoreAdapter=new ReportMoreAdapter(getActivity(),R.layout.item_report_week_month_year,listReportWeek);
        rcvFoodReportLineChar.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        rcvFoodReportLineChar.setAdapter(mReportMoreAdapter);
        mReportMoreAdapter.notifyDataSetChanged();
        showReportByLineChart();
    }

    @Override
    public void getReportTimeMonthSuccess(List<Report> listReportWeek) {
        mListReport=listReportWeek;
        mReportMoreAdapter=new ReportMoreAdapter(getActivity(),R.layout.item_report_week_month_year,listReportWeek);
        rcvFoodReportLineChar.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        rcvFoodReportLineChar.setAdapter(mReportMoreAdapter);
        mReportMoreAdapter.notifyDataSetChanged();
        showReportByLineChart();
    }

    @Override
    public void getReportDataNull() {
        llReportNull.setVisibility(View.VISIBLE);
        rcvFoodReport.setVisibility(View.GONE);
        cvChart.setVisibility(View.GONE);
        frmMainReportContent.setVisibility(View.GONE);
        llReportLine.setVisibility(View.GONE);
    }

    private String formatDatePeroid(){
        String[] startDay=tvStartDay.getText().toString().split("-");
        String[] endDay=tvEndDay.getText().toString().split("-");
        return "Từ "+startDay[2]+"/"+startDay[1]+"/"+startDay[0]+" - "+endDay[2]+"/"+endDay[1]+"/"+endDay[0];
    }

    @Override
    public void onFailed() {
            mDialogReportTimeOrther.dismiss();
        llReportNull.setVisibility(View.VISIBLE);
        rcvFoodReport.setVisibility(View.GONE);
        cvChart.setVisibility(View.GONE);
        frmMainReportContent.setVisibility(View.GONE);
    }

    private void showPieChart() {
        int[] colorPie = new int[]{getResources().getColor(R.color.light_bluee), getResources().getColor(R.color.green_light),
                getResources().getColor(R.color.red), getResources().getColor(R.color.orange),
                getResources().getColor(R.color.dark_blue), getResources().getColor(R.color.dark_blue_face),
                getResources().getColor(R.color.light_grey)};
        PieDataSet pieDataSet = new PieDataSet(dataValues(), "");
        pieDataSet.setColors(colorPie);
        pieDataSet.setSliceSpace(1f); // set khoảng trắng phân cách giữa các vùng
        pieDataSet.setValueLinePart1Length(0.1f); // set độ dài thanh sổ chỉ data
        pieDataSet.setValueLinePart2Length(0.6f);

        // đặt giá trị ra bên ngoài
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setValueFormatter(new PercentFormatter(pcReportFood)); // set % cho data
        pieData.setValueTextSize(14);
        pieData.setValueTextColor(Color.GRAY);
        pcReportFood.setData(pieData);
        pcReportFood.invalidate();
        pcReportFood.setCenterTextSize(18);
        pcReportFood.setCenterText("Tổng \n doanh thu \n" + mAmount);
        pcReportFood.setHoleRadius(75);
        pcReportFood.getDescription().setEnabled(false);// set gone label
        pcReportFood.setUsePercentValues(true); // set % cho data
        // enable rotation of the chart by touch
        pcReportFood.setRotationEnabled(false);
        pcReportFood.getLegend().setEnabled(false); // ẩn ghi chú
        pcReportFood.animateXY(1400,1400); // set animation
    }

    /**
     * Mục đích method thực hiện khởi tạo dữ liệu cho biểu đồ
     * Dữ liệu được lấy từ danh sách thống kê sản phẩm thành công
     *
     * @return trả về 1 mảng PieEntry
     * @created_by cvmanh on 01/28/2021
     */
    private ArrayList<PieEntry> dataValues() {
        ArrayList<PieEntry> daVal = new ArrayList<>();
        for (int i = 0; i < mListProductReport.size(); i++) {
            if (i < 7) {
                daVal.add(new PieEntry((mListProductReport.get(i).getmProductPriceOut()/mTotalMoneyDay)*100, "%"));
                float a=(mListProductReport.get(i).getmProductPriceOut());
                Log.d("aaaaa",a+"");
            }
        }
        return daVal;
    }

    private void showReportByLineChart(){
        LineDataSet lineDataSet=new LineDataSet(dataValueLine(),"");
        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData lineData=new LineData(dataSets);
        lcReportFoodLineChart.setData(lineData);

        // set đường vẽ và hình tròn màu xanh
        lineDataSet.setColor(getResources().getColor(R.color.green_light));
        lineDataSet.setCircleColor(getResources().getColor(R.color.green_light));
        lcReportFoodLineChart.getLegend().setEnabled(false);// ẩn ghi chú
        lcReportFoodLineChart.getDescription().setEnabled(false);// ẩn label
        lcReportFoodLineChart.animateXY(1400,1400);// set animation
        lineDataSet.setDrawCircleHole(false);// hình tròn đặc
        lcReportFoodLineChart.invalidate();
    }

    private ArrayList<Entry> dataValueLine(){
        ArrayList<Entry> dataValue=new ArrayList<>();
        for(int i=0;i<mListReport.size();i++){
            dataValue.add(new Entry(i,mListReport.get(i).getTotalMoney()/1000));
        }
        return dataValue;
    }
}
