package com.wuye.piaoliuim.bean;

import java.io.Serializable;

/**
 * @ClassName ChannelModel
 * @Description
 * @Author VillageChief
 * @Date 2019/12/16 10:38
 */
public class ChannelModel implements Serializable {

    public static final int ONE = 1001;
    public static final int TWO = 1002;
    public static final int THREE = 1003;
    public static final int FOUR = 1004;

    public int type;
    public Object data;
    public String jinBi;
    public String jine;
    public String addJinbi;
    public int imgSrc;

    public ChannelModel(int type, Object data,String jinBi,String addJinbi,String jine,int imgSrc) {
        this.type = type;
        this.data = data;
        this.jinBi = jinBi;
        this.jine=jine;
        this.addJinbi=addJinbi;
        this.imgSrc=imgSrc;

    }
}
