package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.ProductCategory;
import vn.com.misa.ccl.model.ActivityRestaurantMenuModel;
import vn.com.misa.ccl.view.restaurantsetup.IActivityRestaurantMenu;

/**
 * ‐ Mục đích Class thực hiện việc luân chuyển dữ liệu giữa ActivityRestaurantMenuModel và ActivityRestaurantMenu
 * <p>
 * ‐ {@link ActivityRestaurantMenuModel}
 * ‐ {@link vn.com.misa.ccl.view.restaurantsetup.ActivityRestaurantMenu}
 * <p>
 * ‐ @created_by cvmanh on 01/19/2021
 */

public class ActivityRestaurantMenuPresenter implements IActivityRestaurantMenu.IActivityRestaurantMenuPresenter, ActivityRestaurantMenuModel.IResultActivityRestaurantMenu {
    private IActivityRestaurantMenu.IFragmentRestaurantMenuView mIActivityRestaurantMenuView;

    public ActivityRestaurantMenuPresenter(IActivityRestaurantMenu.IFragmentRestaurantMenuView mIFragmentShopMenuView) {
        this.mIActivityRestaurantMenuView = mIFragmentShopMenuView;
    }

    private ActivityRestaurantMenuModel mActivityRestaurantMenuModel = new ActivityRestaurantMenuModel(this);

    /**
     * Mục đích method thực hiện việc gọi hàm xử lý lấy dữ liệu danh sách Product từ ActivityRestaurantMenuModel
     *
     * @param activity   instace activity
     * @param categoryID mã loại sản phẩm
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListProduct(Activity activity, int categoryID) {
        mActivityRestaurantMenuModel.loadListProduct(activity, categoryID);
    }

    /**
     * Mục đích method thực hiện gọi model xử lý khởi tạo menu
     *
     * @param activity instance activity
     * @param listMenu danh sách menu
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void initMenu(Activity activity, List<ProductCategory> listMenu) {
        mActivityRestaurantMenuModel.initMenu(activity, listMenu);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý xóa item trong menu
     *
     * @param productID Mã sản phẩm
     * @param listProduct Danh sách sản phẩm
     *
     * @created_by cvmanh on 01/26/2021
     */

    /**
     * Mục đích method thực hiện việc nhân dữ liệu danh sách Product trả về và gửi danh sách đến view
     *
     * @param listProductCategory Danh sách sản phẩm
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListProductSuccess(List<ProductCategory> listProductCategory) {
        mIActivityRestaurantMenuView.loadListProductSuccess(listProductCategory);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả khởi tạo MyMenu thành công và gửi kết quả về view
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void initMenuSuccess() {
        mIActivityRestaurantMenuView.initMenuSuccess();
    }


    /**
     * Mục đích method thực hiện việc nhân dữ liệu thông báo thất bại và gửi danh sách đến view
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onLoadFailed() {
        mIActivityRestaurantMenuView.onFailed();
    }
}
