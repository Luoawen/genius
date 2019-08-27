package com.career.genius.domain.user;

import com.career.genius.application.user.dto.WechatUserInfoDto;
import com.career.genius.domain.common.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-05-21 22:22
 * @discription
 **/
@Data
@Entity
@Table(name = "app_user")
public class User extends BaseEntity {

    @Column(name = "user_name",columnDefinition = "varchar(50) comment'用户名'")
    private String userName;

    @Column(name = "phone",columnDefinition = "varchar(11) comment'电话'")
    private String phone;

    @Column(name = "head_image",columnDefinition = "varchar(50) comment'头像'")
    private String headImage;

    @Column(name = "open_id",columnDefinition = "varchar(50) comment'openId'")
    private String openId;

    @Column(name = "trade",columnDefinition = "varchar(50) comment'所属行业'")
    private String trade;

    @Column(name = "unique_id",columnDefinition = "varchar(50) comment'微信uniqueId'")
    private String wechatUniqueId;

    /**
     * @Author Marker
     * @Date  添加用户
     * @Discription
     **/
    public void addUser(String userName,String phone,String headImage,String openId,String trade) {
        this.userName = userName;
        this.phone = phone;
        this.headImage = headImage;
        this.trade = trade;
        this.openId = openId;
        super.setCreateTime(new Date());
        super.setUpdateTime(new Date());
    }

    /**
     * @Author Marker
     * @Date  绑定微信用户信息
     * @Discription
     **/
    public void bindWechatUser(String wechatUniqueId,String userName,String headImage,String openId) {
        this.wechatUniqueId = wechatUniqueId;
        this.userName = userName;
        this.headImage = headImage;
        this.openId = openId;
        super.setUpdateTime(new Date());
    }



}
