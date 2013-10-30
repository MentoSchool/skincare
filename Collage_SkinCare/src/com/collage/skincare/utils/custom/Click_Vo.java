package com.collage.skincare.utils.custom;

public class Click_Vo
{

	private int Start_clock;

	private int end_clock;
	
	
	private int Color;
	
	private int Score;
	

	public int getColor() {
		return Color;
	}

	public void setColor(int color) {
		Color = color;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	}

	public int getStart_clock()
	{
		return Start_clock;
	}

	public void setStart_clock(int start_clock)
	{
		Start_clock = start_clock;
	}

	public int getEnd_clock()
	{
		return end_clock;
	}

	public void setEnd_clock(int end_clock)
	{
		this.end_clock = end_clock;
	}

	public Click_Vo()
	{
		// TODO Auto-generated constructor stub
	}

	public Click_Vo(int start_clock, int end_clock)
	{
		super();
		this.Start_clock = start_clock;
		this.end_clock = end_clock;
	}

	@Override
	public String toString()
	{
		return "Click_Vo [Start_clock=" + Start_clock + ", end_clock=" + end_clock + "]";
	}

	

}
