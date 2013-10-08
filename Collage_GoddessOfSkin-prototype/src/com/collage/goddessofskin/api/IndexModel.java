package com.collage.goddessofskin.api;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("IndexModel")
public class IndexModel implements Serializable
{
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
	
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public Integer getAreaNo()
	{
		return areaNo;
	}
	public void setAreaNo(Integer areaNo)
	{
		this.areaNo = areaNo;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getToday()
	{
		return today;
	}
	public void setToday(String today)
	{
		this.today = today;
	}
	public String getTomorrow()
	{
		return tomorrow;
	}
	public void setTomorrow(String tomorrow)
	{
		this.tomorrow = tomorrow;
	}
	public String getTheDayAfterTomorrow()
	{
		return theDayAfterTomorrow;
	}
	public void setTheDayAfterTomorrow(String theDayAfterTomorrow)
	{
		this.theDayAfterTomorrow = theDayAfterTomorrow;
	}
}
