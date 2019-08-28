package com.career.genius.utils;

public class Constants {
    public static String SESSION_AUTHS_KEY = "SESSION_AUTHS";

    public static String LOGIN_AUTH_CODE = "LOGIN_AUTH_CODE";

    public static String AUTH_CODE_SESSION_ID = "AUTH_CODE_SESSION_ID";

    public static String MOBILE_MSG_CODE = "mobile_msg_code";

    /* 登录用户信息 */
    public static String SESSION_LOGIN_USER = "SESSION_LOGIN_USER";

    /* 请求客户端的IP */
    public static String REQUEST_CLIENT_IP = "REQUEST_CLEINT_IP";

    /* 登录系统用户信息 */
    public static String SESSION_SYS_USER = "SESSION_SYS_USER";

    /**
     * 登录Token
     */
    public static final String COOKIE_PARAM = "LOGIN_COOKIE";

    /**
     * 默认Cookies的maxAge
     */
    public static final Integer DEFAULT_COOKIE_MAXAGE = 12*60*60;

    //请求致诚阿福群星接口地址
    public static String api_name = "credit.evaluation.share.api";
    public static String api_name1 = "fraud.screening.device.api";
    public static String api_name_jinjie = "fraud.screening.advance.api";
    public static String url = "https://starapi.zhichengcredit.com/submit";

    //多头账号
//    public static String muti_user_name = "shensudai";//用户名(正式)
//    public static String muti_sign = "af2f8b27e499a687";//rc4秘钥 (正式)
    public static String muti_user_name = "shensudai_testusr";//用户名(测试)
    public static String muti_sign = "ec6aef1d861d493e";//rc4秘钥(测试)

    //反欺诈账号
    public static String qizha_user_name = "shensudai";//用户名(正式)
    public static String qizha_sign = "af2f8b27e499a687";//rc4秘钥 (正式)
//    public static String qizha_user_name = "shensudai_testusr";//用户名(测试)
//    public static String qizha_sign = "ec6aef1d861d493e";//rc4秘钥(测试)

    //白骑士反欺诈账号
    public static String apiUrl_baiqishi = "https://api.baiqishi.com/services/decision";//风险决策API生产环境调用地址
    public static String verifyKey_baiqishi = "0d4aea86b58e4eb781f78966f281ecaa";
    public static String partnerId_baiqishi = "lfme";
    public static String appId_baiqishi = "lfme";
    public static String eventType_login = "login";
    public static String eventType_loan = "loan";

    public static String apiUrl_getMnoReport_baiqishi = "https://credit.baiqishi.com/clweb/api/mno/getreport";//获取运营商报告数据
}