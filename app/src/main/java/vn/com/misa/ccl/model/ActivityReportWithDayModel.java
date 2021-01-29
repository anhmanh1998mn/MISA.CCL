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
import vn.com.misa.ccl.entity.Unit;
import vn.com.misa.ccl.util.DatabaseInfomation;

/**
 * ‐ Mục đích Class thực hiện xử lý các công việc của ActivityReportWithDay
 * <p>
 * ‐ {@link vn.com.misa.ccl.presenter.ActivityReportWithDayPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/28/2021
 */

public class ActivityReportWithDayModel {

    private IActivityReportWithDayModel mIActivityReportWithDayModel;

    public ActivityReportWithDayModel(IActivityReportWithDayModel mIActivityReportWithDayModel) {
        this.mIActivityReportWithDayModel = mIActivityReportWithDayModel;
    }

    private List<OrderDetail> mListProductWithDay;

    private SQLiteDatabase mSqliteDatabase;

    private String[] mListDateTimeSplit;

    /**
     * Mục đích method thực hiện việc xử lý lấy danh sách thống kê sản phẩm theo ngày hiện tại và gửi kết quả về
     * presenter
     *
     * @param activity instance activity
     * @created_by cvmanh on 01/28/2021
     */
    public void getListProductThisDay(Activity activity) {
        splitDateTime();
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
                DatabaseInfomation.COLUMN_ORDER_ID + " AND DATE(" + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")=" +
                "DATE('" + mListDateTimeSplit[0] + "-" + mListDateTimeSplit[1] + "-" + mListDateTimeSplit[2] + "') " +
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
        Log.d("ReportProductSize", cursor.getCount() + "");
        if (cursor.getCount() > 0) {
            mIActivityReportWithDayModel.getListProductReportSuccess(mListProductWithDay);
            return;
        }
        mIActivityReportWithDayModel.onFailed();
    }

    /**
     * Mục đích method thực hiện việc xử lý lấy kết quả thống kê sản phẩm theo ngày hiện tại - 1 ngày
     *
     * @param activity instance activity
     * @created_by cvmanh on 01/28/2021
     */
    public void getListProductReportLastDay(Activity activity) {
        splitDateTime();
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
                DatabaseInfomation.COLUMN_ORDER_ID + " AND DATE(" + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")=" +
                "DATE('" + mListDateTimeSplit[0] + "-" + mListDateTimeSplit[1] + "-" + mListDateTimeSplit[2] + "','-1 day') " +
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
//        Log.d("ReportProductSize", cursor.getCount() + "");

        if (cursor.getCount() > 0) {
            mIActivityReportWithDayModel.getListProductReportLastDaySuccess(mListProductWithDay);
            return;
        }
        mIActivityReportWithDayModel.onFailed();
    }


    /**
     * Mục đích method thực hiện việc cắt chuổi ngăn cách với dâu "-" và lưu vàng mảng
     *
     * @created_by cvmanh on 01/28/2021
     */
    private void splitDateTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDateTime = simpleDateFormat.format(calendar.getTime());
        mListDateTimeSplit = formatDateTime.split("-");
    }

    public interface IActivityReportWithDayModel {
        public void getListProductReportSuccess(List<OrderDetail> listProductReport);

        public void getListProductReportLastDaySuccess(List<OrderDetail> listProductReport);

        public void onFailed();
    }
}
