package vn.com.misa.ccl.entity;

import java.io.Serializable;

/**
 * ‐ Mục đích Class thực hiện khai báo đối tượng Color
 * <p>
 * ‐ @created_by cvmanh on 01/13/2021
 */

public class Color implements Serializable {
    private int colorID;

    private String colorName;

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public Color(int colorID, String colorName) {
        this.colorID = colorID;
        this.colorName = colorName;
    }
}
