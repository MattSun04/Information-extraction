package com.sunyutian.core;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jniwrapper.PlatformContext;
import com.jniwrapper.win32.jexcel.ExcelException;
//import com.jniwrapper.win32.jexcel.samples.demo.JExcelDemo;
import com.jniwrapper.win32.jexcel.samples.demo.JWorkbookWindow;



public class ListPanel {
	public JPanel p;
	public JButton setButton,jb,jb1;
	public JTextField jf;
	public JTextField jf1;
	public JLabel jl1;
	
	public static String s="cluster 1";
	public ListPanel()
	{
		
		p=new JPanel();
		
		p.setLayout(new BoxLayout(p,BoxLayout.PAGE_AXIS));
		JPanel p2=new JPanel();
		p2.setLayout(new GridLayout(10,1));
		
		
		JComboBox dbtype = new JComboBox();
		//dbtype.addItem("Display All");
		int count=0;
	
	
		
		while(count<Article.itemList.size())
		{
			count++;
			dbtype.addItem("cluster"+count);	
		}
		dbtype.setSelectedItem(s);
		dbtype.addItemListener(buttonListener);
		//p.add(new ListDemo());
		p2.add(dbtype);
		
		
		setButton=new JButton("Set this cluster as standard");
		
		setButton.addActionListener(setListener);
		p2.add(setButton);
		//jb=new JButton("regenerate clusters");
		jb=new JButton("NextStep");
		jb.addActionListener(nextStepListener);
		JButton back=new JButton("back");
		back.addActionListener(backListener);
		p2.add(jb);
		p2.add(back);
		ListDemo ld=new ListDemo();
		Dimension d=new Dimension(400,200);
		ld.setPreferredSize(d);
		p.add(ld);
		p.add(p2);
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
					ListPanel.s=s;
					
					if(s.charAt(s.length()-1)=='l')
	        		{
	        		BLayout.mode=0;
	        		}
	        		else
	        		{
	        			 int c=Character.getNumericValue(s.charAt(s.length()-1));
	        			 BLayout.mode=c; 
	        		}
	            	Article.blay.repaint();
				}   
	        	catch(Exception head){    
	                     System.out.println("Combo Box Error!");  
	                     head.printStackTrace();
	            }
			} 
		}
	         
	};  
	
	
	
	 ActionListener backListener = new ActionListener() {
         public void actionPerformed(ActionEvent event) 
           {   
              Article.blay.getFrame().setVisible(false);
              Article.lay.getFrame().setVisible(true);
              
           }                 
       };
       
       ActionListener setListener = new ActionListener() {
           public void actionPerformed(ActionEvent event) 
             {   
                Article.StructureStandard=BLayout.mode-1;
               
             }                 
         };
       
       ActionListener nextStepListener = new ActionListener() {
           public void actionPerformed(ActionEvent event) 
             {   
        	   	Article.blay.getFrame().setVisible(false);
        	   	//Article.childFrame.removeAll();
        	   	//Article.itemList.clear();
          	 	SLayout l=new SLayout();
          	 	//SLayout l=new SLayout();
          	 	Article.slay=l;
          	 	Layout.position=0;
              
        	   
             }                 
         };
	
}	  
     
	
	
	
	

