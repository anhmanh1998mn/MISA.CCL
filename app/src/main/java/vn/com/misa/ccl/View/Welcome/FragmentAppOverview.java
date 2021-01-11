package vn.com.misa.ccl.View.Welcome;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import vn.com.misa.ccl.R;

/**
‐ Mục đích Class thực hiện những việc hiển thị thông tin giới thiệu vè ứng dụng
*
‐ {@link vn.com.misa.ccl.adapter.PagerAdapter}
*
‐ @created_by cvmanh on 01/10/2021
*/

public class FragmentAppOverview extends Fragment {

    private static String sCurrentPager;

    private ConstraintLayout clAppOverview;

    public FragmentAppOverview(){

    }

    /**
     * Mục đích method thực hiện truyền current pager hiện tại đang hiển thị cho fragment
     *
     * @param currentPager fragment đang hiển thị
     * @see #showScreenAppOverview()
     *
     * @return trả về fragment
     *
     * @created_by cvmanh on 01/10/2021
     */
    public static FragmentAppOverview newInstance(int currentPager){
        try {
            FragmentAppOverview fragmentAppOverview=new FragmentAppOverview();
            Bundle bundle=new Bundle();
            bundle.putInt(sCurrentPager,currentPager);
            fragmentAppOverview.setArguments(bundle);
            return fragmentAppOverview;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_app_overview,container,false);

        initView(view);

        showScreenAppOverview();

        return view;
    }

    /**
     * Mục đích method thực hiện việc set backgound cho fragment theo giá trị nhận được
     *
     * @created_by cvmanh on 01/10/2021
     */
    private void showScreenAppOverview() {
        try {
            switch (getArguments().getInt(sCurrentPager)){
                case 0:{
                    clAppOverview.setBackground(getResources().getDrawable(R.drawable.ic_tab_one));
                    break;
                }
                case 1:{
                    clAppOverview.setBackground(getResources().getDrawable(R.drawable.ic_tab_two));
                    break;
                }
                case 2:{
                    clAppOverview.setBackground(getResources().getDrawable(R.drawable.ic_tab_three));
                    break;
                }
                case 3:{
                    clAppOverview.setBackground(getResources().getDrawable(R.drawable.ic_tab_four));
                    break;
                }
                case 4:{
                    clAppOverview.setBackground(getResources().getDrawable(R.drawable.ic_tab_five));
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/10/2021
     */
    private void initView(View view) {
        try {
            clAppOverview=view.findViewById(R.id.clAppOverview);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
