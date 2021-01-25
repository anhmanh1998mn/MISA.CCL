package vn.com.misa.ccl.view.order;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Product;

public interface IActivityOrder {

    public interface IActivityOrderPresenter{
        public void getListMenu(Activity activity);

        public void getResultCaculate(String textInput,String numberEnter);

        public void addNewOrder(Activity activity,List<Product> listProduct,String tableName,String totalPeople,float amount,int typeClick);

    }

    public interface IActivityOrderView{
        public void getListMenuSuccess(List<Product> listProduct);

        public void getResultCaculateSuccess(String result);

        public void addNewOrderSuccess();

        public void addNewOrderTwoSuccess(int orderID);

        public void onFailed();
    }
}
