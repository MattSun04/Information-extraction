package com.sunyutian.core;

import java.util.*;
public class Item {
	public int value1;   //λ�õ��Ͻ�
	public int value2;	 //λ�õ��½�
	public int itemNo;   //item���,�������һ��ߴ��ϵ���
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
