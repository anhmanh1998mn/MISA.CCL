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

    private int productCategoryID;

    private Product product;

    private Category category;

    public int getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(int productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductCategory(int mProductCategoryID, Product mProduct, Category mCategory) {
        this.productCategoryID = mProductCategoryID;
        this.product = mProduct;
        this.category = mCategory;
    }
}
