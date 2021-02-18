package vn.com.misa.ccl.view.restaurantsetup;

import android.app.Activity;
import android.widget.TextView;

import java.util.List;

import vn.com.misa.ccl.entity.Color;
import vn.com.misa.ccl.entity.ProductImage;

public interface IActivityFoodUpdate {
    public interface IActivityFoodUpdatePresenter {

        public void loadListColor(Activity activity);

        public void loadProductImage(Activity activity);

        public void loadCaculating(Activity activity);

        public void processCaculator(Activity activity, String tvNumberEnter, String nameClick);

        public void removeItemProduct(int productID);

        public void updateItemProduct(int productId, String productName, float productPrice, int imageID, int unitID,
                                      int colorID, byte[] imageSelect, String keyColor);

        public void deleteItemProductMenu(Activity activity, int productID);

        public void updateItemProductMenu(Activity activity, int productId, String productName, float productPrice, int imageID, int unitID,
                                          int colorID);

        public void addNewFoodMenu(Activity activity, String productName, float productPrice, int imageID, int unitID,
                                   int colorID);

        public void stopSellProduct(Activity activity, int productID);

        public void deleteItemProductMenuOnServer(int productIDLocal, int shopID);

        public void updateItemProductOnServer(String productName, float productPrice, int imageID, int unitID,
                                              int colorID, int shopID, int productId);
    }

    public interface IActivityFoodUpdateView {

        public void loadListColorSuccess(List<Color> listColor);

        public void loadListProductImageSuccess(List<ProductImage> listProductImage);

        public void loadCaculatingSuccess(List<String> listCaculate);

        public void processCaculatorSuccess(String resulText);

        public void processEnterSuccess(String resultText);

        public void removeProductItemSuccess();

        public void updateItemProductSuccess();

        public void deleteItemProductMenuSuccess();

        public void updateItemProductMenuSuccess();

        public void addNewFoodMenuSuccess();

        public void onFailed();

        public void onDeleteProductFailed();
    }
}
