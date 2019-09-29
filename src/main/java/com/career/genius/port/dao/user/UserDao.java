package com.career.genius.port.dao.user;

import com.career.genius.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,String> {
    User findUserByPhone(String phone);

    User findUserById(String id);

    User findUserByOpenId(String openId);

    User findUserByWechatUniqueId(String uniqueId);
}
