package vn.com.misa.ccl.view.restaurantsetup;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Color;
import vn.com.misa.ccl.entity.ProductImage;

public interface IActivityFoodUpdate {
    public interface IActivityFoodUpdatePresenter{

        public void loadListColor(Activity activity);

        public void loadProductImage(Activity activity);

        public void loadCaculating();
    }

    public interface IActivityFoodUpdateView{

        public void loadListColorSuccess(List<Color> listColor);

        public void loadListProductImageSuccess(List<ProductImage> listProductImage);

        public void loadCaculatingSuccess(List<String> listCaculate);

        public void onFailed();
    }
}
