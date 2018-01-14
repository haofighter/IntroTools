package com.intro.hao.mytools.net

import com.intro.hao.mytools.BuildConfig.DEBUG
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by haozhang on 2017/12/28.
 */
class RetrofitManager {

    val baseUrl: String = "http://39.104.54.182:8080";


    val netInterceptor: Interceptor = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        val maxAge = 60
        response.newBuilder()
                .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=" + maxAge)
                .build()
    }

    /****************************************************/
    private val okHttpClientNoHeader = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(netInterceptor)
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

    /****************************************************/

    fun <T> builder(clazz: Class<T>): T {
        return builder(clazz, baseUrl)
    }

    //常规请求
    fun <T> builder(clazz: Class<T>, url: String): T {
        var retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .client(okHttpClientNoHeader).build();
        return retrofit.create(clazz)
    }


    fun <T> builder(tClass: Class<T>, callback: FileSubscribe<Any>): T {
        return builder(tClass, baseUrl, callback)
    }


    //下载进度监听
    fun <T> builder(tClass: Class<T>, url: String, callback: FileSubscribe<Any>): T {
        val clientBuilder =
                OkHttpClient.Builder().addInterceptor { chain ->
                    val originalResponse = chain.proceed(chain.request())
                    originalResponse.newBuilder().body(
                            FileResponseBody(originalResponse.body(), callback))
                            .build()
                }
        val retrofit1 = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .client(clientBuilder.build())
                .build()
        return retrofit1.create(tClass)
    }

    /********************************************************/
}