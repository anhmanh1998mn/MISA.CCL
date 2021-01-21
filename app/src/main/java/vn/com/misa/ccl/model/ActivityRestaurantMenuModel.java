package vn.com.misa.ccl.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.entity.Category;
import vn.com.misa.ccl.entity.Color;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.entity.ProductCategory;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.entity.Unit;
import vn.com.misa.ccl.util.DatabaseInfomation;

/**
‐ Mục đích Class thực hiện việc xử lý các công việc trong ActivityRestaurantMenu
*
‐ {@link vn.com.misa.ccl.presenter.ActivityRestaurantMenuPresenter}
‐ {@link vn.com.misa.ccl.view.restaurantsetup.ActivityRestaurantMenu}
*
‐ @created_by cvmanh on 01/19/2021
*/

public class ActivityRestaurantMenuModel {

    private IResultActivityRestaurantMenu mIResultActivityRestaurantMenu;

    public ActivityRestaurantMenuModel(IResultActivityRestaurantMenu mIResultFragmentShopMenu) {
        this.mIResultActivityRestaurantMenu = mIResultFragmentShopMenu;
    }

    private List<ProductCategory> mListProductCategory;

    private SQLiteDatabase mSqliteDatabase;

    /**
     * Mục đích method thực hiện việc lấy dữ liệu danh sách Product rồi gửi dữ liệu về ActivityRestaurantMenuPresenter
     *
     * @param activity instance activity
     * @param  categoryID Mã loại sản phẩm
     *
     * @created_by cvmanh on 01/19/2021
     */
    public void loadListProduct(Activity activity,int categoryID) {
        try {
            mListProductCategory = new ArrayList<>();
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            Cursor cursor = mSqliteDatabase.rawQuery("SELECT * FROM "+DatabaseInfomation.TABLE_PRODUCTS+"," +
                    ""+DatabaseInfomation.TABLE_PRODUCT_IMAGES+"," +
                    ""+DatabaseInfomation.TABLE_PRODUCT_CATEGORY+","+DatabaseInfomation.TABLE_UNITS+"," +
                    ""+DatabaseInfomation.TABLE_CATEGORIES+","+DatabaseInfomation.TABLE_COLORS+" WHERE " +
                    ""+DatabaseInfomation.TABLE_PRODUCT_IMAGES+"."+DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID+"=" +
                    ""+DatabaseInfomation.TABLE_PRODUCTS+"."+DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID+" AND " +
                    ""+DatabaseInfomation.TABLE_PRODUCTS+"."+DatabaseInfomation.COLUMN_PRODUCT_ID+"=" +
                    ""+DatabaseInfomation.TABLE_PRODUCT_CATEGORY+"."+DatabaseInfomation.COLUMN_PRODUCT_ID+" AND" +
                    " "+DatabaseInfomation.TABLE_PRODUCT_CATEGORY+"."+DatabaseInfomation.COLUMN_CATEGORY_ID+"=" +
                    ""+DatabaseInfomation.TABLE_CATEGORIES+"."+DatabaseInfomation.COLUMN_CATEGORY_ID+" AND " +
                    ""+DatabaseInfomation.TABLE_PRODUCTS+"."+DatabaseInfomation.COLUMN_UNIT_ID+"=" +
                    ""+DatabaseInfomation.TABLE_UNITS+"."+DatabaseInfomation.COLUMN_UNIT_ID+" AND " +
                    ""+DatabaseInfomation.TABLE_COLORS+"."+DatabaseInfomation.COLUMN_COLOR_ID+"=" +
                    ""+DatabaseInfomation.TABLE_PRODUCTS+"."+DatabaseInfomation.COLUMN_COLOR_ID+" AND " +
                    ""+DatabaseInfomation.TABLE_CATEGORIES+"."+DatabaseInfomation.COLUMN_CATEGORY_ID+"="+categoryID+"", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                mListProductCategory.add(
                        new ProductCategory(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_CATEGORY_ID)),
                                new Product(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_ID)),
                                        cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_NAME)),
                                        cursor.getFloat(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_PRICE)),
                                        cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_STATUS)),
                                        new ProductImage(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID)),
                                                cursor.getBlob(cursor.getColumnIndex(DatabaseInfomation.COLUMN_IMAGE))),
                                        new Unit(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_UNIT_ID)),
                                                cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_UNIT_NAME))),
                                        new Color(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_COLOR_ID)),
                                                cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_COLOR_KEY)))),
                                new Category(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_CATEGORY_ID)),
                                        cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_CATEGORY_NAME)))));

            }
            if(cursor!=null){
                mIResultActivityRestaurantMenu.loadListProductSuccess(mListProductCategory);
                return;
            }
            mIResultActivityRestaurantMenu.onLoadFailed();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface IResultActivityRestaurantMenu {
        public void loadListProductSuccess(List<ProductCategory> listProductCategory);

        public void onLoadFailed();
    }
}
