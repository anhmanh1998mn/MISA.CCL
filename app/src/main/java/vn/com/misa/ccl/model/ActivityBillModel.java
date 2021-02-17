package vn.com.misa.ccl.model;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.entity.Color;
import vn.com.misa.ccl.entity.Order;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.entity.Unit;
import vn.com.misa.ccl.service.APIService;
import vn.com.misa.ccl.service.IDataService;
import vn.com.misa.ccl.util.DatabaseInfomation;

/**
 * ‐ Mục đích Class thực hiện việc xử lý các công việc trong ActivityBill
 * <p>
 * ‐ {@link vn.com.misa.ccl.presenter.ActivityBillPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class ActivityBillModel {

    private IActivityBillModel mIActivityBillModel;

    public ActivityBillModel(IActivityBillModel mIActivityBillModel) {
        this.mIActivityBillModel = mIActivityBillModel;
    }

    private SQLiteDatabase mSqLiteDatabase;

    private List<OrderDetail> mListOrderDetaill;

    private String resultNumberEnter = "0";

    private final int ORDER_STATUS_SUCCESS = 2;

    private int[] mParValuesOne = {2, 5, 10, 20, 50, 100, 200, 500};

    private int[] mParValuesTwo = {100, 200, 500};

    private int[] mAnotherParValuesOne = {40, 60, 70, 80, 90, 400, 600, 700, 800, 900};

    private int[] mAnotherParValuesTwo = {40, 60, 70, 80, 90};

    private List<Integer> mArraySuggestedMoney;

    private int MAX_SIZE_LIST_SUGGESTED_MONEY = 4;

    /**
     * Mục đích method thực hiện việc lấy danh sánh OrderDetail từ database và gửi dữ liệu về presenter
     *
     * @param activity instance activity
     * @param orderID  Mã order
     * @created_by cvmanh on 01/25/2021
     */
    public void getOrderDetail(Activity activity, int orderID) {
        try {
            mListOrderDetaill = new ArrayList<>();
            mSqLiteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            Cursor cursor = mSqLiteDatabase.rawQuery("SELECT " + DatabaseInfomation.TABLE_ORDERS + "."
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
                mIActivityBillModel.getOrderDetail(mListOrderDetaill);
                return;
            }
            mIActivityBillModel.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý tiền thừa và tiền khách đưa trên máy tính và gửi kết quả về presenter
     *
     * @param activity    instace activity
     * @param numberEnter Giá trị hiển thị khi người dùng click button trên máy tính
     * @param amount      Tổng tiền Order
     * @param nameClick   Số mà người dùng chọn
     * @created_by cvmanh on 01/25/2021
     */
    public void processCaculator(Activity activity, String numberEnter, String nameClick, float amount) {
        try {
            switch (nameClick) {
                case "C": {
                    resultNumberEnter = "0";
                    mIActivityBillModel.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "": {
                    if (numberEnter.equals("0") || numberEnter.length() == 1) {
                        resultNumberEnter = "0";
                        mIActivityBillModel.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    resultNumberEnter = (numberEnter.substring(0, numberEnter.length() - 1));
                    mIActivityBillModel.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "7": {
                    checkNumberCaculate(numberEnter, nameClick);
                    break;
                }
                case "8": {
                    checkNumberCaculate(numberEnter, nameClick);
                    break;
                }
                case "9": {
                    checkNumberCaculate(numberEnter, nameClick);
                    break;
                }
                case "4": {
                    checkNumberCaculate(numberEnter, nameClick);
                    break;
                }
                case "5": {
                    checkNumberCaculate(numberEnter, nameClick);
                    break;
                }
                case "6": {
                    checkNumberCaculate(numberEnter, nameClick);
                    break;
                }
                case "1": {
                    checkNumberCaculate(numberEnter, nameClick);
                    break;
                }
                case "2": {
                    checkNumberCaculate(numberEnter, nameClick);
                    break;
                }
                case "3": {
                    checkNumberCaculate(numberEnter, nameClick);
                    break;
                }
                case "0": {
                    if (numberEnter.startsWith("0") || numberEnter.startsWith("-0")) {
                        resultNumberEnter = (nameClick);
                        mIActivityBillModel.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    checkNumberCaculate(numberEnter, nameClick);
                    break;
                }
                case "000": {
                    if (numberEnter.startsWith("0") || numberEnter.startsWith("-0")) {
                        resultNumberEnter = "0";
                        mIActivityBillModel.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    checkNumberCaculate(numberEnter, nameClick);
                    break;
                }
                case ",": {

                    break;
                }
                case "ĐỒNG Ý": {
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    if (Float.parseFloat(numberEnter) < amount) {
                        mIActivityBillModel.onFailed();
                        return;
                    }
                    mIActivityBillModel.resultSuccess(decimalFormat.format(Float.parseFloat(numberEnter) - amount));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc kiểm tra khi người dùng chọn số trong máy tính
     *
     * @param numberEnter   Giá trị có trong textview
     * @param nameItemClick số mà người dùng chọn
     * @created_by cvmanh on 01/21/2021
     */
    private void checkNumberCaculate(String numberEnter, String nameItemClick) {
        try {
            if (numberEnter.startsWith("0")) {
                resultNumberEnter = (nameItemClick);
                mIActivityBillModel.resultTextEnter(resultNumberEnter);
                return;
            }
            if (numberEnter.length() < 9) {
                resultNumberEnter = numberEnter + (nameItemClick);
                mIActivityBillModel.resultTextEnter(resultNumberEnter);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý cập nhật thông tin order, 2: trạng thái đã thu tiền
     *
     * @param activity instance activity
     * @param orderID  mã order
     * @created_by cvmanh on 01/28/2021
     */
    public void updateOrderStatus(Activity activity, int orderID) {
        try {
            mSqLiteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseInfomation.COLUMN_ORDER_STATUS, ORDER_STATUS_SUCCESS); // 2: trạng thái order là đã thu tiền
            long result = mSqLiteDatabase.update(DatabaseInfomation.TABLE_ORDERS, contentValues,
                    DatabaseInfomation.COLUMN_ORDER_ID + "=?", new String[]{String.valueOf(orderID)});
            if (result > 0) {
                mIActivityBillModel.updateOrderStatusSuccess();
                return;
            }
            mIActivityBillModel.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc thêm dữ liệu order lên server
     *
     * @param shopID          mã cửa hàng
     * @param listOrderDetail thông tin order
     * @created_by cvmanh on 02/03/2021
     */
    public void doInsertOrderDataToServer(int shopID, List<OrderDetail> listOrderDetail) {
        try {
            IDataService dataService = APIService.getService();
            Call<String> callbackInsertOrder = dataService.doInsertOrderDataToServer(
                    2,
                    listOrderDetail.get(0).getOrder().getCreatedAt(),
                    listOrderDetail.get(0).getOrder().getTableName(),
                    listOrderDetail.get(0).getOrder().getTotalPeople(),
                    listOrderDetail.get(0).getOrder().getTotalMoney(),
                    listOrderDetail.get(0).getOrder().getOrderId(),
                    shopID
            );
            callbackInsertOrder.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body().toString().trim().equals("Failed")) {
                        return;
                    }
                    doInsertOrderDetailToServer(Integer.parseInt(response.body().toString().trim()), shopID, listOrderDetail);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý thêm thông tin chi tiết order lên server
     *
     * @param orderServerID Mã order trên server
     * @param shopID        mã cửa hàng
     * @param listDetail    danh sách sản phẩm
     * @created_by cvmanh on 02/03/2021
     */
    private void doInsertOrderDetailToServer(int orderServerID, int shopID, List<OrderDetail> listDetail) {
        try {
            IDataService dataService = APIService.getService();
            for (int i = 0; i < listDetail.size(); i++) {
                Call<String> callbackInsertDetail = dataService.doInsertOrderDetailToServer(
                        listDetail.get(i).getOrder().getOrderId(),
                        shopID, listDetail.get(i).getQuantity(), listDetail.get(i).getProductPriceOut(),
                        listDetail.get(i).getProduct().getProductID(),
                        orderServerID, listDetail.get(i).getOrderDetailID()
                );
                callbackInsertDetail.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().toString().trim().equals("Success")) {
                            Log.d("Success", "Success");
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Failed", t.toString());
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý gợi ý tiền khách đưa theo tiền phải trả
     * Sau đó gửi kết quả xử lý về presenter
     *
     * @param inputtedMoney tiền phải trả
     * @created_by cvmanh on 02/08/2021
     */
    public void suggestMoney(int inputtedMoney) {
        try {
            mArraySuggestedMoney = new ArrayList<>();
            int roundedMoneyOne = (inputtedMoney / 10) * 10;
            int roundedMoneyTwo = (inputtedMoney / 100) * 100;
            int roundedMoneyThree = (inputtedMoney / 1000) * 1000;
            int tempOne = inputtedMoney % 10;
            if (tempOne == 0 && roundedMoneyTwo == 0 && !mArraySuggestedMoney.contains(roundedMoneyOne)) {
                mArraySuggestedMoney.add(roundedMoneyOne);
                for (int i = 0; i < mParValuesOne.length; i++) {
                    if (mParValuesOne[i] > roundedMoneyOne && !mArraySuggestedMoney.contains(mParValuesOne[i])) {
                        mArraySuggestedMoney.add(mParValuesOne[i]);
                    }
                }
                for (int i = 0; i < mAnotherParValuesOne.length; i++) {
                    if (mAnotherParValuesOne[i] == (roundedMoneyOne + 10) && !mArraySuggestedMoney.contains(roundedMoneyOne + 10)) {
                        mArraySuggestedMoney.add(roundedMoneyOne + 10);
                    }
                }
                if ((roundedMoneyOne + 10) == 100) {
                    mArraySuggestedMoney.add(roundedMoneyOne + 20);
                    mArraySuggestedMoney.add(inputtedMoney);
                }
                mArraySuggestedMoney.add(inputtedMoney);
            }
            if (tempOne == 0 && roundedMoneyTwo != 0) {
            } else if (tempOne != 0) {
                if (inputtedMoney == 2) {
                    if ((roundedMoneyOne + 5) > inputtedMoney && !mArraySuggestedMoney.contains(roundedMoneyOne + 5)) {
                        mArraySuggestedMoney.add(roundedMoneyOne + 5);
                    }
                    mArraySuggestedMoney.add(roundedMoneyOne + 10);
                    for (int i = 0; i < mParValuesOne.length; i++) {
                        if (mParValuesOne[i] > (roundedMoneyOne + 10) && !mArraySuggestedMoney.contains(mParValuesOne[i])) {
                            mArraySuggestedMoney.add(mParValuesOne[i]);
                        }
                    }
                    for (int i = 0; i < mAnotherParValuesOne.length; i++) {
                        if (mAnotherParValuesOne[i] == (roundedMoneyOne + 20) && !mArraySuggestedMoney.contains(mAnotherParValuesOne[i])) {
                            mArraySuggestedMoney.add(roundedMoneyOne + 20);
                        }
                    }
                    mArraySuggestedMoney.add(inputtedMoney);
                } else {
                    if ((inputtedMoney + 1) % 10 != 0 && !mArraySuggestedMoney.contains(inputtedMoney + 1)) {
                        mArraySuggestedMoney.add(inputtedMoney + 1);
                    }
                    if ((roundedMoneyOne + 5) > inputtedMoney && !mArraySuggestedMoney.contains(roundedMoneyOne + 5)) {
                        mArraySuggestedMoney.add(roundedMoneyOne + 5);
                    }
                    mArraySuggestedMoney.add(roundedMoneyOne + 10);
                    for (int i = 0; i < mParValuesOne.length; i++) {
                        if (mParValuesOne[i] > (roundedMoneyOne + 10) && !mArraySuggestedMoney.contains(mParValuesOne[i])) {
                            mArraySuggestedMoney.add(mParValuesOne[i]);
                        }
                    }
                    for (int i = 0; i < mAnotherParValuesOne.length; i++) {
                        if (mAnotherParValuesOne[i] == (roundedMoneyOne + 20) && !mArraySuggestedMoney.contains(mAnotherParValuesOne[i])) {
                            mArraySuggestedMoney.add(roundedMoneyOne + 20);
                        }
                    }
                    mArraySuggestedMoney.add(inputtedMoney);
                }
            }
            if (inputtedMoney >= 100) {
                int tempTwo = inputtedMoney % 100;
                if (tempTwo == 0) {
                    if (!mArraySuggestedMoney.contains(inputtedMoney)) {
                        mArraySuggestedMoney.add(inputtedMoney);
                    }
                    for (int i = 0; i < mParValuesTwo.length; i++) {
                        if (mParValuesTwo[i] > inputtedMoney && !mArraySuggestedMoney.contains(mParValuesTwo[i])) {
                            mArraySuggestedMoney.add(mParValuesTwo[i]);
                        }
                    }
                    for (int i = 0; i < mAnotherParValuesOne.length; i++) {
                        if (mAnotherParValuesOne[i] == (inputtedMoney + 100) && !mArraySuggestedMoney.contains(inputtedMoney + 100)) {
                            mArraySuggestedMoney.add(inputtedMoney + 100);
                        }
                    }
                    mArraySuggestedMoney.add(inputtedMoney + 1);
                    mArraySuggestedMoney.add(inputtedMoney + 10);
                } else {
                    int tempThree = (tempTwo / 10) * 10;
                    for (int i = 0; i < mAnotherParValuesTwo.length; i++) {
                        if (mAnotherParValuesTwo[i] == (tempThree + 10) && !mArraySuggestedMoney.contains(mAnotherParValuesTwo[i])) {
                            mArraySuggestedMoney.add(roundedMoneyTwo + mAnotherParValuesTwo[i]);
                        }
                    }
                    if ((roundedMoneyTwo + 20) >= inputtedMoney && !mArraySuggestedMoney.contains(roundedMoneyTwo + 20)) {
                        mArraySuggestedMoney.add(roundedMoneyTwo + 20);
                    }
                    if ((roundedMoneyTwo + 50) >= inputtedMoney && !mArraySuggestedMoney.contains(roundedMoneyTwo + 50)) {
                        mArraySuggestedMoney.add(roundedMoneyTwo + 50);
                    }
                    if (!mArraySuggestedMoney.contains(roundedMoneyTwo + 100)) {
                        mArraySuggestedMoney.add(roundedMoneyTwo + 100);
                    }
                    for (int i = 0; i < mParValuesOne.length; i++) {
                        if (mParValuesOne[i] > (roundedMoneyTwo + 100) && !mArraySuggestedMoney.contains(mParValuesOne[i])) {
                            mArraySuggestedMoney.add(mParValuesOne[i]);
                        }
                    }
                    for (int i = 0; i < mAnotherParValuesOne.length; i++) {
                        if (mAnotherParValuesOne[i] == (roundedMoneyTwo + 200) && !mArraySuggestedMoney.contains(mAnotherParValuesOne[i])) {
                            mArraySuggestedMoney.add(mAnotherParValuesOne[i]);
                        }
                    }
                    mArraySuggestedMoney.add(inputtedMoney);
                }
            }
            if (inputtedMoney >= 1000) {
                int tempSix = inputtedMoney % 1000;
                if (tempSix != 0) {
                    int tempFive = (inputtedMoney % 100);
                    if (tempFive == 0) {
                        if (!mArraySuggestedMoney.contains(inputtedMoney + 100)) {
                            mArraySuggestedMoney.add(inputtedMoney + 100);
                        }
                        if (roundedMoneyThree + 500 > inputtedMoney && !mArraySuggestedMoney.contains(roundedMoneyThree + 500)) {
                            mArraySuggestedMoney.add(roundedMoneyThree + 500);
                        }
                        mArraySuggestedMoney.add(roundedMoneyThree + 1000);
                    }
                } else {
                    mArraySuggestedMoney.add(inputtedMoney + 100);
                    mArraySuggestedMoney.add(inputtedMoney + 500);
                    mArraySuggestedMoney.add(inputtedMoney);
                }

            }
            removeSuggestedMoneyDuplicateInputtedMoney(mArraySuggestedMoney, inputtedMoney);
            sortListSuggestedMoney(mArraySuggestedMoney);
            mIActivityBillModel.resultSuggestedMoneySuccess(mArraySuggestedMoney);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xóa tiền trùng với tiền phải trả trong danh sách gợi ý tiền nếu số lượng
     * gợi ý tiền lớn hơn 4. Ngược lại, không xóa
     *
     * @param listSuggestedMoney Danh sách tiền gợi ý
     * @param inputtedMoney      tiền phải trả
     * @created_by cvmanh on 02/08/2021
     */
    private void removeSuggestedMoneyDuplicateInputtedMoney(List<Integer> listSuggestedMoney, int inputtedMoney) {
        try {
            if (mArraySuggestedMoney.size() > MAX_SIZE_LIST_SUGGESTED_MONEY) {
                for (int i = 0; i < mArraySuggestedMoney.size(); i++) {
                    if (mArraySuggestedMoney.get(i) == inputtedMoney) {
                        mArraySuggestedMoney.remove(mArraySuggestedMoney.get(i));
                    }
                }
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc sắp xếp danh sách gợi ý tiền từ thấp đến cao
     *
     * @param listSuggestedMoney Danh sách tiền gợi ý
     * @created_by cvmanh on 02/08/2021
     */
    private void sortListSuggestedMoney(List<Integer> listSuggestedMoney) {
        try {
            Collections.sort(listSuggestedMoney, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    if (o1 < o2) {
                        return -1;
                    }
                    return 0;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IActivityBillModel {
        public void getOrderDetail(List<OrderDetail> listOrderDetail);

        public void resultTextEnter(String moneyIn);

        public void resultSuccess(String amount);

        public void updateOrderStatusSuccess();

        public void resultSuggestedMoneySuccess(List<Integer> listSuggestedMoney);

        public void onFailed();
    }
}
