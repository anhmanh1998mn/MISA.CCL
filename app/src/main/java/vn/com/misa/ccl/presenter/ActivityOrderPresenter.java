package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.model.ActivityOrderModel;
import vn.com.misa.ccl.view.order.IActivityOrder;

/**
 * ‐ Mục đích Class thực hiện những việc luân chuyển dữ liệu giữa ActivityOrder và ActivityOrderModel
 * <p>
 * ‐ {@link ActivityOrderModel}
 * ‐ {@link vn.com.misa.ccl.view.order.ActivityOrder}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class ActivityOrderPresenter implements IActivityOrder.IActivityOrderPresenter, ActivityOrderModel.IActivityOrderModel {

    private IActivityOrder.IActivityOrderView mIActivityOrderView;

    public ActivityOrderPresenter(IActivityOrder.IActivityOrderView mIActivityOrderView) {
        this.mIActivityOrderView = mIActivityOrderView;
    }

    private ActivityOrderModel mActivityOrderModel = new ActivityOrderModel(this);

    /**
     * Mục đích method thực hiện gọi hàm xử lý lấy danh sách menu từ model
     *
     * @param activity instance activity
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getListMenu(Activity activity) {
        mActivityOrderModel.getListMenu(activity);
    }

    /**
     * Mục đích method thực hiện gọi hàm xử lý máy tính từ model
     *
     * @param textInput   Button mà người dùng click trên máy tính
     * @param numberEnter Giá trị hiển thị kết quả xử lý
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getResultCaculate(String textInput, String numberEnter) {
        mActivityOrderModel.getResultCaculate(textInput, numberEnter);
    }

    /**
     * Mục đích method thực hiện gọi hàm xử lý thêm mới order từ model
     *
     * @param activity    instace activity
     * @param listProduct Danh sách Product
     * @param tableName   Tên bàn
     * @param totalPeople Số lượng người
     * @param amount      Tổng tiền đơn hàng
     * @param typeClick   Loại button clcik: 1: Thực hiện Lưu . 2: Thực hiện thu iền
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void addNewOrder(Activity activity, List<Product> listProduct, String tableName, String totalPeople, float amount, int typeClick) {
        mActivityOrderModel.addNewOrder(activity, listProduct, tableName, totalPeople, amount, typeClick);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý lấy danh sách thông tin chi tiết order theo id
     *
     * @param activity instance activity
     * @param orderID  mã order
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void getListOrderDetailWithOrderID(Activity activity, int orderID) {
        mActivityOrderModel.getListOrderDetailWithOrderID(activity, orderID);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý gán số lượng bán của list order sang list product
     *
     * @param listOrder   danh sách order
     * @param listProduct danh sách product
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void checkQuantityProductItem(List<OrderDetail> listOrder, List<Product> listProduct) {
        mActivityOrderModel.checkQuantityProductItem(listOrder, listProduct);
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
    @Override
    public void updateOrder(Activity activity, int orderID, List<Product> listProdcut, int typeClick, String tableName, String totalPeople, float amount) {
        mActivityOrderModel.updateOrder(activity, orderID, listProdcut, typeClick, tableName, totalPeople, amount);
    }

    /**
     * Mục đích method thực hiện việc nhận danh sách menu và gửi về view nếu lấy xử lý thành công
     *
     * @param listMenu Danh sách menu
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getListMenuSuccess(List<Product> listMenu) {
        mIActivityOrderView.getListMenuSuccess(listMenu);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xử lý máy tính và gửi về view nếu lấy xử lý thành công
     *
     * @param result kết quả xử lý
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void resultProcessCaculateSuccess(String result) {
        mIActivityOrderView.getResultCaculateSuccess(result);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xử lý lưu order thành công và gửi về view
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void addNewOrderSuccess() {
        mIActivityOrderView.addNewOrderSuccess();
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xử lý lưu order thành công và gửi về view
     *
     * @param orderID Mã order
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void addNewOrderTwoSuccess(int orderID) {
        mIActivityOrderView.addNewOrderTwoSuccess(orderID);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả lấy thông tin order và gửi kết quả sang view
     *
     * @param listOrderDetail danh sách orderDetail
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void getListDetailWithOrderIDSuccess(List<OrderDetail> listOrderDetail) {
        mIActivityOrderView.getListOrderDetailWithOrderID(listOrderDetail);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả cập nhật thông tin order thành công và gửi kết quả cho view
     *
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void updateOrderSaveSuccess() {
        mIActivityOrderView.updateOrderSaveSuccess();
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả cập nhật thông tin order thành công và gửi kết quả cho view
     *
     * @param orderID Mã order
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void updateOrderMoneySuccess(int orderID) {
        mIActivityOrderView.updateOrderMoneySuccess(orderID);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả gán số lượng bán từ list order sang list product thành công
     *
     * @param listProduct danh sách product
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void checkQuantitiOrderClickSuccess(List<Product> listProduct) {
        mIActivityOrderView.checkQuantityOrderClickSuccess(listProduct);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả xử lý thất bại và gửi về view
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void onFailed() {
        mIActivityOrderView.onFailed();
    }
}
