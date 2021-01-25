package vn.com.misa.ccl.view.order;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;

public interface IActivityBill {

    public interface IActivityBillPresenter{
        public void getOrderDetail(Activity activity,int orderID);
    }

    public interface IActivityBillView{
        public void getOrderDetailSuccess(List<OrderDetail> listOrderDetail);

        public void onFailed();
    }
}
