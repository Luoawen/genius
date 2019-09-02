package com.career.genius.application.template.vo;

import com.career.genius.utils.jdbcframework.ColumnAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-06-21 11:47
 * @discription
 **/
@ApiModel("模板")
@Data
public class TemplateVO {

    @ApiModelProperty(value = "模板ID")
    @ColumnAlias(value = "id")
    private String templateId;

    @ApiModelProperty(value = "模板名称")
    @ColumnAlias(value = "template_name")
    private String templateName;

    @ApiModelProperty(value = "标题")
    @ColumnAlias(value = "title")
    private String title;

    @ApiModelProperty(value = "模板内容")
    @ColumnAlias(value = "content")
    private String content;

    @ApiModelProperty(value = "创建时间")
    @ColumnAlias(value = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @ColumnAlias(value = "update_time")
    private Date updateTime;


    @ApiModelProperty(value = "浏览记录ID")
    @ColumnAlias(value = "viewId")
    private String viewId;

    @ColumnAlias(value = "content_type")
    private Integer contentType;
}
