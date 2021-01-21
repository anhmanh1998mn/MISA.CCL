package vn.com.misa.ccl.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.entity.Unit;
import vn.com.misa.ccl.util.DatabaseInfomation;

public class ActivityUnitModel {

    private IResultProcessActivityUnit mIResultProcessActivityUnit;

    public ActivityUnitModel(IResultProcessActivityUnit mIResultProcessActivityUnit) {
        this.mIResultProcessActivityUnit = mIResultProcessActivityUnit;
    }

    private List<Unit> mListUnit;

    private SQLiteDatabase mSqliteDatabase;

    public void loadListUnit(Activity activity){
        try {
            mListUnit=new ArrayList<>();
            mSqliteDatabase= DatabaseHelper.initDatabase(activity,DatabaseInfomation.DATABASE_NAME);
            Cursor cursor=mSqliteDatabase.rawQuery("SELECT * FROM "+DatabaseInfomation.TABLE_UNITS+"",null);
            for(int i=0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                mListUnit.add(new Unit(cursor.getInt(cursor.getColumnIndex(DatabaseInfomation.COLUMN_UNIT_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseInfomation.COLUMN_UNIT_NAME))));
            }
            if(cursor!=null){
                mIResultProcessActivityUnit.loadListUnitSuccess(mListUnit);
                return;
            }
            mIResultProcessActivityUnit.onFailed();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface IResultProcessActivityUnit{
        public void loadListUnitSuccess(List<Unit> listUnit);

        public void onFailed();
    }
}
