package vn.com.misa.ccl.presenter;

import java.util.List;

import vn.com.misa.ccl.entity.Setting;
import vn.com.misa.ccl.model.ActivityRestaurantManageModel;
import vn.com.misa.ccl.view.manage.IActivityRestaurantManage;

public class ActivityRestaurantManagePresenter implements IActivityRestaurantManage.IActivityManagePresenter, ActivityRestaurantManageModel.IActivityManageModel {

    private IActivityRestaurantManage.IActivityManageView mManageView;

    public ActivityRestaurantManagePresenter(IActivityRestaurantManage.IActivityManageView mManageView) {
        this.mManageView = mManageView;
    }

    private ActivityRestaurantManageModel mManageModel=new ActivityRestaurantManageModel(this);

    @Override
    public void getListSetting() {
        mManageModel.getListSetting();
    }

    @Override
    public void getListSettingSucess(List<Setting> listSetting) {
        mManageView.getListSettingSuccess(listSetting);
    }

    @Override
    public void onFailed() {
        mManageView.onFailed();
    }
}
