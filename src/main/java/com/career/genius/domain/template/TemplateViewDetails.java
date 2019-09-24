package com.career.genius.domain.template;

import com.career.genius.domain.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-09-23 08:51
 * @discription
 **/
@Entity
@Table(name = "template_view_detail")
public class TemplateViewDetails extends BaseEntity {

    @Column(name = "template_id",columnDefinition = "varchar(32) comment'模板ID'")
    private String templateId;

    @Column(name = "view_user_openid",columnDefinition = "varchar(50) comment'浏览者openId'")
    private String viewUserOpenId;

    @Column(name = "view_user_name",columnDefinition = "varchar(50) comment'浏览者姓名'")
    private String viewUserName;

    @Column(name = "view_user_head_image",columnDefinition = "varchar(50) comment'浏览者头像'")
    private String viewUserHeadImage;
}
