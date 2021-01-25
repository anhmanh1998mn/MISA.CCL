package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Setting;
import vn.com.misa.ccl.model.ActivityRestaurantManageModel;
import vn.com.misa.ccl.view.manage.IActivityRestaurantManage;

/**
‐ Mục đích Class thực hiện những công việc luân chuyển dữ liệu giữa ActivityRestaurantMange và ActivityRestaurantMangeModel
*
‐ {@link vn.com.misa.ccl.view.manage.ActivityRestaurantManage}
‐ {@link ActivityRestaurantManageModel}
*
‐ @created_by cvmanh on 01/25/2021
*/

public class ActivityRestaurantManagePresenter implements IActivityRestaurantManage.IActivityManagePresenter, ActivityRestaurantManageModel.IActivityManageModel {

    private IActivityRestaurantManage.IActivityManageView mManageView;

    public ActivityRestaurantManagePresenter(IActivityRestaurantManage.IActivityManageView mManageView) {
        this.mManageView = mManageView;
    }

    private ActivityRestaurantManageModel mManageModel=new ActivityRestaurantManageModel(this);

    /**
     * Mục đích method thực hiện gọi hàm xử lý lấy danh sách setting từ model
     *
     * @param activity instance activity
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getListSetting(Activity activity) {
        mManageModel.getListSetting(activity);
    }

    /**
     * Mục đích method thực hiện nhận danh sách setting khi xử lý thành công và gửi về view
     *
     * @param listSetting Danh sách setting
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void getListSettingSucess(List<Setting> listSetting) {
        mManageView.getListSettingSuccess(listSetting);
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý thất bại và gửi về view
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void onFailed() {
        mManageView.onFailed();
    }
}
