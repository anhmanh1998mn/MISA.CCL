package vn.com.misa.ccl.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.entity.Color;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.entity.Unit;
import vn.com.misa.ccl.util.DatabaseInfomation;

/**
 * ‐ Mục đích Class thực hiện việc xử lý các công việc của FragmentMenu
 * <p>
 * ‐ {@link vn.com.misa.ccl.presenter.FragmentMenuPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/27/2021
 */

public class FragmentMenuModel {

    private IFragmentMenuModel mIFragmentMenuModel;

    public FragmentMenuModel(IFragmentMenuModel mIFragmentMenuModel) {
        this.mIFragmentMenuModel = mIFragmentMenuModel;
    }

    private List<Product> mListMenu;

    private SQLiteDatabase mSqliteDatabase;

    /**
     * Mục đích method thực hiện việc xử lý lấy danh sách menu và gửi kết quả về presenter
     *
     * @param activity instance activity
     * @created_by cvmanh on 01/27/2021
     */
    public void getListMenu(Activity activity) {
        try {
            mListMenu = new ArrayList<>();
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            Cursor cursor = mSqliteDatabase.rawQuery("SELECT * FROM " + DatabaseInfomation.TABLE_UNITS + ","
                    + DatabaseInfomation.TABLE_MYPRODUCTS + ","
                    + DatabaseInfomation.TABLE_PRODUCT_IMAGES + ","
                    + DatabaseInfomation.TABLE_COLORS + " WHERE "
                    + DatabaseInfomation.TABLE_UNITS + "."
                    + DatabaseInfomation.COLUMN_UNIT_ID + "="
                    + DatabaseInfomation.TABLE_MYPRODUCTS + "."
                    + DatabaseInfomation.COLUMN_UNIT_ID + " AND "
                    + DatabaseInfomation.TABLE_COLORS + "."
                    + DatabaseInfomation.COLUMN_COLOR_ID + "="
                    + DatabaseInfomation.TABLE_MYPRODUCTS + "."
                    + DatabaseInfomation.COLUMN_COLOR_ID + " AND "
                    + DatabaseInfomation.TABLE_PRODUCT_IMAGES + "."
                    + DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID + "="
                    + DatabaseInfomation.TABLE_MYPRODUCTS + "." + DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID + "", null);
            for (int i = 0; i < cursor.getCount(); i++) {
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
            if (cursor != null) {
                mIFragmentMenuModel.getListMenuSuccess(mListMenu);
                return;
            }
            mIFragmentMenuModel.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IFragmentMenuModel {
        public void getListMenuSuccess(List<Product> listProduct);

        public void onFailed();
    }
}
