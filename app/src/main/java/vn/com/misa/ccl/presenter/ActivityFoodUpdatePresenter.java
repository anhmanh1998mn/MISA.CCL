package vn.com.misa.ccl.presenter;

import android.app.Activity;
import android.widget.TextView;

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
     * Mục đích method thực hiện việc gọi lấy dữ liệu hiển thị trên máy tính từ model
     *
     * @created_by cvmanh on 01/21/2021
     */
    @Override
    public void loadCaculating(Activity activity) {
        mActivityFoodUpdateModel.loadCaculating(activity);
    }

    /**
     * Mục đích method thực hiện việc gọi hàm xử lý kết quả trong model khi thao tác với máy tính
     *
     * @param numberEnter Giá trị có trong textview
     * @param  nameClick giá trị của phím người dùng thao tác
     *
     * @created_by cvmanh on 01/21/2021
     */
    @Override
    public void processCaculator(Activity activity,String numberEnter,String nameClick) {
        mActivityFoodUpdateModel.processCaculator(activity,numberEnter,nameClick);
    }

    @Override
    public void removeItemProduct(int productID) {
        mActivityFoodUpdateModel.removeItemProduct(productID);
    }

    @Override
    public void updateItemProduct(int productId, String productName, float productPrice, int imageID, int unitID, int colorID) {
        mActivityFoodUpdateModel.updateItemProduct(productId,productName,productPrice,
                imageID,unitID,colorID);
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
     * Mục đích method thực hiện việc nhận dữ liệu định dang cho máy tình từ model và gửi tới view
     *
     * @param listCaculate Danh sách dữ liệu hiển thị lên máy tính
     *
     * @created_by cvmanh on 01/21/2021
     */
    @Override
    public void loadCaculating(List<String> listCaculate) {
        mIActivityFoodUpdateView.loadCaculatingSuccess(listCaculate);
    }

    /**
     * Mục đích method thực hiện việc lấy kết quả xử lý phép tính từ model và trả kết quả tới view
     *
     * @param resutText Kết quả xử lý
     *
     * @created_by cvmanh on 01/21/2021
     */
    @Override
    public void resultTextEnter(String resutText) {
        mIActivityFoodUpdateView.processCaculatorSuccess(resutText);
    }

    @Override
    public void resultTextEnterSuccess(String resutText) {
        mIActivityFoodUpdateView.processEnterSuccess(resutText);
    }

    @Override
    public void removeItemProductSuccess() {
        mIActivityFoodUpdateView.removeProductItemSuccess();
    }

    @Override
    public void updateItemProductSuccess() {
        mIActivityFoodUpdateView.updateItemProductSuccess();
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
