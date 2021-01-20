package vn.com.misa.ccl.view.restaurantsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private void initActionBar() {
        tbUnit=findViewById(R.id.tbUnit);
        setSupportActionBar(tbUnit);
    }

    private void initView() {
        mActivityUnitPresenter=new ActivityUnitPresenter(this);
        rcvUnit=findViewById(R.id.rcvUnit);
        tvBack=findViewById(R.id.tvBack);
        btnSuccess=findViewById(R.id.btnSuccess);
    }

    private void loadListUnit(){
        mActivityUnitPresenter.loadListUnit(this);
    }

    @Override
    public void loadListUnitSuccess(List<Unit> listUnit) {
        mListUnit=listUnit;
        mUnitAdapter=new UnitAdapter(this,R.layout.item_unit,mListUnit);
        rcvUnit.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rcvUnit.setAdapter(mUnitAdapter);
        mUnitAdapter.notifyDataSetChanged();
        mUnitAdapter.setmIItemClickListener(this);
    }

    @Override
    public void onFailed() {
        Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    private void clickViewListener(){
        tvBack.setOnClickListener(this);
        btnSuccess.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvBack:{
                finish();
                break;
            }
            case R.id.btnSuccess:{
                SharedPreferences sharedPreferences=getSharedPreferences("UnitSelection",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("UNIT_NAME",mUnitName);
                editor.commit();
                finish();
                break;
            }
        }
    }

    @Override
    public void getUnitNameItemClick(String unitName, int unitID) {
        mUnitName=unitName;
        mUnitID=unitID;
    }
}