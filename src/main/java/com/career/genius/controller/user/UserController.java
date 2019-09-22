package com.career.genius.controller.user;

import com.career.genius.application.user.UserApplication;
import com.career.genius.application.user.dto.WechatUserInfoDto;
import com.career.genius.application.user.vo.MainMuenVo;
import com.career.genius.config.sys.Submit;
import com.usm.enums.CodeEnum;
import com.usm.vo.BaseResultDto;
import com.usm.vo.EntityDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-05-21 22:11
 * @discription
 **/

@RestController
@Data
@Slf4j
@Api(tags = "首页")
public class UserController {

    @Autowired
    UserApplication userApplication;

    @ApiOperation(value = "首页数据接口")
    @GetMapping(value = "/users/{userId}")
    public EntityDto<MainMuenVo> getUserData() {
        return new EntityDto<>(new MainMuenVo(), CodeEnum.Success.getCode(),"成功");
    }

    @Submit
    @GetMapping(value = "/foo")
    public Object foo() {
        return UUID.randomUUID().toString();
    }

    /**
     * @Author Marker
     * @Date  绑定微信用户信息
     * @Discription
     **/
    @ApiOperation(("绑定微信用户信息"))
    @PatchMapping(value = "/user/wechat/info")
    public BaseResultDto bindWeChatUser(@RequestBody WechatUserInfoDto dto) {
        userApplication.bindWeChatUser(dto);
        return new BaseResultDto(CodeEnum.Success.getCode(),"成功");
    }


}
