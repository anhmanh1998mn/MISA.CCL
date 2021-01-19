package vn.com.misa.ccl.view.restaurantsetup;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Category;

public interface IActivityRestaurantType {

    public interface IFragmentRestaurantTypePresenter {
        public void loadListShopType(Activity activity);
    }

    public interface IActivityRestaurantTypeView {
        public void loadListRestaurantTypeSuccess(List<Category> listCategory);

        public void onFailed();
    }
}
