package com.career.genius.application.template;

import com.alibaba.fastjson.JSON;
import com.career.genius.application.template.dto.TemplateDto;
import com.career.genius.application.template.dto.ViewTemplateDto;
import com.career.genius.application.template.query.TemplateQuery;
import com.career.genius.application.template.vo.TemplateVO;
import com.career.genius.config.Exception.GeniusException;
import com.career.genius.domain.template.Template;
import com.career.genius.domain.template.TemplateViews;
import com.career.genius.domain.user.User;
import com.career.genius.port.dao.template.TemplateDao;
import com.career.genius.port.dao.template.ViewTemplateDao;
import com.career.genius.port.dao.user.UserDao;
import com.usm.utils.ObjectHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-06-19 14:33
 * @discription
 **/
@Service
@Data
@Slf4j
public class TemplateApplicaton {

    public static final String URI = "http://m.yunmaidianzi.com/index";

    @Autowired
    TemplateDao templateDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ViewTemplateDao viewTemplateDao;

    @Autowired
    TemplateQuery templateQuery;



    /**
     * 添加模板
     * @param dto
     */
    @Transactional
    public TemplateDto addTemplate(TemplateDto dto) {
        Template template = new Template();
        template.addTemplate(dto.getTitle(),dto.getContent(),dto.getUserId(),dto.getDescription(),dto.getTitleImage(), JSON.toJSONString(dto.getAppContent()),dto.getContentType());
        template = templateDao.save(template);
        TemplateDto result = new TemplateDto();
        result.setResult(template.getId(),dto.getUserId(),URI + "?userId=" + dto.getUserId() + "&templateId=" + template.getId(),dto.getDescription(),dto.getTitleImage());
        return result;
    }

    @Transactional
    public void updateTemplate(TemplateDto dto) throws GeniusException {
        Template template = templateDao.findTemplateById(dto.getTemplateId());

        if (ObjectHelper.isEmpty(template)) {
            throw new GeniusException("模板不存在！");
        }
        template.updateTemplate(dto.getTitle(),dto.getContent());
        templateDao.save(template);
    }

    @Transactional
    public void deleteTemplate(String templateId) throws GeniusException {
        Template template = templateDao.findTemplateById(templateId);

        if (ObjectHelper.isEmpty(template)) {
            throw new GeniusException("模板不存在！");
        }
        template.deleteTemplate();
        templateDao.save(template);
    }

    /**
     * 添加模板被浏览数据
     * @param dto
     * @return
     * @throws GeniusException
     */
    @Transactional
    public TemplateVO addViewInfo(ViewTemplateDto dto) throws GeniusException {
        Template template = templateDao.findTemplateById(dto.getTemplateId());
        if (ObjectHelper.isEmpty(template)) {
            throw new GeniusException("模板不存在！");
        }
        TemplateViews views = viewTemplateDao.findTemplateViewsByViewUserOpenIdAndTemplateId(dto.getViewUserOpenId(), dto.getTemplateId());
        if (ObjectHelper.isNotEmpty(views)) {
            views.addViewTimes();
        } else {
            views = new TemplateViews();
            views.addViewInfo(dto.getTemplateId(),dto.getViewUserOpenId(),dto.getViewUserName(),dto.getViewUserHeadImage(),dto.getUserId());
        }
        views = viewTemplateDao.save(views);
        TemplateVO templateVO = templateQuery.getTemplateInfo(dto.getTemplateId());
        User user = userDao.findUserByOpenId(dto.getViewUserOpenId());
        if (ObjectHelper.isEmpty(user)) {
            user = new User();
            user.addUser(dto.getViewUserName(),"",dto.getViewUserHeadImage(),dto.getViewUserOpenId(),"");
            userDao.save(user);
        }
        templateVO.setViewId(views.getId());
        return templateVO;
    }

    /**
     * @Author Marker
     * @Date  添加浏览时长
     * @Discription
     **/
    @Transactional
    public void updateTemplateViewTimes(String viewId, String times) throws GeniusException {
        TemplateViews templateView = viewTemplateDao.findTemplateViewsById(viewId);
        if (templateView != null) {
            templateView.changeTemplateViewDuration(times);
            viewTemplateDao.save(templateView);
        }
    }





}
