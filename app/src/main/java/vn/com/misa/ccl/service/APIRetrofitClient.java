package vn.com.misa.ccl.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ‐ Mục đích Class thực hiện những công việc cài đặt retrofit
 * <p>
 * ‐ {@link APIService}
 * ‐ {@link IDataService}
 * <p>
 * ‐ @created_by cvmanh on 01/31/2021
 */

public class APIRetrofitClient {

    public static Retrofit sRetrofit = null;

    /**
     * Mục đích method thực hiện việc cài đặt retrofit
     *
     * @param baseURL đường dẫn API
     * @return trả về retrofit
     * @created_by cvmanh on 01/31/2021
     */
    public static Retrofit getClient(String baseURL) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .protocols(Arrays.asList(Protocol.HTTP_1_1))
                .build();
        Gson gson = new GsonBuilder().setLenient().create();
        sRetrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return sRetrofit;
    }
}
