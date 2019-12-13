package com.wuye.piaoliuim.http;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;

/**
 * @ClassName RequestParams
 * @Description
 * @Author VillageChief
 * @Date 2019/12/6 17:50
 */
public class RequestParams {
    private String url;
    private HashMap<String, String> params;
    private String paramsJson;
    private List<File> files  ;
    private String fileName;
    private MediaType type;
//    public MultipartBody getMultipartBody() {
//        return multipartBody;
//    }
//
//    public void setMultipartBody(MultipartBody multipartBody) {
//        this.multipartBody = multipartBody;
//    }

    public String getParamsJson() {
        return paramsJson;
    }

    public void setParamsJson(String paramsJson) {
        this.paramsJson = paramsJson;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }
}
