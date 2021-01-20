package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Unit;
import vn.com.misa.ccl.model.ActivityUnitModel;
import vn.com.misa.ccl.view.restaurantsetup.IActivityUnit;

public class ActivityUnitPresenter implements IActivityUnit.IActivityUnitPresenter, ActivityUnitModel.IResultProcessActivityUnit {
    private IActivityUnit.IActivityUnitView mIActivityUnitView;

    public ActivityUnitPresenter(IActivityUnit.IActivityUnitView mIActivityUnitView) {
        this.mIActivityUnitView = mIActivityUnitView;
    }

    private ActivityUnitModel mActivityUnitModel=new ActivityUnitModel(this);

    @Override
    public void loadListUnit(Activity activity) {
        mActivityUnitModel.loadListUnit(activity);
    }

    @Override
    public void loadListUnitSuccess(List<Unit> listUnit) {
        mIActivityUnitView.loadListUnitSuccess(listUnit);
    }

    @Override
    public void onFailed() {
        mIActivityUnitView.onFailed();
    }
}
