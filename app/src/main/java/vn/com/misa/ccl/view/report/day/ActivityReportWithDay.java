package vn.com.misa.ccl.view.report.day;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.ReportWithDayAdapter;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.presenter.ActivityReportWithDayPresenter;

/**
 * ‐ Mục đích Class thực hiện thống kê doanh thu theo sản phẩm theo ngày hiện tại hoặc ngày hôm trước
 * <p>
 * ‐ {@link ActivityReportWithDayPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/28/2021
 */

public class ActivityReportWithDay extends AppCompatActivity implements IActivityReportWithDay.IActivityReportWithDayView {

    private PieChart pcReportFood;

    private Toolbar tbReportFood;

    private RecyclerView rcvFoodReport;

    private String mAmount="",mStartDay,mEndDay;

    private ActivityReportWithDayPresenter mActivityReportWithDayPresenter;

    private ReportWithDayAdapter mReportWithDayAdapter;

    private List<OrderDetail> mListProductReport;

    private TextView tvBack;

    private float mTotalMoneyDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_with_day);

        initActionBar();

        initView();

        receiveAmount();

        onViewClickListener();

    }

    /**
     * Mục đích method thực hiện việc xử lý các công việc click view
     *
     * @return giải thích hàm này trả về
     * @created_by cvmanh on 01/28/2021
     */
    private void onViewClickListener() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Mục đích method thực hiện kiểm tra thống kê theo tháng hay năm
     * Nếu getIntent=2: Thống kê doanh thu theo sản phẩm theo ngày hiện tại
     * Ngược lại, =1: thóng kê doanh thu theo sản phẩm theo ngày hiện tại -1 ngày
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void receiveAmount() {
        Intent intent = getIntent();
        if (intent.getIntExtra("REPORT_TYPE", -1) == 2) {
            mAmount = intent.getStringExtra("AMOUNT");
            mTotalMoneyDay=intent.getFloatExtra("AMOUNT_FLOAT",-1);
            getListProductReport();
return;
        }
            mAmount = intent.getStringExtra("AMOUNT");
            mTotalMoneyDay=intent.getFloatExtra("AMOUNT_FLOAT",-1);
            getListProductReportLastDay();


    }


    /**
     * Mục đích method thực hiện việc gọi presenter xử lý lấy danh sách thống kê theo sản phẩm
     * theo ngày hiện tại - 1 ngày
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void getListProductReportLastDay() {
        mActivityReportWithDayPresenter = new ActivityReportWithDayPresenter(this);
        mActivityReportWithDayPresenter.getListProductReportLastDay(this);
    }

    /**
     * Mục đích method thực hiện việc khởi tạo actionbar
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void initActionBar() {
        tbReportFood = findViewById(R.id.tbReportFood);
        setSupportActionBar(tbReportFood);
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void initView() {
        pcReportFood = findViewById(R.id.pcReportFood);
        rcvFoodReport = findViewById(R.id.rcvFoodReport);
        tvBack = findViewById(R.id.tvBack);
    }

    /**
     * Mục đích method thực hiện việc hiển thị biểu đồ thống kê
     * Sau khi lấy được danh sách thống kế sẽ hiển thị biểu đồ
     *
     * @created_by cvmanh on 01/28/2021
     */
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

    /**
     * Mục đích method thực hiện việc gọi presenter xử lý lấy dữ liệu thống kê theo ngày hiện tại
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void getListProductReport() {
        mActivityReportWithDayPresenter = new ActivityReportWithDayPresenter(this);
        mActivityReportWithDayPresenter.getListProductThisDay(this);
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách thống kê theo sản phẩm thành công và hiển thị lên view,
     * hiện biểu đồ thống kê
     *
     * @param listOrderDetail Danh sách thống kê theo sản phẩm
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void getListProductThisDaySuccess(List<OrderDetail> listOrderDetail) {
        mListProductReport = listOrderDetail;
        mReportWithDayAdapter = new ReportWithDayAdapter(this, R.layout.item_report_with_day, listOrderDetail);
        rcvFoodReport.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcvFoodReport.setAdapter(mReportWithDayAdapter);
        mReportWithDayAdapter.notifyDataSetChanged();
        showPieChart();
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách thống kê sản phẩm theo ngày hiện tại - 1 ngày và hiển thị lên recycleView,
     * hiện biểu đồ thống kê
     *
     * @param listProductReport Danh sách sản phẩm thống kê
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void getListProductReportLastDaySuccess(List<OrderDetail> listProductReport) {
        mListProductReport = listProductReport;
        mReportWithDayAdapter = new ReportWithDayAdapter(this, R.layout.item_report_with_day, mListProductReport);
        rcvFoodReport.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcvFoodReport.setAdapter(mReportWithDayAdapter);
        mReportWithDayAdapter.notifyDataSetChanged();
        showPieChart();
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xử lý thất bại từ presenter và hiện thông báo
     *
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void onFailed() {
        Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }
}