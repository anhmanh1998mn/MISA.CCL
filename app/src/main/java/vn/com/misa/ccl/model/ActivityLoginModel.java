package vn.com.misa.ccl.model;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.entity.Order;
import vn.com.misa.ccl.entity.OrderDetailServer;
import vn.com.misa.ccl.entity.OrderServer;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.entity.ProductServer;
import vn.com.misa.ccl.service.APIService;
import vn.com.misa.ccl.service.IDataService;
import vn.com.misa.ccl.util.DatabaseInfomation;

/**
 * ‐ Mục đích Class thực hiện việc xử lý các yêu cầu từ ActivityLoginPresenter truyền sang
 * <p>
 * ‐ {@link vn.com.misa.ccl.presenter.ActivityLoginPresenter}
 * ‐ {@link APIService}
 * <p>
 * ‐ @created_by cvmanh on 01/31/2021
 */

public class ActivityLoginModel {

    private List<Product> mListMenu;

    private List<Order> mListOrderServer;

    private IActivityLoginModel mIActivityLoginModel;

    private SQLiteDatabase mSqliteDatabase;

    private Call<String> callbackInsertMenuData;

    private List<ProductServer> mListProductServer;

    private List<OrderDetailServer> mListOrderDetailOnServer;

    private List<OrderServer> mListOrderOnServer;

    public ActivityLoginModel(IActivityLoginModel mIActivityLoginModel) {
        this.mIActivityLoginModel = mIActivityLoginModel;
    }

