package vn.com.misa.ccl.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.misa.ccl.database.DatabaseHelper;
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

    private IActivityLoginModel mIActivityLoginModel;

    private SQLiteDatabase mSqliteDatabase;

    private Call<String> callbackInsertMenuData;

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
     *
     * @created_by cvmanh on 02/03/2021
     */
    public void checkSyncData(int shopID, Activity activity) {
        IDataService dataService=APIService.getService();
        Call<String> callback=dataService.checkSyncData(shopID);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().toString().trim().equals("Exists")){
                    return;
                }
                doSyncDataProductToServer(shopID,activity);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("ErrorCheckSyncData",t.toString());
            }
        });
    }

    /**
     * Mục đích method thực hiện việc đẩy dữ liệu menu của cửa hàng lên server
     *
     * @param shopID mã cửa hàng
     *
     * @created_by cvmanh on 02/03/2021
     */
    private void doSyncDataProductToServer(int shopID,Activity activity) {
        mSqliteDatabase= DatabaseHelper.initDatabase(activity, DatabaseInfomation.DATABASE_NAME);
        Cursor cursorGetMenuLocal=mSqliteDatabase.rawQuery("SELECT * FROM "+DatabaseInfomation.TABLE_MYPRODUCTS+"",null);
        for(int i=0;i<cursorGetMenuLocal.getCount();i++){
            cursorGetMenuLocal.moveToPosition(i);
            IDataService dataService=APIService.getService();
            callbackInsertMenuData=dataService.doInsertDataProduct
                    (cursorGetMenuLocal.getString(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_NAME)),
                    cursorGetMenuLocal.getInt(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_PRICE)),
                    cursorGetMenuLocal.getInt(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_STATUS)),
                    cursorGetMenuLocal.getInt(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_PRODUCT_IMAGE_ID)),
                    cursorGetMenuLocal.getInt(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_UNIT_ID)),
                    cursorGetMenuLocal.getInt(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_COLOR_ID)),
                    shopID,cursorGetMenuLocal.getInt(cursorGetMenuLocal.getColumnIndex(DatabaseInfomation.COLUMN_MYPRODUCT_ID)));
            callbackInsertMenuData.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body().toString().trim().equals("Success")){
                        Log.d("InsertMenuServerSuccess","Success");
                        return;
                    }
                    Log.d("InsertMenuServerFailed","Failed");
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("InsertMenuServerFailed","Failed");
                }
            });
        }
    }

    public interface IActivityLoginModel {
        public void loginSuccess(int shopID);

        public void onFailed();
    }
}
