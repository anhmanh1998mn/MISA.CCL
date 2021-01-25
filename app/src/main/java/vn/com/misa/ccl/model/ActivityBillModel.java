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

public class ActivityBillModel {

    private IActivityBillModel mIActivityBillModel;

    public ActivityBillModel(IActivityBillModel mIActivityBillModel) {
        this.mIActivityBillModel = mIActivityBillModel;
    }

    private SQLiteDatabase mSqLiteDatabase;

    private List<OrderDetail> mListOrderDetaill;

    public void getOrderDetail(Activity activity,int orderID){
        mListOrderDetaill=new ArrayList<>();
        mSqLiteDatabase= DatabaseHelper.initDatabase(activity,DatabaseInfomation.DATABASE_NAME);
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
                +DatabaseInfomation.COLUMN_UNIT_NAME+","
                +DatabaseInfomation.TABLE_MYPRODUCTS+"."+DatabaseInfomation.COLUMN_PRODUCT_NAME+","+
                DatabaseInfomation.TABLE_ORDERS+"."+DatabaseInfomation.COLUMN_ORDER_CREATED_AT+" FROM "
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
                +DatabaseInfomation.COLUMN_COLOR_ID+" AND "
                +DatabaseInfomation.TABLE_ORDERS+"."+DatabaseInfomation.COLUMN_ORDER_ID+"="+orderID+"",null);
        for(int i=cursor.getCount()-1;i>=0;i--){
            cursor.moveToPosition(i);
            mListOrderDetaill.add(new OrderDetail(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUM_ORDER_DETAIL_ID)),
                    new Order(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_ORDER_ID)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_ORDER_STATUS)),
                            cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_ORDER_CREATED_AT)),
                            cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_TABLE_NAME)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_TOTAL_PEOPLE)),
                            cursor.getFloat(cursor.getColumnIndex(DatabaseInfomation.COLUM_ORDER_AMOUNT))),

                    new Product(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_MYPRODUCT_ID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_NAME)),
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
            mIActivityBillModel.getOrderDetail(mListOrderDetaill);
            return;
        }
        mIActivityBillModel.onFailed();
    }

    public interface IActivityBillModel{
        public void getOrderDetail(List<OrderDetail> listOrderDetail);

        public void onFailed();
    }
}
