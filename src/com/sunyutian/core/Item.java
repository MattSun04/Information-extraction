package com.sunyutian.core;

import java.util.*;
public class Item {
	public int value1;   //位置的上界
	public int value2;	 //位置的下界
	public int itemNo;   //item编号,从左向右或者从上到下
	public int checked;  //whether this cluster has been checked
	public int intraDistance;
	public ArrayList<Integer> member;		
	
	public Item(int a,int b,int c)
	{
		checked=1;
		value1=a;
		value2=b;
		itemNo=c;
		member=new ArrayList<Integer>();
		member.add(itemNo);
		intraDistance=b-a;
	}
	
	public void setChecked()
	{
		checked=0;
	}
	public int getValue1()
	{
		return value1;
	}
	public int getValue2()
	{
		return value2;
	}
	public void add(int a)
	{
		member.add(a);
	}
	public ArrayList<Integer> getMember()
	{
		return member;
	}
	public void removeMember()
	{
		member.remove(0);
	}
	public int getItemNo()
	{
		return itemNo;

	}
	
	
}
