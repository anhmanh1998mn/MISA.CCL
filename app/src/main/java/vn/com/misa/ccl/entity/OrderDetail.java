package vn.com.misa.ccl.entity;

/**
 * ‐ Mục đích Class thực hiện việc khai báo OrderDetail
 * <p>
 * ‐ {@link vn.com.misa.ccl.adapter.OrderAdapter}
 * ‐ {@link vn.com.misa.ccl.adapter.BillAdapter}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class OrderDetail {
    private int orderDetailID;

    private Order order;

    private Product product;

    private int quantity;

    private float productPriceOut;

    public float getProductPriceOut() {
        return productPriceOut;
    }

    public void setProductPriceOut(float productPriceOut) {
        this.productPriceOut = productPriceOut;
    }

    public OrderDetail(int mOrderDetailID, Order mOrder, Product mProduct, int mQuantity, float mProductPriceOut) {
        this.orderDetailID = mOrderDetailID;
        this.order = mOrder;
        this.product = mProduct;
        this.quantity = mQuantity;
        this.productPriceOut = mProductPriceOut;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderDetail(int mOrderDetailID, Order mOrder, Product mProduct, int mQuantity) {
        this.orderDetailID = mOrderDetailID;
        this.order = mOrder;
        this.product = mProduct;
        this.quantity = mQuantity;
    }
}
