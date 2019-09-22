package com.career.genius.application.template.vo;

import com.career.genius.utils.jdbcframework.ColumnAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-09-20 21:08
 * @discription
 **/
@Data
@ApiModel
public class AllTemplateViewVO {

    @ColumnAlias(value = "template_id")
    private String templateId;

    @ApiModelProperty(value = "浏览者openId")
    @ColumnAlias(value = "view_user_openid")
    private String viewerOpenId;

    @ApiModelProperty(value = "浏览者名")
    @ColumnAlias(value = "view_user_name")
    private String viewerNickName;

    @ApiModelProperty(value = "浏览者头像")
    @ColumnAlias(value = "view_user_head_image")
    private String viewerHeadImage;

    @ApiModelProperty(value = "浏览次数")
    @ColumnAlias(value = "view_times")
    private Number viewTimes;

    @ApiModelProperty(value = "浏览时长")
    @ColumnAlias(value = "view_duration")
    private Number viewDuration;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    @ColumnAlias(value = "create_time")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    @ColumnAlias(value = "update_time")
    private Date updateTime;

    public Integer getViewTimes() {
        return viewTimes.intValue();
    }

    public Integer getViewDuration() {
        return viewDuration.intValue();
    }
}
