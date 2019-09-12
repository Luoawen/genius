package com.career.genius.application.template.vo;

import com.alibaba.fastjson.JSONArray;
import com.career.genius.utils.jdbcframework.ColumnAlias;
import com.usm.utils.ObjectHelper;
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
    @ColumnAlias(value = "app_content")
    private String content;

    @ApiModelProperty(value = "创建时间")
    @ColumnAlias(value = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @ColumnAlias(value = "update_time")
    private Date updateTime;

    @ApiModelProperty(value = "模板内容描述")
    @ColumnAlias(value = "description")
    private String description;

    @ApiModelProperty(value = "分享链接带的图片")
    @ColumnAlias(value = "title_image")
    private String titleImage;

    @ApiModelProperty(value = "用户ID")
    @ColumnAlias(value = "user_id")
    private String userId;


    @ApiModelProperty(value = "浏览记录ID")
    @ColumnAlias(value = "viewId")
    private String viewId;

    @ApiModelProperty(value = "内容类型<1:链接  2：文本>")
    @ColumnAlias(value = "content_type")
    private Integer contentType;

    @ApiModelProperty(value = "今天被浏览量")
    @ColumnAlias(value = "today")
    private Long today;

    @ApiModelProperty(value = "总浏览量")
    @ColumnAlias(value = "total")
    private Long total;

    @ApiModelProperty(value = "仅七天浏览量")
    @ColumnAlias(value = "seven")
    private Long seven;

    public JSONArray getContent() {
        if (ObjectHelper.isNotEmpty(this.content)) {
            return JSONArray.parseArray(this.content);
        }
        return null;
    }


    public Long getToday() {
        if (ObjectHelper.isEmpty(this.today)) {
            return 0l;
        }
        return today;
    }

    public Long getTotal() {
        if (ObjectHelper.isEmpty(this.total)) {
            return 0l;
        }
        return total;
    }

    public Long getSeven() {
        if (ObjectHelper.isEmpty(this.seven)) {
            return 0l;
        }
        return seven;
    }
}
