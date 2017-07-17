package com.ybyb.zzq.freecartoon.util;

/**
 * 作者：周正权 on 2017/6/7
 * 邮箱：zhouzhengquan@shixinyun.com
 */

import android.content.Context;
import android.util.Log;
import com.ybyb.zzq.freecartoon.App;
import com.ybyb.zzq.freecartoon.AppConstants;
import com.ybyb.zzq.freecartoon.BuildConfig;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * api网络请求管理器
 *
 * @author PengZhenjin
 * @date 2017-1-17
 */
public class ApiManager {

    // 读取超时时间为15秒
    private static final int READ_TIME_OUT = 15;

    // 连接超时时间为15秒
    private static final int CONNECT_TIME_OUT = 15;

    // 缓存大小20MB
    private static final long CACHE_SIZE = 1024 * 1024 * 20;

    // RetrofitManager实例
    private static volatile ApiManager mInstance = null;

    // Retrofit实例
    private Retrofit mRetrofit = null;

    // OkHttpClient实例
    private OkHttpClient mOkHttpClient = null;

    private Context mContext;

    /**
     * 单例
     */
    public static ApiManager getInstance() {
        if (mInstance == null) {
            synchronized (ApiManager.class) {
                if (mInstance == null) {
                    mInstance = new ApiManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 构造方法
     */
    private ApiManager() {
        this.init();
    }

    /**
     * 初始化
     */
    private void init() {
        this.mContext = App.getContext();
        this.initOkHttp();
        this.initRetrofit();
    }

    /**
     * 初始化OkHttp
     */
    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 打印请求log日志
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(App.getContext().getCacheDir(), "cache"), CACHE_SIZE);

        // 缓存拦截器
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtil.isNetworkConnected(mContext)) {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
                }

                Response response = chain.proceed(request);

                if (NetworkUtil.isNetworkConnected(mContext)) {
                    // 有网络时 设置缓存超时时间0个小时
                    int maxAge = 0;
                    response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge).removeHeader("Pragma").build();
                }
                else {
                    // 无网络时，设置超时为2天
                    int maxStale = 60 * 60 * 24 * 2;
                    response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).removeHeader("Pragma").build();
                }

                return response;
            }
        };

        // 头部拦截器
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder();
                requestBuilder.header("Accept-Charset", "UTF-8");
                requestBuilder.header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                requestBuilder.method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        builder.cache(cache);
        builder.interceptors().add(cacheInterceptor);   // 添加本地缓存拦截器，用来拦截本地缓存
        builder.networkInterceptors().add(cacheInterceptor); //添加网络拦截器，用来拦截网络数据
        builder.addInterceptor(headerInterceptor); // 设置头部
        builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS); // 设置连接超时
        builder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);   // 设置读取超时
        builder.writeTimeout(READ_TIME_OUT, TimeUnit.SECONDS);  // 设置写入超时
        builder.retryOnConnectionFailure(true); // 设置重连

        this.setSSL(builder);

        this.mOkHttpClient = builder.build();
    }

    /**
     * 初始化Retrofit
     */
    private void initRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(AppConstants.SERVER_BASE_URL);
        builder.client(this.mOkHttpClient);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        this.mRetrofit = builder.build();
    }

    /**
     * 获取ApiService
     */
    public ApiService getApiService() {
        if (this.mRetrofit == null) {
            return null;
        }
        return this.mRetrofit.create(ApiService.class);
    }

    /**
     * RxJava线程切换方法，网络请求在Schedulers.io()线程，结果处理切换到AndroidSchedulers.mainThread()线程
     *
     * @param o
     * @param s
     * @param <T>
     */
    public <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s);
    }

    /**
     * 设置忽略ssl证书验证
     *
     * @param builder
     */
    private void setSSL(OkHttpClient.Builder builder) {
        try {
            X509TrustManager xtm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[] { xtm }, new SecureRandom());

            HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            builder.sslSocketFactory(sslContext.getSocketFactory(), xtm);
            builder.hostnameVerifier(DO_NOT_VERIFY);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }
}

