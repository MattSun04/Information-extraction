package com.sunyutian.core;

import java.util.ArrayList;
import java.util.List;

public class Row_vector {
	
	public static List<SimBetween> simList;
	
	
	public static void calRowSim()
	{
		List<List<Double>> temp=Article.vList;
		simList=new ArrayList<SimBetween>();
		for(int i=0;i<temp.size();i++)
		{
			List<Double> t1=temp.get(i);
			if(t1.size()==0)
				continue;
			for(int j=i+1;j<temp.size();j++)
			{
				List<Double> t2=temp.get(j);
				if(t2.size()==0)
					continue;
				calSim(t1,t2,i,j);
			}
		}	
	}
	
	private static void calSim(List<Double> t1,List<Double> t2,int a,int b)
	{
		 double vector1Modulo = 0.00;//mod for vector 1
         double vector2Modulo = 0.00;//
         double vectorProduct = 0.00; //
         
         int length;
         if(t1.size()>t2.size())
        	 length= t2.size();
         else 
        	 length=t1.size();
         
         for(int i=0;i<length;i++)
         {
        	 vectorProduct+=t1.get(i)*t2.get(i);
         }
         for(int i=0;i<t1.size();i++)
         {
        	 vector1Modulo+=t1.get(i)*t1.get(i);
         }
         for(int i=0;i<t2.size();i++)
         {
        	 vector2Modulo+=t2.get(i)*t2.get(i);
         }
         vector1Modulo = Math.sqrt(vector1Modulo);  
         vector2Modulo = Math.sqrt(vector2Modulo); 
         SimBetween s=new SimBetween(a,b,vectorProduct/(vector1Modulo*vector2Modulo));
         simList.add(s);
         Article.simiForCluster.add(s);
         
         System.out.println(a+" "+b+" :"+vectorProduct/(vector1Modulo*vector2Modulo));
         
         
	}
	
	public static void calRowCluster()
	{
		
		
		
	}
}
