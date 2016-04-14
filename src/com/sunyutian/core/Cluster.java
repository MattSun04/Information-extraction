package com.sunyutian.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Cluster {
	protected int clusterNo;
	public List<List<Item>> itemList=new ArrayList<List<Item>>();
	public List<Integer> potentialLine=new ArrayList<Integer>();
	public List<List<Range>> rangeList=new ArrayList<List<Range>>();
	public List<Integer> seperateLine=new ArrayList<Integer>();
	public static List<Integer> seperateNo=new ArrayList<Integer>();
	public int maxClusterNo=1;
	public List<Integer> lowerLevel=new ArrayList<Integer>();
	public List<Integer> lineNumber=new ArrayList<Integer>();
	public String s;
	
	
	public List<Integer> getLineNo()
	{
		return lineNumber;
	}
	public void maxMatch(int standards)
	{
		
		
	}
	
	public void processRange()
	{
		seperateLine=new ArrayList<Integer>();
		//int offset=seperateNo.get(clusterNo);
		
		int a[]=new int[rangeList.size()];//表示每行的第几个是下一个
		for(int i=0;i<rangeList.size();i++)
			a[i]=0;
		
		
		for(List<Range> j:rangeList)
		{
			Collections.sort(j, new Comparator<Range>() {
		        @Override
		        public int compare(Range r1, Range r2)
		        {
		            return  r1.getRight()-r2.getRight();
		        }
		    });
		}
		for(int i=0;i<rangeList.get(0).size();i++)
		{
			int left=0;
			int right=Article.maxLength;
			int count=-1;
			int flag=1;
			for(List<Range> j:rangeList)
			{
				count++;
				if(a[count]>=rangeList.get(0).size())
				{	
					flag=2;
					break;
				}
				int q=j.get(a[count]).left;
				int w=j.get(a[count]).right;
				while(w<left&&q<left)
				{
					a[count]++;
					if(a[count]>=rangeList.get(0).size())
					{	
						flag=2;
						break;
					}
					q=j.get(a[count]).left;
					w=j.get(a[count]).right;
				}
				if(w>right&&q>right)
				{
					flag=0;
					break;
				}
				else 
				{
					if(q>left)
						left=q;
					if(w<right)
						right=w;
					a[count]++;
				
				}
				
			}
			if(left<=right&&flag==1)
			{
				//int width=Article.fm.stringWidth(this.getText().substring(0,right+1));
				seperateLine.add(right);
			}
			if(flag==2)
				break;
		}
		if(seperateLine.size()>maxClusterNo)
		{
			maxClusterNo=seperateLine.size();
		}
	}
	
	public Cluster(List<List<Item>> iList,String s,int n)
	{
		this.itemList=iList;
		this.s=s;
		this.clusterNo=n;
	}
	
	public String getText()
	{
		return this.s;
	}
	public List<List<Item>> getitemList()
	{
		return this.itemList;
	}
	public void setRangeList(List<Range> l)
	{
		this.rangeList.add(l);
	}
	public void removeRangeList()
	{
		this.rangeList=new ArrayList<List<Range>>();
			
	}
}
