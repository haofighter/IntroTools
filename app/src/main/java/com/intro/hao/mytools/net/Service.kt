package com.intro.hao.mytools.net

import com.intro.hao.mytools.ResponseBean.BaseResponce
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import rx.Observable

/**
 * Created by haozhang on 2017/12/29.
 */
interface Service {

    //    网易云音乐查询
    @POST("http://music.163.com/api/search/pc")
    @FormUrlEncoded
    fun WYMusic(@Field("s") searchContent: String, @Field("offset") page: Int, @Field("limit") limit: Int, @Field("type") type: String): retrofit2.Call<BaseResponce>


    //获取车辆类型
    @GET("/ecar/r/car/cartype")
    fun getCarInfo(): Call<BaseResponce>//获取车辆类型

    @GET("/ecar/r/car/cartype")
    fun getCarInfoObservable(): Observable<BaseResponce>
}