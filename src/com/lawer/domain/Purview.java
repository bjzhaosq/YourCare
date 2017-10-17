package com.lawer.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The persistent class for the purview database table.
 * 
 */
@Entity
public class Purview implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private int level;

	private String name;

	private int pid;

	private String purview;

	private String remark;

	private String url;

	// one-to-one association to UserTypepurview
	@OneToMany(mappedBy="purview", fetch=FetchType.LAZY)
	private List<UserTypepurview> userTypepurviews;
	
	public Purview() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPid() {
		return this.pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPurview() {
		return this.purview;
	}

	public void setPurview(String purview) {
		this.purview = purview;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<UserTypepurview> getUserTypepurviews() {
		return userTypepurviews;
	}

	public void setUserTypepurviews(List<UserTypepurview> userTypepurviews) {
		this.userTypepurviews = userTypepurviews;
	}
	
	public UserTypepurview addUserTypepurview(UserTypepurview userTypepurview) {
		getUserTypepurviews().add(userTypepurview);
		userTypepurview.setPurview(this);
		return userTypepurview;
	}

	public UserTypepurview removeUserTypepurview(UserTypepurview userTypepurview) {
		getUserTypepurviews().remove(userTypepurview);
		userTypepurview.setPurview(null);
		return userTypepurview;
	}

}
