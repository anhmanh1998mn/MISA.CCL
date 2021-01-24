package vn.com.misa.ccl.entity;

public class OrderDetail {
    private int mOrderDetailID;

    private Order mOrder;

    private Product mProduct;

    private int mQuantity;

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
