package com.wuye.piaoliuim.utils;

import com.wuye.piaoliuim.config.Constants;

public class ImagUrlUtils {
    public static String getImag(String uri) {
        //shouji xinghap
        if (uri.contains("http")){
            return uri;
        }else {
            return Constants.BASEURL+uri;
        }
     }
}
