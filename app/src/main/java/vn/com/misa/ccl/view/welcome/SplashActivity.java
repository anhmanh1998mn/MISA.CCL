package vn.com.misa.ccl.view.welcome;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import vn.com.misa.ccl.MainActivity;
import vn.com.misa.ccl.database.DatabaseHelper;
import vn.com.misa.ccl.util.DatabaseInfomation;
import vn.com.misa.ccl.view.manage.ActivityRestaurantManage;

/**
 * ‐ Mục đích Class thực hiện việc show splash Screen
 * <p>
 * ‐ @created_by cvmanh on 01/06/2021
 */

public class SplashActivity extends AppCompatActivity {

    private final int TIME_SHOW = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showSpashScreen();
    }

    /**
     * Mục đích method thực hiện việc show mành hình giới thiệu trong 2s, sau đó chuyển sang màn hình overview
     *
     * @created_by cvmanh on 01/11/2021
     */
    private void showSpashScreen() {
        try {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkData();
                    if (checkData()) {
                        startActivity(new Intent(SplashActivity.this, ActivityRestaurantManage.class));
                        finish();
                        return;
                    }
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, TIME_SHOW);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc kiểm tra xem có tồn tại database trong ứng dụng
     *
     * @return trả về true nếu tồn tại database
     * @created_by cvmanh on 01/22/2021
     */
    private boolean checkData() {
        File databaseName = this.getDatabasePath(DatabaseInfomation.DATABASE_NAME);
        return databaseName.exists();
    }
}
