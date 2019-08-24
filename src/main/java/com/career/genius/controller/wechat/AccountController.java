package com.career.genius.controller.wechat;

import com.career.genius.application.user.UserApplication;
import com.career.genius.application.user.dto.CookieUser;
import com.career.genius.config.config.Config;
import com.career.genius.port.setvice.WxService;
import com.career.genius.utils.Constants;
import com.career.genius.utils.CookiesUtil;
import com.career.genius.utils.StringUtil;
import com.career.genius.utils.wechat.WechatUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录注册验证控制器
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    UserApplication userApplication;


    /**
     * 微信授权登录
     * 把用户ID放到缓存
     * 设置本地cookie
     * @return
     */
    @GetMapping(value = "/wechatUserLogin")
    public String getWeixinUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String sourceUrl = request.getParameter("sourceUrl");
        String appId = request.getAttribute("appId") == null ? Config.WX_APP_ID : request.getAttribute("appId").toString();
        String appSecretKey = request.getAttribute("appSecretKey") == null ? Config.WX_APP_SECRET : request.getAttribute("appSecretKey").toString();
        logger.info("微信授权回调成功,开始授权回调code:{} appid:{} appSecretKey:{} url:{}", code, appId, appSecretKey, sourceUrl);
        if (!StringUtil.empty(code)) {
            JSONObject weiXinOauth2Token = WechatUtil.getAccessToken(appId, appSecretKey, code);
            if (weiXinOauth2Token != null) {
                String userId = userApplication.getUserId(weiXinOauth2Token, request);
                if (StringUtil.isNotEmpty(userId)) {
                    String loginToken = WxService.genUUID();
                    CookieUser cookieUser = new CookieUser(userId, appId);
//                    AliOcsMemcachedUtil.setToCache(loginToken, cookieUser);
                    // 获得配置cookie有效时长
                    int time = StringUtil.parseInt(Config.COOKIE_DOMAIN, Constants.DEFAULT_COOKIE_MAXAGE);
                    // 写入cookie
                    CookiesUtil.addCookie(Constants.COOKIE_PARAM, loginToken, Config.COOKIE_DOMAIN, time, response);
                    logger.info("获取微信用户成功,进行缓存并跳转到业务 url:{} code:{}", sourceUrl, code);
                    return "redirect:" + sourceUrl;
                } else {
                    logger.error("user is empty!");
                }
            } else {
                logger.error("weiXinOauth2Token is empty!");
            }
        }
        logger.error("微信授权回调成功,微信授权失败code:{} is empty ! sourceUrl:{}", code, sourceUrl);
        return "error";
    }


}
