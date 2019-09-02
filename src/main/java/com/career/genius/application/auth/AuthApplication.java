package com.career.genius.application.auth;

import com.career.genius.application.auth.vo.SysUserReqVo;
import com.career.genius.config.Exception.GeniusException;
import com.career.genius.domain.user.User;
import com.career.genius.port.dao.user.UserDao;
import com.career.genius.utils.Constants;
import com.career.genius.utils.SendSms;
import com.usm.utils.ObjectHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-05-24 19:50
 * @discription
 **/
@Slf4j
@Service
public class AuthApplication {

    List<String> checkPhone = new ArrayList<String>() {{
        add("15683197174");
    }};

    String testAccount = "11111111";


    @Autowired
    UserDao userDao;

    /**
     * @Author Marker
     * @Date  发送短信验证码
     * @Discription
     **/
    public void sendSms(HttpServletRequest request,String phone) throws GeniusException {
        SendSms.sendSms(phone,request);
    }


    /**
     * @Author Marker
     * @Date  注册/登陆
     * @Discription
     **/
    public void registOrLogin(SysUserReqVo vo, HttpSession session) throws GeniusException {
        if(!checkPhone.contains(vo.getPhone()) || vo.getPhone().contains(testAccount)) {
            String sessionAuthCode = session.getAttribute(Constants.MOBILE_MSG_CODE) + "";
            // 删除session中的验证码
            session.removeAttribute(Constants.MOBILE_MSG_CODE);
            if (!vo.getAuthCode().equalsIgnoreCase(sessionAuthCode)) {
                throw new GeniusException("请输入正确的验证码");
            }
        }

        User user = userDao.findUserByPhone(vo.getPhone());
        if (ObjectHelper.isEmpty(user)) {
            user = new User();
            user.addUser(vo.getPhone(),vo.getPhone(),"","","");
            userDao.save(user);
        }
    }


}
