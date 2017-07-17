package com.ybyb.zzq.freecartoon.util;

import com.ybyb.zzq.freecartoon.response.detail.ChapterCartoon;
import com.ybyb.zzq.freecartoon.response.survey.CartoonData;
import com.ybyb.zzq.freecartoon.response.survey.ChapterData;
import java.util.List;
import rx.Observable;

/**
 * 作者：周正权 on 2017/6/7
 * 邮箱：zhouzhengquan@shixinyun.com
 */

public class UserRepository {
    private static volatile UserRepository mInstance;

    private ApiFactory mApiFactory;

    private UserRepository() {
        this.mApiFactory = new ApiFactory();
    }

    public static UserRepository getInstance() {
        if (null == mInstance) {
            synchronized (UserRepository.class) {
                if (null == mInstance) {
                    mInstance = new UserRepository();
                }
            }
        }
        return mInstance;
    }

    /**
     * 查询漫画分类
     */
    public Observable<List<String>> queryTypes(String key) {
        return this.mApiFactory.queryTypes(key);
    }

    /**
     * 根据漫画分类查询基本漫画信息
     */
    public Observable<CartoonData> queryDataList(String key, String type) {
        return this.mApiFactory.queryDataList(key, type);
    }

    /**
     * 根据漫画分类隔页查询基本漫画信息
     */
    public Observable<CartoonData> queryDataListSkip(String key, String type, int skip) {
        return this.mApiFactory.queryDataListSkip(key, type, skip);
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
        return this.mApiFactory.queryChapters(key, comicName);
    }

    /**
     * 根据漫画名称和跳转条数查询章节信息
     *
     * @param key
     * @param comicName
     *
     * @return
     */
    public Observable<ChapterData> queryChaptersSkip(String key, String comicName, int skip) {
        return this.mApiFactory.queryChaptersSkip(key, comicName, skip);
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
        return this.mApiFactory.queryDetail(key, comicName, id);
    }
}
