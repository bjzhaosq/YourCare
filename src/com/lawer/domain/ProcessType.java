package com.lawer.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "process_type")
public class ProcessType implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

    private String name;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date starttime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date destime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date processtime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date backtime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endtime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addtime;

    @OneToMany(mappedBy="processTypeId")
    private List<LawProcessType> lawProcessTypes;

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
        this.name = name == null ? null : name.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getDestime() {
        return destime;
    }

    public void setDestime(Date destime) {
        this.destime = destime;
    }

    public Date getProcesstime() {
        return processtime;
    }

    public void setProcesstime(Date processtime) {
        this.processtime = processtime;
    }

    public Date getBacktime() {
        return backtime;
    }

    public void setBacktime(Date backtime) {
        this.backtime = backtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public List<LawProcessType> getLawProcessTypes() {
        return lawProcessTypes;
    }

    public void setLawProcessTypes(List<LawProcessType> lawProcessTypes) {
        this.lawProcessTypes = lawProcessTypes;
    }
}