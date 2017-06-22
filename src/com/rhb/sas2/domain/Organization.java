package com.rhb.sas2.domain;

public class Organization{
	private String name;
	private String business;
	private String industry;
	private String note;
	private String ipoDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public String getIpoDate() {
		return ipoDate;
	}
	public void setIpoDate(String ipoDate) {
		this.ipoDate = ipoDate;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	@Override
	public String toString() {
		return "Organization [name=" + name + ", business=" + business
				+ ", industry=" + industry + ", note=" + note + ", ipoDate="
				+ ipoDate + "]";
	}
	
}
