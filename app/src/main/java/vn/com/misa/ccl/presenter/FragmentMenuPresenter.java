package vn.com.misa.ccl.presenter;

import android.app.Activity;

import java.util.List;

import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.model.FragmentMenuModel;
import vn.com.misa.ccl.view.rmenu.IFragmentMenu;

/**
 * ‐ Mục đích Class thực hiện việc luân chuyển dữ liệu giữa FragmentMenuModel và FragmentMenu
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.rmenu.FragmentMenu}
 * ‐ {@link FragmentMenuModel}
 * <p>
 * ‐ @created_by cvmanh on 01/27/2021
 */

public class FragmentMenuPresenter implements IFragmentMenu.IFragmentMenuPresenter, FragmentMenuModel.IFragmentMenuModel {

    private IFragmentMenu.IFragmentMenuView mIFragmentMenuView;

    public FragmentMenuPresenter(IFragmentMenu.IFragmentMenuView mIFragmentMenuView) {
        this.mIFragmentMenuView = mIFragmentMenuView;
    }

    private FragmentMenuModel mFragmentMenuModel = new FragmentMenuModel(this);

    /**
     * Mục đích method thực hiện việc gọi model xử lý lấy danh sách menu
     *
     * @param activity instance activity
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void getListMenu(Activity activity) {
        mFragmentMenuModel.getListMenu(activity);
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý lấy danh sách menu thành công và gửi kết quả về view
     *
     * @param listProduct Danh sách product
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void getListMenuSuccess(List<Product> listProduct) {
        mIFragmentMenuView.getListMenuSuccess(listProduct);
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý thất bại và gửi kết quả về view
     *
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void onFailed() {
        mIFragmentMenuView.onFailed();
    }
}
