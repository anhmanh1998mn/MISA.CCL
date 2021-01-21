package vn.com.misa.ccl.view.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.util.AndroidDeviceHelper;

public class FragmentListOrder extends Fragment {

    private ImageView ivLogo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list_order,container,false);

        initView(view);


        return view;
    }

    private void initView(View view) {
        ivLogo=view.findViewById(R.id.ivLogo);
    }

}
