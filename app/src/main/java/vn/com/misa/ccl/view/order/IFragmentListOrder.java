package vn.com.misa.ccl.view.order;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;

public interface IFragmentListOrder {

    public interface IFragmentListOrderPresenter{
        public void getListOrder(Activity activity);

        public void removeItemOrder(Activity activity,int orderID);
    }

    public interface IFragmentListOrderView{
        public void getListOrderSuccess(List<OrderDetail> listOrderDetail);

        public void removeOrderSuccess();

        public void onFailed();
    }
}
