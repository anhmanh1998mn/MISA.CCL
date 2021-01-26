package vn.com.misa.ccl.model;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.entity.Color;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.entity.Unit;
import vn.com.misa.ccl.util.DatabaseInfomation;

/**
‐ Mục đích Class thực hiện việc xử lý các công việc của ActivityOrderModel
*
‐ {@link vn.com.misa.ccl.presenter.ActivityOrderPresenter}
*
‐ @created_by cvmanh on 01/25/2021
*/

public class ActivityOrderModel {

    public ActivityOrderModel(IActivityOrderModel mIActivityOrderModel) {
        this.mIActivityOrderModel = mIActivityOrderModel;
    }

    private IActivityOrderModel mIActivityOrderModel;

    private List<Product> mListMenu;

    private SQLiteDatabase mSqliteDatabase;

    private String resultNumberEnter="";

    private int mSumQuantity=0;

    private int CICK_BUTTON_SAVE =1;

    private int CLICK_BUTTON_MONEY=2;

    /**
     * Mục đích method thực hiện việc lấy danh sách các món ăn có trong menu và gửi kết quả về presenter
     *
     * @param activity instance activity
     *
     * @created_by cvmanh on 01/25/2021
     */
    public void getListMenu(Activity activity){
        try {
            mListMenu=new ArrayList<>();
            mSqliteDatabase= DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            Cursor cursor=mSqliteDatabase.rawQuery("SELECT * FROM "+DatabaseInfomation.TABLE_UNITS+","
                    +DatabaseInfomation.TABLE_MYPRODUCTS+","
                    +DatabaseInfomation.TABLE_PRODUCT_IMAGES+","
                    +DatabaseInfomation.TABLE_COLORS+" WHERE "
                    +DatabaseInfomation.TABLE_UNITS+"."
                    +DatabaseInfomation.COLUMN_UNIT_ID+"="
                    +DatabaseInfomation.TABLE_MYPRODUCTS+"."
                    +DatabaseInfomation.COLUMN_UNIT_ID+" AND "
                    +DatabaseInfomation.TABLE_COLORS+"."
                    +DatabaseInfomation.COLUMN_COLOR_ID+"="
                    +DatabaseInfomation.TABLE_MYPRODUCTS+"."
                    +DatabaseInfomation.COLUMN_COLOR_ID+" AND "
                    +DatabaseInfomation.TABLE_PRODUCT_IMAGES+"."
                    +DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID+"="
                    +DatabaseInfomation.TABLE_MYPRODUCTS+"."+DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID+"",null);
            for(int i=0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                mListMenu.add(new Product(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_MYPRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_NAME)),
                        cursor.getFloat(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_PRICE)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_STATUS)),
                        new ProductImage(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID)),
                                cursor.getBlob(cursor.getColumnIndex(DatabaseInfomation.COLUMN_IMAGE))),
                        new Unit(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_UNIT_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_UNIT_NAME))),
                        new Color(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_COLOR_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_COLOR_KEY))),
                        0));
            }
            if(cursor!=null){
                mIActivityOrderModel.getListMenuSuccess(mListMenu);
                return;
            }
            mIActivityOrderModel.onFailed();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý kết quả hiển thị trên máy tính và gửi kết quả về presenter
     *
     * @param numberEnter Giá trị hiển thị kết quả xử lý
     * @param textInput  Button mà người dùng click trên máy tính
     *
     * @created_by cvmanh on 01/25/2021
     */
    public void getResultCaculate(String textInput,String numberEnter){
        try {
            switch (textInput){
                case "C":{
                    resultNumberEnter="0";
                    mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                    break;
                }
                case "Giảm":{
                    if(Integer.parseInt(numberEnter.trim())<1){
                        resultNumberEnter="0";
                        mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                        return;
                    }
                    resultNumberEnter=((Integer.parseInt(numberEnter)-1)+"");
                    mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                    break;
                }
                case "Tăng":{
                    if(numberEnter.equals("")){
                        resultNumberEnter=resultNumberEnter+1;
                        mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                        return;
                    }
                    resultNumberEnter=((Integer.parseInt(numberEnter)+1)+"");
                    mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                    break;
                }
                case "":{
                    if(numberEnter.equals("0")||numberEnter.length()==1){
                        resultNumberEnter="0";
                        mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                        return;
                    }
                    resultNumberEnter=(numberEnter.substring(0,numberEnter.length()-1));
                    mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                    break;
                }
                case "7":{
                    checkNumberCaculate(numberEnter,textInput);
                    break;
                }
                case "8":{
                    checkNumberCaculate(numberEnter,textInput);
                    break;
                }
                case "9":{
                    checkNumberCaculate(numberEnter,textInput);
                    break;
                }
                case "4":{
                    checkNumberCaculate(numberEnter,textInput);
                    break;
                }
                case "5":{
                    checkNumberCaculate(numberEnter,textInput);
                    break;
                }
                case "6":{
                    checkNumberCaculate(numberEnter,textInput);
                    break;
                }
                case "1":{
                    checkNumberCaculate(numberEnter,textInput);
                    break;
                }
                case "2":{
                    checkNumberCaculate(numberEnter,textInput);
                    break;
                }
                case "3":{
                    checkNumberCaculate(numberEnter,textInput);
                    break;
                }
                case "0":{
                    if(numberEnter.startsWith("0")||numberEnter.startsWith("-0")){
                        resultNumberEnter=(textInput);
                        mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                        return;
                    }
                    checkNumberCaculate(numberEnter,textInput);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Mục đích method thực hiện việc kiểm tra nếu người dùng click vào các số
     *
     * @param numberEnter Kết quả hiển thị trên view
     * @param  nameItemClick Số mà người dùng click
     *
     * @created_by cvmanh on 01/25/2021
     */
    private void checkNumberCaculate(String numberEnter,String nameItemClick){
        try {
            if(numberEnter.startsWith("0")){ // Text bắt đầu bằng 0
                resultNumberEnter=(nameItemClick);
                mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                return;
            }
            if(numberEnter.length()<9){ // Độ dài chuỗi < 9
                resultNumberEnter=numberEnter+(nameItemClick);
                mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc thêm mới môt order và gửi kết quả về presenter
     *
     * @param activity instace activity
     * @param  listProduct Danh sách Product
     * @param tableName Tên bàn
     * @param totalPeople Số lượng người
     * @param amount Tổng tiền đơn hàng
     * @param typeClick Loại button clcik: 1: Thực hiện Lưu . 2: Thực hiện thu iền
     *
     * @created_by cvmanh on 01/25/2021
     */
    public void addNewOrder(Activity activity,List<Product> listProduct,String tableName,String totalPeople,float amount,int typeClick){
        try {
            mSqliteDatabase=DatabaseHelper.initDatabase(activity,DatabaseInfomation.DATABASE_NAME);
            if(tableName.equals("")){
                tableName="0";
            }
            if(totalPeople.equals("")){
                totalPeople="0";
            }
            for(int i=0;i<listProduct.size();i++){
                if(listProduct.get(i).getQuantity()>0){
                    mSumQuantity=mSumQuantity+1;
                }
            }
            if(mSumQuantity>0){
                ContentValues contentValues=new ContentValues();
                ContentValues contentOrderDetail=new ContentValues();
                contentValues.put(DatabaseInfomation.COLUMN_ORDER_STATUS,1); // 1: trạng thái đơn hàng là chưa thu tiền
                contentValues.put(DatabaseInfomation.COLUMN_ORDER_CREATED_AT,getDate());
                contentValues.put(DatabaseInfomation.COLUMN_TABLE_NAME,tableName);
                contentValues.put(DatabaseInfomation.COLUMN_TOTAL_PEOPLE,Integer.parseInt(totalPeople));
                contentValues.put(DatabaseInfomation.COLUM_ORDER_AMOUNT,amount);
                long resultInsertOrder=mSqliteDatabase.insert(DatabaseInfomation.TABLE_ORDERS,null,contentValues);
                if(resultInsertOrder>0){
                    for (int i=0;i<listProduct.size();i++){
                        if(listProduct.get(i).getQuantity()>0){
                            contentOrderDetail.put(DatabaseInfomation.COLUMN_ORDER_ID,resultInsertOrder);
                            contentOrderDetail.put(DatabaseInfomation.COLUMN_MYPRODUCT_ID,listProduct.get(i).getmProductID());
                            contentOrderDetail.put(DatabaseInfomation.COLUMN_QUANTITY,listProduct.get(i).getQuantity());
                            mSqliteDatabase.insert(DatabaseInfomation.TABLE_ORDER_DETAIL,null,contentOrderDetail);
                        }
                    }
                }
                if(typeClick== CICK_BUTTON_SAVE){
                    mIActivityOrderModel.addNewOrderSuccess();
                }else {
                    mIActivityOrderModel.addNewOrderTwoSuccess(Integer.parseInt(String.valueOf((resultInsertOrder))));
                }

                return;
            }
            mIActivityOrderModel.onFailed();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lấy ngày tháng năm hiện tại
     *
     * @return trả về ngày tháng năm hiện tại
     *
     * @created_by cvmanh on 01/25/2021
     */
    private String getDate(){
        try {
            Calendar calendar=Calendar.getInstance();
            int mYear=calendar.get(Calendar.YEAR);
            int mMonth=calendar.get(Calendar.MONTH);
            int mDay=calendar.get(Calendar.DATE);
            calendar.set(mYear,mMonth,mDay);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            return simpleDateFormat.format(calendar.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public interface IActivityOrderModel{
        public void getListMenuSuccess(List<Product> listMenu);

        public void resultProcessCaculateSuccess(String result);

        public void addNewOrderSuccess();

        public void addNewOrderTwoSuccess(int orderID);

        public void onFailed();
    }
}
