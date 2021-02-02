package vn.com.misa.ccl.entity;

import android.app.Activity;

/**
 * ‐ Mục đích Class thực hiện những việc khai báo Setting
 * <p>
 * ‐ {@link vn.com.misa.ccl.adapter.SettingAdapter}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class Setting {
    private String settingName;

    private Integer iconSetting;

    private boolean checkLogout;

    public boolean isCheckLogout() {
        return checkLogout;
    }

    public void setCheckLogout(boolean checkLogout) {
        this.checkLogout = checkLogout;
    }

    public Setting(String settingName, Integer iconSetting, boolean checkLogout) {
        this.settingName = settingName;
        this.iconSetting = iconSetting;
        this.checkLogout = checkLogout;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public Integer getIconSetting() {
        return iconSetting;
    }

    public void setIconSetting(Integer iconSetting) {
        this.iconSetting = iconSetting;
    }

    public Setting(String settingName, Integer iconSetting) {
        this.settingName = settingName;
        this.iconSetting = iconSetting;
    }
}
