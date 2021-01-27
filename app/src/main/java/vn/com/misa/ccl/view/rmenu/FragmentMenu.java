package vn.com.misa.ccl.view.rmenu;

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

public class FragmentMenu extends Fragment implements IFragmentMenu.IFragmentMenuView {

    private RecyclerView rcvMenu;

    private MyMenuAdapter mMyMenuAdapter;

    private FragmentMenuPresenter mFragmentMenuPresenter;

    private List<Product> mListProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_menu,container,false);

        initView(view);

        getListMenu();

        return view;
    }

    @Override
    public void onResume() {
        getListMenu();
        super.onResume();
    }

    private void initView(View view) {
        rcvMenu=view.findViewById(R.id.rcvMenu);
    }

    private void getListMenu(){
        mFragmentMenuPresenter=new FragmentMenuPresenter(this);
        mFragmentMenuPresenter.getListMenu(getActivity());
    }

    @Override
    public void getListMenuSuccess(List<Product> listProduct) {
        mListProduct=listProduct;
        mMyMenuAdapter=new MyMenuAdapter(getActivity(),R.layout.item_restaurant_menu,mListProduct);
        rcvMenu.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        rcvMenu.setAdapter(mMyMenuAdapter);
        mMyMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {
        Toast.makeText(getContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }
}
