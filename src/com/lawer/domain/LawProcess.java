package com.lawer.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "law_process")
public class LawProcess implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

    private String name;

    @Column(name="is_message")
    private String isMessage;

    @ManyToOne()
	@JoinColumn(name="law_case_id")
    private LawCase lawCaseId;

    private String status;

    private String context;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;

    @ManyToOne()
	@JoinColumn(name="process_type")
    private ProcessType processType;

    @ManyToOne()
   	@JoinColumn(name="user_id")
    private User userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addtime;

    @OneToMany(mappedBy="lawProcessId")
    private List<LawProcessContext> lawProcessContexts;

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

    public String getIsMessage() {
        return isMessage;
    }

    public void setIsMessage(String isMessage) {
    	this.isMessage = isMessage == null ? null : isMessage.trim();
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }


    public LawCase getLawCaseId() {
		return lawCaseId;
	}

	public void setLawCaseId(LawCase lawCaseId) {
		this.lawCaseId = lawCaseId;
	}

	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<LawProcessContext> getLawProcessContexts() {
        return lawProcessContexts;
    }

    public void setLawProcessContexts(List<LawProcessContext> lawProcessContexts) {
        this.lawProcessContexts = lawProcessContexts;
    }
}