package com.lawer.domain;

import com.lawer.context.Global;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = Global.TABLE_PREFIX +  "wechat_phone")
public class WechatPhone {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="phone")
    private String phone;

    @Column(name="telephone")
    private String telephone;

    @Column(name="position")
    private String position;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addtime;

    private String status;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
}
