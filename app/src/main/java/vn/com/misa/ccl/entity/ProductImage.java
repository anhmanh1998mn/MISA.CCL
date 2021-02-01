package vn.com.misa.ccl.entity;

import java.io.Serializable;

/**
 * ‐ Mục đích Class thực hiện khai báo ProductImage
 * <p>
 * ‐ @created_by cvmanh on 01/13/2021
 */

public class ProductImage implements Serializable {

    private int productImageID;

    private byte[] image;

    public int getProductImageID() {
        return productImageID;
    }

    public void setProductImageID(int productImageID) {
        this.productImageID = productImageID;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public ProductImage(int mProductImageID, byte[] mImage) {
        this.productImageID = mProductImageID;
        this.image = mImage;
    }
}
