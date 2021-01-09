package vn.com.misa.ccl.View.ShopSetup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.com.misa.ccl.R;

public class FragmentShopMenu extends Fragment {

    private Button btnNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_shop_type,container,false);
        Toast.makeText(getContext(), "b", Toast.LENGTH_SHORT).show();

        initView(view);

        return view;
    }

    private void initView(View view) {
        btnNext=view.findViewById(R.id.btnNext);
        btnNext.setText(R.string.success);
    }
}
