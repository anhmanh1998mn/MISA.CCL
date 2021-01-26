package vn.com.misa.ccl.database;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ‐ Mục đích Class thực hiện việc khởi tạo database
 * <p>
 * ‐ {@link vn.com.misa.ccl.model.ActivityRestaurantMenuModel}
 * ‐ {@link vn.com.misa.ccl.model.ActivityRestaurantTypeModel}
 * <p>
 * ‐ @created_by cvmanh on 01/19/2021
 */

public class DatabaseHelper {
    public static SQLiteDatabase initDatabase(Activity activity, String databaseName) {
        try {
            String outFileName = activity.getApplicationInfo().dataDir + "/databases/" + databaseName;
            File f = new File(outFileName);
            if (!f.exists()) {
                InputStream inputStream = activity.getAssets().open(databaseName);
                File folder = new File(activity.getApplicationInfo().dataDir + "/databases/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                FileOutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];

                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return activity.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
    }
}
