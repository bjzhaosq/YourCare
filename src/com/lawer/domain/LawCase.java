package com.lawer.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "law_case")
public class LawCase implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="case_number")
    private String caseNumber;

	@Column(name="case_name")
    private String caseName;

	@ManyToOne()
	@JoinColumn(name="law_type_id")
    private LawType lawTypeId;

	@ManyToOne()
	@JoinColumn(name="customer_id")
    private Customer customerId;

    @OneToMany(mappedBy="lawCaseId")
	private List<LawAccessory> lawAccessories;

    @OneToMany(mappedBy="lawCaseId")
    private List<LawProcess> lawProcesses;

    @OneToMany(mappedBy="lawCaseId")
    private List<LawerCase> lawerCases;

    @OneToMany(mappedBy="lawCaseId")
    private List<LawComment> lawComments;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addtime;

    private String status;
    
    private String content;

    @Column(name="context_zs")
    private String contentZs;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber == null ? null : caseNumber.trim();
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName == null ? null : caseName.trim();
    }


    public LawType getLawTypeId() {
		return lawTypeId;
	}

	public void setLawTypeId(LawType lawTypeId) {
		this.lawTypeId = lawTypeId;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
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
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

    public List<LawAccessory> getLawAccessories() {
        return lawAccessories;
    }

    public void setLawAccessories(List<LawAccessory> lawAccessories) {
        this.lawAccessories = lawAccessories;
    }

    public List<LawProcess> getLawProcesses() {
        return lawProcesses;
    }

    public void setLawProcesses(List<LawProcess> lawProcesses) {
        this.lawProcesses = lawProcesses;
    }

    public List<LawerCase> getLawerCases() {
        return lawerCases;
    }

    public void setLawerCases(List<LawerCase> lawerCases) {
        this.lawerCases = lawerCases;
    }

    public List<LawComment> getLawComments() {
        return lawComments;
    }

    public void setLawComments(List<LawComment> lawComments) {
        this.lawComments = lawComments;
    }

    public String getContentZs() {
        return contentZs;
    }

    public void setContentZs(String contentZs) {
        this.contentZs = contentZs;
    }
}