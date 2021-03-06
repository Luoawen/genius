package com.career.genius.application.user;

import com.career.genius.application.user.dto.WechatUserInfoDto;
import com.career.genius.application.wechat.bean.WechatOauth2Token;
import com.career.genius.application.wechat.bean.WechatUserInfo;
import com.career.genius.domain.user.User;
import com.career.genius.port.dao.user.UserDao;
import com.career.genius.port.setvice.WxService;
import com.usm.utils.ObjectHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-07-09 12:49
 * @discription
 **/
@Slf4j
@Data
@Service
public class UserApplication {

    @Autowired
    UserDao userDao;

    @Autowired
    WxService wxService;

    /**
     * @Author Marker
     * @Date  绑定微信用户
     * @Discription
     **/
    @Transactional
    public void bindWeChatUser(WechatUserInfoDto dto) {
        User user = userDao.findUserById(dto.getUserId());
        if (ObjectHelper.isEmpty(user)) {
            user = new User();
            user.bindWechatUser(dto.getUniqueId(),dto.getUserName(),dto.getHeadImage(),dto.getOpenId());
        } else {
            user.addUser(dto.getUserName(),dto.getPhone(),dto.getHeadImage(),dto.getOpenId(),"");
        }
        userDao.save(user);
    }

    public User getUserById(String userId) {
        return userDao.findUserById(userId);
    }

    /**
     * 生成微信用户
     * @param weiXinOauth2Token
     * @param request
     * @return
     */
    public String getUserId(JSONObject weiXinOauth2Token, HttpServletRequest request) throws UnsupportedEncodingException {
        log.info("weiXinOauth2Token:{}", weiXinOauth2Token);
//        {"access_token":"24_Dk0Qxpzf4NYCX_r5sXDH9H8OnW38DQN309Qscu7cMVV4ZAqjIF0t1BHxZHiQ3loQukaMd18CszFZvIQPZALr1QBWkpW_L1Tpm95JiyvdHTk","expires_in":7200,"refresh_token":"24_iq5_SIsIrdCMVg5yDLtfL6PvFF4-RPHi1O2-OZ9rKrBQ1qWh9-R8dXo-nqHL2_IIBrwgB35yi3l5LyhoHtpm1AqgDDcA_LYWQqP9VP4DoIw","openid":"oeepj0XnImNTH4NglMNtK0xu_mQU","scope":"snsapi_userinfo","unionid":"ogaZW5_sQo63fPjoYSv3P9holuUI"}
//        {"access_token":"24_eDcOy8FDkdi4NvOpNE2oQa7qCzMXS65ZPvhkUs2ys_uV_W8hiqlbHiKPxxfQKgg8VtOIT-r_SnzEz-GZDwi4Ew","expires_in":7200,"refresh_token":"24_pLEUAhJqN4cTQirTnnpAmnPp_svoW7mI1OmRJEiIJ_zrAjmUJ0dkIJQYlA769Df1IfA5-bcCTS91c-bxs7vqJA","openid":"oeepj0XnImNTH4NglMNtK0xu_mQU","scope":"snsapi_base"}
       // WechatOauth2Token wechatToken = (WechatOauth2Token) JSONObject.toBean(weiXinOauth2Token);
        log.info("微信个人信息--------------->{}",weiXinOauth2Token.toString());
        String openId = weiXinOauth2Token.getString("openid");
        String uniqueId = weiXinOauth2Token.getString("unionid");
        String accessToken = weiXinOauth2Token.getString("access_token");
        User user = userDao.findUserByWechatUniqueId(uniqueId);

        //用户存在则直接返回用户ID
        if (ObjectHelper.isNotEmpty(user)) {
            return user.getId();
        }
        //不存在生成用户
        //TODO 获取微信详细信息失败
        WechatUserInfo wechatUserInfo = wxService.getWechatInfoByTokenAndOpenId(accessToken, openId);
        user = new User();
        user.bindWechatUser(wechatUserInfo.getUnionid(), wechatUserInfo.getNickname(), wechatUserInfo.getHeadImgUrl(), openId);
        user = userDao.save(user);
        return user.getId();
    }
}
