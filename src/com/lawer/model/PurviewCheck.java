package com.lawer.model;

import com.lawer.domain.Purview;

public class PurviewCheck {
	boolean checked;
	private Purview purview;
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Purview getPurview() {
		return purview;
	}

	public void setPurview(Purview purview) {
		this.purview = purview;
	}
	
}
