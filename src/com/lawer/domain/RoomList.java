package com.lawer.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "room_list")
public class RoomList implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@ManyToOne()
	@JoinColumn(name="site_one")
    private Site siteOne;
	
	@ManyToOne()
	@JoinColumn(name="site_two")
    private Site siteTwo;

    private String status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Site getSiteOne() {
        return siteOne;
    }

    public void setSiteOne(Site siteOne) {
        this.siteOne = siteOne;
    }

    public Site getSiteTwo() {
        return siteTwo;
    }

    public void setSiteTwo(Site siteTwo) {
        this.siteTwo = siteTwo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}