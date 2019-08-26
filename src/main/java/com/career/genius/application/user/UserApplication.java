package com.career.genius.application.user;

import com.career.genius.application.user.dto.WechatUserInfoDto;
import com.career.genius.domain.user.User;
import com.career.genius.port.dao.user.UserDao;
import com.usm.utils.ObjectHelper;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

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
            user.bindWechatUser(dto);
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
    public String getUserId(JSONObject weiXinOauth2Token, HttpServletRequest request) {
        //TODO 创建用户
        log.info("weiXinOauth2Token:{}", weiXinOauth2Token);
//        {"access_token":"24_Dk0Qxpzf4NYCX_r5sXDH9H8OnW38DQN309Qscu7cMVV4ZAqjIF0t1BHxZHiQ3loQukaMd18CszFZvIQPZALr1QBWkpW_L1Tpm95JiyvdHTk","expires_in":7200,"refresh_token":"24_iq5_SIsIrdCMVg5yDLtfL6PvFF4-RPHi1O2-OZ9rKrBQ1qWh9-R8dXo-nqHL2_IIBrwgB35yi3l5LyhoHtpm1AqgDDcA_LYWQqP9VP4DoIw","openid":"oeepj0XnImNTH4NglMNtK0xu_mQU","scope":"snsapi_userinfo","unionid":"ogaZW5_sQo63fPjoYSv3P9holuUI"}

        return "2c9242a76c2801cd016c4079ce09000d";
    }
}
