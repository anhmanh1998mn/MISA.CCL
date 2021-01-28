package vn.com.misa.ccl.view.report.day;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.ReportWithDayAdapter;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.presenter.ActivityReportWithDayPresenter;

public class ActivityReportWithDay extends AppCompatActivity implements IActivityReportWithDay.IActivityReportWithDayView {

    private PieChart pcReportFood;

    private Toolbar tbReportFood;

    private RecyclerView rcvFoodReport;

    private String mAmount;

    private ActivityReportWithDayPresenter mActivityReportWithDayPresenter;

    private ReportWithDayAdapter mReportWithDayAdapter;

    private List<OrderDetail> mListProductReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_with_day);

        initActionBar();

        initView();

        receiveAmount();

        getListProductReport();

        showPieChart();


    }

    private void receiveAmount() {
        Intent intent=getIntent();
        mAmount=intent.getStringExtra("AMOUNT");
    }

    private void initActionBar() {
        tbReportFood=findViewById(R.id.tbReportFood);
        setSupportActionBar(tbReportFood);
    }

    private void initView() {
        pcReportFood =findViewById(R.id.pcReportFood);
        rcvFoodReport=findViewById(R.id.rcvFoodReport);
    }

    private void showPieChart(){
        int[] colorPie=new int[]{getResources().getColor(R.color.light_bluee),getResources().getColor(R.color.green_light),
                getResources().getColor(R.color.red),getResources().getColor(R.color.orange),
                getResources().getColor(R.color.dark_blue),getResources().getColor(R.color.dark_blue_face)};
        PieDataSet pieDataSet=new PieDataSet(dataValues(),"");
        pieDataSet.setColors(colorPie);
        pieDataSet.setSliceSpace(1f); // set khoảng trắng phân cách giữa các vùng
        // đặt giá trị ra bên ngoài
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData pieData=new PieData(pieDataSet);
        pcReportFood.setData(pieData);
        pcReportFood.invalidate();
        pcReportFood.setCenterTextSize(18);
        pcReportFood.setCenterText("Tổng \n doanh thu \n"+mAmount);
        pcReportFood.setHoleRadius(80);
        pcReportFood.getDescription().setEnabled(false);// set gone label
        // enable rotation of the chart by touch
        pcReportFood.setRotationEnabled(false);
        pcReportFood.getLegend().setEnabled(false); // ẩn ghi chú
        pcReportFood.animateY(1400, Easing.EaseInOutQuad); // set animation
    }

    private ArrayList<PieEntry> dataValues(){
        ArrayList<PieEntry>daVal=new ArrayList<>();
        for(int i=0;i<mListProductReport.size();i++){
            daVal.add(new PieEntry(mListProductReport.get(i).getmProductPriceOut(),""));
        }
//        daVal.add(new PieEntry(Integer.parseInt("450000"),"Tiền Bán"));
//        daVal.add(new PieEntry(Integer.parseInt("150000"),"Tiền Nhập"));
        return daVal;
    }

    private void getListProductReport(){
        mActivityReportWithDayPresenter=new ActivityReportWithDayPresenter(this);
        mActivityReportWithDayPresenter.getListProductThisDay(this);
    }

    @Override
    public void getListProductThisDaySuccess(List<OrderDetail> listOrderDetail) {
        mListProductReport=listOrderDetail;
        mReportWithDayAdapter=new ReportWithDayAdapter(this,R.layout.item_report_with_day,listOrderDetail);
        rcvFoodReport.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rcvFoodReport.setAdapter(mReportWithDayAdapter);
        mReportWithDayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {

    }
}