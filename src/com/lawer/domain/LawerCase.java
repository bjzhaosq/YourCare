package com.lawer.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lawer_case")
public class LawerCase implements Serializable{
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
	@JoinColumn(name="law_case_id")
    private LawCase lawCaseId;
	
	@Column(name="case_view")
    private String caseView;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addtime;

	@OneToMany(mappedBy="lawrCaseId")
	private List<LawerHelpCase> lawerHelpCases;

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

	public LawCase getLawCaseId() {
		return lawCaseId;
	}

	public void setLawCaseId(LawCase lawCaseId) {
		this.lawCaseId = lawCaseId;
	}


    public String getCaseView() {
		return caseView;
	}

	public void setCaseView(String caseView) {
		this.caseView = caseView;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

	public List<LawerHelpCase> getLawerHelpCases() {
		return lawerHelpCases;
	}

	public void setLawerHelpCases(List<LawerHelpCase> lawerHelpCases) {
		this.lawerHelpCases = lawerHelpCases;
	}
}