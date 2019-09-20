package com.career.genius.domain.template;

import com.career.genius.domain.common.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-06-19 14:20
 * @discription
 **/
@Data
@Entity
@Table(name = "app_template")
public class Template extends BaseEntity {

    @Column(name = "title",columnDefinition = "varchar(255) comment'标题'")
    private String title;

    @Column(name = "user_id",columnDefinition = "varchar(32) comment'用户ID'")
    private String userId;

    @Column(name = "content",columnDefinition = "text comment'模板详情'")
    private String content;

    @Column(name = "template_name",columnDefinition = "varchar(255) comment'模板名称'")
    private String templateName;

    @Column(name = "description",columnDefinition = "varchar(255) comment'模板内容描述'")
    private String description;

    @Column(name = "title_image",columnDefinition = "varchar(255) comment'分享链接带的图片'")
    private String titleImage;

    @Column(name = "app_content",columnDefinition = "text comment'移动端模板内容'")
    private String appContent;

    @Column(name = "content_type",columnDefinition = "int comment'内容类型<1:链接  2：文本>'")
    private Integer contentType;


    public void addTemplate(String title,String content,String userId,String description, String titleImage, String appContent,Integer contentType) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.description = description;
        this.titleImage = titleImage;
        this.appContent = appContent;
        this.contentType = contentType;
        super.setCreateTime(new Date());
        super.setUpdateTime(new Date());
    }


    public void updateTemplate(String title,String content) {
        this.title = title;
        this.content = content;
        super.setUpdateTime(new Date());
    }

    public void deleteTemplate() {
        super.setIsDeleted(true);
        super.setUpdateTime(new Date());
    }

}
