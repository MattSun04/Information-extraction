package com.sunyutian.core;



//Class for storing similarity between different rows.
public class SimBetween {
	
	public int p1;
	public int p2;
	public double sim;
	public SimBetween(int a,int b, double c)
	{
		p1=a;
		p2=b;
		sim=c;
	}
	public int getP1()
	{
		return p1;
	}
	
	public int getP2()
	{
		return p2;
	}
	
	public double getSim()
	{
		return sim;
	}
	
	
	
}
