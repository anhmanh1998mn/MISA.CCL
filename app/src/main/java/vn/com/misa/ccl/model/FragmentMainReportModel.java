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
import vn.com.misa.ccl.util.DatabaseInfomation;

public class FragmentMainReportModel {

    private IFragmentMainReportModel mIFragmentMainReportModel;

    private String[] mListDateTimeSplit;

    private List<Report> mListReportDayOfWeek;

    public FragmentMainReportModel(IFragmentMainReportModel mIFragmentMainReportModel) {
        this.mIFragmentMainReportModel = mIFragmentMainReportModel;
    }

    private SQLiteDatabase mSqliteDatabase;

    private List<OrderDetail> mListProductWithDay;

    private Cursor cursor4,cursor1;

    public void getListProductReportPeriod(Activity activity, String startDay, String endDay) {
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
                DatabaseInfomation.COLUMN_ORDER_ID + " AND DATE(" + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") BETWEEN DATE('"+startDay+"') AND DATE('"+endDay+"') " +
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
        float sumAllMoney=0;
        for(int i=0;i<mListProductWithDay.size();i++){
            sumAllMoney=sumAllMoney+mListProductWithDay.get(i).getmProductPriceOut();
        }
        if (cursor.getCount() > 0) {
            mIFragmentMainReportModel.getListReportWithPeroid(mListProductWithDay,sumAllMoney);
            return;
        }
        mIFragmentMainReportModel.onFailed();
    }

