package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Color;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.model.ActivityFoodUpdateModel;
import vn.com.misa.ccl.view.restaurantsetup.IActivityFoodUpdate;

/**
‐ Mục đích Class thực hiện việc giao tiếp giữa ActivityFoodUpdateModel và ActivityFoodUpdate
*
‐ {@link vn.com.misa.ccl.view.restaurantsetup.ActivityFoodUpdate}
‐ {@link ActivityFoodUpdateModel}
*
‐ @created_by cvmanh on 01/19/2021
*/

public class ActivityFoodUpdatePresenter implements IActivityFoodUpdate.IActivityFoodUpdatePresenter, ActivityFoodUpdateModel.IResultActivityFoodUpdate {

    private IActivityFoodUpdate.IActivityFoodUpdateView mIActivityFoodUpdateView;

    public ActivityFoodUpdatePresenter(IActivityFoodUpdate.IActivityFoodUpdateView mIActivityFoodUpdateView) {
        this.mIActivityFoodUpdateView = mIActivityFoodUpdateView;
    }

    private ActivityFoodUpdateModel mActivityFoodUpdateModel=new ActivityFoodUpdateModel(this);

    /**
     * Mục đích method thực hiện việc gọi hàm xử lý lấy danh sách màu sản phẩm
     *
     * @param activity instace activity
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListColor(Activity activity) {
        mActivityFoodUpdateModel.loadListColor(activity);
    }

    /**
     * Mục đích method thực hiện việc gọi hàm xử lý lấy danh sách hình ảnh
     *
     * @param  activity instace activity
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadProductImage(Activity activity) {
        mActivityFoodUpdateModel.loadListProductImage(activity);
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách màu từ ActivityFoodUpdateModel và gửi danh sách tới view
     *
     * @param listColor Danh sách màu
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListColorSuccess(List<Color> listColor) {
        mIActivityFoodUpdateView.loadListColorSuccess(listColor);
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách hình ảnh từ ActivityFoodUpdateModel và gửi danh sách tới view
     *
     * @param listImage Danh sách hình ảnh
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListImageSuccess(List<ProductImage> listImage) {
        mIActivityFoodUpdateView.loadListProductImageSuccess(listImage);
    }

    /**
     * Mục đích method thực hiện việc thông báo lỗi xử lý tới view
     *
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void onFailed() {
        mIActivityFoodUpdateView.onFailed();
    }
}
