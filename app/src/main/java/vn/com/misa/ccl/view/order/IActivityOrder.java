package vn.com.misa.ccl.view.order;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Product;

public interface IActivityOrder {

    public interface IActivityOrderPresenter{
        public void getListMenu(Activity activity);
    }

    public interface IActivityOrderView{
        public void getListMenuSuccess(List<Product> listProduct);

        public void onFailed();
    }
}
