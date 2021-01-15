package vn.com.misa.ccl.entity;

public class ProductCategory {

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
