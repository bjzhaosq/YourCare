package com.lawer.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "law_type")
public class LawType implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="status_one")
    private String statusOne;

	@Column(name="status_two")
    private String statusTwo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getStatusOne() {
		return statusOne;
	}

	public void setStatusOne(String statusOne) {
		this.statusOne = statusOne;
	}

	public String getStatusTwo() {
		return statusTwo;
	}

	public void setStatusTwo(String statusTwo) {
		this.statusTwo = statusTwo;
	}

	public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}