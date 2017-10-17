package com.lawer.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "lawer_help_case")
public class LawerHelpCase implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@ManyToOne()
	@JoinColumn(name="user_id")
    private User userId;

	@ManyToOne()
	@JoinColumn(name="lawr_case_id")
    private LawerCase lawrCaseId;
	
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}


	public LawerCase getLawrCaseId() {
		return lawrCaseId;
	}

	public void setLawrCaseId(LawerCase lawrCaseId) {
		this.lawrCaseId = lawrCaseId;
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