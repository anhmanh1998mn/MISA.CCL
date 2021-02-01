package vn.com.misa.ccl.view.report.day;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
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

    private String mAmount = "", mStartDay, mEndDay, mDayName;

    private ActivityReportWithDayPresenter mActivityReportWithDayPresenter;

    private ReportWithDayAdapter mReportWithDayAdapter;

    private List<OrderDetail> mListProductReport;

    private TextView tvBack, tvTime;

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
        try {
            tvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện kiểm tra thống kê theo tháng hay năm
     * Nếu loại dữ liệu nhận=2: Thống kê doanh thu theo sản phẩm theo ngày hiện tại
     * Nếu loại dữ liệu nhận=3: Thống kê doanh thu theo sản phẩm theo ngày của tuần
     * Nếu loại dữ liệu nhận =1: thóng kê doanh thu theo sản phẩm theo ngày hiện tại -1 ngày
     * Nếu loại dữ liệu nhận =4: thóng kê doanh thu theo sản phẩm theo tháng của năm
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void receiveAmount() {
        try {
            Intent intent = getIntent();
            if (intent.getIntExtra("REPORT_TYPE", -1) == 2) {
                mAmount = intent.getStringExtra("AMOUNT");
                mTotalMoneyDay = intent.getFloatExtra("AMOUNT_FLOAT", -1);
                tvTime.setText(getResources().getString(R.string.time_this_day));
                getListProductReport();

            } else if (intent.getIntExtra("REPORT_TYPE", -1) == 1) {
                mAmount = intent.getStringExtra("AMOUNT");
                mTotalMoneyDay = intent.getFloatExtra("AMOUNT_FLOAT", -1);
                tvTime.setText(getResources().getString(R.string.time_last_day));
                getListProductReportLastDay();
            } else if (intent.getIntExtra("REPORT_TYPE", -1) == 3) {
                mAmount = String.valueOf(intent.getFloatExtra("AMOUNT_FLOAT", -1));
                mTotalMoneyDay = intent.getFloatExtra("AMOUNT_FLOAT", -1);
                mDayName = intent.getStringExtra("DAY_NAME");
                tvTime.setText(intent.getStringExtra("DAY_OF_WEEK") + " (" + splitDate(mDayName) + ")");
                getReportTimeDay();
            } else {
                mAmount = String.valueOf(intent.getFloatExtra("AMOUNT_FLOAT", -1));
                mTotalMoneyDay = intent.getFloatExtra("AMOUNT_FLOAT", -1);
                mDayName = intent.getStringExtra("YEAR_NAME") + "-" + intent.getStringExtra("MONTH_NAME");
                tvTime.setText("Tháng " + intent.getStringExtra("MONTH_NAME") + " (01/" +
                        intent.getStringExtra("MONTH_NAME") + "/" + intent.getStringExtra("YEAR_NAME") + " - " +
                        "29/" + intent.getStringExtra("MONTH_NAME") + "/" + intent.getStringExtra("YEAR_NAME") + ")");
                getReportTimeYear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc định đạng ngày tháng theo dd/MM/yyyy
     *
     * @param inputDate Ngày tháng truyền vào
     * @return trả về ngày đã được định đạng theo định đạng dd/MM/yyy
     * @created_by cvmanh on 01/30/2021
     */
    private String splitDate(String inputDate) {
        String[] arraySplitDate = inputDate.split("-");
        return arraySplitDate[2] + "/" + arraySplitDate[1] + "/" + arraySplitDate[0];
    }

    /**
     * Mục đích method thực hiện việc gọi presenter xử lý thống kê theo năm
     *
     * @created_by cvmanh on 01/30/2021
     */
    private void getReportTimeYear() {
        try {
            mActivityReportWithDayPresenter = new ActivityReportWithDayPresenter(this);
            mActivityReportWithDayPresenter.getReportTimeYear(this, mDayName + "-01", mDayName + "-29");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc gọi presenter xử lý thống kê theo ngày
     *
     * @created_by cvmanh on 01/30/2021
     */
    private void getReportTimeDay() {
        try {
            mActivityReportWithDayPresenter = new ActivityReportWithDayPresenter(this);
            mActivityReportWithDayPresenter.getReportTimeDay(this, mDayName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc gọi presenter xử lý lấy danh sách thống kê theo sản phẩm
     * theo ngày hiện tại - 1 ngày
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void getListProductReportLastDay() {
        try {
            mActivityReportWithDayPresenter = new ActivityReportWithDayPresenter(this);
            mActivityReportWithDayPresenter.getListProductReportLastDay(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo actionbar
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void initActionBar() {
        try {
            tbReportFood = findViewById(R.id.tbReportFood);
            setSupportActionBar(tbReportFood);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void initView() {
        try {
            pcReportFood = findViewById(R.id.pcReportFood);
            rcvFoodReport = findViewById(R.id.rcvFoodReport);
            tvBack = findViewById(R.id.tvBack);
            tvTime = findViewById(R.id.tvTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc hiển thị biểu đồ thống kê
     * Sau khi lấy được danh sách thống kế sẽ hiển thị biểu đồ
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void showPieChart() {
        try {
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
            pcReportFood.animateXY(1400, 1400); // set animation
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện khởi tạo dữ liệu cho biểu đồ
     * Dữ liệu được lấy từ danh sách thống kê sản phẩm thành công
     *
     * @return trả về 1 mảng PieEntry
     * @created_by cvmanh on 01/28/2021
     */
    private ArrayList<PieEntry> dataValues() {
        try {
            ArrayList<PieEntry> daVal = new ArrayList<>();
            for (int i = 0; i < mListProductReport.size(); i++) {
                if (i < 7) {
                    daVal.add(new PieEntry((mListProductReport.get(i).getmProductPriceOut() / mTotalMoneyDay) * 100, "%"));
                    float a = (mListProductReport.get(i).getmProductPriceOut());
                }
            }
            return daVal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mục đích method thực hiện việc gọi presenter xử lý lấy dữ liệu thống kê theo ngày hiện tại
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void getListProductReport() {
        try {
            mActivityReportWithDayPresenter = new ActivityReportWithDayPresenter(this);
            mActivityReportWithDayPresenter.getListProductThisDay(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            mListProductReport = listOrderDetail;
            mReportWithDayAdapter = new ReportWithDayAdapter(this, R.layout.item_report_with_day, listOrderDetail);
            rcvFoodReport.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rcvFoodReport.setAdapter(mReportWithDayAdapter);
            mReportWithDayAdapter.notifyDataSetChanged();
            showPieChart();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            mListProductReport = listProductReport;
            mReportWithDayAdapter = new ReportWithDayAdapter(this, R.layout.item_report_with_day, mListProductReport);
            rcvFoodReport.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rcvFoodReport.setAdapter(mReportWithDayAdapter);
            mReportWithDayAdapter.notifyDataSetChanged();
            showPieChart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý thống kê theo năm thành công
     * Hiển thị danh sách thống kê lên view
     * Hiển thị biểu đồ thống kê
     *
     * @param listReportYear danh sách thống kê theo năm
     * @created_by cvmanh on 01/30/2021
     */
    @Override
    public void getReportYearSuccess(List<OrderDetail> listReportYear) {
        try {
            mListProductReport = listReportYear;
            mReportWithDayAdapter = new ReportWithDayAdapter(this, R.layout.item_report_with_day, mListProductReport);
            rcvFoodReport.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rcvFoodReport.setAdapter(mReportWithDayAdapter);
            mReportWithDayAdapter.notifyDataSetChanged();
            showPieChart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xử lý thất bại từ presenter và hiện thông báo
     *
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void onFailed() {
        try {
            Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}