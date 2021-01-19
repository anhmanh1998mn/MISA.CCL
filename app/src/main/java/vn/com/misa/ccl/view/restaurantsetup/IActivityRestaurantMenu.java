package vn.com.misa.ccl.view.restaurantsetup;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.ProductCategory;

public interface IActivityRestaurantMenu {
    public interface IActivityRestaurantMenuPresenter {
        public void loadListProduct(Activity activity,int categoryID);
    }

    public interface IFragmentRestaurantMenuView {
        public void loadListProductSuccess(List<ProductCategory> listProductCategory);

        public void onFailed();
    }
}
