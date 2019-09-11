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
 * @time 2019-08-20 08:57
 * @discription
 **/
@Data
@Table(name = "template_views")
@Entity
public class TemplateViews extends BaseEntity {

    @Column(name = "template_id",columnDefinition = "varchar(32) comment'模板ID'")
    private String templateId;

    @Column(name = "view_user_openid",columnDefinition = "varchar(50) comment'浏览者openId'")
    private String viewUserOpenId;

    @Column(name = "view_user_name",columnDefinition = "varchar(50) comment'浏览者姓名'")
    private String viewUserName;

    @Column(name = "view_user_head_image",columnDefinition = "varchar(50) comment'浏览者头像'")
    private String viewUserHeadImage;

    @Column(name = "view_times",columnDefinition = "int comment'浏览次数'")
    private int viewTimes;

    @Column(name = "view_duration",columnDefinition = "int comment'浏览时长'")
    private long viewDuration;

    @Column(name = "from_user",columnDefinition = "int comment'分享来自'")
    private String fromUser;

    /**
     * @Author Marker
     * @Date  添加模板被浏览记录
     * @Discription
     **/
    public void addViewInfo(String templateId,String viewUserOpenId,String viewUserName,String viewUserHeadImage, String fromUser) {
        this.templateId = templateId;
        this.viewUserOpenId = viewUserOpenId;
        this.viewUserName = viewUserName;
        this.viewUserHeadImage = viewUserHeadImage;
        this.viewTimes = 1;
        this.fromUser = fromUser;
        super.setCreateTime(new Date());
        super.setUpdateTime(new Date());
    }

    /**
     * 设置浏览时常
     */
    public void changeTemplateViewDuration(String viewDuration) {
        this.viewDuration = Long.valueOf(viewDuration);
        super.setUpdateTime(new Date());
    }

    /**
     * @Author Marker
     * @Date  添加浏览次数
     * @Discription
     **/
    public void addViewTimes() {
        ++ this.viewTimes;
        super.setUpdateTime(new Date());
    }
}
