package vn.com.misa.ccl.entity;

import android.app.Activity;

/**
‐ Mục đích Class thực hiện những việc khai báo Setting
*
‐ {@link vn.com.misa.ccl.adapter.SettingAdapter}
*
‐ @created_by cvmanh on 01/25/2021
*/

public class Setting {
    private String settingName;

    private Integer iconSetting;

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
