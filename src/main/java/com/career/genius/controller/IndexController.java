package com.career.genius.controller;

import com.career.genius.application.template.TemplateApplicaton;
import com.career.genius.application.template.query.TemplateQuery;
import com.career.genius.application.user.dto.CookieUser;
import com.career.genius.config.config.Config;
import com.career.genius.config.config.RedisService;
import com.career.genius.config.enumconf.UaType;
import com.career.genius.domain.user.User;
import com.career.genius.port.dao.user.UserDao;
import com.career.genius.utils.Constants;
import com.career.genius.utils.CookiesUtil;
import com.career.genius.utils.wechat.WechatUtil;
import com.google.gson.Gson;
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
    @Autowired
    RedisService redisService;

    @Autowired
    TemplateApplicaton templateApplicaton;


    @GetMapping(value = "/index")
    public String index(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String sourceUrl = WechatUtil.getRequestUri(req);
        log.info("sourceUrl:{}", sourceUrl);
        CookieUser cookieUser = getCookieUser(req, resp);
        log.info("templateId:{}", req.getParameter("templateId"));
        if (null == cookieUser) {
            UaType uaType = getUaType(req);
            switch (uaType) {
                case wechat: {
                    // 微信浏览器
                    String redirectUrl = getWechatOAuthUrl(req, sourceUrl);
                    log.info("index redirectUrl:{}", redirectUrl);
                    return "redirect:" + redirectUrl;
                }
                case other: {
                    // 其他浏览器
                    break;
                }
            }
        }

        String userId = "";
        if (null != cookieUser) {
            userId = cookieUser.getUserId();
            WechatUtil.getJsSdkParameter(model, req, true);
        }
        model.addAttribute("templateId", req.getParameter("templateId"));
        model.addAttribute("userId", userId);
        return "index";
    }

    private String getWechatOAuthUrl(HttpServletRequest request, String sourceUrl) {
        Object appIdObj = request.getAttribute("appId");
        log.info("get request parameter appId:{}", appIdObj);

        String redirectUri = Config.CURRENT_DOMAIN + "/account/wechatUserLogin?sourceUrl=" + sourceUrl;
        log.info("getWechatOAuthUrl redirectUri:{}", redirectUri);
        return WechatUtil.getCode(appIdObj.toString(), redirectUri, WechatUtil.SCOPESNSAPI_USERINFO, "state");
    }

    private CookieUser getCookieUser(HttpServletRequest req, HttpServletResponse resp) {
        // 获取 loginToken
        String loginToken = CookiesUtil.getValueByCookieName(req, Constants.COOKIE_PARAM);
        log.info("loginToken:{}", loginToken);

        CookieUser cookieUser = null;
        // cookie 校验
        if (StringUtil.isNotEmpty(loginToken)) {
//            cookieUser = new CookieUser("2c9242a76c2801cd016c4079ce09000d", Config.WX_APP_ID);
            if (redisService.exists(loginToken)){
                cookieUser = new Gson().fromJson(redisService.get(loginToken).toString(), CookieUser.class);
                String userId = cookieUser.getUserId();
                User user = userDao.findUserById(userId);
                if (user == null) {
                    CookiesUtil.removeCookie(req, resp, Constants.COOKIE_PARAM, Config.CURRENT_DOMAIN);
                    redisService.remove(loginToken);
                    return null;
                }
            } else {
                CookiesUtil.removeCookie(req, resp, Constants.COOKIE_PARAM, Config.CURRENT_DOMAIN);
                redisService.remove(loginToken);
                return null;
            }
        }

        req.setAttribute("appId", Config.WX_APP_ID);
        return cookieUser;
    }

    private UaType getUaType(HttpServletRequest req) {
        //如果开启用户调试模式 使用调试配置
        String uagent = req.getHeader("user-agent");
        log.info("当前浏览器的user-agent:{}", uagent);
        return UaType.get(uagent.toLowerCase());
    }

    @GetMapping(value = "/clearCookie")
    public void clearCookie(HttpServletRequest req, HttpServletResponse resp){
        CookiesUtil.removeCookie(req, resp, Constants.COOKIE_PARAM, Config.COOKIE_DOMAIN);
    }

}
