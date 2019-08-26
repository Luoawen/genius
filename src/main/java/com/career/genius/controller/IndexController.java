package com.career.genius.controller;

import com.career.genius.application.user.dto.CookieUser;
import com.career.genius.config.config.Config;
import com.career.genius.port.dao.user.UserDao;
import com.career.genius.utils.Constants;
import com.career.genius.utils.CookiesUtil;
import com.career.genius.utils.wechat.WechatUtil;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-08-22 08:52
 * @discription
 **/
@Slf4j
@Controller
public class IndexController {

    @Autowired
    UserDao userDao;

    @GetMapping(value = "/index")
    public String index(HttpServletRequest req, Model model) {
        String sourceUrl = WechatUtil.getRequestUri(req);
        log.info("sourceUrl:{}", sourceUrl);
        CookieUser cookieUser = getCookieUser(req);
        if (null == cookieUser) {
            String redirectUrl = getWechatOAuthUrl(req, sourceUrl);
            log.info("index redirectUrl:{}", redirectUrl);
            return "redirect:" + redirectUrl;
        }

        model.addAttribute("userId", cookieUser.getUserId());
//        String phone = req.getParameter("phone");
//        if (StringUtil.isEmpty(phone)){
//            phone = "110";
//        }
//        model.addAttribute("phone", phone);

        WechatUtil.getJsSdkParameter(model, req, true);
        return "index";
    }

    private String getWechatOAuthUrl(HttpServletRequest request, String sourceUrl) {
        Object appIdObj = request.getAttribute("appId");
        log.info("get request parameter appId:{}", appIdObj);

        String redirectUri = Config.CURRENT_DOMAIN + "/account/wechatUserLogin?sourceUrl=" + sourceUrl;
        log.info("getWechatOAuthUrl redirectUri:{}", redirectUri);
        return WechatUtil.getCode(appIdObj.toString(), redirectUri, WechatUtil.SCOPESNSAPI_USERINFO, "state");
    }

    private CookieUser getCookieUser(HttpServletRequest req) {
        // 获取 loginToken
        String loginToken = CookiesUtil.getValueByCookieName(req, Constants.COOKIE_PARAM);
        log.info("loginToken:{}", loginToken);

        // 测试代码
        if (StringUtil.isNotEmpty(loginToken)) {
            log.info("sumulate testUserId");
            CookieUser cookieUser = new CookieUser("2c9242a76c2801cd016c4079ce09000d", Config.WX_APP_ID);
            return cookieUser;
        }

        CookieUser cookieUser = null;

        //cookie 校验
//        if (StringUtil.isNotEmpty(loginToken)) {
//            cookieUser = AliOcsMemcachedUtil.getCache(loginToken, CookieUser.class);
//            if (cookieUser != null){
//                String userId = cookieUser.getUserId();
//                User user = userDao.findUserById(userId);
//                if (user == null) {
//                    CookiesUtil.removeCookie(req, resp, Constants.COOKIE_PARAM, Config.CURRENT_DOMAIN);
//                    AliOcsMemcachedUtil.deleteCache(loginToken);
//                    return null;
//                }
//            } else {
//                CookiesUtil.removeCookie(req, resp, Constants.COOKIE_PARAM, Config.CURRENT_DOMAIN);
//                AliOcsMemcachedUtil.deleteCache(loginToken);
//                return null;
//            }
//        }

        req.setAttribute("appId", Config.WX_APP_ID);
        return cookieUser;
    }

}
