package com.career.genius.utils.wechat;

import com.career.genius.application.wechat.dto.WechatDto;
import com.career.genius.config.config.Config;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.career.genius.port.setvice.WxService.urlEncodeUTF8;

@Slf4j
public class WechatUtil {

    /**
     * 不弹出授权页面，直接跳转，只能获取用户openid
     */
    public static final String SCOPESNSAPIBASE = "snsapi_base";
    /**
     * 弹出用户授权页面，可获取客户其他信息
     */
    public static final String SCOPESNSAPI_USERINFO = "snsapi_userinfo";

    /**
     * 网页授权获取用户基本信息第一步：用户同意授权，获取code（GET）
     */
    public static String oauth2_authorize_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=RESPONSE_TYPE&scope=SCOPE&state=STATE#wechat_redirect";

    /**
     * 网页授权获取用户基本信息第二步：通过code换取网页授权access_token（GET）
     */
    public static String oauth2_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    public static WechatDto getWinXinEntity(String url) {
        WechatDto wx = new WechatDto();
       // String access_token = getAccessToken();
        String access_token = ""; // test
        String ticket = getTicket(access_token);
        Map<String, String> ret = Sign.sign(ticket, url);
        wx.setTicket(ret.get("jsapi_ticket"));
        wx.setSignature(ret.get("signature"));
        wx.setNoncestr(ret.get("nonceStr"));
        wx.setTimestamp(ret.get("timestamp"));
        return wx;
    }

    /**
     * 获取当前请求完整的URL
     * @param request
     * @return
     */
    public static String getRequestUri(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        String requestUri = request.getRequestURL().toString();
        if (requestUri.contains("http://127.0.0.1:8080")){
            requestUri = requestUri.replace("http://127.0.0.1:8080", Config.CURRENT_DOMAIN);
        }
        String queryString = "";
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                queryString += key + "=" + urlEncodeUTF8(value) + "&";
            }
        }
        if (StringUtil.isNotEmpty(queryString)) {
            queryString = queryString.substring(0, queryString.length() - 1);
            requestUri = requestUri + "?" + queryString;
        }
        return requestUri.trim();
    }

    public static String getCode(String appid, String redirectUri, String scope, String state) {
        String url = oauth2_authorize_url.replace("APPID", urlEncodeUTF8(appid)).replace("REDIRECT_URI", urlEncodeUTF8(redirectUri))
                .replace("RESPONSE_TYPE", "code").replace("SCOPE", scope).replace("STATE", state);
        return url;
    }

    public static String getAccessToken(String appId, String secret) {
        String accessToken = "";
        String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appId).replace("APPSECRET", secret);
        try {
            URL urlGet = new URL(requestUrl);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes);
            JSONObject json = JSONObject.fromObject(message);
            accessToken = json.getString("access_token");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    /**
     * 获取token
     * @param appId
     * @param secret
     * @param code
     * @return
     */
    public static JSONObject getAccessToken(String appId, String secret, String code) {
//        String grant_type = "client_credential";//获取access_token填写client_credential
        //这个url链接地址和参数皆不能变
        String url = oauth2_access_token_url.replace("APPID", appId).replace("SECRET", secret).replace("CODE", code);
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes);
            JSONObject json = JSONObject.fromObject(message);
//            access_token = demoJson.getString("access_token");
            is.close();
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取ticket
     * @param accessToken
     * @return
     */
    public static String getTicket(String accessToken) {
        String ticket = null;
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";// 这个url链接和参数不能变
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            ticket = demoJson.getString("ticket");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }

    public static void getJsSdkParameter(Model model, HttpServletRequest request, boolean needParam) {
        Object appIdObj = request.getAttribute("appId");
        log.debug("get request parameter appId:{}", appIdObj);
        String appId = "";
        if (appIdObj == null){
            appId = Config.WX_APP_ID;
        } else {
            appId = appIdObj.toString();
        }
        String appSecretKey = request.getAttribute("appSecretKey") == null ? Config.WX_APP_SECRET : request.getAttribute("appSecretKey").toString();
        String accessToken = getAccessToken(appId, appSecretKey);
        log.info("accessToken:{}", accessToken);

        String jsapiTicket = getTicket(accessToken);
        log.info("jsapiTicket:{}", jsapiTicket);
        SortedMap<String, String> params = new TreeMap<String, String>();
        String nonceStr = com.career.genius.utils.StringUtil.randomString(32);
        String timeStamp = Sha1Util.getTimeStamp();
        params.put("noncestr", nonceStr);
        params.put("jsapi_ticket", jsapiTicket);
        params.put("timestamp", timeStamp);
        String url = getRequestUrl(request, needParam);
        log.info("getJsSdkParameter url : {}", url);
        params.put("url", url);
        try {
            String signature = Sha1Util.createSHA1Sign(params);
            model.addAttribute("appId", appId);
            model.addAttribute("nonceStr", nonceStr);
            model.addAttribute("timeStamp", timeStamp);
            model.addAttribute("signature", signature);
            log.info("--- weixin model params: appId:{} --signature:{}", appId, signature);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static String getRequestUrl(HttpServletRequest request, boolean needParams) {
        Map<String, String[]> params = request.getParameterMap();
        String requestUrl = request.getRequestURL().toString();
        if (requestUrl.contains("http://127.0.0.1:8080")){
            requestUrl = requestUrl.replace("http://127.0.0.1:8080", Config.CURRENT_DOMAIN);
        }
        if (!needParams) {
            return requestUrl;
        }
        String queryString = "";
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                queryString += key + "=" + urlEncodeUTF8(value) + "&";
            }
        }
        if (StringUtil.isNotEmpty(queryString)) {
            queryString = queryString.substring(0, queryString.length() - 1);
            requestUrl = requestUrl + "?" + queryString;
        }
        return requestUrl.trim();
    }

}