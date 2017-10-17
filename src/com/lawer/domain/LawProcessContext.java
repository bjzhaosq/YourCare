package com.lawer.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "law_process_context")
public class LawProcessContext implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

    @ManyToOne()
    @JoinColumn(name="law_process_id")
	private LawProcess lawProcessId;

    private String context;

    private String status;

    @Column(name="task_time")
    private int taskTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="start_time")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_time")
    private Date endTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addtime;

    @OneToMany(mappedBy="lawProcessContextId")
    private List<LawProcessTime> lawProcessTimes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LawProcess getLawProcessId() {
        return lawProcessId;
    }

    public void setLawProcessId(LawProcess lawProcessId) {
        this.lawProcessId = lawProcessId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(int taskTime) {
        this.taskTime = taskTime;
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

    public List<LawProcessTime> getLawProcessTimes() {
        return lawProcessTimes;
    }

    public void setLawProcessTimes(List<LawProcessTime> lawProcessTimes) {
        this.lawProcessTimes = lawProcessTimes;
    }
}