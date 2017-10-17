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
@Table(name = "law_process_time")
public class LawProcessTime implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

	@ManyToOne()
	@JoinColumn(name="law_process_type_id")
    private LawProcessType lawProcessTypeId;
	
	@ManyToOne()
	@JoinColumn(name="law_process_context_id")
    private LawProcessContext lawProcessContextId;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="start_time")
    private Date startTime;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_time")
    private Date endTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addtime;

    private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LawProcessType getLawProcessTypeId() {
		return lawProcessTypeId;
	}

	public void setLawProcessTypeId(LawProcessType lawProcessTypeId) {
		this.lawProcessTypeId = lawProcessTypeId;
	}

	public LawProcessContext getLawProcessContextId() {
		return lawProcessContextId;
	}

	public void setLawProcessContextId(LawProcessContext lawProcessContextId) {
		this.lawProcessContextId = lawProcessContextId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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