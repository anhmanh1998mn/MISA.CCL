package vn.com.misa.ccl.entity;

import android.app.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * ‐ Mục đích Class thực hiện việc định nghĩa ProductServer
 * <p>
 * ‐ {@link vn.com.misa.ccl.model.ActivityLoginModel}
 * <p>
 * ‐ @created_by cvmanh on 02/06/2021
 */

public class ProductServer {

    @SerializedName("MyProductID")
    @Expose
    private int productID;

    @SerializedName("ProductName")
    @Expose
    private String productName;

    @SerializedName("ProductPrice")
    @Expose
    private float productPrice;

    @SerializedName("ProductStatus")
    @Expose
    private int productStatus;

    @SerializedName("ProductImageID")
    @Expose
    private int imageID;

    @SerializedName("UnitID")
    @Expose
    private int unitID;

    @SerializedName("ColorID")
    @Expose
    private int colorID;

    @SerializedName("ProductLocalID")
    @Expose
    private int productLocalID;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public int getProductLocalID() {
        return productLocalID;
    }

    public void setProductLocalID(int productLocalID) {
        this.productLocalID = productLocalID;
    }

    public ProductServer(int productID, String productName, float productPrice, int productStatus, int imageID, int unitID, int colorID, int productLocalID) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.imageID = imageID;
        this.unitID = unitID;
        this.colorID = colorID;
        this.productLocalID = productLocalID;
    }
}
