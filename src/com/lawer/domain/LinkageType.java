package com.lawer.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the linkage_type database table.
 * 
 */
@Entity
@Table(name="linkage_type")
public class LinkageType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String addip;

	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;

	private String name;

	private String nid;

	private short order;

	//bi-directional many-to-one association to Linkage
	@OneToMany(mappedBy="linkageType")
	private List<Linkage> linkages;

	public LinkageType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNid() {
		return this.nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public short getOrder() {
		return this.order;
	}

	public void setOrder(short order) {
		this.order = order;
	}

	public List<Linkage> getLinkages() {
		return this.linkages;
	}

	public void setLinkages(List<Linkage> linkages) {
		this.linkages = linkages;
	}

	public Linkage addLinkage(Linkage linkage) {
		getLinkages().add(linkage);
		linkage.setLinkageType(this);

		return linkage;
	}

	public Linkage removeLinkage(Linkage linkage) {
		getLinkages().remove(linkage);
		linkage.setLinkageType(null);

		return linkage;
	}

}
