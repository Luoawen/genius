package com.career.genius.application.template.query;

import com.career.genius.application.template.vo.TemplateVO;
import com.career.genius.utils.jdbcframework.SupportJdbcTemplate;
import com.usm.utils.ObjectHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    /**
     * 获取用户发布的所有素材
     * @param userId
     * @return
     */
    public List<TemplateVO> getTemplateList(String userId,String query) {
        StringBuffer sql = new StringBuffer();
        ArrayList<String> param = new ArrayList<>();
        sql.append(" SELECT t.id, t.template_name, t.title, t.content, t.create_time,");
        sql.append(" t.update_time, t.user_id, t.app_content, t.description, t.title_image, t.content_type, ");
        sql.append(" v1.today, v2.total, v3.seven ");
        sql.append(" FROM app_template t ");
        sql.append(" LEFT JOIN (SELECT COUNT(1) today,template_id FROM template_views WHERE is_delete = 0 AND  to_days(create_time) = to_days(now()) GROUP BY template_id) v1 ");
        sql.append(" ON t.id = v1.template_id ");
        sql.append(" LEFT JOIN (SELECT COUNT(1) total,template_id FROM template_views WHERE is_delete = 0 GROUP BY template_id) v2 ");
        sql.append(" ON t.id = v2.template_id ");
        sql.append(" LEFT JOIN (SELECT COUNT(1) seven,template_id  FROM template_views WHERE is_delete = 0 AND  DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time) GROUP BY template_id) v3 ");
        sql.append(" ON t.id = v3.template_id ");
        sql.append(" WHERE user_id = ? AND is_delete = 0 ");
        param.add(userId);
        if (ObjectHelper.isNotEmpty(query)) {
            sql.append(" AND t.app_content LIKE ? ");
            param.add("%" + query + "%");
        }
        List<TemplateVO> result = supportJdbcTemplate.queryForList(sql.toString(), TemplateVO.class, param.toArray());
        return result;
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
