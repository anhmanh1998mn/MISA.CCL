package vn.com.misa.ccl.entity;

public class ProductImage {

    private int mProductImageID;

    private byte[] mImage;

    public int getmProductImageID() {
        return mProductImageID;
    }

    public void setmProductImageID(int mProductImageID) {
        this.mProductImageID = mProductImageID;
    }

    public byte[] getmImage() {
        return mImage;
    }

    public void setmImage(byte[] mImage) {
        this.mImage = mImage;
    }

    public ProductImage(int mProductImageID, byte[] mImage) {
        this.mProductImageID = mProductImageID;
        this.mImage = mImage;
    }
}
