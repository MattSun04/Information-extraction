package com.sunyutian.core;

public class ParaFrame {
	private int num;
	public int frame;
	private String s;
	public int[] pointToPara={0,0};
	
	public ParaFrame(int number,int frame,String s)
	{
		this.num=number;
		this.frame=frame;	
		this.s=s;
	}
	
	public void setFrame(int frame)
	{
		this.frame=frame;	
	}
	
	public String getString()
	{
		return this.s;	
	}
	
	public int getNum()
	{
		return this.num;
	}
	public void setPointer(int a,int b)
	{
		this.pointToPara[0]=a;
		this.pointToPara[1]=b;
	}
	public String getPointer()
	{
		return ""+pointToPara[0]+" "+pointToPara[1];
	}
	
	public boolean comparePointer()
	{
		if(pointToPara[0]==0 && pointToPara[1]==0)
		  return true;
		else 
			return false;
	} 
	
	public boolean comparePointer(int i)
	{
		if(pointToPara[0]<=i && pointToPara[1]>=i)
		  return true;
		else 
			return false;
	} 
}
