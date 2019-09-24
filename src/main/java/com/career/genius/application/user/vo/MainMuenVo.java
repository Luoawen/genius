package com.career.genius.application.user.vo;

import com.career.genius.utils.jdbcframework.ColumnAlias;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-06-21 14:01
 * @discription
 **/
@Api(tags = "首页")
@Data
public class MainMuenVo {

    @ColumnAlias(value = "user_id")
    private String userId;

    @ApiModelProperty(value = "微信openId<为空则未绑定>")
    @ColumnAlias(value = "open_id")
    private String openId;

    @ApiModelProperty(value = "转发过")
    private Integer transfered;

    @ApiModelProperty(value = "被看过")
    private Integer looked;

    @ApiModelProperty(value = "发布过")
    private Integer released;
}
