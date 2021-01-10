package vn.com.misa.ccl.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import vn.com.misa.ccl.View.Welcome.FragmentAppOverview;

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        Fragment fragment=null;
//        switch (position){
//            case 0:{
//                fragment=new FragmentAppOverviewOne();
//                break;
//            }
//            case 1:{
//                fragment=new FragmentAppOverviewTwo();
//                break;
//            }
//            case 2:{
//                fragment=new FragmentAppOverviewThree();
//                break;
//            }
//            case 3:{
//                fragment=new FragmentAppOverviewFour();
//                break;
//            }
//            case 4:{
//                fragment=new FragmentAppoverviewFive();
//                break;
//            }
//        }
//        return fragment;
        return FragmentAppOverview.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
