package vn.com.misa.ccl.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.entity.Color;
import vn.com.misa.ccl.entity.Order;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.entity.Unit;
import vn.com.misa.ccl.util.DatabaseInfomation;

/**
‐ Mục đích Class thực hiện việc xử lý các công việc của FragmentListOrder
*
‐ {@link vn.com.misa.ccl.presenter.FragmentListOrderPresenter}
*
‐ @created_by cvmanh on 01/25/2021
*/

public class FragmentListOrderModel {
    private IFragmentListOrderModel mIFragmentListOrderModel;

    public FragmentListOrderModel(IFragmentListOrderModel mIFragmentListOrderModel) {
        this.mIFragmentListOrderModel = mIFragmentListOrderModel;
    }

    private SQLiteDatabase mSqLiteDatabase;

    private List<OrderDetail> mListOrderDetail;

    /**
     * Mục đích method thực hiện việc lấy danh sách Order từ database rồi gửi kết quả về presenter
     *
     * @param activity instance activity
     *
     * @created_by cvmanh on 01/25/2021
     */
    public void getListOrder(Activity activity){
        mSqLiteDatabase= DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
        mListOrderDetail=new ArrayList<>();
        Cursor cursor=mSqLiteDatabase.rawQuery("SELECT "+DatabaseInfomation.TABLE_ORDERS+"."
                +DatabaseInfomation.COLUMN_ORDER_ID+"" +
                ","+DatabaseInfomation.COLUMN_ORDER_STATUS+","+DatabaseInfomation.COLUMN_TABLE_NAME+"" +
                ","+DatabaseInfomation.COLUMN_TOTAL_PEOPLE+","+DatabaseInfomation.COLUM_ORDER_AMOUNT+"," +
                ""+DatabaseInfomation.TABLE_PRODUCT_IMAGES+"."+DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID+","
                +DatabaseInfomation.COLUMN_IMAGE+"," +
                ""+DatabaseInfomation.TABLE_ORDER_DETAIL+"."+DatabaseInfomation.COLUM_ORDER_DETAIL_ID+","
                +DatabaseInfomation.COLUMN_QUANTITY+","+DatabaseInfomation.TABLE_COLORS+"."+
                DatabaseInfomation.COLUMN_COLOR_ID+","+DatabaseInfomation.COLUMN_COLOR_KEY+","
                +DatabaseInfomation.TABLE_MYPRODUCTS+"."+
                DatabaseInfomation.COLUMN_MYPRODUCT_ID+","+DatabaseInfomation.COLUMN_PRODUCT_PRICE+","+
                DatabaseInfomation.COLUMN_PRODUCT_STATUS+","+DatabaseInfomation.TABLE_UNITS+"."
                +DatabaseInfomation.COLUMN_UNIT_ID+","
                +DatabaseInfomation.COLUMN_UNIT_NAME+"," +
                "group_concat("+DatabaseInfomation.TABLE_MYPRODUCTS+"."
                +DatabaseInfomation.COLUMN_PRODUCT_NAME+ "||' ('||" +DatabaseInfomation.COLUMN_QUANTITY+"||')',', ') as Tong" +" FROM "
                +DatabaseInfomation.TABLE_ORDERS+", "
                +DatabaseInfomation.TABLE_UNITS+","
                +DatabaseInfomation.TABLE_PRODUCT_IMAGES+","+DatabaseInfomation.TABLE_COLORS+","
                +DatabaseInfomation.TABLE_ORDER_DETAIL+","
                +DatabaseInfomation.TABLE_MYPRODUCTS+" WHERE "
                +DatabaseInfomation.TABLE_ORDERS+"."
                +DatabaseInfomation.COLUMN_ORDER_ID+"="
                +DatabaseInfomation.TABLE_ORDER_DETAIL+"."
                +DatabaseInfomation.COLUMN_ORDER_ID+" AND "
                +DatabaseInfomation.TABLE_ORDER_DETAIL+"."
                +DatabaseInfomation.COLUMN_MYPRODUCT_ID+"="
                +DatabaseInfomation.TABLE_MYPRODUCTS+"."
                +DatabaseInfomation.COLUMN_MYPRODUCT_ID+" AND "
                +DatabaseInfomation.TABLE_UNITS+"."+DatabaseInfomation.COLUMN_UNIT_ID+"="
                +DatabaseInfomation.TABLE_MYPRODUCTS+"."+DatabaseInfomation.COLUMN_UNIT_ID+" AND "
                +DatabaseInfomation.TABLE_PRODUCT_IMAGES+"."
                +DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID+"="+DatabaseInfomation.TABLE_MYPRODUCTS
                +"."+DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID+" AND "
                +DatabaseInfomation.TABLE_MYPRODUCTS+"."
                +DatabaseInfomation.COLUMN_COLOR_ID+"="+DatabaseInfomation.TABLE_COLORS+"."
                +DatabaseInfomation.COLUMN_COLOR_ID+" GROUP BY "
                +DatabaseInfomation.TABLE_ORDERS+"."+DatabaseInfomation.COLUMN_ORDER_ID+"",null);
        for(int i=cursor.getCount()-1;i>=0;i--){
            cursor.moveToPosition(i);
            mListOrderDetail.add(new OrderDetail(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUM_ORDER_DETAIL_ID)),
                    new Order(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_ORDER_ID)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_ORDER_STATUS)),
                            cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_TABLE_NAME)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_TOTAL_PEOPLE)),
                            cursor.getFloat(cursor.getColumnIndex(DatabaseInfomation.COLUM_ORDER_AMOUNT))),

                    new Product(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_MYPRODUCT_ID)),
                            cursor.getString(cursor.getColumnIndex("Tong")),
                            cursor.getFloat(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_PRICE)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_STATUS)),
                            new ProductImage(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID)),
                                    cursor.getBlob(cursor.getColumnIndex(DatabaseInfomation.COLUMN_IMAGE))),
                            new Unit(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_UNIT_ID)),
                                    cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_UNIT_NAME))),
                            new Color(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_COLOR_ID)),
                                    cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_COLOR_KEY)))),
                    cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_QUANTITY))));
        }
        if(cursor!=null){
            mIFragmentListOrderModel.getListOrderSuccess(mListOrderDetail);
        }
    }

    /**
     * Mục đích method thực hiện việc xóa order theo mã order trong database và gửi kết quả về presenter
     *
     * @param activity instance activity
     * @param  orderID mã order
     *
     * @created_by cvmanh on 01/25/2021
     */
    public void removeItemOrder(Activity activity,int orderID){
        mSqLiteDatabase=DatabaseHelper.initDatabase(activity,DatabaseInfomation.DATABASE_NAME);
        long resulOrderDetail=mSqLiteDatabase.delete(DatabaseInfomation.TABLE_ORDER_DETAIL,
                DatabaseInfomation.COLUMN_ORDER_ID+"=?",new String[]{String.valueOf(orderID)});
        if(resulOrderDetail>0){
            long resultOrder=mSqLiteDatabase.delete(DatabaseInfomation.TABLE_ORDERS,
                    DatabaseInfomation.COLUMN_ORDER_ID+"=?",new String[]{String.valueOf(orderID)});
            if(resultOrder>0){
                mIFragmentListOrderModel.removeOrderSuccess();
                return;
            }
            mIFragmentListOrderModel.onFailed();
            return;
        }
        mIFragmentListOrderModel.onFailed();
    }

    public interface IFragmentListOrderModel{
        public void getListOrderSuccess(List<OrderDetail> listOrderDetail);

        public void removeOrderSuccess();

        public void onFailed();
    }
}
