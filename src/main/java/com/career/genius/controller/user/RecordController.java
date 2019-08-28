package com.career.genius.controller.user;

import com.career.genius.application.template.TemplateApplicaton;
import com.career.genius.config.Exception.GeniusException;
import com.career.genius.utils.StringUtil;
import com.usm.enums.CodeEnum;
import com.usm.vo.EntityDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@Api(tags = "页面统计")
public class RecordController {

    @Autowired
    TemplateApplicaton templateApplicaton;


    /**
     * @param viewsId 访问记录ID
     * @param times 停留时长，单位：秒
     * @param title 页面标题
     * @param url 统计的URL
     * @return
     */
    @ApiOperation(value = "统计页面停留时长")
    @PostMapping(value = "/record")
    public EntityDto<String> record(String viewsId, String times, String title, String url) throws GeniusException {
        log.info("viewsId:{} times:{} title:{} url:{}", viewsId, times, title, url);
        if (StringUtil.isNotEmpty(viewsId)) {
            templateApplicaton.updateTemplateViewTimes(viewsId, times);
            return new EntityDto<>(times, CodeEnum.Success.getCode(), "统计成功");
        } else {
            return new EntityDto<>(times, CodeEnum.InvalidParameter.getCode(), "访问记录不存在！");
        }
    }



}
