package vn.com.misa.ccl.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;

import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.util.DatabaseInfomation;

public class FragmentReportTimeRecentlyModel {

    private SQLiteDatabase mSqliteDatabase;

    public void processReport(Activity activity) {
        // strftime: lấy ngày, tháng, năm trong sql. định dạng nagyf tháng trong sql phải được lưu dưới dang: yyyy-MM-dd
        mSqliteDatabase= DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
        Cursor cursor=mSqliteDatabase.rawQuery("SELECT strftime('%Y',"+DatabaseInfomation.COLUMN_ORDER_CREATED_AT+") as Thang," +
                "SUM("+DatabaseInfomation.COLUM_ORDER_AMOUNT+") as Tong FROM "+DatabaseInfomation.TABLE_ORDERS+" " +
                "GROUP BY strftime('%Y',"+DatabaseInfomation.COLUMN_ORDER_CREATED_AT+")",null);
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            Log.d("Tien:",cursor.getString(cursor.getColumnIndex("Tong"))+"");
        }
    }
}
