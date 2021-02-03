package vn.com.misa.ccl.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.com.misa.ccl.entity.Order;
import vn.com.misa.ccl.entity.Product;

/**
 * ‐ Mục đích Class thực hiện việc định nghĩa các phương thức thao tác với server
 * <p>
 * ‐ {@link APIRetrofitClient}
 * ‐ {@link APIService}
 * <p>
 * ‐ @created_by cvmanh on 01/31/2021
 */

public interface IDataService {

    /**
     * Mục đích method thực hiện việc gọi api kiểm tra việc tồn tại tài khoản đã đăng ký
     *
     * @param userName tên tài khoản
     * @created_by cvmanh on 01/31/2021
     */
    @FormUrlEncoded
    @POST("checkRegister.php")
    Call<String> checkRegisterUser(@Field("UserName") String userName);

    /**
     * Mục đích method thực hiện việc gọi api đăng ký tài khoản
     *
     * @param userName tên tài khoản
     * @param password tên mật khẩu
     * @created_by cvmanh on 01/31/2021
     */
    @FormUrlEncoded
    @POST("doRegister.php")
    Call<String> doRegisterUser(@Field("UserName") String userName, @Field("Password") String password);

    /**
     * Mục đích method thực hiện việc gọi api đăng nhập vào ứng dụng
     *
     * @param userName tên tài khoản
     * @param password tên mật khẩu
     * @created_by cvmanh on 01/31/2021
     */
    @FormUrlEncoded
    @POST("loginApp.php")
    Call<String> doLoginApp(@Field("UserName") String userName, @Field("Password") String password);

    /**
     * Mục đích method thực hiện việc gọi api kiểm tra dữ liệu của cửa hàng theo mã cửa hàng trên server
     *
     * @param shopID mã cửa hàng
     * @created_by cvmanh on 02/03/2021
     */
    @FormUrlEncoded
    @POST("checkSyncData.php")
    Call<String> checkSyncData(@Field("ShopID") int shopID);

    /**
     * Mục đích method thực hiện việc gọi api đồng bộ dữ liệu menu cửa hàng lên server
     *
     * @param shopID mã cửa hàng
     * @created_by cvmanh on 02/03/2021
     */
    @FormUrlEncoded
    @POST("doInsertDataProduct.php")
    Call<String> doInsertDataProduct(@Field("ProductName") String productName,
                                     @Field("ProductPrice") float productPrice, @Field("ProductStatus") int productStatus,
                                     @Field("ProductImageID") int imageID, @Field("UnitID") int unitID,
                                     @Field("ColorID") int colorID, @Field("ShopID") int shopID,
                                     @Field("ProductLocalID") int productLocalID);

    /**
     * Mục đích method thực hiện gọi api thêm dữ liệu order lên server
     *
     * @param orderStatus  trạng thái order: 2: trạng thái order đã thu tiền
     * @param createdAt    ngày lập đơn hàng
     * @param tableName    tên bàn ngồi
     * @param totalPeople  số người đến
     * @param amount       tổng tiền đơn hàng
     * @param orderIDLocal mã order local
     * @param shopID       mã cửa hàng
     * @return giải thích hàm này trả về
     * @created_by cvmanh on 02/03/2021
     */
    @FormUrlEncoded
    @POST("doInsertDataOrder.php")
    Call<String> doInsertOrderDataToServer(@Field("OrderStatus") int orderStatus,
                                           @Field("CreatedAt") String createdAt,
                                           @Field("TableName") String tableName,
                                           @Field("TotalPeople") int totalPeople,
                                           @Field("Amount") float amount,
                                           @Field("OrderIDLocal") int orderIDLocal,
                                           @Field("ShopID") int shopID);

    /**
     * Mục đích method thực hiện việc gọi api thêm dữ liệu chi tiết order
     *
     * @param orderIDLocal    mã order local
     * @param shopID          mã cửa hàng
     * @param quantity        số lượng sản phẩm
     * @param productPriceOut Giá bán của sản phẩm
     * @param productLocalID  mã sản phẩm local
     * @param orderServerID   mã order server
     * @return giải thích hàm này trả về
     * @created_by cvmanh on 02/03/2021
     */
    @FormUrlEncoded
    @POST("doInsertOrderDetailToServer.php")
    Call<String> doInsertOrderDetailToServer(@Field("OrderIDLocal") int orderIDLocal,
                                             @Field("ShopID") int shopID,
                                             @Field("Quantity") int quantity,
                                             @Field("ProductPriceOut") float productPriceOut,
                                             @Field("ProductLocalID") int productLocalID,
                                             @Field("OrderServerID") float orderServerID);

}
