package vn.com.misa.ccl.entity;

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
