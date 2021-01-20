package vn.com.misa.ccl.view.restaurantsetup;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Unit;

public interface IActivityUnit {

    public interface IActivityUnitPresenter{

        public void loadListUnit(Activity activity);
    }

    public interface IActivityUnitView{

        public void loadListUnitSuccess(List<Unit> listUnit);

        public void onFailed();
    }
}
