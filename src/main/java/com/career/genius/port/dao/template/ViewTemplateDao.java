package com.career.genius.port.dao.template;

import com.career.genius.domain.template.TemplateViews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewTemplateDao extends JpaRepository<TemplateViews,String> {
    TemplateViews findTemplateViewsById(String templateId);

    TemplateViews findTemplateViewsByViewUserOpenIdAndTemplateId(String openId,String TemplateId);

}
