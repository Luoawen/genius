package com.career.genius.application.template.vo;

import com.career.genius.utils.jdbcframework.ColumnAlias;
import com.usm.utils.ObjectHelper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-09-12 09:47
 * @discription
 **/
@Data
@ApiModel
public class TemplateViewInfoVO {
    @ApiModelProperty(value = "总查看人数")
    @ColumnAlias(value = "totalPerson")
    private Number totalPerson;

    @ApiModelProperty(value = "总浏览次数")
    @ColumnAlias(value = "totalTimes")
    private Number totalTimes;

    @ApiModelProperty(value = "总查看时长<秒>")
    @ColumnAlias(value = "totalDuration")
    private Number totalDuration;

    public Integer getTotalPerson() {
        if (ObjectHelper.isEmpty(totalPerson)) {
            return 0;
        }
        return totalPerson.intValue();
    }

    public Integer getTotalTimes() {
        if (ObjectHelper.isEmpty(totalTimes)) {
            return 0;
        }
        return totalTimes.intValue();
    }

    public Integer getTotalDuration() {
        if (ObjectHelper.isEmpty(totalDuration)) {
            return 0;
        }
        return totalDuration.intValue();
    }
}
