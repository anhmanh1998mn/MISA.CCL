package vn.com.misa.ccl.entity;

import java.io.Serializable;

/**
 * ‐ Mục đích Class thực hiện khai báo đối tượng Unit
 * <p>
 * ‐ {@link vn.com.misa.ccl.adapter.UnitAdapter}
 * <p>
 * ‐ @created_by cvmanh on 01/13/2021
 */

public class Unit implements Serializable {

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
