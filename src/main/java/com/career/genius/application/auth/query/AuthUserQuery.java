package com.career.genius.application.auth.query;

import com.career.genius.application.auth.dto.AuthUserInfoDto;
import com.career.genius.application.user.vo.MainMuenVo;
import com.career.genius.config.Exception.GeniusException;
import com.career.genius.utils.jdbcframework.SupportJdbcTemplate;
import com.usm.utils.ObjectHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-05-31 15:20
 * @discription
 **/
@Repository
@Slf4j
public class AuthUserQuery {

    @Autowired
    SupportJdbcTemplate supportJdbcTemplate;

    public AuthUserInfoDto queryForUser(String phone) throws GeniusException {
        if (ObjectHelper.isEmpty(phone)) {
            throw new GeniusException("手机号为空");
        }

        String sql = " SELECT id, user_name, head_image, open_id, phone FROM app_user WHERE phone = ? ";

        AuthUserInfoDto infoDto = this.supportJdbcTemplate.queryForDto(sql, AuthUserInfoDto.class, phone);
        if (null == infoDto) {
            throw new GeniusException("用户不存在");
        }
        return infoDto;
    }

    /**
     * 首页数据
     * @param userId
     * @return
     */
    public MainMuenVo getMainVO(String userId) {
        String sql = "SELECT id user_id, open_id FROM app_user WHERE id = ?";
        MainMuenVo mainMuenVo = supportJdbcTemplate.queryForDto(sql, MainMuenVo.class, userId);
        mainMuenVo.setReleased(getRelease(userId));
        mainMuenVo.setLooked(getViewTimes(userId));
        return mainMuenVo;
    }

    /**
     * @Author Marker
     * @Date  发布过
     * @Discription
     **/
    public Integer getRelease(String userId) {
        String sql = " SELECT COUNT(1) FROM app_template WHERE is_delete = 0 AND user_id = ?";
        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql,Integer.class,userId);
    }

    /**
     * 被看过
     * @param userId
     * @return
     */
    public Integer getViewTimes(String userId) {
        String sql = " SELECT SUM(view_times) FROM template_views WHERE user_id = ? ";
        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql,Number.class,userId).intValue();
    }



}
