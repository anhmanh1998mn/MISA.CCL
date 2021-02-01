package vn.com.misa.ccl.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.entity.Order;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.entity.Report;
import vn.com.misa.ccl.entity.Unit;
import vn.com.misa.ccl.util.Common;
import vn.com.misa.ccl.util.DatabaseInfomation;

/**
 * ‐ Mục đích Class thực hiện xử lý các công việc yêu cầu từ FragmentMainReportPresenter
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.report.FragmentMainReport}
 * ‐ {@link vn.com.misa.ccl.presenter.FragmentMainReportPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/30/2021
 */

public class FragmentMainReportModel {

    private IFragmentMainReportModel mIFragmentMainReportModel;

    private String[] mListDateTimeSplit;

    private List<Report> mListReportDayOfWeek;

    public FragmentMainReportModel(IFragmentMainReportModel mIFragmentMainReportModel) {
        this.mIFragmentMainReportModel = mIFragmentMainReportModel;
    }

    private SQLiteDatabase mSqliteDatabase;

    private List<OrderDetail> mListProductWithDay;

    private Cursor mCursorWithWeek, mCursorWithMonth, mCursorWithYear;

    private String mDayOfMonth = "1";

    private int mEndDayOfMonth = 0;

    /**
     * Mục đích method thực hiện việc xử lý lấy dữ liệu thống kê theo khoảng thời gian và gửi kết quả
     * xử lý thống kê vè presenter
     *
     * @param activity instance activity
     * @param startDay ngày bắt đầu thống kê
     * @param endDay   ngày kết thúc thống kê
     * @created_by cvmanh on 01/30/2021
     */
    public void getListProductReportPeriod(Activity activity, String startDay, String endDay) {
        try {
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            mListProductWithDay = new ArrayList<>();
            Cursor cursor = mSqliteDatabase.rawQuery("SELECT *,SUM(" + DatabaseInfomation.COLUMN_QUANTITY + "*" +
                    "" + DatabaseInfomation.COLUM_PRODUCT_PRICE_OUT + ") as TotalAmount," +
                    "SUM(" + DatabaseInfomation.COLUMN_QUANTITY + ") as TotalQuantity FROM " + DatabaseInfomation.TABLE_MYPRODUCTS + "," +
                    DatabaseInfomation.TABLE_ORDERS + "," + DatabaseInfomation.TABLE_ORDER_DETAIL + "," +
                    DatabaseInfomation.TABLE_UNITS + " WHERE " + DatabaseInfomation.TABLE_MYPRODUCTS + "." +
                    DatabaseInfomation.COLUMN_UNIT_ID + "=" + DatabaseInfomation.TABLE_UNITS + "." +
                    DatabaseInfomation.COLUMN_UNIT_ID + " AND " + DatabaseInfomation.TABLE_MYPRODUCTS + "." +
                    DatabaseInfomation.COLUMN_MYPRODUCT_ID + "=" + DatabaseInfomation.TABLE_ORDER_DETAIL + "." +
                    DatabaseInfomation.COLUMN_MYPRODUCT_ID + " AND " + DatabaseInfomation.TABLE_ORDER_DETAIL + "." +
                    DatabaseInfomation.COLUMN_ORDER_ID + "=" + DatabaseInfomation.TABLE_ORDERS + "." +
                    DatabaseInfomation.COLUMN_ORDER_ID + " AND DATE(" + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") BETWEEN DATE('" + startDay + "') AND DATE('" + endDay + "') " +
                    "GROUP BY " + DatabaseInfomation.TABLE_ORDER_DETAIL + "." + DatabaseInfomation.COLUMN_MYPRODUCT_ID + " ORDER BY TotalAmount DESC", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                mListProductWithDay.add(new OrderDetail(
                        cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUM_ORDER_DETAIL_ID)),
                        new Order(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_ORDER_ID)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_ORDER_STATUS)),
                                cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_ORDER_CREATED_AT)),
                                cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_TABLE_NAME)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_TOTAL_PEOPLE)),
                                cursor.getFloat(cursor.getColumnIndex(DatabaseInfomation.COLUM_ORDER_AMOUNT))),
                        new Product(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_MYPRODUCT_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_NAME)),
                                new Unit(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_UNIT_ID)),
                                        cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_UNIT_NAME)))),
                        cursor.getInt(cursor.getColumnIndex("TotalQuantity")),
                        cursor.getFloat(cursor.getColumnIndex("TotalAmount"))));
            }
            float sumAllMoney = 0;
            for (int i = 0; i < mListProductWithDay.size(); i++) {
                sumAllMoney = sumAllMoney + mListProductWithDay.get(i).getProductPriceOut();
            }
            if (cursor.getCount() > 0) {
                mIFragmentMainReportModel.getListReportWithPeroid(mListProductWithDay, sumAllMoney);
                return;
            }
            mIFragmentMainReportModel.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý thống kê theo tuần và gửi kết quả thống kê về presenter
     *
     * @param activity  instance activity
     * @param typeClick loại button click: typeClick=ThisWeek: Thống kê theo tuần này, Ngược lại, thống kê theo tuần trước
     * @created_by cvmanh on 01/30/2021
     */
    public void getReportLineChart(Activity activity, String typeClick) {
        try {
            splitDateTime();
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            if (typeClick.equals("ThisWeek")) {
                mCursorWithWeek = mSqliteDatabase.rawQuery("SELECT strftime('%W'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Tuan, " +
                        "SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong," +
                        "strftime('%w'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Thu," +
                        "strftime('%d'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Ngay," +
                        DatabaseInfomation.COLUMN_ORDER_CREATED_AT + " FROM " + DatabaseInfomation.TABLE_ORDERS + " " +
                        " WHERE strftime('%W'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")=strftime('%W','now') AND " +
                        "strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='"
                        + mListDateTimeSplit[0] + "' AND " + DatabaseInfomation.COLUMN_ORDER_STATUS + "=2 " +
                        "GROUP BY strftime('%d'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")", null);
                insertDataListReport();
            } else {
                mCursorWithWeek = mSqliteDatabase.rawQuery("SELECT strftime('%W'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Tuan, " +
                        "SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong," +
                        "strftime('%w'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Thu," +
                        "strftime('%d'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Ngay," +
                        DatabaseInfomation.COLUMN_ORDER_CREATED_AT + " FROM " + DatabaseInfomation.TABLE_ORDERS + " " +
                        " WHERE strftime('%W'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")=(strftime('%W','now','-7 days')) AND " +
                        "strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='"
                        + mListDateTimeSplit[0] + "' AND " + DatabaseInfomation.COLUMN_ORDER_STATUS + "=2 " +
                        "GROUP BY strftime('%d'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")", null);
                insertDataListReport();
            }
            for (int i = 0; i < mCursorWithWeek.getCount(); i++) {
                mCursorWithWeek.moveToPosition(i);
                float amount = mCursorWithWeek.getFloat(mCursorWithWeek.getColumnIndex("Tong"));
                for (int j = 0; j < mListReportDayOfWeek.size(); j++) {
                    if (("Thứ " + (Integer.parseInt(mCursorWithWeek.getString(mCursorWithWeek.getColumnIndex("Thu"))) + 1)).equals(mListReportDayOfWeek.get(j).getDayOfWeek()) ||
                            ((Integer.parseInt(mCursorWithWeek.getString(mCursorWithWeek.getColumnIndex("Thu"))) + 1)) == 8) {
                        mListReportDayOfWeek.get(j).setDayOfMonth(mCursorWithWeek.getString(mCursorWithWeek.getColumnIndex(DatabaseInfomation.COLUMN_ORDER_CREATED_AT)));
                        mListReportDayOfWeek.get(j).setTotalMoney(amount);
                    }
                }
            }
            float totalReport = 0;
            for (int i = 0; i < mListReportDayOfWeek.size(); i++) {
                totalReport = totalReport + mListReportDayOfWeek.get(i).getTotalMoney();
            }
            if (totalReport > 0) {
                mIFragmentMainReportModel.getReportTimeWeekSuccess(mListReportDayOfWeek);
                return;
            }
            mIFragmentMainReportModel.getReportDataNull();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo dữ liệu ban đầu cho danh sách thống kê tuần
     *
     * @created_by cvmanh on 01/30/2021
     */
    private void insertDataListReport() {
        try {
            mListReportDayOfWeek = new ArrayList<>();
            mListReportDayOfWeek.add(new Report(1, Common.MONDAY, mDayOfMonth, 0));
            mListReportDayOfWeek.add(new Report(1, Common.TUESDAY, mDayOfMonth, 0));
            mListReportDayOfWeek.add(new Report(1, Common.WEDNESDAY, mDayOfMonth, 0));
            mListReportDayOfWeek.add(new Report(1, Common.THURSDAY, mDayOfMonth, 0));
            mListReportDayOfWeek.add(new Report(1, Common.FRIDAY, mDayOfMonth, 0));
            mListReportDayOfWeek.add(new Report(1, Common.SATURDAY, mDayOfMonth, 0));
            mListReportDayOfWeek.add(new Report(1, Common.SUNDAY, mDayOfMonth, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý thống kê kết quả theo tháng và trả kết quả về presenter
     *
     * @param activity  instance activity
     * @param typeClick loại button click. typeClick=ThisMonth: thống kê theo tháng này.
     *                  Ngược lại, thống kê theo tháng trước
     * @created_by cvmanh on 01/30/2021
     */
    public void getReportLineChartWithMonth(Activity activity, String typeClick) {
        try {
            splitDateTime();
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            if (typeClick.equals("ThisMonth")) {

                //lấy ngày cuối cùng của tháng hiện tại
                getEndDayOfThisMonth();
                mCursorWithMonth = mSqliteDatabase.rawQuery("SELECT strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Nam," +
                        "strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Thang" +
                        ",SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong," +
                        "strftime('%d'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Ngay," +
                        DatabaseInfomation.COLUMN_ORDER_CREATED_AT + " FROM " + DatabaseInfomation.TABLE_ORDERS + " " +
                        "WHERE strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='" + mListDateTimeSplit[1] + "' AND " +
                        "strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='" + mListDateTimeSplit[0] + "' AND " +
                        DatabaseInfomation.COLUMN_ORDER_STATUS + "=2" +

                        " GROUP BY strftime('%d'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")", null);
            } else {
                int lastMonth = Integer.parseInt(mListDateTimeSplit[1]) - 1;
                mCursorWithMonth = mSqliteDatabase.rawQuery("SELECT strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Nam," +
                        "strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Thang" +
                        ",SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong," +
                        "strftime('%d'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Ngay," +
                        DatabaseInfomation.COLUMN_ORDER_CREATED_AT + " FROM " + DatabaseInfomation.TABLE_ORDERS + " " +
                        "WHERE strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")=strftime('%m','now','-20 days') AND " +
                        "strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='" + mListDateTimeSplit[0] + "' AND " +
                        DatabaseInfomation.COLUMN_ORDER_STATUS + "=2" +

                        " GROUP BY strftime('%d'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")", null);
                //lấy ngày cuối cùng của tháng trước
                getEndDayOfLastMonth();
            }
            insertDatatoListReport();
            for (int i = 0; i < mCursorWithMonth.getCount(); i++) {
                mCursorWithMonth.moveToPosition(i);
                float amount = mCursorWithMonth.getFloat(mCursorWithMonth.getColumnIndex("Tong"));
                Log.d("TongThang", amount + "");
                for (int j = 0; j < mListReportDayOfWeek.size(); j++) {
                    if (("Ngày " + (mCursorWithMonth.getString(mCursorWithMonth.getColumnIndex("Ngay")))).equals(mListReportDayOfWeek.get(j).getDayOfWeek())) {
                        mListReportDayOfWeek.get(j).setDayOfMonth(mCursorWithMonth.getString(mCursorWithMonth.getColumnIndex(DatabaseInfomation.COLUMN_ORDER_CREATED_AT)));
                        mListReportDayOfWeek.get(j).setTotalMoney(amount);
                    }
                }
            }
            float totalMoneyReport = 0;
            for (int i = 0; i < mListReportDayOfWeek.size(); i++) {
                totalMoneyReport = totalMoneyReport + mListReportDayOfWeek.get(i).getTotalMoney();
            }
            if (totalMoneyReport > 0) {
                mIFragmentMainReportModel.getReportTimeMonthSuccess(mListReportDayOfWeek);
                return;
            }
            mIFragmentMainReportModel.getReportDataNull();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý lấy ngàu cuối cùng của tháng hiện tại -1 tháng ( tháng trước)
     *
     * @created_by cvmanh on 02/01/2021
     */
    private void getEndDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        mEndDayOfMonth = calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * Mục đích method thực hiện việc xử lý lấy ngàu cuối cùng của tháng hiện tại
     *
     * @created_by cvmanh on 02/01/2021
     */
    private void getEndDayOfThisMonth() {
        Calendar calendar = Calendar.getInstance();
        mEndDayOfMonth = calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * Mục đích method thực hiện việc khởi tạo danh sách thống kê ban đầu theo các ngày trong tháng
     *
     * @created_by cvmanh on 01/30/2021
     */
    private void insertDatatoListReport() {
        try {
            mListReportDayOfWeek = new ArrayList<>();
            for (int i = 1; i <= mEndDayOfMonth; i++) {
                if (i < 10) {
                    mListReportDayOfWeek.add(new Report(i, Common.DAY_NAME_ONE + i, "0", Common.sTotalMoney));
                } else {
                    mListReportDayOfWeek.add(new Report(i, Common.DAY_NAME_TWO + i, "0", Common.sTotalMoney));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý thống kê theo năm và gửi kết quả về presenter
     *
     * @param activity  instance activity
     * @param typeClick loại button click. typeClick=ThisYear: thống kê theo năm hiện tại
     *                  Ngược lại, thống kê theo năm trước
     * @created_by cvmanh on 01/30/2021
     */
    public void getReportLineChartWithYear(Activity activity, String typeClick) {
        try {
            splitDateTime();
            mListReportDayOfWeek = new ArrayList<>();
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            if (typeClick.equals("ThisYear")) {
                mCursorWithYear = mSqliteDatabase.rawQuery("SELECT strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Nam," +
                        "strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Thang" +
                        ",SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong FROM " + DatabaseInfomation.TABLE_ORDERS + " " +
                        "WHERE strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='" + mListDateTimeSplit[0] + "' AND " +
                        DatabaseInfomation.COLUMN_ORDER_STATUS + "=2" +

                        " GROUP BY strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")", null);
            } else {
                int lastYear = Integer.parseInt(mListDateTimeSplit[0]) - 1;
                mCursorWithYear = mSqliteDatabase.rawQuery("SELECT strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Nam," +
                        "strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Thang" +
                        ",SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong FROM " + DatabaseInfomation.TABLE_ORDERS + " " +
                        "WHERE strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='" + lastYear + "' AND " +
                        DatabaseInfomation.COLUMN_ORDER_STATUS + "=2" +

                        " GROUP BY strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")", null);
            }
            for (int i = 0; i < mCursorWithYear.getCount(); i++) {
                mCursorWithYear.moveToPosition(i);
//            Log.d("year",cursorYear.getFloat(cursorYear.getColumnIndex("Tong"))+"");
                mListReportDayOfWeek.add(new Report(i + 1, mCursorWithYear.getString(mCursorWithYear.getColumnIndex("Thang")),
                        mCursorWithYear.getString(mCursorWithYear.getColumnIndex("Nam")),
                        mCursorWithYear.getFloat(mCursorWithYear.getColumnIndex("Tong"))));
            }
            if (mListReportDayOfWeek.size() == 0) {
                mIFragmentMainReportModel.getReportDataNull();
                return;
            }
            mIFragmentMainReportModel.getReportTimeYearSuccess(mListReportDayOfWeek);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lấy ngày, tháng, năm hiện tại và lưu vào mảng string
     *
     * @created_by cvmanh on 01/30/2021
     */
    private void splitDateTime() {
        try {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DATE);
            calendar.set(year, month, day);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formatDateTime = simpleDateFormat.format(calendar.getTime());
            mListDateTimeSplit = formatDateTime.split("-");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IFragmentMainReportModel {
        public void getListReportWithPeroid(List<OrderDetail> listReportProduct, float sumAllMoney);

        public void getReportTimeWeekSuccess(List<Report> listReportWeek);

        public void getReportTimeMonthSuccess(List<Report> listReportWeek);

        public void getReportTimeYearSuccess(List<Report> listReportWeek);

        public void getReportDataNull();

        public void onFailed();
    }
}
