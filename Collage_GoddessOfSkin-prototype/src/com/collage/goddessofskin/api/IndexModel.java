package com.collage.goddessofskin.api;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("IndexModel")
public class IndexModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5713515543767395549L;

	private String code;

	private Integer areaNo;

	private String date;

	private String today;

	private String tomorrow;

	private String theDayAfterTomorrow;

	private String h3;
	
	private String h6;
	
	private String h9;
	
	private String h12;
	
	private String h15;
	
	private String h18;
	
	private String h21;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(Integer areaNo) {
		this.areaNo = areaNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getTomorrow() {
		return tomorrow;
	}

	public void setTomorrow(String tomorrow) {
		this.tomorrow = tomorrow;
	}

	public String getTheDayAfterTomorrow() {
		return theDayAfterTomorrow;
	}

	public void setTheDayAfterTomorrow(String theDayAfterTomorrow) {
		this.theDayAfterTomorrow = theDayAfterTomorrow;
	}

	public String getH3() {
		return h3;
	}

	public void setH3(String h3) {
		this.h3 = h3;
	}

	public String getH6() {
		return h6;
	}

	public void setH6(String h6) {
		this.h6 = h6;
	}

	public String getH9() {
		return h9;
	}

	public void setH9(String h9) {
		this.h9 = h9;
	}

	public String getH12() {
		return h12;
	}

	public void setH12(String h12) {
		this.h12 = h12;
	}

	public String getH15() {
		return h15;
	}

	public void setH15(String h15) {
		this.h15 = h15;
	}

	public String getH18() {
		return h18;
	}

	public void setH18(String h18) {
		this.h18 = h18;
	}

	public String getH21() {
		return h21;
	}

	public void setH21(String h21) {
		this.h21 = h21;
	}
}
