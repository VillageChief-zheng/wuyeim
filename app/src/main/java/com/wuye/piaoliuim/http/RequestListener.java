package com.wuye.piaoliuim.http;

/**
 * @ClassName RequestListener
 * @Description
 * @Author VillageChief
 * @Date 2019/12/6 17:45
 */
public interface RequestListener<T> {

    public void onComplete(T requestEntity);

    public void onError(String message);
}
