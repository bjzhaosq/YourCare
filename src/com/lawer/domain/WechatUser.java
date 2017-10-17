package com.lawer.domain;

import com.lawer.context.Global;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = Global.TABLE_PREFIX +  "wechat_user")
public class WechatUser {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name="3rd_session")
    private String rd_session;

    @Column(name="session_key")
    private String session_key;

    @Column(name="expires_in")
    private Integer expires_in;

    private String openid;

    @OneToOne()
    @JoinColumn(name="wechat_userinfo_id")
    private WechatUserInfo wechatUserInfo;

    @OneToMany(mappedBy="wechatUser")
    private List<WechatTodoList> wechatTodoLists;

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

    public String getRd_session() {
        return rd_session;
    }

    public void setRd_session(String rd_session) {
        this.rd_session = rd_session;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public List<WechatTodoList> getWechatTodoLists() {
        return wechatTodoLists;
    }

    public void setWechatTodoLists(List<WechatTodoList> wechatTodoLists) {
        this.wechatTodoLists = wechatTodoLists;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public WechatUserInfo getWechatUserInfo() {
        return wechatUserInfo;
    }

    public void setWechatUserInfo(WechatUserInfo wechatUserInfo) {
        this.wechatUserInfo = wechatUserInfo;
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
}
