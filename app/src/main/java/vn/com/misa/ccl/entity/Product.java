package vn.com.misa.ccl.entity;

import java.io.Serializable;

/**
 * ‐ Mục đích Class thực hiện khai báo Product
 * <p>
 * ‐ {@link ProductCategory}
 * <p>
 * ‐ @created_by cvmanh on 01/13/2021
 */

public class Product implements Serializable {

    private int productID;

    private String productName;

    private float productPrice;

    private int productStatus;

    private ProductImage productImage;

    private Unit unit;

    private Color color;

    private int quantity;

    public Product(int mProductID, String mProductName, Unit mUnit) {
        this.productID = mProductID;
        this.productName = mProductName;
        this.unit = mUnit;
    }

    public Product(int mProductID, String mProductName, float mProductPrice, int mProductStatus) {
        this.productID = mProductID;
        this.productName = mProductName;
        this.productPrice = mProductPrice;
        this.productStatus = mProductStatus;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product(int mProductID, String mProductName, float mProductPrice, int mProductStatus, ProductImage mProductImage, Unit mUnit, Color mColor, int quantity) {
        this.productID = mProductID;
        this.productName = mProductName;
        this.productPrice = mProductPrice;
        this.productStatus = mProductStatus;
        this.productImage = mProductImage;
        this.unit = mUnit;
        this.color = mColor;
        this.quantity = quantity;
    }

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

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Product(int mProductID, String mProductName, float mProductPrice, int mProductStatus, ProductImage mProductImage, Unit mUnit, Color mColor) {
        this.productID = mProductID;
        this.productName = mProductName;
        this.productPrice = mProductPrice;
        this.productStatus = mProductStatus;
        this.productImage = mProductImage;
        this.unit = mUnit;
        this.color = mColor;
    }
}
