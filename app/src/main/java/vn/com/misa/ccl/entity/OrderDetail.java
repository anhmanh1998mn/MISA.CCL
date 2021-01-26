package vn.com.misa.ccl.entity;

import android.app.Activity;

/**
 * ‐ Mục đích Class thực hiện việc khai báo OrderDetail
 * <p>
 * ‐ {@link vn.com.misa.ccl.adapter.OrderAdapter}
 * ‐ {@link vn.com.misa.ccl.adapter.BillAdapter}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class OrderDetail {
    private int mOrderDetailID;

    private Order mOrder;

    private Product mProduct;

    private int mQuantity;

    private float mProductPriceOut;

    public float getmProductPriceOut() {
        return mProductPriceOut;
    }

    public void setmProductPriceOut(float mProductPriceOut) {
        this.mProductPriceOut = mProductPriceOut;
    }

    public OrderDetail(int mOrderDetailID, Order mOrder, Product mProduct, int mQuantity, float mProductPriceOut) {
        this.mOrderDetailID = mOrderDetailID;
        this.mOrder = mOrder;
        this.mProduct = mProduct;
        this.mQuantity = mQuantity;
        this.mProductPriceOut = mProductPriceOut;
    }

    public int getmOrderDetailID() {
        return mOrderDetailID;
    }

    public void setmOrderDetailID(int mOrderDetailID) {
        this.mOrderDetailID = mOrderDetailID;
    }

    public Order getmOrder() {
        return mOrder;
    }

    public void setmOrder(Order mOrder) {
        this.mOrder = mOrder;
    }

    public Product getmProduct() {
        return mProduct;
    }

    public void setmProduct(Product mProduct) {
        this.mProduct = mProduct;
    }

    public int getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public OrderDetail(int mOrderDetailID, Order mOrder, Product mProduct, int mQuantity) {
        this.mOrderDetailID = mOrderDetailID;
        this.mOrder = mOrder;
        this.mProduct = mProduct;
        this.mQuantity = mQuantity;
    }
}
