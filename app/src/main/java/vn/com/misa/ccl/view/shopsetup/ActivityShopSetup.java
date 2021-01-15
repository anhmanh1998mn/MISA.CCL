package vn.com.misa.ccl.view.shopsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.com.misa.ccl.R;

/**
‐ Mục đích Class thực hiện việc chứa fragmentShopMenu, fragmentShopType
*
‐ @created_by cvmanh on 01/11/2021
*/

public class ActivityShopSetup extends AppCompatActivity implements View.OnClickListener {

    private Toolbar tbShopSetup;

    private TextView tvBackShop,tvNext,tvSuccess,tvSetupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_setup);

        initActionBar();

        initView();

        onClickViewListener();

        addNewFragment(new FragmentShopType());
    }

    /**
     * Mục đích method thực hiện việc khởi tạo actionbar
     *
     * @created_by cvmanh on 01/09/2021
     */
    private void initActionBar() {
        try {
            tbShopSetup=findViewById(R.id.tbShopType);
            setSupportActionBar(tbShopSetup);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

 /**
  * Mục đích method thực hiện việc khởi tạo các view
  *
  * @created_by cvmanh on 01/09/2021
  */
    private void initView(){
        try {
            tvBackShop=findViewById(R.id.tvBackShop);
            tvNext=findViewById(R.id.tvNext);
            tvSuccess=findViewById(R.id.tvSuccess);
            tvSetupName=findViewById(R.id.tvSetupName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc lắng nghe các sự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/09/2021
     */
    private void onClickViewListener(){
        tvBackShop.setOnClickListener(this);
        tvNext.setOnClickListener(this);
    }

    /**
     * Mục đích method thực hiện việc thay thế fragment mới
     *
     * @param fragment fragment được thay thế
     *
     * @return trả về fragmemnt được thay thế
     *
     * @created_by cvmanh on 01/09/2021
     */
    private Fragment addNewFragment(Fragment fragment){
        try {
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frmShopSetup,fragment);
            transaction.commit();
            return fragment;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mục đích method thực hiện xử lý các công việc khi người dùng click
     *
     * @param view các view được click
     *
     * @created_by cvmanh on 01/11/2021
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvBackShop:{
                Fragment fragmentInFrame = getSupportFragmentManager().findFragmentById(R.id.frmShopSetup);
                if(fragmentInFrame instanceof FragmentShopType){
                    finish();
                    return;
                }
                addNewFragment(new FragmentShopType());
                tvNext.setVisibility(View.VISIBLE);
                tvSuccess.setVisibility(View.GONE);
                tvSetupName.setText(R.string.selection_shop_type);
                break;
            }
            case R.id.tvNext:{
                addNewFragment(new FragmentShopMenu());
                tvNext.setVisibility(View.GONE);
                tvSuccess.setVisibility(View.VISIBLE);
                tvSetupName.setText(R.string.menu);
                break;
            }
        }
    }
}