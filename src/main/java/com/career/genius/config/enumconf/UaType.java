package com.career.genius.config.enumconf;

import com.alibaba.fastjson.JSON;

/**
 * 浏览器类型
 */
public enum UaType {
    wechat("micromessenger", "微信"),
//    alipay("alipayclient", "支付宝"),
    other("unknowua", "其他");
    
    private String code;
    private String cnName;
    private UaType(String code, String cnName) {
       this.code = code;
       this.cnName = cnName;
    }
    
    public static UaType get(String uaAgent){
        for (UaType etype : UaType.values()) {
            if(uaAgent.indexOf(etype.getCode()) != -1) {return etype;}
        }
        return UaType.other;
    }
    
    public static UaType getByName(String name){
        for (UaType etype : UaType.values()) {
            if(etype.name().equals(name)) {return etype;}
        }
        return UaType.other;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getCnName() {
        return cnName;
    }
    
    public boolean equals(UaType etype){
        if(null == etype) {return false;}
        return etype.getCode().equals(this.getCode());
    }
    
    public boolean equals(String code){
        if(null == code) {return false;}
        return code.equals(this.getCode());
    }
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
