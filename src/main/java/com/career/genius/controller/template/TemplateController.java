package com.career.genius.controller.template;

import com.career.genius.application.template.TemplateApplicaton;
import com.career.genius.application.template.dto.TemplateDto;
import com.career.genius.application.template.query.TemplateQuery;
import com.career.genius.application.template.vo.TemplateDetailVO;
import com.career.genius.application.template.vo.TemplateVO;
import com.career.genius.application.template.vo.TemplateViewInfoVO;
import com.career.genius.config.Exception.GeniusException;
import com.career.genius.utils.common.PageQuery;
import com.usm.dto.SearchDto;
import com.usm.enums.CodeEnum;
import com.usm.vo.BaseResultDto;
import com.usm.vo.EntityDto;
import com.usm.vo.ListDto;
import com.usm.vo.PageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-06-19 14:26
 * @discription
 **/
@RestController
@Data
@RequestMapping(value = "")
@Api(tags = "模板接口")
public class TemplateController {

    @Autowired
    TemplateApplicaton templateApplicaton;

    @Autowired
    TemplateQuery templateQuery;

    /**
     * 新增模板
     * @param dto
     * @return
     */
    @ApiOperation(value = "新增模板")
    @PostMapping(value = "/template")
    public EntityDto<TemplateDto> addTemplate(@RequestBody TemplateDto dto) {
        TemplateDto result = templateApplicaton.addTemplate(dto);
        return new EntityDto<>(result,CodeEnum.Success.getCode(),"新增模板成功");
    }

    /**
     * 修改模板
     * @param dto
     * @return
     */
    @ApiOperation(value = "修改模板")
    @PutMapping(value = "/template/{templateId}")
    public BaseResultDto updateTemplate(@PathVariable("templateId") String templateId,@RequestBody TemplateDto dto) throws GeniusException {
        dto.setTemplateId(templateId);
        templateApplicaton.updateTemplate(dto);
        return new BaseResultDto(CodeEnum.Success.getCode(),"修改模板成功");
    }

    /**
     * 删除模板
     * @param templateId
     * @return
     */
    @ApiOperation(value = "删除模板")
    @DeleteMapping(value = "/templates/{templateId}")
    public BaseResultDto deleteTemplate(@PathVariable("templateId") String templateId) throws GeniusException {
        templateApplicaton.deleteTemplate(templateId);
        return new BaseResultDto(CodeEnum.Success.getCode(),"修改模板成功");
    }

    @ApiOperation(value = "获取用户模板浏览总数据")
    @GetMapping(value = "/templates/{templateId}/view")
    public EntityDto<TemplateViewInfoVO> getTemplateViewInfo(@PathVariable("templateId") String templateId) {
        TemplateViewInfoVO viewInfo = templateQuery.getTemplateViewInfo(templateId);
        return new EntityDto<>(viewInfo,CodeEnum.Success.getCode(),"成功");
    }

    @ApiOperation(value = "获取用户模板浏览数据分页")
    @GetMapping(value = "/templates/{templateId}/view/page")
    public PageDto<TemplateDetailVO> getTemplateViewPage(@PathVariable("templateId") String templateId) {
        return templateQuery.getTemplateViewDetail(templateId);
    }

    @ApiOperation(value = "获取模板列表")
    @GetMapping(value = "/templates/{userId}")
    public PageDto<TemplateVO> getTemplateList(@PathVariable("userId") String userId, PageQuery query) {
        return templateQuery.getTemplateList(userId, query);
    }
}
