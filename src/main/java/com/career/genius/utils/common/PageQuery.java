package com.career.genius.utils.common;

import com.usm.utils.ObjectHelper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-04-22 08:54
 * @discription
 **/
@Data
@ApiModel
public class PageQuery {
    @ApiModelProperty(value = "当前页")
    protected Integer page;
    @ApiModelProperty(value = "单页总数")
    protected Integer limit;
    @ApiModelProperty(value = "默认查询字段")
    private String query;
    //排序规则<0：升序  1：降序>
    @ApiModelProperty(value="排序规则<0：升序  1：降序>",name="sort")
    private Integer sort;
    //排序字段
    @ApiModelProperty(value="排序字段",name="sortBy")
    private String sortBy;
    @ApiModelProperty(value = "控制资源",hidden = true)
    private int sysType;

    public Integer getPage() {
        if (ObjectHelper.isEmpty(this.page)) {
            this.page = 1;
        }
        return this.page;
    }

    public Integer getLimit() {
        if (ObjectHelper.isEmpty(limit)) {
            this.limit = 10;
        }
        return this.limit;
    }

    public String getQuery() {
        this.query = StringEscapeUtils.escapeSql(this.query);
        return this.query;
    }
}
