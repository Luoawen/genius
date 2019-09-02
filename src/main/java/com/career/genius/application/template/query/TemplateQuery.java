package com.career.genius.application.template.query;

import com.career.genius.application.template.vo.TemplateVO;
import com.career.genius.utils.jdbcframework.SupportJdbcTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-06-21 11:52
 * @discription
 **/
@Repository
@Slf4j
public class TemplateQuery {

    @Resource
    SupportJdbcTemplate supportJdbcTemplate;

    public List<TemplateVO> getTemplateList(String userId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT id, template_name, title, content, create_time, update_time ");
        sql.append(" FROM app_template ");
        sql.append(" WHERE user_id = ? ");
        return supportJdbcTemplate.queryForList(sql.toString(),TemplateVO.class,userId);
    }

    public TemplateVO getTemplateInfo(String templateId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT id, template_name, title, content, create_time, update_time,content_type ");
        sql.append(" FROM app_template ");
        sql.append(" WHERE id = ? ");
        TemplateVO templateVO = supportJdbcTemplate.queryForDto(sql.toString(), TemplateVO.class, templateId);
        return templateVO;
    }
}
