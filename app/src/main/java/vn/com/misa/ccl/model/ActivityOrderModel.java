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
import vn.com.misa.ccl.entity.Order;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.entity.Unit;
import vn.com.misa.ccl.util.DatabaseInfomation;

/**
 * ‐ Mục đích Class thực hiện việc xử lý các công việc của ActivityOrderModel
 * <p>
 * ‐ {@link vn.com.misa.ccl.presenter.ActivityOrderPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class ActivityOrderModel {

    public ActivityOrderModel(IActivityOrderModel mIActivityOrderModel) {
        this.mIActivityOrderModel = mIActivityOrderModel;
    }

    private IActivityOrderModel mIActivityOrderModel;

    private List<Product> mListMenu;

    private SQLiteDatabase mSqliteDatabase;

    private String resultNumberEnter = "";

    private int mSumQuantity = 0;

    private int CICK_BUTTON_SAVE = 1;

    private int CLICK_BUTTON_MONEY = 2;

    private List<OrderDetail> mListOrderDetaill;

    /**
     * Mục đích method thực hiện việc lấy danh sách các món ăn có trong menu và gửi kết quả về presenter
     *
     * @param activity instance activity
     * @created_by cvmanh on 01/25/2021
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
                    + DatabaseInfomation.TABLE_MYPRODUCTS + "." + DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID + " AND " +
                    DatabaseInfomation.COLUMN_PRODUCT_STATUS + "=1", null);
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
                mIActivityOrderModel.getListMenuSuccess(mListMenu);
                return;
            }
            mIActivityOrderModel.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý kết quả hiển thị trên máy tính và gửi kết quả về presenter
     *
     * @param numberEnter Giá trị hiển thị kết quả xử lý
     * @param textInput   Button mà người dùng click trên máy tính
     * @created_by cvmanh on 01/25/2021
     */
    public void getResultCaculate(String textInput, String numberEnter) {
        try {
            switch (textInput) {
                case "C": {
                    resultNumberEnter = "0";
                    mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                    break;
                }
                case "Giảm": {
                    if (Integer.parseInt(numberEnter.trim()) < 1) {
                        resultNumberEnter = "0";
                        mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                        return;
                    }
                    resultNumberEnter = ((Integer.parseInt(numberEnter) - 1) + "");
                    mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                    break;
                }
                case "Tăng": {
                    if (numberEnter.equals("")) {
                        resultNumberEnter = resultNumberEnter + 1;
                        mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                        return;
                    }
                    resultNumberEnter = ((Integer.parseInt(numberEnter) + 1) + "");
                    mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                    break;
                }
                case "": {
                    if (numberEnter.equals("0") || numberEnter.length() == 1) {
                        resultNumberEnter = "0";
                        mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                        return;
                    }
                    resultNumberEnter = (numberEnter.substring(0, numberEnter.length() - 1));
                    mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                    break;
                }
                case "7": {
                    checkNumberCaculate(numberEnter, textInput);
                    break;
                }
                case "8": {
                    checkNumberCaculate(numberEnter, textInput);
                    break;
                }
                case "9": {
                    checkNumberCaculate(numberEnter, textInput);
                    break;
                }
                case "4": {
                    checkNumberCaculate(numberEnter, textInput);
                    break;
                }
                case "5": {
                    checkNumberCaculate(numberEnter, textInput);
                    break;
                }
                case "6": {
                    checkNumberCaculate(numberEnter, textInput);
                    break;
                }
                case "1": {
                    checkNumberCaculate(numberEnter, textInput);
                    break;
                }
                case "2": {
                    checkNumberCaculate(numberEnter, textInput);
                    break;
                }
                case "3": {
                    checkNumberCaculate(numberEnter, textInput);
                    break;
                }
                case "0": {
                    if (numberEnter.startsWith("0") || numberEnter.startsWith("-0")) {
                        resultNumberEnter = (textInput);
                        mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                        return;
                    }
                    checkNumberCaculate(numberEnter, textInput);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc kiểm tra nếu người dùng click vào các số
     *
     * @param numberEnter   Kết quả hiển thị trên view
     * @param nameItemClick Số mà người dùng click
     * @created_by cvmanh on 01/25/2021
     */
    private void checkNumberCaculate(String numberEnter, String nameItemClick) {
        try {
            if (numberEnter.startsWith("0")) { // Text bắt đầu bằng 0
                resultNumberEnter = (nameItemClick);
                mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                return;
            }
            if (numberEnter.length() < 9) { // Độ dài chuỗi < 9
                resultNumberEnter = numberEnter + (nameItemClick);
                mIActivityOrderModel.resultProcessCaculateSuccess(resultNumberEnter);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc thêm mới môt order và gửi kết quả về presenter
     *
     * @param activity    instace activity
     * @param listProduct Danh sách Product
     * @param tableName   Tên bàn
     * @param totalPeople Số lượng người
     * @param amount      Tổng tiền đơn hàng
     * @param typeClick   Loại button clcik: 1: Thực hiện Lưu . 2: Thực hiện thu iền
     * @created_by cvmanh on 01/25/2021
     */
    public void addNewOrder(Activity activity, List<Product> listProduct, String tableName, String totalPeople, float amount, int typeClick) {
        try {
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            if (tableName.equals("")) {
                tableName = "0";
            }
            if (totalPeople.equals("")) {
                totalPeople = "0";
            }
            for (int i = 0; i < listProduct.size(); i++) {
                if (listProduct.get(i).getQuantity() > 0) {
                    mSumQuantity = mSumQuantity + 1;
                }
            }
            if (mSumQuantity > 0) {
                ContentValues contentValues = new ContentValues();
                ContentValues contentOrderDetail = new ContentValues();
                contentValues.put(DatabaseInfomation.COLUMN_ORDER_STATUS, 1); // 1: trạng thái đơn hàng là chưa thu tiền
                contentValues.put(DatabaseInfomation.COLUMN_ORDER_CREATED_AT, getDate());
                contentValues.put(DatabaseInfomation.COLUMN_TABLE_NAME, tableName);
                contentValues.put(DatabaseInfomation.COLUMN_TOTAL_PEOPLE, Integer.parseInt(totalPeople));
                contentValues.put(DatabaseInfomation.COLUM_ORDER_AMOUNT, amount);
                long resultInsertOrder = mSqliteDatabase.insert(DatabaseInfomation.TABLE_ORDERS, null, contentValues);
                if (resultInsertOrder > 0) {
                    for (int i = 0; i < listProduct.size(); i++) {
                        if (listProduct.get(i).getQuantity() > 0) {
                            contentOrderDetail.put(DatabaseInfomation.COLUMN_ORDER_ID, resultInsertOrder);
                            contentOrderDetail.put(DatabaseInfomation.COLUMN_MYPRODUCT_ID, listProduct.get(i).getProductID());
                            contentOrderDetail.put(DatabaseInfomation.COLUMN_QUANTITY, listProduct.get(i).getQuantity());
                            contentOrderDetail.put(DatabaseInfomation.COLUM_PRODUCT_PRICE_OUT, listProduct.get(i).getProductPrice());
                            mSqliteDatabase.insert(DatabaseInfomation.TABLE_ORDER_DETAIL, null, contentOrderDetail);
                        }
                    }
                }
                if (typeClick == CICK_BUTTON_SAVE) {
                    mIActivityOrderModel.addNewOrderSuccess();
                } else {
                    mIActivityOrderModel.addNewOrderTwoSuccess(Integer.parseInt(String.valueOf((resultInsertOrder))));
                }

                return;
            }
            mIActivityOrderModel.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lấy ngày tháng năm hiện tại
     *
     * @return trả về ngày tháng năm hiện tại
     * @created_by cvmanh on 01/25/2021
     */
    private String getDate() {
        try {
            Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DATE);
            calendar.set(mYear, mMonth, mDay);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Mục đích method thực hiện việc xử lý lấy thông tin chi tiết order theo mã order
     *
     * @param activity instance activity
     * @param orderID  mã order
     * @created_by cvmanh on 01/26/2021
     */
    public void getListOrderDetailWithOrderID(Activity activity, int orderID) {
        try {
            mListOrderDetaill = new ArrayList<>();
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            Cursor cursor = mSqliteDatabase.rawQuery("SELECT " + DatabaseInfomation.TABLE_ORDERS + "."
                    + DatabaseInfomation.COLUMN_ORDER_ID + "" +
                    "," + DatabaseInfomation.COLUMN_ORDER_STATUS + "," + DatabaseInfomation.COLUMN_TABLE_NAME + "" +
                    "," + DatabaseInfomation.COLUMN_TOTAL_PEOPLE + "," + DatabaseInfomation.COLUM_ORDER_AMOUNT + "," +
                    "" + DatabaseInfomation.TABLE_PRODUCT_IMAGES + "." + DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID + ","
                    + DatabaseInfomation.COLUMN_IMAGE + "," +
                    "" + DatabaseInfomation.TABLE_ORDER_DETAIL + "." + DatabaseInfomation.COLUM_ORDER_DETAIL_ID + ","
                    + DatabaseInfomation.COLUMN_QUANTITY + "," + DatabaseInfomation.TABLE_COLORS + "." +
                    DatabaseInfomation.COLUMN_COLOR_ID + "," + DatabaseInfomation.COLUMN_COLOR_KEY + ","
                    + DatabaseInfomation.TABLE_MYPRODUCTS + "." +
                    DatabaseInfomation.COLUMN_MYPRODUCT_ID + "," + DatabaseInfomation.COLUMN_PRODUCT_PRICE + "," +
                    DatabaseInfomation.COLUMN_PRODUCT_STATUS + "," + DatabaseInfomation.TABLE_UNITS + "."
                    + DatabaseInfomation.COLUMN_UNIT_ID + ","
                    + DatabaseInfomation.COLUMN_UNIT_NAME + ","
                    + DatabaseInfomation.TABLE_MYPRODUCTS + "." + DatabaseInfomation.COLUMN_PRODUCT_NAME + "," +
                    DatabaseInfomation.TABLE_ORDERS + "." + DatabaseInfomation.COLUMN_ORDER_CREATED_AT + "," +
                    DatabaseInfomation.TABLE_ORDER_DETAIL + "." + DatabaseInfomation.COLUM_PRODUCT_PRICE_OUT + " FROM "
                    + DatabaseInfomation.TABLE_ORDERS + ", "
                    + DatabaseInfomation.TABLE_UNITS + ","
                    + DatabaseInfomation.TABLE_PRODUCT_IMAGES + "," + DatabaseInfomation.TABLE_COLORS + ","
                    + DatabaseInfomation.TABLE_ORDER_DETAIL + ","
                    + DatabaseInfomation.TABLE_MYPRODUCTS + " WHERE "
                    + DatabaseInfomation.TABLE_ORDERS + "."
                    + DatabaseInfomation.COLUMN_ORDER_ID + "="
                    + DatabaseInfomation.TABLE_ORDER_DETAIL + "."
                    + DatabaseInfomation.COLUMN_ORDER_ID + " AND "
                    + DatabaseInfomation.TABLE_ORDER_DETAIL + "."
                    + DatabaseInfomation.COLUMN_MYPRODUCT_ID + "="
                    + DatabaseInfomation.TABLE_MYPRODUCTS + "."
                    + DatabaseInfomation.COLUMN_MYPRODUCT_ID + " AND "
                    + DatabaseInfomation.TABLE_UNITS + "." + DatabaseInfomation.COLUMN_UNIT_ID + "="
                    + DatabaseInfomation.TABLE_MYPRODUCTS + "." + DatabaseInfomation.COLUMN_UNIT_ID + " AND "
                    + DatabaseInfomation.TABLE_PRODUCT_IMAGES + "."
                    + DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID + "=" + DatabaseInfomation.TABLE_MYPRODUCTS
                    + "." + DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID + " AND "
                    + DatabaseInfomation.TABLE_MYPRODUCTS + "."
                    + DatabaseInfomation.COLUMN_COLOR_ID + "=" + DatabaseInfomation.TABLE_COLORS + "."
                    + DatabaseInfomation.COLUMN_COLOR_ID + " AND "
                    + DatabaseInfomation.TABLE_ORDERS + "." + DatabaseInfomation.COLUMN_ORDER_ID + "=" + orderID + "", null);
            for (int i = cursor.getCount() - 1; i >= 0; i--) {
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
                        cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_QUANTITY)),
                        cursor.getFloat(cursor.getColumnIndex(DatabaseInfomation.COLUM_PRODUCT_PRICE_OUT))));
            }
            if (cursor != null) {
                mIActivityOrderModel.getListDetailWithOrderIDSuccess(mListOrderDetaill);
                return;
            }
            mIActivityOrderModel.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc cập nhật thông tin order
     *
     * @param activity    instance activity
     * @param orderID     mã order
     * @param listProdcut danh sách sản phẩm
     * @param typeClick   loại click: typeClick=1: button Cất, typeClick=2: button thu tiền
     * @param tableName   tên bàn
     * @param totalPeople số người
     * @param amount      tổng số tiền
     * @created_by cvmanh on 01/26/2021
     */
    public void updateOrder(Activity activity, int orderID, List<Product> listProdcut, int typeClick, String tableName, String totalPeople, float amount) {
        try {
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            ContentValues contentValuesUpdate = new ContentValues();
            contentValuesUpdate.put(DatabaseInfomation.COLUMN_TABLE_NAME, tableName);
            contentValuesUpdate.put(DatabaseInfomation.COLUMN_TOTAL_PEOPLE, Integer.parseInt(totalPeople));
            contentValuesUpdate.put(DatabaseInfomation.COLUM_ORDER_AMOUNT, amount);
            long resultUpdateOrder = mSqliteDatabase.update(DatabaseInfomation.TABLE_ORDERS, contentValuesUpdate, DatabaseInfomation.COLUMN_ORDER_ID + "=?",
                    new String[]{String.valueOf(orderID)});
            if (resultUpdateOrder > 0) {
                long resultDeleteOrderDetail = mSqliteDatabase.delete(DatabaseInfomation.TABLE_ORDER_DETAIL, DatabaseInfomation.COLUMN_ORDER_ID + "=?",
                        new String[]{String.valueOf(orderID)});
                if (resultDeleteOrderDetail > 0) {
                    ContentValues contentValues = new ContentValues();
                    for (int i = 0; i < listProdcut.size(); i++) {
                        if (listProdcut.get(i).getQuantity() > 0) {
                            contentValues.put(DatabaseInfomation.COLUMN_ORDER_ID, orderID);
                            contentValues.put(DatabaseInfomation.COLUMN_MYPRODUCT_ID, listProdcut.get(i).getProductID());
                            contentValues.put(DatabaseInfomation.COLUMN_QUANTITY, listProdcut.get(i).getQuantity());
                            contentValues.put(DatabaseInfomation.COLUM_PRODUCT_PRICE_OUT, listProdcut.get(i).getProductPrice());
                            long resultInsertOrderDetail = mSqliteDatabase.insert(DatabaseInfomation.TABLE_ORDER_DETAIL, null, contentValues);

                        }
                    }
                    if (typeClick == CICK_BUTTON_SAVE) {
                        mIActivityOrderModel.updateOrderSaveSuccess();
                        return;
                    }
                    mIActivityOrderModel.updateOrderMoneySuccess(orderID);
                    return;
                }
                return;
            }
            mIActivityOrderModel.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Mục đích method thực hiện việc gán số lượng bán của listOrder sang listProduct
     *
     * @param listOrder   danh sách order
     * @param listProduct danh sách product
     * @created_by cvmanh on 01/26/2021
     */
    public void checkQuantityProductItem(List<OrderDetail> listOrder, List<Product> listProduct) {
        try {
            for (int i = 0; i < listProduct.size(); i++) {
                for (int j = 0; j < listOrder.size(); j++) {
                    if (listProduct.get(i).getProductID() == listOrder.get(j).getProduct().getProductID()) {
                        listProduct.get(i).setQuantity(listProduct.get(i).getQuantity() + listOrder.get(j).getQuantity());
                    }
                }
            }
            mIActivityOrderModel.checkQuantitiOrderClickSuccess(listProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IActivityOrderModel {
        public void getListMenuSuccess(List<Product> listMenu);

        public void resultProcessCaculateSuccess(String result);

        public void addNewOrderSuccess();

        public void addNewOrderTwoSuccess(int orderID);

        public void getListDetailWithOrderIDSuccess(List<OrderDetail> listOrderDetail);

        public void updateOrderSaveSuccess();

        public void updateOrderMoneySuccess(int orderID);

        public void checkQuantitiOrderClickSuccess(List<Product> listProduct);

        public void onFailed();
    }
}
