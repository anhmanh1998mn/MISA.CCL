package vn.com.misa.ccl.entity;

import java.io.Serializable;

/**
 * ‐ Mục đích Class thực hiện khai báo đối tượng ProductImage
 * <p>
 * ‐ @created_by cvmanh on 01/13/2021
 */

public class ProductImage implements Serializable {

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
