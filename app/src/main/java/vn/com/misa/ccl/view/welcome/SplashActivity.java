package vn.com.misa.ccl.view.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import vn.com.misa.ccl.MainActivity;

/**
 ‐ Mục đích Class thực hiện việc show splash Screen
 *
 ‐ @created_by cvmanh on 01/06/2021
 */

public class SplashActivity extends AppCompatActivity {

    private int TIME_SHOW=2000;


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
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            },TIME_SHOW);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
