package vn.com.misa.ccl.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.entity.Category;
import vn.com.misa.ccl.util.DatabaseInfomation;

/**
‐ Mục đích Class thực hiện việc xử lý các công việc của ActivityRestaurantType
*
‐ {@link vn.com.misa.ccl.view.restaurantsetup.ActivityRestaurantType}
‐ {@link vn.com.misa.ccl.presenter.ActivityRestaurantTypePresenter}
*
‐ @created_by cvmanh on 01/19/2021
*/

public class ActivityRestaurantTypeModel {

    private IActivityRestaurantTypeModel mIActivityRestaurantTypeModel;

    public ActivityRestaurantTypeModel(IActivityRestaurantTypeModel mIFragmentShopTypeModel) {
        this.mIActivityRestaurantTypeModel = mIFragmentShopTypeModel;
    }

    private SQLiteDatabase mSqliteDatabase;

    private List<Category> mListCategory;

    /**
     * Mục đích method thực hiện việc lấy dữ liệu danh sách loại sản phẩm rồi gửi dữ liệu về ActivityRestaurantTypePresenter
     *
     * @param activity instance activity
     *
     * @created_by cvmanh on 01/19/2021
     */
    public void loadListRestaurantType(Activity activity){
        mSqliteDatabase=DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
        Cursor cursor=mSqliteDatabase.rawQuery("SELECT * FROM "+DatabaseInfomation.TABLE_CATEGORIES+"",null);
        mListCategory=new ArrayList<>();
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            mListCategory.add(new Category(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_CATEGORY_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_CATEGORY_NAME))));
        }
        if(cursor!=null){
            mIActivityRestaurantTypeModel.loadListRestaurantTypeSuccess(mListCategory);
            return;
        }
        mIActivityRestaurantTypeModel.onFailed();
    }
}
