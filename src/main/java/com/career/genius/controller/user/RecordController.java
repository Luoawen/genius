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


    @ApiOperation(value = "统计页面停留时长")
    @PostMapping(value = "/record")
    public EntityDto<String> record(String times, String referrer, String title, String url) {
        log.info("times:{} referrer:{} title:{} url:{}", times, referrer, title, url);

        return new EntityDto<>(times, CodeEnum.Success.getCode(),"统计成功");
    }



}
