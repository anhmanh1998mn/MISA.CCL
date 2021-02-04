package vn.com.misa.ccl.model;

import android.app.Activity;
import android.content.ContentValues;
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
 * ‐ Mục đích Class thực hiện việc xử lý các công việc của ActivityFoodUpdate
 * <p>
 * ‐ {@link vn.com.misa.ccl.presenter.ActivityFoodUpdatePresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/19/2021
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

    private String resultNumberEnter = "";

    private List<String> mListOperation;

    private List<Float> mListNumber;

    private final int TEXT_MAX_LENGHT = 17;

    /**
     * Mục đích method thực hiện việc xử lý lấy danh sách màu và gửi tới presenter
     *
     * @param activity instace activity
     * @created_by cvmanh on 01/19/2021
     */
    public void loadListColor(Activity activity) {
        try {
            mListColor = new ArrayList<>();
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            Cursor cursor = mSqliteDatabase.rawQuery("SELECT * FROM " + DatabaseInfomation.TABLE_COLORS + "", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                mListColor.add(new Color(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_COLOR_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_COLOR_KEY))));
            }
            if (cursor != null) {
                mIResultActivityFoodUpdate.loadListColorSuccess(mListColor);
                return;
            }
            mIResultActivityFoodUpdate.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý lấy danh sách hình ảnh và gửi tới presenter
     *
     * @param activity instace activity
     * @created_by cvmanh on 01/19/2021
     */
    public void loadListProductImage(Activity activity) {
        try {
            mListProductImage = new ArrayList<>();
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            Cursor cursor = mSqliteDatabase.rawQuery("SELECT * FROM " + DatabaseInfomation.TABLE_PRODUCT_IMAGES + "", null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                mListProductImage.add(new ProductImage(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID)),
                        cursor.getBlob(cursor.getColumnIndex(DatabaseInfomation.COLUMN_IMAGE))));
            }
            if (cursor != null) {
                mIResultActivityFoodUpdate.loadListImageSuccess(mListProductImage);
                return;
            }
            mIResultActivityFoodUpdate.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc tạo dữ liệu hiển thị cho máy tính và gửi tới presenter
     *
     * @created_by cvmanh on 01/21/2021
     */
    public void loadCaculating(Activity activity) {
        try {
            mListCaculate = new ArrayList<>();
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Mục đích method thực hiện việc xử lý xự kiện click trong máy tính và trả kết quả về prsenter
     *
     * @param numberEnter Giá trị có trong textview
     * @param nameClick   giá trị người dùng click trong máy tính
     * @created_by cvmanh on 01/21/2021
     */
    public void processCaculator(Activity activity, String numberEnter, String nameClick) {
        try {
            switch (nameClick) {
                case "C": {
                    resultNumberEnter = "0";
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "Giảm": {
                    if (Float.parseFloat(numberEnter.trim()) < 1) {
                        resultNumberEnter = "0";
                        mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    resultNumberEnter = ((Float.parseFloat(numberEnter) - 1) + "");
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "Tăng": {
                    resultNumberEnter = ((Float.parseFloat(numberEnter) + 1) + "");
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "": {
                    if (numberEnter.equals("0") || numberEnter.length() == 1) {
                        resultNumberEnter = "0";
                        mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    resultNumberEnter = (numberEnter.substring(0, numberEnter.length() - 1));
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
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
                case "-": {
                    if (numberEnter.startsWith("0")) {
                        resultNumberEnter = "0";
                        mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    if (numberEnter.endsWith("-") || numberEnter.endsWith("+")) {
                        return;
                    }
                    resultNumberEnter = numberEnter + nameClick;
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
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
                case "+": {
                    if (numberEnter.startsWith("0")) {
                        resultNumberEnter = "0";
                        mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    if (numberEnter.endsWith("+") || numberEnter.endsWith("-")) {
                        return;
                    }
                    resultNumberEnter = numberEnter + (nameClick);
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
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
                case "±": {
                    if (numberEnter.startsWith("-")) {
                        return;
                    }
                    resultNumberEnter = ("-" + numberEnter);
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "0": {
                    if (numberEnter.startsWith("0") || numberEnter.startsWith("-0")) {
                        resultNumberEnter = (nameClick);
                        mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    checkNumberCaculate(numberEnter, nameClick);
                    break;
                }
                case "000": {
                    if (numberEnter.startsWith("0") || numberEnter.startsWith("-0")) {
                        resultNumberEnter = "0";
                        mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                        return;
                    }
                    checkNumberCaculate(numberEnter, nameClick);
                    break;
                }
                case ",": {
                    resultNumberEnter = (nameClick);
                    mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                    break;
                }
                case "XONG": {
                    resultCaculatorProcessSuccess(numberEnter);
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
                mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                return;
            }
            if (numberEnter.contains("+") || numberEnter.contains("-")) {
                resultNumberEnter = numberEnter + (nameItemClick);
                mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
            } else if (numberEnter.length() < TEXT_MAX_LENGHT) { //17: độ ài của chuỗi
                resultNumberEnter = numberEnter + (nameItemClick);
                mIResultActivityFoodUpdate.resultTextEnter(resultNumberEnter);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý tính toán và trả kết quả về presenter
     *
     * @param textInput Phép tính
     * @created_by cvmanh on 01/21/2021
     */
    private void resultCaculatorProcess(String textInput) {
        try {
            addOperation(textInput);
            addNumber(textInput);
            if (mListOperation.size() < 1) {
                mIResultActivityFoodUpdate.resultTextEnter(textInput);
                return;
            }
            float result = 0;
            for (int i = 0; i < (mListNumber.size() - 1); i++) {
                switch (mListOperation.get(i)) {
                    case "+": {
                        if (i == 0) {
                            result = mListNumber.get(i) + mListNumber.get(i + 1);
                        } else {
                            result = result + mListNumber.get(i + 1);
                        }
                        break;
                    }
                    case "-": {
                        if (i == 0) {
                            result = mListNumber.get(i) - mListNumber.get(i + 1);
                        } else {
                            result = result - mListNumber.get(i + 1);
                        }
                    }
                }
            }
            mIResultActivityFoodUpdate.resultTextEnter(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý kết quả thao tác +,- trên máy tính
     *
     * @param textInput Kết quả hiển thị
     * @created_by cvmanh on 01/28/2021
     */
    private void resultCaculatorProcessSuccess(String textInput) {
        try {
            addOperation(textInput);
            addNumber(textInput);
            if (mListOperation.size() < 1) {
                mIResultActivityFoodUpdate.resultTextEnterSuccess(textInput);
                return;
            }
            float result = 0;
            for (int i = 0; i < (mListNumber.size() - 1); i++) {
                switch (mListOperation.get(i)) {
                    case "+": {
                        if (i == 0) {
                            result = mListNumber.get(i) + mListNumber.get(i + 1);
                        } else {
                            result = result + mListNumber.get(i + 1);
                        }
                        break;
                    }
                    case "-": {
                        if (i == 0) {
                            result = mListNumber.get(i) - mListNumber.get(i + 1);
                        } else {
                            result = result - mListNumber.get(i + 1);
                        }
                    }
                }
            }
            mIResultActivityFoodUpdate.resultTextEnterSuccess(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc tách các phép tính +,- sau đó thêm vào mảng phép tính
     *
     * @param inputText Phép tính
     * @created_by cvmanh on 01/21/2021
     */
    private void addOperation(String inputText) {
        try {
            mListOperation = new ArrayList<>();
            char[] cArray = inputText.toCharArray();// lấy tất cả các ký tự có trong chuỗi và lưu vào cArray
            for (int i = 0; i < cArray.length; i++) {
                switch (cArray[i]) {
                    case '+': {
                        mListOperation.add(cArray[i] + "");
                        break;
                    }
                    case '-': {
                        mListOperation.add(cArray[i] + "");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc tách các số từ phép tính, sau đó thêm vào mảng sô
     *
     * @param inputText Phép tính
     * @created_by cvmanh on 01/21/2021
     */
    private void addNumber(String inputText) {
        try {
            mListNumber = new ArrayList<>();
            Pattern regex = Pattern.compile("(\\d+(?:\\.\\d+)?)");// lấy tất cả các số có trong chuỗi
            Matcher matcher = regex.matcher(inputText);
            while (matcher.find()) {
                mListNumber.add(Float.valueOf(matcher.group(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện xử lý xóa item product và gửi kết quả về presenter
     *
     * @param productID mã product
     * @created_by cvmanh on 01/27/2021
     */
    public void removeItemProduct(int productID) {
        try {
            for (int i = 0; i < ActivityRestaurantMenuModel.mListProductCategory.size(); i++) {
                if (ActivityRestaurantMenuModel.mListProductCategory.get(i).getProduct().getProductID() == productID) {
                    ActivityRestaurantMenuModel.mListProductCategory.remove(i);
                }
            }
            mIResultActivityFoodUpdate.removeItemProductSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý cập nhật thông tin sản phẩm theo mã sản phẩm và gửi kết quả về presenter
     *
     * @param productId    mã product
     * @param productName  tên sản phẩm
     * @param productPrice giá sản phẩm
     * @param imageID      mã ảnh
     * @param unitID       mã unit
     * @param colorID      mã màu
     * @created_by cvmanh on 01/27/2021
     */
    public void updateItemProduct(int productId, String productName, float productPrice, int imageID, int unitID, int colorID, byte[] imageSelect, String keyColor) {
        try {
            for (int i = 0; i < ActivityRestaurantMenuModel.mListProductCategory.size(); i++) {
                if (ActivityRestaurantMenuModel.mListProductCategory.get(i).getProduct().getProductID() == productId) {
                    ActivityRestaurantMenuModel.mListProductCategory.get(i).getProduct().setProductName(productName);
                    ActivityRestaurantMenuModel.mListProductCategory.get(i).getProduct().setProductPrice(productPrice);
                    ActivityRestaurantMenuModel.mListProductCategory.get(i).getProduct().getProductImage().setProductImageID(imageID);
                    ActivityRestaurantMenuModel.mListProductCategory.get(i).getProduct().getUnit().setUnitID(unitID);
                    ActivityRestaurantMenuModel.mListProductCategory.get(i).getProduct().getColor().setColorID(colorID);
                    ActivityRestaurantMenuModel.mListProductCategory.get(i).getProduct().getProductImage().setImage(imageSelect);
                    ActivityRestaurantMenuModel.mListProductCategory.get(i).getProduct().getColor().setColorName(keyColor);
                }
            }
            mIResultActivityFoodUpdate.updateItemProductSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý xóa sản phẩm theo mã sản phẩm và gửi kết quả vềpresenter
     *
     * @param activity  instance activity
     * @param productID mã product
     * @created_by cvmanh on 01/27/2021
     */
    public void deleteItemProductMenu(Activity activity, int productID) {
        try {

            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);

            Cursor cursor = mSqliteDatabase.rawQuery("SELECT * FROM " + DatabaseInfomation.TABLE_ORDER_DETAIL + " " +
                    "WHERE " + DatabaseInfomation.COLUMN_MYPRODUCT_ID + "=" + productID + "", null);
            if (cursor.getCount() < 1) {
                long result = mSqliteDatabase.delete(DatabaseInfomation.TABLE_MYPRODUCTS, DatabaseInfomation.COLUMN_MYPRODUCT_ID + "=?",
                        new String[]{String.valueOf(productID)});
                if (result > 0) {
                    mIResultActivityFoodUpdate.deleteItemProductMenuSuccess();
                    return;
                }
                mIResultActivityFoodUpdate.onFailed();
                return;
            }
            mIResultActivityFoodUpdate.onFailed();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý cập nahatj thông tin sản phẩm trong menu và gửi kết quả về presenter
     *
     * @param activity     instance activity
     * @param productId    mã product
     * @param productName  tên sản phẩm
     * @param productPrice giá sản phẩm
     * @param imageID      mã ảnh
     * @param unitID       mã unit
     * @param colorID      mã màu
     * @created_by cvmanh on 01/27/2021
     */
    public void updateItemProductMenu(Activity activity, int productId, String productName, float productPrice, int imageID, int unitID, int colorID) {
        try {
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseInfomation.COLUMN_PRODUCT_NAME, productName);
            contentValues.put(DatabaseInfomation.COLUMN_PRODUCT_PRICE, productPrice);
            contentValues.put(DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID, imageID);
            contentValues.put(DatabaseInfomation.COLUMN_UNIT_ID, unitID);
            contentValues.put(DatabaseInfomation.COLUMN_COLOR_ID, colorID);
            contentValues.put(DatabaseInfomation.COLUMN_PRODUCT_STATUS, 1);
            long result = mSqliteDatabase.update(DatabaseInfomation.TABLE_MYPRODUCTS,
                    contentValues, DatabaseInfomation.COLUMN_MYPRODUCT_ID + "=?",
                    new String[]{String.valueOf(productId)});
            if (result > 0) {
                mIResultActivityFoodUpdate.updateItemProducrMenuSuccess();
                return;
            }
            mIResultActivityFoodUpdate.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý thêm mới một sản phẩm vào menu và gửi kết quả về presenter
     *
     * @param activity     instance activity
     * @param productName  tên sản phẩm
     * @param productPrice giá sản phẩm
     * @param imageID      mã ảnh
     * @param unitID       mã unit
     * @param colorID      mã màu
     * @created_by cvmanh on 01/27/2021
     */
    public void addNewFoodMenu(Activity activity, String productName, float productPrice, int imageID, int unitID, int colorID) {
        try {
            if (productName.equals("") || productPrice <= 0 || imageID < 0 || unitID < 0 || colorID < 0) {
                mIResultActivityFoodUpdate.onFailed();
                return;
            }

            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseInfomation.COLUMN_PRODUCT_NAME, productName);
            contentValues.put(DatabaseInfomation.COLUMN_PRODUCT_PRICE, productPrice);
            contentValues.put(DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID, imageID);
            contentValues.put(DatabaseInfomation.COLUMN_UNIT_ID, unitID);
            contentValues.put(DatabaseInfomation.COLUMN_PRODUCT_STATUS, 1);
            contentValues.put(DatabaseInfomation.COLUMN_COLOR_ID, colorID);
            long result = mSqliteDatabase.insert(DatabaseInfomation.TABLE_MYPRODUCTS, null, contentValues);
            if (result > 0) {
                mIResultActivityFoodUpdate.addNewFoodMenuSuccess();
                return;
            }
            mIResultActivityFoodUpdate.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện viêc xử lý ngừng bán sản phẩm và trả kế quả về presenter
     *
     * @param activity  instance activity
     * @param productID mã sản phẩm
     * @created_by cvmanh on 01/31/2021
     */
    public void stopSellProduct(Activity activity, int productID) {
        try {
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseInfomation.COLUMN_PRODUCT_STATUS, 2);//2: ngừng bán sản phẩm
            long resultStopSellProduct = mSqliteDatabase.update(DatabaseInfomation.TABLE_MYPRODUCTS, contentValues,
                    DatabaseInfomation.COLUMN_MYPRODUCT_ID + "=?", new String[]{String.valueOf(productID)});
            if (resultStopSellProduct > 0) {
                mIResultActivityFoodUpdate.updateItemProductSuccess();
                return;
            }
            mIResultActivityFoodUpdate.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IResultActivityFoodUpdate {
        public void loadListColorSuccess(List<Color> listColor);

        public void loadListImageSuccess(List<ProductImage> listImage);

        public void loadCaculating(List<String> listCaculate);

        public void resultTextEnter(String resutText);

        public void resultTextEnterSuccess(String resutText);

        public void removeItemProductSuccess();

        public void updateItemProductSuccess();

        public void deleteItemProductMenuSuccess();

        public void updateItemProducrMenuSuccess();

        public void addNewFoodMenuSuccess();

        public void onFailed();
    }
}
