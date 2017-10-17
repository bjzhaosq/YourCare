package com.lawer.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the user_typepurview database table.
 * 
 */
@Entity
@Table(name="user_typepurview")
public class UserTypepurview implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	// one-to-one association to Purview
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="purview_id")
	private Purview purview;

	// many-to-one association to UserType
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_type_id")
	private UserType userType;

	public UserTypepurview() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Purview getPurview() {
		return this.purview;
	}

	public void setPurview(Purview purview) {
		this.purview = purview;
	}

	public UserType getUserType() {
		return this.userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}
