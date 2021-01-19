package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Category;
import vn.com.misa.ccl.model.ActivityRestaurantTypeModel;
import vn.com.misa.ccl.model.IActivityRestaurantTypeModel;
import vn.com.misa.ccl.view.restaurantsetup.IActivityRestaurantType;

/**
‐ Mục đích Class thực hiện việc luân chuyển dữ liệu giữa ActivityRestaurantTypeModel và ActivityRestaurantType
*
‐ {@link ActivityRestaurantTypeModel}
‐ {@link vn.com.misa.ccl.view.restaurantsetup.ActivityRestaurantType}
*
‐ @created_by cvmanh on 01/19/2021
*/

public class ActivityRestaurantTypePresenter implements IActivityRestaurantType.IFragmentRestaurantTypePresenter, IActivityRestaurantTypeModel {

    private IActivityRestaurantType.IActivityRestaurantTypeView mIActivityShopTypeView;

    public ActivityRestaurantTypePresenter(IActivityRestaurantType.IActivityRestaurantTypeView mIFragmentShopTypeView) {
        this.mIActivityShopTypeView = mIFragmentShopTypeView;
    }

    private ActivityRestaurantTypeModel mActivityRestaurantTypeModel =new ActivityRestaurantTypeModel(this);

    /**
     * Mục đích method thực hiện việc gọi hàm xử lý lấy dữ liệu danh sách loại cửa hàng
     *
     * @param activity instance activity
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListShopType(Activity activity) {
        mActivityRestaurantTypeModel.loadListRestaurantType(activity);
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách loại cửa hàng và gửi tới view
     *
     * @param listCategory Danh sách loại cửa hàng
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListRestaurantTypeSuccess(List<Category> listCategory) {
        mIActivityShopTypeView.loadListRestaurantTypeSuccess(listCategory);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả thất bại và gửi tới view
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onFailed() {
        mIActivityShopTypeView.onFailed();
    }
}
