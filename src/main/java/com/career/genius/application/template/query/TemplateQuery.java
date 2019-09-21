package com.career.genius.application.template.query;

import com.career.genius.application.template.vo.AllTemplateViewVO;
import com.career.genius.application.template.vo.TemplateDetailVO;
import com.career.genius.application.template.vo.TemplateVO;
import com.career.genius.application.template.vo.TemplateViewInfoVO;
import com.career.genius.domain.template.TemplateViews;
import com.career.genius.port.dao.template.TemplateDao;
import com.career.genius.port.dao.template.ViewTemplateDao;
import com.career.genius.utils.common.PageQuery;
import com.career.genius.utils.jdbcframework.SupportJdbcTemplate;
import com.usm.enums.CodeEnum;
import com.usm.utils.ObjectHelper;
import com.usm.vo.PageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ViewTemplateDao viewTemplateDao;
    /**
     * 获取用户发布的所有素材
     * @param userId
     * @return
     */
    public PageDto<TemplateVO> getTemplateList(String userId,PageQuery query) {
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
        if (ObjectHelper.isNotEmpty(query.getQuery())) {
            sql.append(" AND t.app_content LIKE ? ");
            param.add("%" + query + "%");
        }
        sql.append(" ORDER BY t.create_time DESC ");
        return supportJdbcTemplate.queryForPage(sql, TemplateVO.class, param,query);
    }

    public TemplateVO getTemplateInfo(String templateId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT id, template_name, title, content, create_time, update_time,content_type ");
        sql.append(" FROM app_template ");
        sql.append(" WHERE id = ? ");
        return supportJdbcTemplate.queryForDto(sql.toString(), TemplateVO.class, templateId);
    }

    public TemplateViewInfoVO getTemplateViewInfo(String templateId) {
        StringBuffer sql = new StringBuffer();

        sql.append(" SELECT SUM(view_duration) totalDuration, SUM(view_times) totalTimes, t1.totalPerson ");
        sql.append(" FROM template_views t ");
        sql.append(" INNER JOIN (SELECT COUNT(1) totalPerson FROM template_views WHERE is_delete = 0 AND template_id = ?) t1 ");
        sql.append(" WHERE t.is_delete = 0 AND t.template_id = ?");

        return supportJdbcTemplate.queryForDto(sql.toString(),TemplateViewInfoVO.class,templateId,templateId);
    }

    public PageDto<TemplateDetailVO> getTemplateViewDetail(String templateId) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> param = new ArrayList<>();
        sql.append(" SELECT v.template_id, v.view_times, v.view_user_head_image, v.view_user_name, v.view_user_openid, v.view_duration, ");
        sql.append(" v.create_time, v.update_time ");
        sql.append(" FROM template_views v ");
        sql.append(" WHERE v.is_delete = 0 AND v.template_id = ? ");
        param.add(templateId);
        sql.append(" ORDER BY v.create_time DESC ");
        PageQuery query = new PageQuery();
        return this.supportJdbcTemplate.queryForPage(sql,TemplateDetailVO.class,param,query);
    }

    public PageDto<AllTemplateViewVO> getTemplateViewsDetail(String userId,PageQuery queryParam) {
        StringBuffer sql = new StringBuffer();
        PageDto<AllTemplateViewVO> result = new PageDto<>();

        sql.append(" SELECT SUM(view_times) view_times,SUM(view_duration) view_duration,user_id,view_user_openid,view_user_head_image,view_user_name,");
        sql.append(" create_time, update_time ");
        sql.append(" FROM template_views  ");
        sql.append(" WHERE user_id = ? GROUP BY view_user_openid ORDER BY create_time");


        List<AllTemplateViewVO> viewVOS = this.supportJdbcTemplate.queryForList(sql.toString(), AllTemplateViewVO.class, userId);
        result.setData(viewVOS);
        sql.setLength(0);
        sql.append(" SELECT COUNT(1) FROM template_views where user_id = ?");
        Integer total = supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class, userId);
        result.setTotal(total);
        result.setPageSize(queryParam.getLimit());
        result.setCurrentpage(queryParam.getPage());
        int temp = (total + queryParam.getLimit() - 1) / queryParam.getLimit();
        result.setTotalPage(temp <= 0 ? 1 : temp);
        result.setCode(CodeEnum.Success.getCode());
        result.setMsg("成功");
        return result;
    }

   /* public PageDto<TemplateViewDetailVO> getTemplateViewDetail(String templateId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT view_times, view_duration, v.template_id, v.create_time ");
        sql.append(" FROM template_views ");
        sql.append(" WHERE  ")

    }*/
}
