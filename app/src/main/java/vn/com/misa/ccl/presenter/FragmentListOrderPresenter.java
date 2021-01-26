package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.model.FragmentListOrderModel;
import vn.com.misa.ccl.view.order.IFragmentListOrder;

/**
 * ‐ Mục đích Class thực hiện những công việc luân chuyển dữ liệu giữa FragmentListOrder và FragmentListOrderModel
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.order.FragmentListOrder}
 * ‐ {@link FragmentListOrderModel}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class FragmentListOrderPresenter implements IFragmentListOrder.IFragmentListOrderPresenter, FragmentListOrderModel.IFragmentListOrderModel {

    private IFragmentListOrder.IFragmentListOrderView mIFragmentListOrderView;

    public FragmentListOrderPresenter(IFragmentListOrder.IFragmentListOrderView mIFragmentListOrderView) {
        this.mIFragmentListOrderView = mIFragmentListOrderView;
    }

    private FragmentListOrderModel mFragmentListOrderModel = new FragmentListOrderModel(this);

    /**
     * Mục đích method thực hiện gọi hàm xử lý lấy danh sách order từ model
     *
     * @param activity instance activity
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getListOrder(Activity activity) {
        mFragmentListOrderModel.getListOrder(activity);
    }

    /**
     * Mục đích method thực hiện việc gọi hàm xử lý xóa order theo mã order từ model
     *
     * @param activity instance activity
     * @param orderID  mã order
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void removeItemOrder(Activity activity, int orderID) {
        mFragmentListOrderModel.removeItemOrder(activity, orderID);
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý thành công và gửi về view
     *
     * @param listOrderDetail Danh sách chi tiết order
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getListOrderSuccess(List<OrderDetail> listOrderDetail) {
        mIFragmentListOrderView.getListOrderSuccess(listOrderDetail);
    }

    /**
     * Mục đích method thực hiện nhận kết quả xóa order thành công và gửi về view
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void removeOrderSuccess() {
        mIFragmentListOrderView.removeOrderSuccess();
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý thất bại và gửi về view
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void onFailed() {
        mIFragmentListOrderView.onFailed();
    }
}
