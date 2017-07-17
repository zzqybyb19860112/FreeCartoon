package com.ybyb.zzq.freecartoon.util;

import com.ybyb.zzq.freecartoon.base.ResultData;
import com.ybyb.zzq.freecartoon.response.detail.ChapterCartoon;
import com.ybyb.zzq.freecartoon.response.survey.CartoonData;
import com.ybyb.zzq.freecartoon.response.survey.ChapterData;
import java.util.List;
import java.util.Map;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 作者：周正权 on 2017/6/7
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public interface ApiService {

    /**
     * 查找漫画分类
     *
     * @param parms
     *
     * @return
     */
    @POST("category")
    @FormUrlEncoded
    Observable<ResultData<List<String>>> queryTypes(@FieldMap Map<String, String> parms);

    /**
     * 一般查找漫画分类
     *
     * @param parms
     *
     * @return
     */
    @POST("book")
    @FormUrlEncoded
    Observable<ResultData<CartoonData>> queryDataList(@FieldMap Map<String, String> parms);

    /**
     * 隔页查找漫画分类
     *
     * @param parms
     *
     * @return
     */
    @POST("book")
    @FormUrlEncoded
    Observable<ResultData<CartoonData>> queryDataListSkip(@FieldMap Map<String, String> parms);

    /**
     * 根据漫画名称查询章节信息
     *
     * @param params
     *
     * @return
     */
    @POST("chapter")
    @FormUrlEncoded
    Observable<ResultData<ChapterData>> queryChapters(@FieldMap Map<String, String> params);

    /**
     * 根据漫画名称和跳转条数查询章节信息
     *
     * @param params
     *
     * @return
     */
    @POST("chapter")
    @FormUrlEncoded
    Observable<ResultData<ChapterData>> queryChaptersSkip(@FieldMap Map<String, String> params);

    /**
     * 查询单章节漫画详情
     *
     * @param params
     *
     * @return
     */
    @POST("chapterContent")
    @FormUrlEncoded
    Observable<ResultData<ChapterCartoon>> queryDetail(@FieldMap Map<String, String> params);
}
