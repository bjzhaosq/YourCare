package com.lawer.domain;

import com.lawer.context.Global;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = Global.TABLE_PREFIX +  "wechat_todo_type")
public class WechatTodoType {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private String nid;

    private String icon;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addtime;

    private String status;

    private String remark;

    @OneToMany(mappedBy="wechatTodoType")
    private List<WechatTodoList> wechatTodoLists;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public List<WechatTodoList> getWechatTodoLists() {
        return wechatTodoLists;
    }

    public void setWechatTodoLists(List<WechatTodoList> wechatTodoLists) {
        this.wechatTodoLists = wechatTodoLists;
    }
}
