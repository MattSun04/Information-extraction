package com.sunyutian.core;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
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



public class ButtonPanel2 {
	public JPanel p;
	public JButton jb,jb1;
	public JTextField jf;
	public JTextField jf1;
	public JLabel jl1;
	
	public static String s="cluster 1";
	public ButtonPanel2()
	{
		p=new JPanel();
		p.setLayout(new GridLayout(4,1));
		
		JComboBox dbtype = new JComboBox();
		//dbtype.addItem("Display All");
		int count=0;
		
		jl1=new JLabel("Generate according to baseline:");
		jf1=new JTextField("1");
		jb1=new JButton("generate");
		p.add(jl1);
		p.add(jf1);
		p.add(jb1);
		while(count<Article.itemList.size())
		{
			count++;
			dbtype.addItem("cluster"+count);	
		}
		dbtype.setSelectedItem(s);
		dbtype.addItemListener(buttonListener);
		
		p.add(dbtype);
		//jb=new JButton("regenerate clusters");
		jb=new JButton("NextStep(Open Jexcel)");
		jb.addActionListener(nextStepListener);
		JButton back=new JButton("back");
		back.addActionListener(backListener);
		p.add(jb);
		p.add(back);
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
					ButtonPanel2.s=s;
					
					if(s.charAt(s.length()-1)=='l')
	        		{
	        		SLayout.mode=0;
	        		}
	        		else
	        		{
	        			 int c=Character.getNumericValue(s.charAt(s.length()-1));
	        			 SLayout.mode=c; 
	        		}
	            	System.out.println(SLayout.mode);
	            	Article.slay.repaint();
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
              Article.slay.getFrame().setVisible(false);
              Article.blay.getFrame().setVisible(true);
              
           }                 
       };
       
       ActionListener nextStepListener = new ActionListener() {
           public void actionPerformed(ActionEvent event) 
             {   
        	   if (!PlatformContext.isWindows())
               {
                   JOptionPane.showMessageDialog(null, "JExcel Demo can be launch in MS Windows enviroment only!",
                           "JExcel Demo", JOptionPane.WARNING_MESSAGE);
               }
        	   
        	   
        	   try {
        		   Article.sjw= new JWorkbookWindow();
        		   Article.sjw.setSize(800, 600);
        		   Article.sjw.setLocationRelativeTo(null);
        		   Article.sjw.setVisible(true);
        	   	} catch (ExcelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
        	   }
              
        	   
             }                 
         };
	
}	  
     
	
	
	
	

