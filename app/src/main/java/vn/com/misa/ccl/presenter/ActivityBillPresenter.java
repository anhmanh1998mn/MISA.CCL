package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.model.ActivityBillModel;
import vn.com.misa.ccl.view.order.IActivityBill;

public class ActivityBillPresenter implements IActivityBill.IActivityBillPresenter, ActivityBillModel.IActivityBillModel {
    private IActivityBill.IActivityBillView mIActivityBillView;

    public ActivityBillPresenter(IActivityBill.IActivityBillView mIActivityBillView) {
        this.mIActivityBillView = mIActivityBillView;
    }

    private ActivityBillModel mActivityBillModel=new ActivityBillModel(this);
    @Override
    public void getOrderDetail(Activity activity, int orderID) {
        mActivityBillModel.getOrderDetail(activity,orderID);
    }

    @Override
    public void getOrderDetail(List<OrderDetail> listOrderDetail) {
        mIActivityBillView.getOrderDetailSuccess(listOrderDetail);
    }

    @Override
    public void onFailed() {
        mIActivityBillView.onFailed();
    }
}
