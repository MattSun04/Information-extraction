package com.sunyutian.core;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import weka.clusterers.XMeans;
import weka.core.Instances;

public class Weka 
{
    public static BufferedReader readDataFile(String filename) 
    {
        BufferedReader inputReader = null;
        try 
        {
            inputReader = new BufferedReader(new FileReader(filename));
        }
        catch (FileNotFoundException ex) 
        {
            System.err.println("File not found: " + filename);
        }
        return inputReader;
    }

    public static void calXmeans(int j,int k) 		//max min
    {
        XMeans xmean = new XMeans();
        xmean.setSeed(10);
        xmean.setMaxNumClusters(j);
        xmean.setMinNumClusters(k);
        BufferedReader datafile = readDataFile("weka_prepared.arff"); 
        
        Article.cluster.clear();
        for(int i=0;i<j;i++)
        {
        	List<Integer> l=new ArrayList<Integer>();
        	Article.cluster.add(l);
        }
        
        Instances data;
        try{
        	data=new Instances(datafile);
  
        	xmean.buildClusterer(data);
        	
        	int dd;
        	if(Article.ignoreList.size()!=0)
        		dd=Article.ignoreList.get(0);
        	else 
        		dd=-1;
        	int count=0;
        	int offset=0;
        	for(int i=0;i<Article.pList.size();i++)
        	{
        		if(i==dd)
        		{
        			offset++;
        			count++;
        			if(count<Article.ignoreList.size())
        			{
        				dd=Article.ignoreList.get(count);
        			}
        			else
        				dd=-1;
        			Article.cList.put(i, -1);
        			//Article.cluster.get(temp).add(i-offset);
        		}
        		else
        		{
        			int temp=xmean.clusterInstance(data.instance(i-offset));
        			
        			Article.cList.put(i, temp);
        			//Article.cluster.get(temp).add(i);
        			
        		}
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	System.out.println("WEKA PROBLEM");
        }
    }
} 