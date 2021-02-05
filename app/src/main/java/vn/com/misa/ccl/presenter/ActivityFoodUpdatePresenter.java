package vn.com.misa.ccl.presenter;

import android.app.Activity;
import android.widget.TextView;

import java.util.List;

import vn.com.misa.ccl.entity.Color;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.model.ActivityFoodUpdateModel;
import vn.com.misa.ccl.view.restaurantsetup.IActivityFoodUpdate;

/**
 * ‐ Mục đích Class thực hiện việc giao tiếp giữa ActivityFoodUpdateModel và ActivityFoodUpdate
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.restaurantsetup.ActivityFoodUpdate}
 * ‐ {@link ActivityFoodUpdateModel}
 * <p>
 * ‐ @created_by cvmanh on 01/19/2021
 */

public class ActivityFoodUpdatePresenter implements IActivityFoodUpdate.IActivityFoodUpdatePresenter, ActivityFoodUpdateModel.IResultActivityFoodUpdate {

    private IActivityFoodUpdate.IActivityFoodUpdateView mIActivityFoodUpdateView;

    public ActivityFoodUpdatePresenter(IActivityFoodUpdate.IActivityFoodUpdateView mIActivityFoodUpdateView) {
        this.mIActivityFoodUpdateView = mIActivityFoodUpdateView;
    }

    private ActivityFoodUpdateModel mActivityFoodUpdateModel = new ActivityFoodUpdateModel(this);

    /**
     * Mục đích method thực hiện việc gọi hàm xử lý lấy danh sách màu sản phẩm
     *
     * @param activity instace activity
     * @created_by cvmanh on 01/19/2021
     */
    @Override
    public void loadListColor(Activity activity) {
        mActivityFoodUpdateModel.loadListColor(activity);
    }

    /**
     * Mục đích method thực hiện việc gọi hàm xử lý lấy danh sách hình ảnh
     *
     * @param activity instace activity
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
     * @param nameClick   giá trị của phím người dùng thao tác
     * @created_by cvmanh on 01/21/2021
     */
    @Override
    public void processCaculator(Activity activity, String numberEnter, String nameClick) {
        mActivityFoodUpdateModel.processCaculator(activity, numberEnter, nameClick);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý xóa item product
     *
     * @param productID mã product
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void removeItemProduct(int productID) {
        mActivityFoodUpdateModel.removeItemProduct(productID);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý sửa thông tin product
     *
     * @param productId    mã product
     * @param productName  tên product
     * @param productPrice giá product
     * @param imageID      mã ảnh
     * @param unitID       mã unit
     * @param colorID      mã màu
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void updateItemProduct(int productId, String productName, float productPrice, int imageID, int unitID, int colorID, byte[] imageSelect, String keyColor) {
        mActivityFoodUpdateModel.updateItemProduct(productId, productName, productPrice,
                imageID, unitID, colorID, imageSelect, keyColor);
    }

    /**
     * Mục đích method thực hiện gọi model xử lý xóa đồ trong menu
     *
     * @param activity  instance activity
     * @param productID mã product
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void deleteItemProductMenu(Activity activity, int productID) {
        mActivityFoodUpdateModel.deleteItemProductMenu(activity, productID);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý cập nhật thông tin sản phẩm trong menu
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
    @Override
    public void updateItemProductMenu(Activity activity, int productId, String productName, float productPrice, int imageID, int unitID, int colorID) {
        mActivityFoodUpdateModel.updateItemProductMenu(activity, productId, productName, productPrice, imageID, unitID, colorID);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý cthêm mới thông tin sản phẩm trong menu
     *
     * @param activity     instance activity
     * @param productName  tên sản phẩm
     * @param productPrice giá sản phẩm
     * @param imageID      mã ảnh
     * @param unitID       mã unit
     * @param colorID      mã màu
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void addNewFoodMenu(Activity activity, String productName, float productPrice, int imageID, int unitID, int colorID) {
        mActivityFoodUpdateModel.addNewFoodMenu(activity, productName, productPrice, imageID, unitID, colorID);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý ngừng bán sản phẩm
     *
     * @param activity  instance activity
     * @param productID mã sản phẩm
     * @created_by cvmanh on 01/31/2021
     */
    @Override
    public void stopSellProduct(Activity activity, int productID) {
        mActivityFoodUpdateModel.stopSellProduct(activity, productID);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý xóa sản phẩm của menu trên máy chủ
     *
     * @param productIDLocal mã sản phẩm phía local
     * @param shopID         mã cửa hàng
     * @created_by cvmanh on 02/05/2021
     */
    @Override
    public void deleteItemProductMenuOnServer(int productIDLocal, int shopID) {
        mActivityFoodUpdateModel.deleteItemProductMenuOnServer(productIDLocal, shopID);
    }

    @Override
    public void updateItemProductOnServer(String productName, float productPrice, int imageID, int unitID, int colorID, int shopID, int productId) {
        mActivityFoodUpdateModel.updateItemProductOnServer(productName, productPrice, imageID, unitID, colorID, shopID, productId);
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách màu từ ActivityFoodUpdateModel và gửi danh sách tới view
     *
     * @param listColor Danh sách màu
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
     * @created_by cvmanh on 01/21/2021
     */
    @Override
    public void resultTextEnter(String resutText) {
        mIActivityFoodUpdateView.processCaculatorSuccess(resutText);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xử lý
     *
     * @param resutText Kết quả xử lý
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void resultTextEnterSuccess(String resutText) {
        mIActivityFoodUpdateView.processEnterSuccess(resutText);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xóa thông tin item product thành công và gửi kết quả về view
     *
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void removeItemProductSuccess() {
        mIActivityFoodUpdateView.removeProductItemSuccess();
    }

    /**
     * Mục đích method thực hiện nhận kết quả cập nhật thông tin item sản phẩm thành công và gửi kết quả về view
     *
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void updateItemProductSuccess() {
        mIActivityFoodUpdateView.updateItemProductSuccess();
    }

    /**
     * Mục đích method thực hiện nhận kết quả xóa item product thành công và gửi kết quả về view
     *
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void deleteItemProductMenuSuccess() {
        mIActivityFoodUpdateView.deleteItemProductMenuSuccess();
    }

    /**
     * Mục đích method thực hiện nhận kết quả cập nhật thông tin sản phẩm thành công và gửi về view
     *
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void updateItemProducrMenuSuccess() {
        mIActivityFoodUpdateView.updateItemProductMenuSuccess();
    }

    /**
     * Mục đích method thực hiện nhận kết quả thêm mới thông tin sản phẩm thành công và gửi về view
     *
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void addNewFoodMenuSuccess() {
        mIActivityFoodUpdateView.addNewFoodMenuSuccess();
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
