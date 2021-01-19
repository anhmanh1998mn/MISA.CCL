package vn.com.misa.ccl.entity;

import java.io.Serializable;

/**
 * ‐ Mục đích Class thực hiện khai báo đối tượng Category
 * <p>
 * ‐ {@link vn.com.misa.ccl.adapter.CategoryAdapter}
 * <p>
 * ‐ @created_by cvmanh on 01/13/2021
 */

public class Category implements Serializable {

    private int mCategoryID;

    private String mCategoryName;

    public int getmCategoryID() {
        return mCategoryID;
    }

    public void setmCategoryID(int mCategoryID) {
        this.mCategoryID = mCategoryID;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void setmCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public Category(int mCategoryID, String mCategoryName) {
        this.mCategoryID = mCategoryID;
        this.mCategoryName = mCategoryName;
    }
}
