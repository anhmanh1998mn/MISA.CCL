package vn.com.misa.ccl.entity;

import android.app.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * ‐ Mục đích Class thực hiện định nghĩa OrderDetailServer
 * <p>
 * ‐ {@link vn.com.misa.ccl.model.ActivityLoginModel}
 * <p>
 * ‐ @created_by cvmanh on 02/06/2021
 */

public class OrderDetailServer {

    @SerializedName("Quantity")
    @Expose
    private int quantity;

    @SerializedName("ProductPriceOut")
    @Expose
    private float productPriceOut;

    @SerializedName("OrderIDLocal")
    @Expose
    private int orderIDLocal;

    @SerializedName("ProductLocalID")
    @Expose
    private int productLocalID;

    @SerializedName("OrderDetailIDLocal")
    @Expose
    private int orderDetailIDLocal;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getProductPriceOut() {
        return productPriceOut;
    }

    public void setProductPriceOut(float productPriceOut) {
        this.productPriceOut = productPriceOut;
    }

    public int getOrderIDLocal() {
        return orderIDLocal;
    }

    public void setOrderIDLocal(int orderIDLocal) {
        this.orderIDLocal = orderIDLocal;
    }

    public int getProductLocalID() {
        return productLocalID;
    }

    public void setProductLocalID(int productLocalID) {
        this.productLocalID = productLocalID;
    }

    public int getOrderDetailIDLocal() {
        return orderDetailIDLocal;
    }

    public void setOrderDetailIDLocal(int orderDetailIDLocal) {
        this.orderDetailIDLocal = orderDetailIDLocal;
    }

    public OrderDetailServer(int quantity, float productPriceOut, int orderIDLocal, int productLocalID, int orderDetailIDLocal) {
        this.quantity = quantity;
        this.productPriceOut = productPriceOut;
        this.orderIDLocal = orderIDLocal;
        this.productLocalID = productLocalID;
        this.orderDetailIDLocal = orderDetailIDLocal;
    }
}
