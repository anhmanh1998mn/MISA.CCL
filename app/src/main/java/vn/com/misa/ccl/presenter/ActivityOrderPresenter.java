package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.model.ActivityOrderModel;
import vn.com.misa.ccl.view.order.IActivityOrder;

public class ActivityOrderPresenter implements IActivityOrder.IActivityOrderPresenter, ActivityOrderModel.IActivityOrderModel {

    private IActivityOrder.IActivityOrderView mIActivityOrderView;

    public ActivityOrderPresenter(IActivityOrder.IActivityOrderView mIActivityOrderView) {
        this.mIActivityOrderView = mIActivityOrderView;
    }

    private ActivityOrderModel mActivityOrderModel=new ActivityOrderModel(this);

    @Override
    public void getListMenu(Activity activity) {
        mActivityOrderModel.getListMenu(activity);
    }

    @Override
    public void getResultCaculate(String textInput,String numberEnter) {
        mActivityOrderModel.getResultCaculate(textInput,numberEnter);
    }


    @Override
    public void getListMenuSuccess(List<Product> listMenu) {
        mIActivityOrderView.getListMenuSuccess(listMenu);
    }

    @Override
    public void resultProcessCaculateSuccess(String result) {
        mIActivityOrderView.getResultCaculateSuccess(result);
    }

    @Override
    public void onFailed() {
        mIActivityOrderView.onFailed();
    }
}
