package vn.com.misa.ccl.entity;

public class Product {

    private int mProductID;

    private String mProductName;

    private float mProductPrice;

    private int mProductStatus;

    private ProductImage mProductImage;

    private Unit mUnit;

    public int getmProductID() {
        return mProductID;
    }

    public void setmProductID(int mProductID) {
        this.mProductID = mProductID;
    }

    public String getmProductName() {
        return mProductName;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public float getmProductPrice() {
        return mProductPrice;
    }

    public void setmProductPrice(float mProductPrice) {
        this.mProductPrice = mProductPrice;
    }

    public int getmProductStatus() {
        return mProductStatus;
    }

    public void setmProductStatus(int mProductStatus) {
        this.mProductStatus = mProductStatus;
    }

    public ProductImage getmProductImage() {
        return mProductImage;
    }

    public void setmProductImage(ProductImage mProductImage) {
        this.mProductImage = mProductImage;
    }

    public Unit getmUnit() {
        return mUnit;
    }

    public void setmUnit(Unit mUnit) {
        this.mUnit = mUnit;
    }

    public Product(int mProductID, String mProductName, float mProductPrice, int mProductStatus, ProductImage mProductImage, Unit mUnit) {
        this.mProductID = mProductID;
        this.mProductName = mProductName;
        this.mProductPrice = mProductPrice;
        this.mProductStatus = mProductStatus;
        this.mProductImage = mProductImage;
        this.mUnit = mUnit;
    }
}
