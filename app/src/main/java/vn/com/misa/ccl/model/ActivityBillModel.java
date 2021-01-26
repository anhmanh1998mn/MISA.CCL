package vn.com.misa.ccl.model;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DecimalFormat;
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
‐ Mục đích Class thực hiện việc xử lý các công việc trong ActivityBill
*
‐ {@link vn.com.misa.ccl.presenter.ActivityBillPresenter}
*
‐ @created_by cvmanh on 01/25/2021
*/

public class ActivityBillModel {

    private IActivityBillModel mIActivityBillModel;

    public ActivityBillModel(IActivityBillModel mIActivityBillModel) {
        this.mIActivityBillModel = mIActivityBillModel;
    }

    private SQLiteDatabase mSqLiteDatabase;

    private List<OrderDetail> mListOrderDetaill;

    private String resultNumberEnter="0";

    /**
     * Mục đích method thực hiện việc lấy danh sánh OrderDetail từ database và gửi dữ liệu về presenter
     *
     * @param activity instance activity
     * @param orderID Mã order
     *
     * @created_by cvmanh on 01/25/2021
     */
    public void getOrderDetail(Activity activity,int orderID){
        try {
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý tiền thừa và tiền khách đưa trên máy tính và gửi kết quả về presenter
     *
     * @param activity instace activity
     * @param  numberEnter Giá trị hiển thị khi người dùng click button trên máy tính
     * @param amount Tổng tiền Order
     * @param nameClick Số mà người dùng chọn
     *
     * @created_by cvmanh on 01/25/2021
     */
    public void processCaculator(Activity activity,String numberEnter,String nameClick,float amount){
        try {
            switch (nameClick){
                case "C":{
                    resultNumberEnter="0";
                    mIActivityBillModel.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "":{
                    if(numberEnter.equals("0")||numberEnter.length()==1){
                        resultNumberEnter="0";
                        mIActivityBillModel.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    resultNumberEnter=(numberEnter.substring(0,numberEnter.length()-1));
                    mIActivityBillModel.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "7":{
                    checkNumberCaculate(numberEnter,nameClick);
                    break;
                }
                case "8":{
                    checkNumberCaculate(numberEnter,nameClick);
                    break;
                }
                case "9":{
                    checkNumberCaculate(numberEnter,nameClick);
                    break;
                }
                case "4":{
                    checkNumberCaculate(numberEnter,nameClick);
                    break;
                }
                case "5":{
                    checkNumberCaculate(numberEnter,nameClick);
                    break;
                }
                case "6":{
                    checkNumberCaculate(numberEnter,nameClick);
                    break;
                }
                case "1":{
                    checkNumberCaculate(numberEnter,nameClick);
                    break;
                }
                case "2":{
                    checkNumberCaculate(numberEnter,nameClick);
                    break;
                }
                case "3":{
                    checkNumberCaculate(numberEnter,nameClick);
                    break;
                }
                case "0":{
                    if(numberEnter.startsWith("0")||numberEnter.startsWith("-0")){
                        resultNumberEnter=(nameClick);
                        mIActivityBillModel.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    checkNumberCaculate(numberEnter,nameClick);
                    break;
                }
                case "000":{
                    if(numberEnter.startsWith("0")||numberEnter.startsWith("-0")){
                        resultNumberEnter="0";
                        mIActivityBillModel.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    checkNumberCaculate(numberEnter,nameClick);
                    break;
                }
                case ",":{

                    break;
                }
                case "ĐỒNG Ý":{
                    DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                    mIActivityBillModel.resultSuccess(decimalFormat.format(Float.parseFloat(numberEnter)-amount));
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Mục đích method thực hiện việc kiểm tra khi người dùng chọn số trong máy tính
     *
     * @param numberEnter Giá trị có trong textview
     * @param  nameItemClick số mà người dùng chọn
     *
     * @created_by cvmanh on 01/21/2021
     */
    private void checkNumberCaculate(String numberEnter,String nameItemClick){
        try {
            if(numberEnter.startsWith("0")){
                resultNumberEnter=(nameItemClick);
                mIActivityBillModel.resultTextEnter(resultNumberEnter);
                return;
            }
            if(numberEnter.length()<9){
                resultNumberEnter=numberEnter+(nameItemClick);
                mIActivityBillModel.resultTextEnter(resultNumberEnter);
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateOrderStatus(Activity activity,int orderID){
        try {
            mSqLiteDatabase=DatabaseHelper.initDatabase(activity,DatabaseInfomation.DATABASE_NAME);
            ContentValues contentValues=new ContentValues();
            contentValues.put(DatabaseInfomation.COLUMN_ORDER_STATUS,2); // 2: trạng thái order là đã thu tiền
            long result=mSqLiteDatabase.update(DatabaseInfomation.TABLE_ORDERS,contentValues,
                    DatabaseInfomation.COLUMN_ORDER_ID+"=?",new String[]{String.valueOf(orderID)});
            if(result>0){
                mIActivityBillModel.updateOrderStatusSuccess();
                return;
            }
            mIActivityBillModel.onFailed();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface IActivityBillModel{
        public void getOrderDetail(List<OrderDetail> listOrderDetail);

        public void resultTextEnter(String moneyIn);

        public void resultSuccess(String amount);

        public void updateOrderStatusSuccess();

        public void onFailed();
    }
}
