package vn.com.misa.ccl.view.order;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.OrderDetail;

public interface IActivityBill {

    public interface IActivityBillPresenter{
        public void getOrderDetail(Activity activity,int orderID);

        public void processCaculator(Activity activity,String resultProcess,String nameClick,float amount);
    }

    public interface IActivityBillView{
        public void getOrderDetailSuccess(List<OrderDetail> listOrderDetail);

        public void resultEnterProcessSuccess(String result);

        public void resultMoneyOutSuccess(String moneyOut);

        public void onFailed();
    }
}
