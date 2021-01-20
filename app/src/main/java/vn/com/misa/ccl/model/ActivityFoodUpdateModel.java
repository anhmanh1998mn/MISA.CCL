package vn.com.misa.ccl.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.entity.Color;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.util.DatabaseInfomation;

/**
‐ Mục đích Class thực hiện việc xử lý các công việc của ActivityFoodUpdate
*
‐ {@link vn.com.misa.ccl.presenter.ActivityFoodUpdatePresenter}
‐ {@link DatabaseInfomation}
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

    /**
     * Mục đích method thực hiện việc xử lý lấy danh sách màu và gửi tới presenter
     *
     * @param activity instace activity
     *
     * @created_by cvmanh on 01/19/2021
     */
    public void loadListColor(Activity activity){
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
    }

    /**
     * Mục đích method thực hiện việc xử lý lấy danh sách hình ảnh và gửi tới presenter
     *
     * @param activity instace activity
     *
     * @created_by cvmanh on 01/19/2021
     */
    public void loadListProductImage(Activity activity){
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
    }

    public void loadCaculating(){
        mListCaculate=new ArrayList<>();
        mListCaculate.add("C");
        mListCaculate.add("Giảm");
        mListCaculate.add("Tăng");
        mListCaculate.add("");
        mListCaculate.add("7");
        mListCaculate.add("8");
        mListCaculate.add("9");
        mListCaculate.add("-");
        mListCaculate.add("4");
        mListCaculate.add("5");
        mListCaculate.add("6");
        mListCaculate.add("+");
        mListCaculate.add("1");
        mListCaculate.add("2");
        mListCaculate.add("3");
        mListCaculate.add("±");
        mListCaculate.add("0");
        mListCaculate.add("000");
        mListCaculate.add(",");
        mListCaculate.add("XONG");
        mIResultActivityFoodUpdate.loadCaculating(mListCaculate);

    }

    public interface IResultActivityFoodUpdate{
        public void loadListColorSuccess(List<Color> listColor);

        public void loadListImageSuccess(List<ProductImage> listImage);

        public void loadCaculating(List<String> listCaculate);

        public void onFailed();
    }
}
