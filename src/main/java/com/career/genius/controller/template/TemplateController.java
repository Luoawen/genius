package com.career.genius.controller.template;

import com.career.genius.application.template.TemplateApplicaton;
import com.career.genius.application.template.dto.TemplateDto;
import com.career.genius.application.template.query.TemplateQuery;
import com.career.genius.application.template.vo.TemplateVO;
import com.career.genius.config.Exception.GeniusException;
import com.usm.enums.CodeEnum;
import com.usm.vo.BaseResultDto;
import com.usm.vo.ListDto;
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
    public BaseResultDto addTemplate(@RequestBody TemplateDto dto) {
        templateApplicaton.addTemplate(dto);
        return new BaseResultDto(CodeEnum.Success.getCode(),"新增模板成功");
    }

    /**
     * 修改模板
     * @param dto
     * @return
     */
    @ApiOperation(value = "修改模板")
    @PutMapping(value = "/template/{{templateId}}")
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

    @ApiOperation(value = "获取模板列表")
    @GetMapping(value = "/templates/{userId}")
    public ListDto<TemplateVO> getTemplateList(@PathVariable("userId") String userId) {
        List<TemplateVO> templateList = templateQuery.getTemplateList(userId);
        return new ListDto<>(templateList,CodeEnum.Success.getCode(),"成功");
    }
}
