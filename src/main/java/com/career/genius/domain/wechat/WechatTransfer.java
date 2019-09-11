package com.career.genius.domain.wechat;

import com.career.genius.domain.common.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-09-11 10:19
 * @discription
 **/
@Entity
@Data
public class WechatTransfer extends BaseEntity {

    @Column(name = "init_user",columnDefinition = "varchar(32) comment'模板创始人'")
    private String initUser;

    @Column(name = "template_id",columnDefinition = "varchar(32) comment'模板ID'")
    private String templateId;

    @Column(name = "from_user",columnDefinition = "varchar(32) comment'上次分享者'")
    private String fromUser;




}
