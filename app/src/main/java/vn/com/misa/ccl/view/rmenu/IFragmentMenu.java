package vn.com.misa.ccl.view.rmenu;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Product;

public interface IFragmentMenu {

    public interface IFragmentMenuPresenter{
        public void getListMenu(Activity activity);
    }

    public interface IFragmentMenuView{
        public void getListMenuSuccess(List<Product> listProduct);

        public void onFailed();
    }
}
