package vn.com.misa.ccl.entity;

import java.io.Serializable;

/**
 * ‐ Mục đích Class thực hiện khai báo ProductCategory
 * <p>
 * ‐ {@link vn.com.misa.ccl.adapter.ProductCategoryAdapter}
 * <p>
 * ‐ @created_by cvmanh on 01/13/2021
 */

public class ProductCategory implements Serializable {

    private int mProductCategoryID;

    private Product mProduct;

    private Category mCategory;

    public int getmProductCategoryID() {
        return mProductCategoryID;
    }

    public void setmProductCategoryID(int mProductCategoryID) {
        this.mProductCategoryID = mProductCategoryID;
    }

    public Product getmProduct() {
        return mProduct;
    }

    public void setmProduct(Product mProduct) {
        this.mProduct = mProduct;
    }

    public Category getmCategory() {
        return mCategory;
    }

    public void setmCategory(Category mCategory) {
        this.mCategory = mCategory;
    }

    public ProductCategory(int mProductCategoryID, Product mProduct, Category mCategory) {
        this.mProductCategoryID = mProductCategoryID;
        this.mProduct = mProduct;
        this.mCategory = mCategory;
    }
}
