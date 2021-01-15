package vn.com.misa.ccl.entity;

public class Category {

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