    /**
     * Mục đích method thực hiện việc xử lý đăng nhập vào ứng dụng
     *
     * @param username tên tài khoản
     * @param password tên mật khẩu
     * @created_by cvmanh on 01/31/2021
     */
    public void doLoginApp(String username, String password) throws NoSuchAlgorithmException {
        try {
            if (username.equals("") || password.equals("")) {
                mIActivityLoginModel.onFailed();
                return;
            }
            IDataService dataService = APIService.getService();
            Call<String> callback = dataService.doLoginApp(username, encodeMD5(password));
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body().toString().trim().equals("onFailed")) {
                        mIActivityLoginModel.onFailed();
                        return;
                    }
                    int shopID = Integer.parseInt(response.body().toString().trim());
                    mIActivityLoginModel.loginSuccess(shopID);
                    Log.d("ShopID", shopID + "");
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    mIActivityLoginModel.onFailed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc mã hóa MD5 mật khẩu
     *
     * @param password mật khẩu
     * @return trả về mật khẩu đã được mã hóa
     * @created_by cvmanh on 01/31/2021
     */
    private String encodeMD5(String password) throws NoSuchAlgorithmException {
        try {
            String result = "";
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            BigInteger bigInteger = new BigInteger(1, md.digest());
            result = bigInteger.toString(16);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Mục đích method thực hiện việc xử lý kiểm tra đã tồn tại dữ liệu trên server với mã id của cửa hàng
     * đăng nhập
     * Nếu tồn tại dữ liệu trên server thì không làm gì
     * Ngược lại, nếu không tồn tại dữ liệu trên server thì sẽ đẩy menu của cửa hàng lên server
     *
     * @param shopID mã cửa hàng đăng nhập
     * @created_by cvmanh on 02/03/2021
     */
    public void checkSyncData(int shopID, Activity activity) {
        try {
            IDataService dataService = APIService.getService();
            Call<String> callback = dataService.checkSyncData(shopID);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body().toString().trim().equals("Exists")) {
                        getListProductOnServer(activity, shopID);
                        getListOrderOnServer(activity, shopID);
                        getListOrderDetailOnServer(activity, shopID);
                        return;
                    }
                    doSyncDataProductToServer(shopID, activity);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("ErrorCheckSyncData", t.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

//        getListProductOnServer(activity, shopID);

    }

    /**
     * Mục đích method thực hiện lấy danh sách sản phẩm của cửa hàng trên server
     * Xóa danh sách sản phẩm ở local
     * Rồi thêm danh sách vừa lấy trên server thêm vào local
     *
     * @param activity instance activity
     * @param shopID   mã cửa hàng
     * @created_by cvmanh on 02/05/2021
     */
    private void getListProductOnServer(Activity activity, int shopID) {
        try {
            mListProductServer = new ArrayList<>();
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            long resultDeleteProductData = mSqliteDatabase.delete(DatabaseInfomation.TABLE_MYPRODUCTS, null, null);
//        if(resultDeleteProductData>0){
            IDataService dataService = APIService.getService();
            Call<List<ProductServer>> callback = dataService.getListProductServer(shopID);
            callback.enqueue(new Callback<List<ProductServer>>() {
                @Override
                public void onResponse(Call<List<ProductServer>> call, Response<List<ProductServer>> response) {
                    mListProductServer = response.body();
                    if (mListProductServer.size() > 0) {
                        doInsertProductDataFromServerToLocal(activity);
                    }
                }

                @Override
                public void onFailure(Call<List<ProductServer>> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
//        }
    }

    /**
     * Mục đích method thực hiện việc thêm dữ liệu lấy từ server vào thêm vào local
     *
     * @param activity instance activity
     * @created_by cvmanh on 02/05/2021
     */
    private void doInsertProductDataFromServerToLocal(Activity activity) {
        try {
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            ContentValues contentValuesInsertProduct = new ContentValues();
            for (int i = 0; i < mListProductServer.size(); i++) {
                contentValuesInsertProduct.put(DatabaseInfomation.COLUMN_MYPRODUCT_ID, mListProductServer.get(i).getProductLocalID());
                contentValuesInsertProduct.put(DatabaseInfomation.COLUMN_PRODUCT_NAME, mListProductServer.get(i).getProductName());
                contentValuesInsertProduct.put(DatabaseInfomation.COLUMN_PRODUCT_PRICE, mListProductServer.get(i).getProductPrice());
                contentValuesInsertProduct.put(DatabaseInfomation.COLUMN_PRODUCT_STATUS, mListProductServer.get(i).getProductStatus());
                contentValuesInsertProduct.put(DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID, mListProductServer.get(i).getImageID());
                contentValuesInsertProduct.put(DatabaseInfomation.COLUMN_UNIT_ID, mListProductServer.get(i).getUnitID());
                contentValuesInsertProduct.put(DatabaseInfomation.COLUMN_COLOR_ID, mListProductServer.get(i).getColorID());
                mSqliteDatabase.insert(DatabaseInfomation.TABLE_MYPRODUCTS, null, contentValuesInsertProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý lấy toàn bộ danh sách order của cửa hàng trên server
     * Sau đó xóa toàn bộ danh sách order hiện có ở local
     * Rồi dẩy dữ liệu danh sách order trên server vừa lấy về vào local
     *
     * @param activity instance activity
     * @param shopID   mã cửa hàng
     * @created_by cvmanh on 02/05/2021
     */
    private void getListOrderOnServer(Activity activity, int shopID) {
        try {
            mListOrderOnServer = new ArrayList<>();
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            long resultDeleteOrderData = mSqliteDatabase.delete(DatabaseInfomation.TABLE_ORDERS, null, null);
//        if(resultDeleteProductData>0){
            IDataService dataService = APIService.getService();
            Call<List<OrderServer>> callback = dataService.getListOrderOnServer(shopID);
            callback.enqueue(new Callback<List<OrderServer>>() {
                @Override
                public void onResponse(Call<List<OrderServer>> call, Response<List<OrderServer>> response) {
                    mListOrderOnServer = response.body();
                    if (mListOrderOnServer.size() > 0) {
                        doInsertOrdertDataFromServerToLocal(activity);
                    }
                }

                @Override
                public void onFailure(Call<List<OrderServer>> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý thêm danh sách order của cửa hàng trên server vào local
     *
     * @param activity instance activity
     * @created_by cvmanh on 02/05/2021
     */
    private void doInsertOrdertDataFromServerToLocal(Activity activity) {
        try {
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            ContentValues contentValuesInsertOrder = new ContentValues();
            for (int i = 0; i < mListOrderOnServer.size(); i++) {
                contentValuesInsertOrder.put(DatabaseInfomation.COLUMN_ORDER_ID, mListOrderOnServer.get(i).getOrderIDLocal());
                contentValuesInsertOrder.put(DatabaseInfomation.COLUMN_ORDER_STATUS, 2);
                contentValuesInsertOrder.put(DatabaseInfomation.COLUMN_ORDER_CREATED_AT, mListOrderOnServer.get(i).getCreatedAt());
                contentValuesInsertOrder.put(DatabaseInfomation.COLUMN_TABLE_NAME, mListOrderOnServer.get(i).getTableName());
                contentValuesInsertOrder.put(DatabaseInfomation.COLUMN_TOTAL_PEOPLE, mListOrderOnServer.get(i).getTotalPeople());
                contentValuesInsertOrder.put(DatabaseInfomation.COLUM_ORDER_AMOUNT, mListOrderOnServer.get(i).getAmount());
                mSqliteDatabase.insert(DatabaseInfomation.TABLE_ORDERS, null, contentValuesInsertOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc xử lý lấy toàn bộ danh sách orderDetail của cửa hàng trên server
     * Sau đó, xóa toàn bộ dữ liệu danh sách orderDetail ở local
     * Rồi đẩy dữ liệu danh sách orderDetail vừa lấy về vào local
     *
     * @param activity instance activity
     * @param shopID   mã cửa hàng
     * @created_by cvmanh on 02/05/2021
     */
    private void getListOrderDetailOnServer(Activity activity, int shopID) {
        try {
            mListOrderDetailOnServer = new ArrayList<>();
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            long resultDeleteOrderDetailData = mSqliteDatabase.delete(DatabaseInfomation.TABLE_ORDER_DETAIL, null, null);
//        if(resultDeleteProductData>0){
            IDataService dataService = APIService.getService();
            Call<List<OrderDetailServer>> callback = dataService.getListOrderDetailOnServer(shopID);
            callback.enqueue(new Callback<List<OrderDetailServer>>() {
                @Override
                public void onResponse(Call<List<OrderDetailServer>> call, Response<List<OrderDetailServer>> response) {
                    mListOrderDetailOnServer = response.body();
                    if (mListOrderDetailOnServer.size() > 0) {
                        doInsertOrderDetailDataFromServerToLocal(activity);
                    }
                }

                @Override
                public void onFailure(Call<List<OrderDetailServer>> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc thêm dữ liệu danh sách orderDetail của cửa hàng trên server vào local
     *
     * @param activity instance activity
     * @created_by cvmanh on 02/05/2021
     */
    private void doInsertOrderDetailDataFromServerToLocal(Activity activity) {
        try {
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            ContentValues contentValuesInsertOrderDetail = new ContentValues();
            for (int i = 0; i < mListOrderDetailOnServer.size(); i++) {
                contentValuesInsertOrderDetail.put(DatabaseInfomation.COLUMN_ORDER_ID, mListOrderDetailOnServer.get(i).getOrderIDLocal());
                contentValuesInsertOrderDetail.put(DatabaseInfomation.COLUMN_MYPRODUCT_ID, mListOrderDetailOnServer.get(i).getProductLocalID());
                contentValuesInsertOrderDetail.put(DatabaseInfomation.COLUMN_QUANTITY, mListOrderDetailOnServer.get(i).getQuantity());
                contentValuesInsertOrderDetail.put(DatabaseInfomation.COLUM_PRODUCT_PRICE_OUT, mListOrderDetailOnServer.get(i).getProductPriceOut());
                contentValuesInsertOrderDetail.put(DatabaseInfomation.COLUM_ORDER_DETAIL_ID, mListOrderDetailOnServer.get(i).getOrderDetailIDLocal());
                mSqliteDatabase.insert(DatabaseInfomation.TABLE_ORDER_DETAIL, null, contentValuesInsertOrderDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc đẩy dữ liệu menu của cửa hàng lên server
     *
     * @param shopID mã cửa hàng
     * @created_by cvmanh on 02/03/2021
     */
    private void doSyncDataProductToServer(int shopID, Activity activity) {
        try {
            mSqliteDatabase = DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
            Cursor cursorGetMenuLocal = mSqliteDatabase.rawQuery("SELECT * FROM " + DatabaseInfomation.TABLE_MYPRODUCTS + "", null);
            for (int i = 0; i < cursorGetMenuLocal.getCount(); i++) {
                cursorGetMenuLocal.moveToPosition(i);
                IDataService dataService = APIService.getService();
                callbackInsertMenuData = dataService.doInsertDataProduct
                        (cursorGetMenuLocal.getString(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_NAME)),
                                cursorGetMenuLocal.getInt(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_PRICE)),
                                cursorGetMenuLocal.getInt(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_STATUS)),
                                cursorGetMenuLocal.getInt(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID)),
                                cursorGetMenuLocal.getInt(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_UNIT_ID)),
                                cursorGetMenuLocal.getInt(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_COLOR_ID)),
                                shopID, cursorGetMenuLocal.getInt(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_MYPRODUCT_ID)));
                callbackInsertMenuData.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().toString().trim().equals("Success")) {
                            Log.d("InsertMenuServerSuccess", "Success");
                            return;
                        }
                        Log.d("InsertMenuServerFailed", "Failed");
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("InsertMenuServerFailed", "Failed");
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IActivityLoginModel {
        public void loginSuccess(int shopID);

        public void onFailed();
    }
}
