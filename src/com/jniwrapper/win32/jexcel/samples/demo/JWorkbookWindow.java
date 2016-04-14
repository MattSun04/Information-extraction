package com.jniwrapper.win32.jexcel.samples.demo;


import com.jniwrapper.win32.jexcel.Cell;
import com.jniwrapper.win32.jexcel.ExcelException;
import com.jniwrapper.win32.jexcel.GenericWorkbook;
import com.jniwrapper.win32.jexcel.Workbook;
import com.jniwrapper.win32.jexcel.Worksheet;
import com.jniwrapper.win32.jexcel.ui.JWorkbook;
import com.jniwrapper.win32.jexcel.ui.JWorkbookEventAdapter;
import com.jniwrapper.win32.jexcel.ui.JWorkbookEventObject;
import com.jniwrapper.win32.jexcel.ui.JWorkbookInterruptException;
import com.jniwrapper.win32.ui.MessageBox;
import com.jniwrapper.win32.ui.Wnd;
import com.jniwrapper.win32.ui.dialogs.OpenSaveFileDialog;
import com.sunyutian.core.Article;
import com.sunyutian.core.Cluster;
import com.sunyutian.core.Range;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;



public class JWorkbookWindow extends JFrame
{
    public JWorkbook uiWorkbook;
    public String s=" ";
    public JTextArea ta;
    public Container contentPane; 
    
    public JScrollPane jp;
    public JWorkbookWindow() throws ExcelException
    {
        super("Excel");
        
        //Create JWorkbook
        try
        {
            uiWorkbook = new JWorkbook();
        }
        catch (ExcelException e)
        {
            throw new RuntimeException("Unable to create JWorkbook", e);
        }

        uiWorkbook.addJWorkbookEventListener(new JWorkbookEventAdapter()
        {
            public void beforeWorkbookClose(JWorkbookEventObject eventObject) throws JWorkbookInterruptException
            {
                GenericWorkbook workbook = eventObject.getWorkbook();
                if (!workbook.isSaved())
                {
                    int msgBoxresult = MessageBox.show(new Wnd(JWorkbookWindow.this),
                            "Excel confirmation",
                            "Do you want to save changes you made to '" + workbook.getWorkbookName() + "'",
                            MessageBox.YESNO);
                    if (msgBoxresult == MessageBox.IDYES)
                    {
                        Window parent = SwingUtilities.getWindowAncestor(JWorkbookWindow.this);
                        OpenSaveFileDialog saveDialog = new OpenSaveFileDialog(parent);
                        String FILTER = "Excel Workbooks (*.xls) | *.xls";
                        saveDialog.setFilter(FILTER);
                        boolean result = saveDialog.getSaveFileName();
                        if (result)
                        {
                            String newFileName = saveDialog.getFileName();
                            File newFile = new File(newFileName);
                            try
                            {
                                workbook.saveCopyAs(newFile);
                            }
                            catch (IOException e)
                            {
                                throw new JWorkbookInterruptException("Input/Output error", e);
                            }
                        }
                    }
                }
            }
        });

        //Insert the JWorkbook into JFrame
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(uiWorkbook, BorderLayout.CENTER);
       
        //String s=Article.itemList.get(0).getText();
        ta=new JTextArea(s);
       
        Dimension d=new Dimension(1600,200);
        ta.setEditable(false);
        jp=new JScrollPane(ta);
        jp.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        jp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jp.setPreferredSize(d);
        contentPane.add(jp,BorderLayout.SOUTH);
        
        Workbook wb=uiWorkbook.getWorkbook();
       
        
        for(int i=0;i<Article.itemList.size();i++)
        {
        	 int t=i+1;
        	 Worksheet ws= wb.addWorksheet("Cluster"+t);
        	 Cluster cl=Article.itemList.get(i);
        	 Scanner scan = new Scanner(cl.getText());
        	 int count=0;
        	 Collections.sort(cl.seperateLine);
        	 while(scan.hasNext())
        	 {
        		 String temp=scan.nextLine();
        		 
        		 int last=0;
        		 int count1=0;
        		 for(int k:cl.seperateLine)
        		 {
        			 Cell cell=ws.getCell(count+1, count1+1);
        		     cell.setValue(temp.substring(last, k).trim());
        		     count1++;
        		     last=k;
        		 }
        		 Cell cell= ws.getCell(count+1,count1+1);
        		 cell.setValue(temp.substring(last,temp.length()-1).trim());
        		 count++;
        	 }
        	 scan.close();
        	 
        }
        
        
        int column=0;
        for(int i=0;i<Article.itemList.size();i++)
        {
       
	        Worksheet ws= wb.getWorksheet("Sheet1");
	        Cluster cl=Article.itemList.get(i);
	        Scanner scan = new Scanner(cl.getText());
	                
	        if(cl.lowerLevel.isEmpty()||i==Article.StructureStandard)
	        {
		        int count=0;
		        int count1=0;;
		   	 	while(scan.hasNext())
		   	 	{
		   	 		String temp=scan.nextLine();
		   		 
		   	 		int last=0;
		   	 		count1=column;
		   	 		for(int k:cl.seperateLine)
		   	 		{
		   	 			Cell cell=ws.getCell(count+1, count1+1);
		   	 			cell.setValue(temp.substring(last, k).trim());
		   	 			count1++;
		   	 			last=k;
		   	 		}
		   	 		Cell cell= ws.getCell(count+1,count1+1);
		   	 		cell.setValue(temp.substring(last,temp.length()-1).trim());
		   	 		count++;
		   	 		
		   	 	}
		   	 		scan.close();
		   	 		column+=cl.seperateLine.size()+1;
	        }
	        else
	        {
	        	List<Integer> tlist=Article.itemList.get(Article.StructureStandard).getLineNo();
	        	int row_no=tlist.size();
	        	List<String> stringList=new ArrayList<String>();
	        	
	        	int count=0;
		        int count1=0;
		        int line_no=0;
		   	 	while(scan.hasNext())
		   	 	{
		   	 		stringList.clear();
		   	 		String temp=scan.nextLine();
		   		 
		   	 		int last=0;
		   	 		count1=column;
		   	 		for(int k:cl.seperateLine)
		   	 		{
		   	 			Cell cell=ws.getCell(count+1, count1+1);
		   	 			cell.setValue(temp.substring(last, k).trim());
		   	 			stringList.add(temp.substring(last, k).trim());
		   	 			count1++;
		   	 			last=k;
		   	 		}
		   	 		Cell cell= ws.getCell(count+1,count1+1);
		   	 		cell.setValue(temp.substring(last,temp.length()-1).trim());
		   	 		stringList.add(temp.substring(last,temp.length()-1).trim());
		   	 		
		   	 		
		   	 		while(line_no<cl.getLineNo().size()&&count<tlist.size()&&cl.getLineNo().get(line_no)>tlist.get(count))
		   	 		{
		   	 			count1=column;
		   	 			for(String s:stringList)
		   	 			{
		   	 				Cell c= ws.getCell(count+1,count1+1);
		   	 				c.setValue(s);
		   	 				count1++;
		   	 			}
		   	 			count++;
		   	 		}
		   	 		
		   	 		line_no++;
		   	 	}
		   	 	scan.close();
		   	 	
		   	 	
		   	 	column+=cl.seperateLine.size()+1;
	        	
	        	
	        	
	        }
        }
   	 	
   	 	
        EventListenersSample e=new EventListenersSample(wb);
    
        //Add window state listener to clean up after yourself
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
            	uiWorkbook.removeAll();
            	uiWorkbook.close();
            }
        });
    }

   
}