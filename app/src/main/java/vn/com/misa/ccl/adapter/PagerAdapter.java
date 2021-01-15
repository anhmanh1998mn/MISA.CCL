package vn.com.misa.ccl.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import vn.com.misa.ccl.view.welcome.FragmentAppOverview;

/**
‐ Mục đích Class thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
*
‐ {@link vn.com.misa.ccl.MainActivity#initViewPager}
*
‐ @created_by cvmanh on 01/10/2021
*/

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mTotalFragment=5;

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    /**
     * Mục đích method thực hiện việc trả về fragment tương ứng
     *
     * @param position giá trị fragment
     *
     * @return trả về fragment tương ứng
     *
     * @created_by cvmanh on 01/10/2021
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FragmentAppOverview.newInstance(position);
    }

    /**
     * Mục đích method chỉ định số lượng fragment được tạo ra
     *
     *
     * @return trả về số lượng fragment
     *
     * @created_by cvmanh on 01/10/2021
     */
    @Override
    public int getCount() {
        return mTotalFragment;
    }
}
