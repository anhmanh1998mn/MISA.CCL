package vn.com.misa.ccl.View.Welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import vn.com.misa.ccl.R;

public class FragmentAppOverview extends Fragment {

    private ConstraintLayout clAppOverview;

    public FragmentAppOverview(){

    }

    public static FragmentAppOverview newInstance(int currentPager){
        FragmentAppOverview fragmentAppOverview=new FragmentAppOverview();
        Bundle bundle=new Bundle();
        bundle.putInt("CURRENT_PAGER",currentPager);
        fragmentAppOverview.setArguments(bundle);
        return fragmentAppOverview;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_app_overview,container,false);

        initView(view);

        showScreenAppOverview();

        return view;
    }

    private void showScreenAppOverview() {
        switch (getArguments().getInt("CURRENT_PAGER")){
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
    }

    private void initView(View view) {
        clAppOverview=view.findViewById(R.id.clAppOverview);

    }
}
