package vn.com.misa.ccl.entity;

public class Unit {

    private int mUnitID;

    private String mUnitName;

    public int getmUnitID() {
        return mUnitID;
    }

    public void setmUnitID(int mUnitID) {
        this.mUnitID = mUnitID;
    }

    public String getmUnitName() {
        return mUnitName;
    }

    public void setmUnitName(String mUnitName) {
        this.mUnitName = mUnitName;
    }

    public Unit(int mUnitID, String mUnitName) {
        this.mUnitID = mUnitID;
        this.mUnitName = mUnitName;
    }
}
