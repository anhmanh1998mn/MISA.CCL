package vn.com.misa.ccl.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.entity.Color;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.util.DatabaseInfomation;

/**
‐ Mục đích Class thực hiện việc xử lý các công việc của ActivityFoodUpdate
*
‐ {@link vn.com.misa.ccl.presenter.ActivityFoodUpdatePresenter}
*
‐ @created_by cvmanh on 01/19/2021
*/

public class ActivityFoodUpdateModel {

    private IResultActivityFoodUpdate mIResultActivityFoodUpdate;

    public ActivityFoodUpdateModel(IResultActivityFoodUpdate mIResultActivityFoodUpdate) {
        this.mIResultActivityFoodUpdate = mIResultActivityFoodUpdate;
    }

    private List<Color> mListColor;

    private List<ProductImage> mListProductImage;

    private SQLiteDatabase mSqliteDatabase;

    private List<String> mListCaculate;

    private String resultNumberEnter="";

    private List<String> mListOperation;

    private List<Float> mListNumber;

    /**
     * Mục đích method thực hiện việc xử lý lấy danh sách màu và gửi tới presenter
     *
     * @param activity instace activity
     *
     * @created_by cvmanh on 01/19/2021
     */
    public void loadListColor(Activity activity){
        try {
            mListColor=new ArrayList<>();
            mSqliteDatabase= DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            Cursor cursor=mSqliteDatabase.rawQuery("SELECT * FROM "+DatabaseInfomation.TABLE_COLORS+"",null);
            for(int i=0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                mListColor.add(new Color(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_COLOR_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_COLOR_KEY))));
            }
            if(cursor!=null){
                mIResultActivityFoodUpdate.loadListColorSuccess(mListColor);
                return;
            }
            mIResultActivityFoodUpdate.onFailed();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý lấy danh sách hình ảnh và gửi tới presenter
     *
     * @param activity instace activity
     *
     * @created_by cvmanh on 01/19/2021
     */
    public void loadListProductImage(Activity activity){
        try {
            mListProductImage=new ArrayList<>();
            mSqliteDatabase=DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            Cursor cursor=mSqliteDatabase.rawQuery("SELECT * FROM "+DatabaseInfomation.TABLE_PRODUCT_IMAGES+"",null);
            for(int i=0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                mListProductImage.add(new ProductImage(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID)),
                        cursor.getBlob(cursor.getColumnIndex(DatabaseInfomation.COLUMN_IMAGE))));
            }
            if (cursor!=null){
                mIResultActivityFoodUpdate.loadListImageSuccess(mListProductImage);
                return;
            }
            mIResultActivityFoodUpdate.onFailed();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc tạo dữ liệu hiển thị cho máy tính và gửi tới presenter
     *
     * @created_by cvmanh on 01/21/2021
     */
    public void loadCaculating(Activity activity){
        try {
            mListCaculate=new ArrayList<>();
            mListCaculate.add(activity.getResources().getString(R.string.caculator_clear));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_decrease));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_increase));
            mListCaculate.add(activity.getResources().getString(R.string.caculator));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_7));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_8));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_9));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_sub));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_4));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_5));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_6));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_add));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_1));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_2));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_3));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_negative_number));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_0));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_000));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_dot));
            mListCaculate.add(activity.getResources().getString(R.string.caculator_result));
            mIResultActivityFoodUpdate.loadCaculating(mListCaculate);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Mục đích method thực hiện việc xử lý xự kiện click trong máy tính và trả kết quả về prsenter
     *
     * @param numberEnter Giá trị có trong textview
     * @param  nameClick giá trị người dùng click trong máy tính
     *
     * @created_by cvmanh on 01/21/2021
     */
    public void processCaculator(Activity activity,String numberEnter,String nameClick){
        try {
            switch (nameClick){
                case "C":{
                    resultNumberEnter="0";
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "Giảm":{
                    if(Float.parseFloat(numberEnter.trim())<1){
                        resultNumberEnter="0";
                        mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    resultNumberEnter=((Float.parseFloat(numberEnter)-1)+"");
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "Tăng":{
                    resultNumberEnter=((Float.parseFloat(numberEnter)+1)+"");
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "":{
                    if(numberEnter.equals("0")||numberEnter.length()==1){
                        resultNumberEnter="0";
                        mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    resultNumberEnter=(numberEnter.substring(0,numberEnter.length()-1));
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
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
                case "-":{
                    if(numberEnter.startsWith("0")){
                        resultNumberEnter="0";
                        mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    if(numberEnter.endsWith("-")||numberEnter.endsWith("+")){
                        return;
                    }
                    resultNumberEnter=numberEnter+nameClick;
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
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
                case "+":{
                    if(numberEnter.startsWith("0")){
                        resultNumberEnter="0";
                        mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    if(numberEnter.endsWith("+")||numberEnter.endsWith("-")){
                        return;
                    }
                    resultNumberEnter=numberEnter+(nameClick);
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
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
                case "±":{
                    if(numberEnter.startsWith("-")){
                        return;
                    }
                    resultNumberEnter=("-"+numberEnter);
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "0":{
                    if(numberEnter.startsWith("0")||numberEnter.startsWith("-0")){
                        resultNumberEnter=(nameClick);
                        mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    checkNumberCaculate(numberEnter,nameClick);
                    break;
                }
                case "000":{
                    if(numberEnter.startsWith("0")||numberEnter.startsWith("-0")){
                        resultNumberEnter="0";
                        mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    checkNumberCaculate(numberEnter,nameClick);
                    break;
                }
                case ",":{
                    resultNumberEnter=(nameClick);
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "XONG":{
                    resultCaculatorProcess(numberEnter);
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
                mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                return;
            }
            if(numberEnter.contains("+")||numberEnter.contains("-")){
                resultNumberEnter=numberEnter+(nameItemClick);
                mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
            }else if(numberEnter.length()<17){
                resultNumberEnter=numberEnter+(nameItemClick);
                mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý tính toán và trả kết quả về presenter
     *
     * @param textInput Phép tính
     *
     * @created_by cvmanh on 01/21/2021
     */
    private void resultCaculatorProcess(String textInput){
        try {
            addOperation(textInput);
            addNumber(textInput);
            if(mListOperation.size()<1){
                mIResultActivityFoodUpdate.resultTextEnter(textInput);
                return;
            }
            float result=0;
            for(int i=0;i<(mListNumber.size()-1);i++){
                switch (mListOperation.get(i)){
                    case "+":{
                        if(i==0){
                            result=mListNumber.get(i)+mListNumber.get(i+1);
                        }
                        else {
                            result=result+mListNumber.get(i+1);
                        }
                        break;
                    }
                    case "-":{
                        if(i==0){
                            result=mListNumber.get(i)-mListNumber.get(i+1);
                        }
                        else {
                            result=result-mListNumber.get(i+1);
                        }
                    }
                }
            }
            mIResultActivityFoodUpdate.resultTextEnter(String.valueOf(result));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc tách các phép tính +,- sau đó thêm vào mảng phép tính
     *
     * @param inputText Phép tính
     *
     * @created_by cvmanh on 01/21/2021
     */
    private void addOperation(String inputText){
        try {
            mListOperation=new ArrayList<>();
            char[] cArray=inputText.toCharArray();// lấy tất cả các ký tự có trong chuỗi và lưu vào cArray
            for(int i=0;i<cArray.length;i++){
                switch (cArray[i]){
                    case '+':{
                        mListOperation.add(cArray[i]+"");
                        break;
                    }
                    case '-':{
                        mListOperation.add(cArray[i]+"");
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc tách các số từ phép tính, sau đó thêm vào mảng sô
     *
     * @param inputText Phép tính
     *
     * @created_by cvmanh on 01/21/2021
     */
    private void addNumber(String inputText){
        try {
            mListNumber=new ArrayList<>();
            Pattern regex=Pattern.compile("(\\d+(?:\\.\\d+)?)");// lấy tất cả các số có trong chuỗi
            Matcher matcher=regex.matcher(inputText);
            while (matcher.find()){
                mListNumber.add(Float.valueOf(matcher.group(1)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface IResultActivityFoodUpdate{
        public void loadListColorSuccess(List<Color> listColor);

        public void loadListImageSuccess(List<ProductImage> listImage);

        public void loadCaculating(List<String> listCaculate);

        public void resultTextEnter(String resutText);

        public void onFailed();
    }
}
