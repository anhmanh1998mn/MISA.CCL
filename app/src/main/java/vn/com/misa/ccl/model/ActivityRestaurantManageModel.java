package vn.com.misa.ccl.model;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.Setting;

/**
 * ‐ Mục đích Class thực hiện việc xử lý các công vệc của ActivityRestaurantManage
 * <p>
 * ‐ {@link vn.com.misa.ccl.presenter.ActivityRestaurantManagePresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class ActivityRestaurantManageModel {

    private IActivityManageModel mIManageModel;

    public ActivityRestaurantManageModel(IActivityManageModel mIManageModel) {
        this.mIManageModel = mIManageModel;
    }

    private List<Setting> mListSetting;

    /**
     * Mục đích method thực hiện việc tạo dữ liệu danh sách setting và gửi dữ liệu về presenter
     *
     * @param activity intance activity
     * @created_by cvmanh on 01/25/2021
     */
    public void getListSetting(Activity activity) {
        try {
            mListSetting = new ArrayList<>();
            mListSetting.add(new Setting(activity.getResources().getString(R.string.sell), R.drawable.ic_coffee_cup_on_hand, false));
            mListSetting.add(new Setting(activity.getResources().getString(R.string.menu), R.drawable.ic_notes, false));
            mListSetting.add(new Setting(activity.getResources().getString(R.string.chart), R.drawable.ic_bar_graph, false));
            mListSetting.add(new Setting(activity.getResources().getString(R.string.setting), R.drawable.ic_computing_cloud, false));
            mListSetting.add(new Setting(activity.getResources().getString(R.string.synchronize), R.drawable.ic_computing_cloud, false));
            mListSetting.add(new Setting(activity.getResources().getString(R.string.setting), R.drawable.ic_settings, false));
            mListSetting.add(new Setting(activity.getResources().getString(R.string.support), R.drawable.ic_share, false));
            mListSetting.add(new Setting(activity.getResources().getString(R.string.notification), R.drawable.ic_notification, false));
            mListSetting.add(new Setting(activity.getResources().getString(R.string.introduce), R.drawable.ic_share, false));
            mListSetting.add(new Setting(activity.getResources().getString(R.string.rate), R.drawable.ic_star, false));
            mListSetting.add(new Setting(activity.getResources().getString(R.string.product_infomation), R.drawable.ic_rounded_info_button, false));
            if (mListSetting != null) {
                mIManageModel.getListSettingSucess(mListSetting);
                return;
            }
            mIManageModel.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IActivityManageModel {
        public void getListSettingSucess(List<Setting> listSetting);

        public void onFailed();
    }
}
