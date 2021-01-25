package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.model.FragmentListOrderModel;
import vn.com.misa.ccl.view.order.IFragmentListOrder;

public class FragmentListOrderPresenter implements IFragmentListOrder.IFragmentListOrderPresenter, FragmentListOrderModel.IFragmentListOrderModel {

    private IFragmentListOrder.IFragmentListOrderView mIFragmentListOrderView;

    public FragmentListOrderPresenter(IFragmentListOrder.IFragmentListOrderView mIFragmentListOrderView) {
        this.mIFragmentListOrderView = mIFragmentListOrderView;
    }

    private FragmentListOrderModel mFragmentListOrderModel=new FragmentListOrderModel(this);
    @Override

    public void getListOrder(Activity activity) {
        mFragmentListOrderModel.getListOrder(activity);
    }

    @Override
    public void removeItemOrder(Activity activity, int orderID) {
        mFragmentListOrderModel.removeItemOrder(activity,orderID);
    }

    @Override
    public void getListOrderSuccess(List<OrderDetail> listOrderDetail) {
        mIFragmentListOrderView.getListOrderSuccess(listOrderDetail);
    }

    @Override
    public void removeOrderSuccess() {
        mIFragmentListOrderView.removeOrderSuccess();
    }

    @Override
    public void onFailed() {
        mIFragmentListOrderView.onFailed();
    }
}
