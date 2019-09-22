package com.career.genius.application.template.dto;

import com.career.genius.utils.jdbcframework.ColumnAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-08-20 09:07
 * @discription
 **/
@Data
@ApiModel
public class ViewTemplateDto {

    @ColumnAlias(value = "template_id")
    @ApiModelProperty(value = "模板ID")
    private String templateId;

    @ApiModelProperty(value = "被分享用户ID")
    private String userId;

    @ApiModelProperty(value = "分享用户ID")
    private String shareUserId;

    @ApiModelProperty(value = "微信授权code")
    private String wechatCode;

    @ColumnAlias(value = "view_user_openid")
    @ApiModelProperty(value = "浏览者openId")
    private String viewUserOpenId;

    @ColumnAlias(value = "view_user_name")
    @ApiModelProperty(value = "浏览者姓名")
    private String viewUserName;

    @ColumnAlias(value = "view_user_head_image")
    @ApiModelProperty(value = "浏览者头像")
    private String viewUserHeadImage;

    public void setWechatUserInfo(String viewUserName,String viewUserHeadImage) {
        this.viewUserName = viewUserName;
        this.viewUserHeadImage = viewUserHeadImage;
    }


}
