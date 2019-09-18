package com.career.genius.port.dao.template;

import com.career.genius.domain.wechat.WechatTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-09-14 16:10
 * @discription
 **/
public interface WechatTransferDao extends JpaRepository<WechatTransfer,String> {

    WechatTransfer findWechatTransferByTemplateId(String templateId);
}
