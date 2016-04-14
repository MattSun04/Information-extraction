package com.jniwrapper.win32.jexcel.samples.demo;

import com.jniwrapper.win32.jexcel.*;
import com.jniwrapper.win32.jexcel.ui.JWorkbook;
import com.sunyutian.core.Article;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * This sample demonstrates how to listen to workbook and worksheet events.
 * <p/>
 * The sample works with MS Excel in non-embedded mode.
 */
public class EventListenersSample
{
    public EventListenersSample(Workbook workbook) 
    {
        //Start MS Excel application. Application starts invisible and without any workbooks
        

        addListeners(workbook);
       
        //Perform cleanup after yourself and close the MS Excel application forcing it to quit
        //workbook.close(false);
        
    }

    /**
     * Add event listeners to workbook and then iterate over its worksheets and add listeners to
     * each of them.
     *
     * @param workbook - workbook to add listeners to.
     */
    private static void addListeners(GenericWorkbook workbook)
    {
        WorkbookEventListenerImpl workbookEventListener = new WorkbookEventListenerImpl();
        WorksheetEventListenerImpl worksheetEventListener = new WorksheetEventListenerImpl();

        workbook.addWorkbookEventListener(workbookEventListener);

        List worksheets = workbook.getWorksheets();
        for (int i = 0; i < worksheets.size(); i++)
        {
            Worksheet worksheet = (Worksheet) worksheets.get(i);
            worksheet.addWorksheetEventListener(worksheetEventListener);
        }
    }

    
    private static void runEventsGenerator(GenericWorkbook workbook) throws ExcelException
    {
        //Activate worksheets in turn
        List worksheets = workbook.getWorksheets();
        for (int i = 0; i < worksheets.size(); i++)
        {
            Worksheet worksheet = (Worksheet) worksheets.get(i);
            worksheet.activate();
        }

        //Create new worksheet
        Worksheet worksheet = workbook.addWorksheet("Custom sheet");
        worksheet.addWorksheetEventListener(new WorksheetEventListenerImpl());

        //Fill in cells
        for (int i = 1; i < 3; i++)
            for (int j = 1; j < 3; j++)
            {
                Cell cell = worksheet.getCell(i, j);
                cell.setValue(i * j);
            }

        //Clear filled cells
        //worksheet.getRange("A1:D3").clear();
    }

    public static void log(String message)
    {
        System.out.println(message);
    }

    public static class WorkbookEventListenerImpl extends WorkbookEventAdapter
    {
        public void activate(WorkbookEventObject eventObject)
        {  
        	log("\"" + eventObject.getWorkbook().getWorkbookName() + "\" is activated.");
        }

        public void deactivate(WorkbookEventObject eventObject)
        {
            log("\"" + eventObject.getWorkbook().getWorkbookName() + "\" is deactivated.");
        }

        public void newSheet(WorkbookEventObject eventObject)
        {
            log("\"" + eventObject.getWorksheet().getName() + "\" is added to \"" +
                    eventObject.getWorkbook().getWorkbookName() + "\".");
        }

        public void beforeClose(WorkbookEventObject eventObject)
        {
            log("\"" + eventObject.getWorkbook().getWorkbookName() + "\" is closed.");
        }
    }

    public static class WorksheetEventListenerImpl extends WorksheetEventAdapter
    {
        public void changed(WorksheetEventObject eventObject)
        {
            log(eventObject.getRange().getAddress() + " is changed.");
        }

        public void activated(WorksheetEventObject eventObject)
        {	
        	String name=eventObject.getWorksheet().getName();
        	int clusterno=Integer.parseInt(name.substring(name.length()-1,name.length()));
        	String s=Article.itemList.get(clusterno-1).getText();
            Article.sjw.contentPane.remove(Article.sjw.jp);
            Article.sjw.jp.remove(Article.sjw.jp);
            Article.sjw.ta.setText(s);
            Article.sjw.jp=new JScrollPane(Article.sjw.ta);
            Article.sjw.jp.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
            Article.sjw.jp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            Dimension d=new Dimension(1600,200);
            Article.sjw.jp.setPreferredSize(d);
            Article.sjw.contentPane.add(Article.sjw.jp,BorderLayout.SOUTH);
            Article.sjw.invalidate();
            Article.sjw.repaint();
            Article.sjw.setVisible(true);
            
            log("\"" + eventObject.getWorksheet().getName() + "\" is activated.");
        }

        public void deactivated(WorksheetEventObject eventObject)
        {
            log("\"" + eventObject.getWorksheet().getName() + "\" is deactivated.");
        }
    }

}