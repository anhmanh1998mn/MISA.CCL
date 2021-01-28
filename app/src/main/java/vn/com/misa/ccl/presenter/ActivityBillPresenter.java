package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.model.ActivityBillModel;
import vn.com.misa.ccl.view.order.IActivityBill;

/**
 * ‐ Mục đích Class thực hiện những việc luân chuyển dữ liệu giữa ActivityBillModel và ActivityBill
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.order.ActivityBill}
 * ‐ {@link ActivityBillModel}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class ActivityBillPresenter implements IActivityBill.IActivityBillPresenter, ActivityBillModel.IActivityBillModel {
    private IActivityBill.IActivityBillView mIActivityBillView;

    public ActivityBillPresenter(IActivityBill.IActivityBillView mIActivityBillView) {
        this.mIActivityBillView = mIActivityBillView;
    }

    private ActivityBillModel mActivityBillModel = new ActivityBillModel(this);

    /**
     * Mục đích method thực hiện việc gọi model xử lý lấy danh sách orderDetail
     *
     * @param activity instance activity
     * @param orderID  mã order
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getOrderDetail(Activity activity, int orderID) {
        mActivityBillModel.getOrderDetail(activity, orderID);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý lấy danh sách Caculate
     *
     * @param activity      instance activity
     * @param resultProcess kết quả xử lý
     * @param nameClick     tên button click
     * @param amount        tổng tiền order
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void processCaculator(Activity activity, String resultProcess, String nameClick, float amount) {
        mActivityBillModel.processCaculator(activity, resultProcess, nameClick, amount);
    }

    /**
     * Mục đích method thực hiện việc gọi model xử lý cập nhật trạng thái order
     *
     * @param activity instance activity
     * @param orderID  mã order
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void updateOrderStatus(Activity activity, int orderID) {
        mActivityBillModel.updateOrderStatus(activity, orderID);
    }

    /**
     * Mục đích method thực hiện việc nhận dữ liệu xử lý lấy orderDetail và gửi kết quả về view
     *
     * @param listOrderDetail Danh sách orderDetail
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getOrderDetail(List<OrderDetail> listOrderDetail) {
        mIActivityBillView.getOrderDetailSuccess(listOrderDetail);
    }

    /**
     * Mục đích method thực hiện nhận số tiền khách đưa và gửi kết quà về view nếu thành công
     *
     * @param moneyIn Số tiền người dùng đưa
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void resultTextEnter(String moneyIn) {
        mIActivityBillView.resultEnterProcessSuccess(moneyIn);
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý lấy tiền thừa và gửi về view nếu thành công
     *
     * @param amount Tiền thừa
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void resultSuccess(String amount) {
        mIActivityBillView.resultMoneyOutSuccess(amount);
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả cập nhật trạng thái order thành công và gửi kết quả về view
     *
     * @created_by cvmanh on 01/28/2021
     */
    @Override
    public void updateOrderStatusSuccess() {
        mIActivityBillView.updateOrderStatusSuccess();
    }

    /**
     * Mục đích method thực hiện việc nhận kết quả thất bại và gửi kết quả về view
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void onFailed() {
        mIActivityBillView.onFailed();
    }
}
