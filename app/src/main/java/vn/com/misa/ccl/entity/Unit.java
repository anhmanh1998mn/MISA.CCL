package vn.com.misa.ccl.entity;

import java.io.Serializable;

/**
 * ‐ Mục đích Class thực hiện khai báo Unit
 * <p>
 * ‐ {@link vn.com.misa.ccl.adapter.UnitAdapter}
 * <p>
 * ‐ @created_by cvmanh on 01/13/2021
 */

public class Unit implements Serializable {

    private int unitID;

    private String unitName;

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Unit(int mUnitID, String mUnitName) {
        this.unitID = mUnitID;
        this.unitName = mUnitName;
    }
}
