package vn.com.misa.ccl.model;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.Setting;

public class ActivityRestaurantManageModel {

    private IActivityManageModel mIManageModel;

    public ActivityRestaurantManageModel(IActivityManageModel mIManageModel) {
        this.mIManageModel = mIManageModel;
    }

    private List<Setting> mListSetting;

    public void getListSetting(){
        mListSetting=new ArrayList<>();
        mListSetting.add(new Setting("Bán hàng", R.drawable.ic_circle_tick));
        mListSetting.add(new Setting("Thực đơn", R.drawable.ic_circle_tick));
        mListSetting.add(new Setting("Báo cáo", R.drawable.ic_circle_tick));
        mListSetting.add(new Setting("Thiết lập", R.drawable.ic_circle_tick));
        mListSetting.add(new Setting("Đồng bộ dữ liệu", R.drawable.ic_circle_tick));
        mListSetting.add(new Setting("Thiết lập", R.drawable.ic_circle_tick));
        mListSetting.add(new Setting("Trợ giúp", R.drawable.ic_circle_tick));
        mListSetting.add(new Setting("Thông báo", R.drawable.ic_circle_tick));
        mListSetting.add(new Setting("Giới thiệu cho bạn", R.drawable.ic_circle_tick));
        mListSetting.add(new Setting("Đánh giá ứng dụng", R.drawable.ic_circle_tick));
        mListSetting.add(new Setting("Thông tin sản phẩm", R.drawable.ic_circle_tick));
        if (mListSetting!=null){
            mIManageModel.getListSettingSucess(mListSetting);
            return;
        }
        mIManageModel.onFailed();
    }

    public interface IActivityManageModel{
        public void getListSettingSucess(List<Setting> listSetting);

        public void onFailed();
    }
}
