package vn.com.misa.ccl.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.entity.Color;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.entity.Unit;
import vn.com.misa.ccl.util.DatabaseInfomation;

public class ActivityOrderModel {

    public ActivityOrderModel(IActivityOrderModel mIActivityOrderModel) {
        this.mIActivityOrderModel = mIActivityOrderModel;
    }

    private IActivityOrderModel mIActivityOrderModel;

    private List<Product> mListMenu;

    private SQLiteDatabase mSqliteDatabase;

    private String resultNumberEnter="";

    public void getListMenu(Activity activity){
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
    }

    public void getResultCaculate(String textInput,String numberEnter){
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
    }
    private void checkNumberCaculate(String numberEnter,String nameItemClick){
        try {
            if(numberEnter.startsWith("0")){
                resultNumberEnter=(nameItemClick);
                mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                return;
            }
            if(numberEnter.length()<9){
                resultNumberEnter=numberEnter+(nameItemClick);
                mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface IActivityOrderModel{
        public void getListMenuSuccess(List<Product> listMenu);

        public void resultProcessCaculateSuccess(String result);

        public void onFailed();
    }
}