    public void getReportLineChart(Activity activity,String typeClick) {
        splitDateTime();
        mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
        if(typeClick.equals("ThisWeek")){
             cursor4 = mSqliteDatabase.rawQuery("SELECT strftime('%W'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Tuan, " +
                    "SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong," +
                    "strftime('%w',"+DatabaseInfomation.COLUMN_ORDER_CREATED_AT+") as Thu," +
                    "strftime('%d',"+DatabaseInfomation.COLUMN_ORDER_CREATED_AT+") as Ngay,"+
                    DatabaseInfomation.COLUMN_ORDER_CREATED_AT+" FROM " + DatabaseInfomation.TABLE_ORDERS + " " +
                    " WHERE strftime('%W'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")=strftime('%W','now') AND " +
                    "strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='"
                    + mListDateTimeSplit[0] + "' AND " + DatabaseInfomation.COLUMN_ORDER_STATUS + "=2 " +
                    "GROUP BY strftime('%d',"+DatabaseInfomation.COLUMN_ORDER_CREATED_AT+")", null);
            insertDataListReport();
        }else {
            cursor4 = mSqliteDatabase.rawQuery("SELECT strftime('%W'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Tuan, " +
                    "SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong," +
                    "strftime('%w',"+DatabaseInfomation.COLUMN_ORDER_CREATED_AT+") as Thu," +
                    "strftime('%d',"+DatabaseInfomation.COLUMN_ORDER_CREATED_AT+") as Ngay,"+
                    DatabaseInfomation.COLUMN_ORDER_CREATED_AT+" FROM " + DatabaseInfomation.TABLE_ORDERS + " " +
                    " WHERE strftime('%W'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")=(strftime('%W','now')-1) AND " +
                    "strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='"
                    + mListDateTimeSplit[0] + "' AND " + DatabaseInfomation.COLUMN_ORDER_STATUS + "=2 " +
                    "GROUP BY strftime('%d',"+DatabaseInfomation.COLUMN_ORDER_CREATED_AT+")", null);
            insertDataListReport();
        }
        for (int i = 0; i < cursor4.getCount(); i++) {
            cursor4.moveToPosition(i);
            float amount = cursor4.getFloat(cursor4.getColumnIndex("Tong"));
            for(int j=0;j<mListReportDayOfWeek.size();j++){
                if(("Thứ "+(Integer.parseInt(cursor4.getString(cursor4.getColumnIndex("Thu")))+1)).equals(mListReportDayOfWeek.get(j).getDayOfWeek())||
                        ((Integer.parseInt(cursor4.getString(cursor4.getColumnIndex("Thu")))+1))==8){
                    mListReportDayOfWeek.get(j).setDayOfMonth(cursor4.getString(cursor4.getColumnIndex(DatabaseInfomation.COLUMN_ORDER_CREATED_AT)));
                    mListReportDayOfWeek.get(j).setTotalMoney(amount);
                }
            }
        }
        float totalReport=0;
        for(int i=0;i<mListReportDayOfWeek.size();i++){
            totalReport=totalReport+mListReportDayOfWeek.get(i).getTotalMoney();
        }
        if (totalReport > 0) {
            mIFragmentMainReportModel.getReportTimeWeekSuccess(mListReportDayOfWeek);
            return;
        }
        mIFragmentMainReportModel.getReportDataNull();
    }

    private void insertDataListReport(){
        mListReportDayOfWeek=new ArrayList<>();
        mListReportDayOfWeek.add(new Report(1,"Thứ 2","1",0));
        mListReportDayOfWeek.add(new Report(1,"Thứ 3","1",0));
        mListReportDayOfWeek.add(new Report(1,"Thứ 4","1",0));
        mListReportDayOfWeek.add(new Report(1,"Thứ 5","1",0));
        mListReportDayOfWeek.add(new Report(1,"Thứ 6","1",0));
        mListReportDayOfWeek.add(new Report(1,"Thứ 7","1",0));
        mListReportDayOfWeek.add(new Report(1,"Chủ nhật","1",0));
    }

    public void getReportLineChartWithMonth(Activity activity, String typeClick) {
        splitDateTime();
        mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
        if(typeClick.equals("ThisMonth")){
             cursor1 = mSqliteDatabase.rawQuery("SELECT strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Nam," +
                    "strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Thang" +
                    ",SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong," +
                    "strftime('%d'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Ngay,"+
                    DatabaseInfomation.COLUMN_ORDER_CREATED_AT+" FROM " + DatabaseInfomation.TABLE_ORDERS + " " +
                    "WHERE strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='" + mListDateTimeSplit[1] + "' AND " +
                    "strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='" + mListDateTimeSplit[0] + "' AND " +
                    DatabaseInfomation.COLUMN_ORDER_STATUS + "=2" +

                    " GROUP BY strftime('%d'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")", null);
        }else {
            int lastMonth=Integer.parseInt(mListDateTimeSplit[1])-1;
            cursor1 = mSqliteDatabase.rawQuery("SELECT strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Nam," +
                    "strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Thang" +
                    ",SUM(" + DatabaseInfomation.COLUM_ORDER_AMOUNT + ") as Tong," +
                    "strftime('%d'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ") as Ngay,"+
                    DatabaseInfomation.COLUMN_ORDER_CREATED_AT+" FROM " + DatabaseInfomation.TABLE_ORDERS + " " +
                    "WHERE strftime('%m'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='" + lastMonth + "' AND " +
                    "strftime('%Y'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")='" + mListDateTimeSplit[0] + "' AND " +
                    DatabaseInfomation.COLUMN_ORDER_STATUS + "=2" +

                    " GROUP BY strftime('%d'," + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + ")", null);
        }
        insertDatatoListReport();
        for(int i=0;i<cursor1.getCount();i++){
            cursor1.moveToPosition(i);
            float amount = cursor1.getFloat(cursor1.getColumnIndex("Tong"));
            Log.d("TongThang",amount+"");
            for(int j=0;j<mListReportDayOfWeek.size();j++){
                if(("Ngày "+(cursor1.getString(cursor1.getColumnIndex("Ngay")))).equals(mListReportDayOfWeek.get(j).getDayOfWeek())){
                    mListReportDayOfWeek.get(j).setDayOfMonth(cursor1.getString(cursor1.getColumnIndex(DatabaseInfomation.COLUMN_ORDER_CREATED_AT)));
                    mListReportDayOfWeek.get(j).setTotalMoney(amount);
                }
            }
        }
        float totalMoneyReport=0;
        for(int i=0;i<mListReportDayOfWeek.size();i++){
            totalMoneyReport=totalMoneyReport+mListReportDayOfWeek.get(i).getTotalMoney();
        }
        if(totalMoneyReport>0){
            mIFragmentMainReportModel.getReportTimeMonthSuccess(mListReportDayOfWeek);
            return;
        }
        mIFragmentMainReportModel.getReportDataNull();
    }

    private void insertDatatoListReport(){
        mListReportDayOfWeek=new ArrayList<>();
        for(int i=1;i<31;i++){
            if(i<10){
                mListReportDayOfWeek.add(new Report(i,"Ngày 0"+i,"0",0));
            }else {
                mListReportDayOfWeek.add(new Report(i,"Ngày "+i,"0",0));
            }
        }
    }

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

    public interface IFragmentMainReportModel{
        public void getListReportWithPeroid(List<OrderDetail> listReportProduct,float sumAllMoney);

        public void getReportTimeWeekSuccess(List<Report> listReportWeek);

        public void getReportTimeMonthSuccess(List<Report> listReportWeek);

        public void getReportDataNull();

        public void onFailed();
    }
}
