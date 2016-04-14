package com.sunyutian.core;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class ButtonPanel {
	public JPanel p,p1,p2,p3;
	public JButton jb,jb1;
	public  JComboBox dbtype;
	public int numCombo=0;
	public JTextField jt1,jt2;
	public static String s="Display All";
	public ButtonPanel()
	{
		p=new JPanel();
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		p.setLayout(new GridLayout(4,1));
		
	    dbtype = new JComboBox();
		dbtype.addItem("Display All");
		
		jt1=new JTextField();
		jt2=new JTextField();
		JLabel jl=new JLabel("Number of Clusters");
		JLabel jl1=new JLabel("Min:");
		JLabel jl2=new JLabel("Max:");
		jt1.setText(""+Article.minNumCluster);
		jt2.setText(""+Article.maxNumCluster);
		
		int count=0;
		while(count<Article.cluster.size())
		{
			count++;
			dbtype.addItem("cluster"+count);	
		}
		numCombo=count;
		dbtype.setSelectedItem(s);
		dbtype.addItemListener(buttonListener);
		p2.add(dbtype);
		JButton regeButton=new JButton("regenerate clusters");
		
		
		jb=new JButton("next step");
		jb.addActionListener(nextStepListener);
		
		jb1=new JButton("clear this cluster");
		jb1.addActionListener(deleteClusterListener);
		p2.add(jb1);
		regeButton.addActionListener(regenerateListener);
		p1.add(jl);
		p1.add(jl1);
		p1.add(jt1);
		p1.add(jl2);
		p1.add(jt2);
		p1.add(regeButton);
		Font f=new Font(Font.DIALOG_INPUT,Font.ITALIC,12);
		p1.setFont(f);
		p.add(p2);
		p.add(p1);
		p3.add(jb);
		p.add(p3);
		Dimension d=new Dimension(500,200);
		p.setPreferredSize(d);
		}
	public JPanel getPanel()
	{
		return p;
	}
	
	ItemListener buttonListener = new ItemListener( ) {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED)
			{
				try
				{    
					
					String s = e.getItem().toString();
					ButtonPanel.s=s;
					
					if(s.charAt(s.length()-1)=='l')
	        		{
	        		Layout.mode=0;
	        		}
	        		else
	        		{
	        			 int c=Character.getNumericValue(s.charAt(s.length()-1));
	        			 Layout.mode=c; 
	        		}
	            	System.out.println(Layout.mode);
	            	Article.lay.repaint();
				}   
	        	catch(Exception head){    
	                     System.out.println("Combo Box Error!");  
	                     head.printStackTrace();
	            }
			} 
		}
	         
	};  
	
	 ActionListener nextStepListener = new ActionListener() {
         public void actionPerformed(ActionEvent event) 
           {   
        	 Article.lay.getFrame().setVisible(false);
             //Article.childFrame.removeAll();
        	 Article.itemList.clear();
        	 BLayout l=new BLayout();
        	 //SLayout l=new SLayout();
             Article.blay=l;
             Layout.position=0;
           }                 
       };
       
       ActionListener deleteClusterListener = new ActionListener() {
           public void actionPerformed(ActionEvent event) 
           {   
        	   //TODO
        	  if(Layout.mode!=0){
	        	  for(int i =0;i<Article.pList.size();i++)
	        	  {
	        		  if(Article.cList.get(i)==Layout.mode-1)
	        		  {
	        			Article.cList.put(i, -1);  
	        			Article.lay.repaint();
	        		  }
	        	  }
        	  }
        	   
           }                 
         };
       
       ActionListener regenerateListener = new ActionListener() {
           public void actionPerformed(ActionEvent event) 
             {   
               
        	   try{
        	   Weka.calXmeans(Integer.parseInt(jt2.getText()),Integer.parseInt(jt1.getText()));
        	   }
        	   catch(Exception e)
        	   {
        		   e.printStackTrace();
        	   }
        	   Article.minNumCluster=Integer.parseInt(jt1.getText());
        	   Article.maxNumCluster=Integer.parseInt(jt2.getText());
        	   Article.lay.repaint();
             }                 
         };
         
         
       
          
           ActionListener newClusterListener = new ActionListener() {
               public void actionPerformed(ActionEvent event) 
                 {   
            	   //TODO
            	 	dbtype.addItem("cluster"+numCombo);
            	 	numCombo++;
                 }                 
             };
  	
}	  
     
	
	
	
	

