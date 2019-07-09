package com.career.genius.application.user;

import com.career.genius.application.user.dto.WechatUserInfoDto;
import com.career.genius.domain.user.User;
import com.career.genius.port.dao.user.UserDao;
import com.usm.utils.ObjectHelper;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-07-09 12:49
 * @discription
 **/
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
}
