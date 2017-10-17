package com.lawer.domain;

import com.lawer.context.Global;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = Global.TABLE_PREFIX +  "wechat_todo_list")
public class WechatTodoList {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name="wechat_todo_type_id")
    private WechatTodoType wechatTodoType;

    @ManyToOne()
    @JoinColumn(name="wechat_user_id")
    private WechatUser wechatUser;

    private String name;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addtime;

    private String status;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WechatTodoType getWechatTodoType() {
        return wechatTodoType;
    }

    public void setWechatTodoType(WechatTodoType wechatTodoType) {
        this.wechatTodoType = wechatTodoType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public WechatUser getWechatUser() {
        return wechatUser;
    }

    public void setWechatUser(WechatUser wechatUser) {
        this.wechatUser = wechatUser;
    }
}
