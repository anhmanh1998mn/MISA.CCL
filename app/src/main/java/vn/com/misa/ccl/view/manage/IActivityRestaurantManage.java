package vn.com.misa.ccl.view.manage;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Setting;

public interface IActivityRestaurantManage {
    public interface IActivityManagePresenter{
        public void getListSetting(Activity activity);
    }

    public interface IActivityManageView{
        public void getListSettingSuccess(List<Setting> listSetting);

        public void onFailed();
    }
}
