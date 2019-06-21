package com.career.genius.application.template;

import com.career.genius.application.template.dto.TemplateDto;
import com.career.genius.config.Exception.GeniusException;
import com.career.genius.domain.template.Template;
import com.career.genius.port.dao.template.TemplateDao;
import com.usm.utils.ObjectHelper;
import lombok.Data;
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
public class TemplateApplicaton {

    @Autowired
    TemplateDao templateDao;

    /**
     * 添加模板
     * @param dto
     */
    @Transactional
    public void addTemplate(TemplateDto dto) {
        Template template = new Template();
        template.addTemplate(dto.getTitle(),dto.getContent(),dto.getUserId());
        templateDao.save(template);
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


}
