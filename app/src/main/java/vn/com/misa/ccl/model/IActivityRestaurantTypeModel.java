package vn.com.misa.ccl.model;

import java.util.List;

import vn.com.misa.ccl.entity.Category;

public interface IActivityRestaurantTypeModel {

    public void loadListRestaurantTypeSuccess(List<Category> listCategory);

    public void onFailed();
}
