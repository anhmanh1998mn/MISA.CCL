package vn.com.misa.ccl.view.restaurantsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.adapter.UnitAdapter;
import vn.com.misa.ccl.entity.Unit;
import vn.com.misa.ccl.presenter.ActivityUnitPresenter;
import vn.com.misa.ccl.util.Common;

/**
 * ‐ Mục đích Class thực hiện những công việc hiển thị danh sách Unit
 * <p>
 * ‐ {@link ActivityUnitPresenter}
 * <p>
 * ‐ @created_by cvmanh on 01/26/2021
 */

public class ActivityUnit extends AppCompatActivity implements IActivityUnit.IActivityUnitView, View.OnClickListener,
        UnitAdapter.IItemClickListener {

    private Toolbar tbUnit;

    private RecyclerView rcvUnit;

    private ActivityUnitPresenter mActivityUnitPresenter;

    private UnitAdapter mUnitAdapter;

    private List<Unit> mListUnit;

    private TextView tvBack;

    private Button btnSuccess;

    private String mUnitName;

    private int mUnitID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);

        initActionBar();

        initView();

        clickViewListener();

        loadListUnit();
    }

    /**
     * Mục đích method thực hiện việc khởi tạo actionBar
     *
     * @created_by cvmanh on 01/26/2021
     */
    private void initActionBar() {
        try {
            tbUnit = findViewById(R.id.tbUnit);
            setSupportActionBar(tbUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc khởi tạo các view
     *
     * @created_by cvmanh on 01/26/2021
     */
    private void initView() {
        try {
            mActivityUnitPresenter = new ActivityUnitPresenter(this);
            rcvUnit = findViewById(R.id.rcvUnit);
            tvBack = findViewById(R.id.tvBack);
            btnSuccess = findViewById(R.id.btnSuccess);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện gọi presenter xử lý lấy danh sách Unit
     *
     * @created_by cvmanh on 01/26/2021
     */
    private void loadListUnit() {
        try {
            mActivityUnitPresenter.loadListUnit(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận danh sách Unit và hiển thị lên recycleView
     *
     * @param listUnit Danh sách Unit
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void loadListUnitSuccess(List<Unit> listUnit) {
        try {
            mListUnit = listUnit;
            mUnitAdapter = new UnitAdapter(this, R.layout.item_unit, mListUnit);
            rcvUnit.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rcvUnit.setAdapter(mUnitAdapter);
            mUnitAdapter.notifyDataSetChanged();
            mUnitAdapter.setmIItemClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận kết quả xử lý thất bại và hiện thông báo
     *
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void onFailed() {
        Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method thực hiện việc lắng nghe xự kiện click từ người dùng
     *
     * @created_by cvmanh on 01/26/2021
     */
    private void clickViewListener() {
        try {
            tvBack.setOnClickListener(this);
            btnSuccess.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện xử lý câc công việc theo view click
     *
     * @param view view
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.tvBack: {
                    finish();
                    break;
                }
                case R.id.btnSuccess: {
                    SharedPreferences sharedPreferences = getSharedPreferences("UnitSelection", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("UNIT_NAME", mUnitName);
                    editor.putInt("UNIT_ID", mUnitID);
                    Common.PRODUCT_UNIT_ID = mUnitID;
                    editor.commit();
                    finish();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện nhận UnitName và unitID từ presenter
     *
     * @param unitID   mã unit
     * @param unitName tên unit
     * @created_by cvmanh on 01/26/2021
     */
    @Override
    public void getUnitNameItemClick(String unitName, int unitID) {
        try {
            mUnitName = unitName;
            mUnitID = unitID;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}