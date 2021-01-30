package vn.com.misa.ccl.view.rmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.MyMenuAdapter;
import vn.com.misa.ccl.adapter.ProductCategoryAdapter;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.presenter.FragmentMenuPresenter;

/**
 * ‐ Mục đích Class thực hiện việc hiển thị danh sách sản phẩm có trong menu
 * <p>
 * ‐ {@link FragmentMenuPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/27/2021
 */

public class FragmentMenu extends Fragment implements IFragmentMenu.IFragmentMenuView {

    private RecyclerView rcvMenu;

    private MyMenuAdapter mMyMenuAdapter;

    private FragmentMenuPresenter mFragmentMenuPresenter;

    private List<Product> mListProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        initView(view);

        getListMenu();

        return view;
    }

    @Override
    public void onResume() {
        getListMenu();
        super.onResume();
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @param view view
     * @created_by cvmanh on 01/27/2021
     */
    private void initView(View view) {
        try {
            rcvMenu = view.findViewById(R.id.rcvMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc gọi presenter xử lý lấy danh sách menu
     *
     * @created_by cvmanh on 01/27/2021
     */
    private void getListMenu() {
        try {
            mFragmentMenuPresenter = new FragmentMenuPresenter(this);
            mFragmentMenuPresenter.getListMenu(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý lấy danh sách menu và hiển thị lên recylceView
     *
     * @param listProduct Danh sách sản phẩm
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void getListMenuSuccess(List<Product> listProduct) {
        try {
            mListProduct = listProduct;
            mMyMenuAdapter = new MyMenuAdapter(getActivity(), R.layout.item_restaurant_menu, mListProduct);
            rcvMenu.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            rcvMenu.setAdapter(mMyMenuAdapter);
            mMyMenuAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý thất bại và hiện thông báo
     *
     * @created_by cvmanh on 01/27/2021
     */
    @Override
    public void onFailed() {
        Toast.makeText(getContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }
}
