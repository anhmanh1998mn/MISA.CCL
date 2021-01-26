package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Unit;
import vn.com.misa.ccl.model.ActivityUnitModel;
import vn.com.misa.ccl.view.restaurantsetup.IActivityUnit;

/**
 * ‐ Mục đích Class thực hiện những công việc luân chuyển dữ liệu từ ActivityUnit và ActivityUnitModel
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.restaurantsetup.ActivityUnit}
 * ‐ {@link ActivityUnitModel}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class ActivityUnitPresenter implements IActivityUnit.IActivityUnitPresenter, ActivityUnitModel.IResultProcessActivityUnit {
    private IActivityUnit.IActivityUnitView mIActivityUnitView;

    public ActivityUnitPresenter(IActivityUnit.IActivityUnitView mIActivityUnitView) {
        this.mIActivityUnitView = mIActivityUnitView;
    }

    private ActivityUnitModel mActivityUnitModel = new ActivityUnitModel(this);

    /**
     * Mục đích method thực hiện gọi hàm xử lý lấy danh sách Unit từ model
     *
     * @param activity intansce activity
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void loadListUnit(Activity activity) {
        mActivityUnitModel.loadListUnit(activity);
    }

    /**
     * Mục đích method thực hiện nhận danh sách unit khi xử lý thành công và gửi về view
     *
     * @param listUnit Danh sách unit
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void loadListUnitSuccess(List<Unit> listUnit) {
        mIActivityUnitView.loadListUnitSuccess(listUnit);
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý thất bại và gửi về view
     *
     * @created_by cvmanh on 01/25/2021
     */
    @Override
    public void onFailed() {
        mIActivityUnitView.onFailed();
    }
}
