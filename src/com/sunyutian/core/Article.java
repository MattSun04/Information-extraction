package com.sunyutian.core;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Panel;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.jniwrapper.win32.jexcel.samples.demo.JWorkbookWindow;


//used for storing parameters 
public class Article {
	public static List<List<Integer>> cluster=new ArrayList<List<Integer>>();
	
	public static JFrame f=new JFrame();
	public static JWorkbookWindow sjw;

	public static JPanel p=new JPanel();
	public static List<XWPFParagraph> pList=new ArrayList<XWPFParagraph>();
	public static List<List<Double>> vList=new ArrayList<List<Double>>();			//vector list for lines 
	public static List<SimBetween> simiForCluster=new ArrayList<SimBetween>();		//similaritie between lines
	public static Map<Integer,Integer> cList=new  HashMap<Integer,Integer>();      //cluster  MAP<line number,cluster>
	public static List<Integer> clusterOrderList=new ArrayList<Integer>();
	
	public static int maxLength;
	
	//object contains frame
	public static Layout lay;
	public static SLayout slay;	
	public static BLayout blay;
	
	//dimension of current window
	public static Dimension dimension;
	public static List<Integer> linePosition=new ArrayList<Integer>(); 
	public static int maxNumCluster=7;
	public static int minNumCluster=2;
	public static int clusterNum=maxNumCluster;
	public static List<String> stringList=new ArrayList<String>();
	public static List<Integer> ignoreList=new ArrayList<Integer>();
	public static List<Cluster> itemList=new ArrayList<Cluster>();  //Cluster for step 2
	
	public static FontMetrics fm;
	public static int a[]=new int[maxNumCluster];
	
	//parameter
	public static double NUMBER=1.0;
	public static double CHARACTER=0.7;
	public static double OTHERS=0.3;
	public static double BLANK=0;
	
	//Standard Cluster No
	public static int StructureStandard=0;
	
	
	public static void clearArticle()
	{
		 p=new JPanel();
		 pList=new ArrayList<XWPFParagraph>();
		 vList=new ArrayList<List<Double>>();			//vector list for lines 
	     simiForCluster=new ArrayList<SimBetween>();		//similaritie between lines
		 cList=new  HashMap<Integer,Integer>();      //cluster  MAP<line number,cluster>
		 maxLength=0;
		 linePosition=new ArrayList<Integer>(); 
		 maxNumCluster=7;
		 minNumCluster=5;
		 clusterNum=maxNumCluster;
		 stringList=new ArrayList<String>();
		 ignoreList=new ArrayList<Integer>();
		 itemList=new ArrayList<Cluster>();  //Cluster for step
		
		
	}
	
	
	public static void calVector()
	{
		 int max=0;
		 int count=0;
		 for (XWPFParagraph para : pList) 
		 {
			 List<Double> temp=new ArrayList<Double>();
			 String s=para.getText();
			 if(s.length()>max)
			 {
				 max=s.length();
			 }
			 int flag=0;
			 for(int i=0;i<s.length();i++)
			 {
				 if(s.charAt(i)>='0'&&s.charAt(i)<='9')
				 {
					 flag=1;
					 temp.add(NUMBER);
				 }
				 else if((s.charAt(i)>'A'&&s.charAt(i)<'Z')||(s.charAt(i)>'a'&&s.charAt(i)<'z'))
				 {
					 flag=1;
					 temp.add(CHARACTER);
				 }
				 else if(s.charAt(i)!=' ')
				 {
					 temp.add(OTHERS);
				 }
				 else 
					 temp.add(BLANK);
			 }
			 if(flag==0)
			 {
				 ignoreList.add(count);
				 
			 }else
				 vList.add(temp);
			 count++; 
	     }
		 maxLength=max;
		 sample();
		 Weka.calXmeans(7,5); //max cluster min cluster
		 //read();
		 //Row_vector.calRowSim();
	}
	
	public static void sample()
	{
		
		 try
		 {
	            FileOutputStream fos=new FileOutputStream("weka_prepared.arff");
	            String s="";
	            s=s+"@RELATION data\n";
	            for(int i=1;i<maxLength+1;i++)
	            {
	            	s=s+"@ATTRIBUTE col"+i+" NUMERIC"+"\n";
	            }
	            s=s+"@data\n";
	            for(List<Double> d:vList)
	            {
	               int count=0;
	        	   if(d.get(0)==-1)
	        	   {
	        		   continue;
	        	   }
	               for(Double f:d)
	        	   {
	        		   count++;
	        		   s=s+f+" ";
	        	   }
	        	   if(count<maxLength)
	        	   {
	        		   while(count!=maxLength)
	        		   {
	        			   count++;
	        			   s=s+"0.0 ";
	        		   }
	        	   }
	        	   s=s+"\n";
	            }
	            
	            byte[] bs=s.getBytes();
	            for(int i=0;i<bs.length;i++)
	            {
	                fos.write(bs[i]);
	            }
	            fos.close();
	        }
	        catch (FileNotFoundException e)
	        {
	            System.out.println("File NOT EXSIT");
	        }
	        catch(IOException e)
	        {
	            System.out.println("WHAT IS IO");
	        }
		
		
	}
	
	public static void read()
	{
		
		 	try
	        {
			 	
	            FileInputStream fos=new FileInputStream("C:\\Users\\sunyutian\\Desktop\\buffer1.arff");
	            BufferedReader br=new BufferedReader(new InputStreamReader(fos));
	            String line=null;
	           
	            int count=0;
	            while((line = br.readLine()) != null){
	            	count++;
	            	char cc = line.charAt(line.length()-1);
	            	int c=Character.getNumericValue(cc);
	            	cList.put(count, c);
	            }
	            br.close();
	            fos.close();
	    
	            
	        }
	        catch (FileNotFoundException e)
	        {
	            System.out.println("WHERE IS FILE?");
	        }
	        catch(IOException e)
	        {
	            System.out.println("WTF IO PROBLEM");
	        }
		
		
	}
	
	public static void setPanel(JPanel fra)
	{
		p=fra;
	}
	
	
	
	public static void print()
	{
	 for (List<Double> l: vList) {
	         //当前段落的属性
	       System.out.println(l);
	        
	      }
		
	}
	
}
