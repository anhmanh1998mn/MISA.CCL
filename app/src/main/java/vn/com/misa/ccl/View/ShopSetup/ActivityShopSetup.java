package vn.com.misa.ccl.View.ShopSetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.com.misa.ccl.R;

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
     * Mục đích method thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
     *
     * @see #getIntent
     * @see #onNewIntent
     *
     * @return giải thích hàm này trả về
     *
     * @created_by cvmanh on 01/09/2021
     */
    private void initActionBar() {
        tbShopSetup=findViewById(R.id.tbShopType);
        setSupportActionBar(tbShopSetup);
    }

 /**
  * Mục đích method thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
  *
  * @see #getIntent
  * @see #onNewIntent
  *
  * @return giải thích hàm này trả về
  *
  * @created_by cvmanh on 01/09/2021
  */
    private void initView(){
        tvBackShop=findViewById(R.id.tvBackShop);
        tvNext=findViewById(R.id.tvNext);
        tvSuccess=findViewById(R.id.tvSuccess);
        tvSetupName=findViewById(R.id.tvSetupName);
    }

    /**
     * Mục đích method thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
     *
     * @see #getIntent
     * @see #onNewIntent
     *
     * @return giải thích hàm này trả về
     *
     * @created_by cvmanh on 01/09/2021
     */
    private void onClickViewListener(){
        tvBackShop.setOnClickListener(this);
        tvNext.setOnClickListener(this);
    }

    /**
     * Mục đích method thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
     *
     * @param fragment Giải thích cho biến này
     * @see #getIntent
     * @see #onNewIntent
     *
     * @return giải thích hàm này trả về
     *
     * @created_by cvmanh on 01/09/2021
     */
    private Fragment addNewFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frmShopSetup,fragment);
        transaction.commit();
        return fragment;
    }

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