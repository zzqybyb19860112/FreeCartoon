package com.ybyb.zzq.freecartoon.util;

import com.ybyb.zzq.freecartoon.base.ApiResultFunc;
import com.ybyb.zzq.freecartoon.response.detail.ChapterCartoon;
import com.ybyb.zzq.freecartoon.response.survey.CartoonData;
import com.ybyb.zzq.freecartoon.response.survey.ChapterData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * 作者：周正权 on 2017/6/7
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class ApiFactory {

    private ApiService mApiService;

    public ApiFactory() {
        this.mApiService = ApiManager.getInstance().getApiService();
    }

    /**
     * 查询漫画分类
     *
     * @param key
     *
     * @return
     */
    public Observable<List<String>> queryTypes(String key) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        return this.mApiService.queryTypes(params).map(new ApiResultFunc<List<String>>()).subscribeOn(Schedulers.io());
    }

    /**
     * 根据漫画分类查询基本漫画信息
     *
     * @param key
     *
     * @return
     */
    public Observable<CartoonData> queryDataList(String key, String type) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("type", type);
        return this.mApiService.queryDataList(params).map(new ApiResultFunc<CartoonData>()).subscribeOn(Schedulers.io());
    }

    /**
     * 根据漫画分类隔页查询基本漫画信息
     *
     * @param key
     *
     * @return
     */
    public Observable<CartoonData> queryDataListSkip(String key, String type, int skip) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("type", type);
        params.put("skip", skip + "");
        return this.mApiService.queryDataListSkip(params).map(new ApiResultFunc<CartoonData>()).subscribeOn(Schedulers.io());
    }

    /**
     * 根据漫画名称查询章节信息
     *
     * @param key
     * @param comicName
     *
     * @return
     */
    public Observable<ChapterData> queryChapters(String key, String comicName) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("comicName", comicName);
        return this.mApiService.queryChapters(params).map(new ApiResultFunc<ChapterData>()).subscribeOn(Schedulers.io());
    }

    /**
     * 根据漫画名称和跳转条数查询章节信息
     *
     * @param key
     * @param comicName
     * @param skip
     *
     * @return
     */
    public Observable<ChapterData> queryChaptersSkip(String key, String comicName, int skip) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("comicName", comicName);
        params.put("skip", skip + "");
        return this.mApiService.queryChaptersSkip(params).map(new ApiResultFunc<ChapterData>()).subscribeOn(Schedulers.io());
    }

    /**
     * 查询单章节漫画详情
     *
     * @param key
     * @param comicName
     * @param id
     *
     * @return
     */
    public Observable<ChapterCartoon> queryDetail(String key, String comicName, int id) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("comicName", comicName);
        params.put("id", id + "");
        return this.mApiService.queryDetail(params).map(new ApiResultFunc<ChapterCartoon>()).subscribeOn(Schedulers.io());
    }
}
