package vn.com.misa.ccl.View.ShopSetup;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import vn.com.misa.ccl.R;

/**
‐ Mục đích Class thực hiện việc chọn loại hàng mà cửa hàng bán
*
 * {@link ActivityShopSetup#onCreate}
‐ {@link ActivityShopSetup#onClick}
*
‐ @created_by cvmanh on 01/11/2021
*/

public class FragmentShopType extends Fragment {
    private Button btnNext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_shop_type,container,false);
        Toast.makeText(getContext(), "a", Toast.LENGTH_SHORT).show();

        initView(view);

        return view;
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @param view
     *
     * @created_by cvmanh on 01/11/2021
     */
    private void initView(View view) {
        try {
            btnNext=view.findViewById(R.id.btnNext);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
