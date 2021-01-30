package vn.com.misa.ccl.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.util.DatabaseInfomation;

/**
 * ‐ Mục đích Class thực hiện việc xử lý các công việc của FragmentReportTimeRecently
 * - Thống kê tổng doanh thu theo: hôm qua, hôm nay, tuần này, tháng nay, năm nay
 * <p>
 * ‐ {@link vn.com.misa.ccl.presenter.FragmentReportTimeRecentlyPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/28/2021
 */

public class FragmentReportTimeRecentlyModel {

    private IFragmentReportTimeRecentlyModel mIFragmentReportTimeRecentlyModel;

    public FragmentReportTimeRecentlyModel(IFragmentReportTimeRecentlyModel mIFragmentReportTimeRecentlyModel) {
        this.mIFragmentReportTimeRecentlyModel = mIFragmentReportTimeRecentlyModel;
    }

    private SQLiteDatabase mSqliteDatabase;

    private String[] mListDateTimeSplit;

    private String mFormatMoney, mFormatMoneyMonth, mFormatMoneyThisDay, mFormatMoneyLastDay, mFormatMoneyThisWeek;

    private Float mAmountThisDay = 0f, mAmountLastDay = 0f;

    /**
     * Mục đích method thực hiện việc xử lý thống kê doanh thu và trả kết quả về presenter
     *
     * @param activity instance activity
     * @created_by cvmanh on 01/28/2021
     */
    public void processReport(Activity activity) {
        try {
            splitDateTime();
            // strftime: lấy ngày, tháng, năm trong sql. định dạng ngày tháng trong sql phải được lưu dưới dang: yyyy-MM-dd
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);

            reportWithThisYear();

            reportWithThisMonth();

            reportWithThisDay();

            reportWithLastDay();

            reportWithThisWeek();

            mIFragmentReportTimeRecentlyModel.processReportTimeRecentlySuccess(mFormatMoney, mFormatMoneyMonth, mFormatMoneyThisDay,
                    mFormatMoneyLastDay, mFormatMoneyThisWeek, mAmountThisDay, mAmountLastDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện thống kê doanh thu theo năm hiện tại
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void reportWithThisYear() {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            Cursor cursor = mSqliteDatabase.rawQuery("SELECT strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Nam," +
                    "strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Thang" +
                    ",SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong FROM " + DatabaseInfomation.TABLE_ORDERS + " " +
                    "WHERE strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='" + mListDateTimeSplit[0] + "' AND " +
                    DatabaseInfomation.COLUMN_ORDER_STATUS + "=2" +

                    " GROUP BY strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                mFormatMoney = (decimalFormat.format((cursor.getFloat(cursor.getColumnIndex("Tong")))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện thống kê doanh thu theo tháng hiện tại
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void reportWithThisMonth() {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            Cursor cursor1 = mSqliteDatabase.rawQuery("SELECT strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Nam," +
                    "strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Thang" +
                    ",SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong FROM " + DatabaseInfomation.TABLE_ORDERS + " " +
                    "WHERE strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='" + mListDateTimeSplit[1] + "' AND " +
                    "strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='" + mListDateTimeSplit[0] + "' AND " +
                    DatabaseInfomation.COLUMN_ORDER_STATUS + "=2" +

                    " GROUP BY strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")", null);
            for (int i = 0; i < cursor1.getCount(); i++) {
                cursor1.moveToPosition(i);
                mFormatMoneyMonth = (decimalFormat.format((cursor1.getFloat(cursor1.getColumnIndex("Tong")))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện thống kê doanh thu theo ngày hiện tại
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void reportWithThisDay() {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            Cursor cursor2 = mSqliteDatabase.rawQuery("SELECT (" + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")" +
                    ",SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong " +
                    "FROM " + DatabaseInfomation.TABLE_ORDERS + " WHERE DATE(" + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")=" +
                    "DATE('" + mListDateTimeSplit[0] + "-" + mListDateTimeSplit[1] + "-" + mListDateTimeSplit[2] + "') AND " +
                    DatabaseInfomation.COLUMN_ORDER_STATUS + "=2" +
                    " GROUP BY " + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + "", null);
            for (int i = 0; i < cursor2.getCount(); i++) {
                cursor2.moveToPosition(i);
                float amount = cursor2.getFloat(cursor2.getColumnIndex("Tong"));
                Log.d("Date", amount + "");
                mFormatMoneyThisDay = decimalFormat.format(amount);
                mAmountThisDay = amount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện thống kê doanh thu theo năm hiện tại - 1 ngày
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void reportWithLastDay() {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            Cursor cursor3 = mSqliteDatabase.rawQuery("SELECT (" + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")" +
                    ",SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong " +
                    "FROM " + DatabaseInfomation.TABLE_ORDERS + " WHERE DATE(" + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")=" +
                    "DATE('" + mListDateTimeSplit[0] + "-" + mListDateTimeSplit[1] + "-" + mListDateTimeSplit[2] + "','-1 day') AND " +
                    DatabaseInfomation.COLUMN_ORDER_STATUS + "=2" +
                    " GROUP BY " + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + "", null);
            for (int i = 0; i < cursor3.getCount(); i++) {
                cursor3.moveToPosition(i);
                float amount = cursor3.getFloat(cursor3.getColumnIndex("Tong"));
                mFormatMoneyLastDay = decimalFormat.format(amount);
                mAmountLastDay = amount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Mục đích method thực hiện thống kê doanh thu theo tuần hiện tại
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void reportWithThisWeek() {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            Cursor cursor4 = mSqliteDatabase.rawQuery("SELECT strftime('%W'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Tuan, " +
                    "SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong FROM " + DatabaseInfomation.TABLE_ORDERS + " " +
                    " WHERE strftime('%W'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")=strftime('%W','now') AND " +
                    "strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='"
                    + mListDateTimeSplit[0] + "' AND " + DatabaseInfomation.COLUMN_ORDER_STATUS + "=2", null);
            for (int i = 0; i < cursor4.getCount(); i++) {
                cursor4.moveToPosition(i);
                float amount = cursor4.getFloat(cursor4.getColumnIndex("Tong"));
                mFormatMoneyThisWeek = decimalFormat.format(amount);
//            String amount=cursor4.getString(cursor4.getColumnIndex("Tuan"));
//            Log.d("Tuan",amount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lấy giá trị ngày, tháng, năm và lưu vào mảng string
     *
     * @created_by cvmanh on 01/28/2021
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

    public interface IFragmentReportTimeRecentlyModel {
        public void processReportTimeRecentlySuccess(String amountYear, String amountMonth,
                                                     String amountThisDay, String amountLastDay, String amountThisWeek,
                                                     float totalMoneyThisDay, float totalMoneyLastDay);
    }
}
