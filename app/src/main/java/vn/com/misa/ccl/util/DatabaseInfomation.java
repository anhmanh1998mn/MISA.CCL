package vn.com.misa.ccl.util;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

/**
‐ Mục đích Class thực hiện việc khai báo tên Database, tên bảng, tên thuộc tính
*
‐ {@link vn.com.misa.ccl.model.IActivityRestaurantTypeModel}
‐ {@link vn.com.misa.ccl.model.ActivityRestaurantMenuModel}
*
‐ @created_by cvmanh on 01/19/2021
*/

public class DatabaseInfomation {
    public static SQLiteDatabase mSqliteDatabase;

    public static String DATABASE_NAME="MISACuckCukLite.db";

    public static String TABLE_CATEGORIES="Categories";

    public static String TABLE_COLORS="Colors";

    public static String TABLE_PRODUCT_CATEGORY="ProductCategories";

    public static String TABLE_PRODUCT_IMAGES="ProductImages";

    public static String TABLE_PRODUCTS="Products";

    public static String TABLE_UNITS="Units";

    public static String TABLE_MYPRODUCTS="MyProducts";

    public static String COLUMN_CATEGORY_ID="CategoryID";

    public static String COLUMN_CATEGORY_NAME="CategoryName";

    public static String COLUMN_COLOR_ID="ColorID";

    public static String COLUMN_COLOR_KEY="ColorKey";

    public static String COLUMN_PRODUCT_CATEGORY_ID="ProductCategoryID";

    public static String COLUMN_PRODUCT_IMAGE_ID="ProductImageID";

    public static String COLUMN_IMAGE="Image";

    public static String COLUMN_PRODUCT_ID="ProductID";

    public static String COLUMN_PRODUCT_NAME="ProductName";

    public static String COLUMN_PRODUCT_PRICE="ProductPrice";

    public static String COLUMN_PRODUCT_STATUS="ProductStatus";

    public static String COLUMN_UNIT_ID="UnitID";

    public static String COLUMN_UNIT_NAME="UnitName";

    public static String COLUMN_MYPRODUCT_ID="MyproductID";
}
