package com.sunyutian.core;
import java.util.*;


public class HierCluster {
	
	public static int cutNumber;
	public static int itemNumber;
	
	
	public static void compare()
	{
		
		List<Cluster> cluster=Article.itemList;
		for(Cluster c:cluster)
		{
			for(List<Item> it:c.getitemList())
			{
				calculate(it);
			}
		}
		
	}

	public static List<Item> calculate(List<Item> iList)
	{
		int itemNumber=iList.size();
		while(itemNumber>1)
		{
			itemNumber--;
			int  min=100000;
			int item1=0;
			int item2=0;
			for(int i=0;i<iList.size()-1;i++)
			{ 
				for(int j=i+1;j<iList.size();j++)
				{
					if(iList.get(i).checked==1&&iList.get(j).checked==1)
					{
						int left1=iList.get(i).getValue1();
						int right1=iList.get(i).getValue2();
						int left2=iList.get(j).getValue1();
						int right2=iList.get(j).getValue2();
						
						if(Math.abs(right1-left2)<min)
						{
							min=Math.abs(right1-left2);
							item1=i;
							item2=j;
						}
						if(Math.abs(right2-left1)<min)
						{
							min=Math.abs(right2-left1);
							item1=i;
							item2=j;
						}
					}
				
				}
				
			}
			int left1=iList.get(item1).getValue1();
			iList.get(item1).setChecked();
			iList.get(item2).setChecked();
			int right1=iList.get(item1).getValue2();
			int left2=iList.get(item2).getValue1();
			int right2=iList.get(item2).getValue2();
			int lValue;
			int rValue;
			if(Math.abs(right1-left2)>=Math.abs(right2-left1))
			{
				lValue=left2;
				rValue=right1;
			}
			else
			{	
				lValue=left1;
				rValue=right2;
			}
			if(lValue==0&&rValue==0)
			{
				lValue=-1;
				rValue=-1;
			}
			Item temp=new Item(lValue,rValue,iList.size());
			temp.removeMember();
			temp.add(item1);
			temp.add(item2);
			iList.add(temp);
		}
		//printResult(iList);
		//return inferSeperateLine(iList);
		return iList;
	}

	
	public static void printResult(List<Item> iList)
	{
		for(int i=iList.size()-1;i>=0;i--)
		{
			
			System.out.print(iList.get(i).getItemNo()+":");
			for(int k=0;k<iList.get(i).getMember().size();k++)
			{
				System.out.print(iList.get(i).getMember().get(k)+" ");
			}
			System.out.println("");
		}
		
	}
	
	
	//cut off by number
	public static List<Range> inferSeperateLine(List<Item> iList,int offset)
	{
		//return 分割线可能存在的空间
		//int offset=6;  //the number of cluster needed to be seperated
		//int no=3;
		
		List<Range> rangeList=new ArrayList<Range>();
		int count=0;
		for(int i=iList.size()-offset+1;i<iList.size();i++)
		{
			int left1=iList.get(iList.get(i).getMember().get(0)).getValue1();
			int right1=iList.get(iList.get(i).getMember().get(0)).getValue2();
			int left2=iList.get(iList.get(i).getMember().get(1)).getValue1();
			int right2=iList.get(iList.get(i).getMember().get(1)).getValue2();
			
			if(left2-right1>0)
			{
				Range ran=new Range(right1+1,left2-1);
				rangeList.add(ran);
				//System.out.println("Range"+count);
				count++;
			}
			else
			{
				Range ran=new Range(right2+1,left1-1);
				rangeList.add(ran);
				//System.out.println("Range =="+count);
				count++;
			}
			
		}
		return rangeList;
		
	}

}
