package com.career.genius.controller.user;

import com.usm.enums.CodeEnum;
import com.usm.vo.EntityDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@Api(tags = "页面统计")
public class RecordController {


    /**
     *
     * @param times 停留时长，单位：秒
     * @param title 页面标题
     * @param url 统计的URL
     * @return
     */
    @ApiOperation(value = "统计页面停留时长")
    @PostMapping(value = "/record")
    public EntityDto<String> record(String times, String title, String url) {
        log.info("times:{} title:{} url:{}", times, title, url);

        return new EntityDto<>(times, CodeEnum.Success.getCode(),"统计成功");
    }



}
