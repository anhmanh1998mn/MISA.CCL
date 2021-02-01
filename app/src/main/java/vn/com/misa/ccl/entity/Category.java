package vn.com.misa.ccl.entity;

import java.io.Serializable;

/**
 * ‐ Mục đích Class thực hiện khai báo Category
 * <p>
 * ‐ {@link vn.com.misa.ccl.adapter.CategoryAdapter}
 * <p>
 * ‐ @created_by cvmanh on 01/13/2021
 */

public class Category implements Serializable {

    private int categoryID;

    private String categoryName;

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(int mCategoryID, String mCategoryName) {
        this.categoryID = mCategoryID;
        this.categoryName = mCategoryName;
    }
}
