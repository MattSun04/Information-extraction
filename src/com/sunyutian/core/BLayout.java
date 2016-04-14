package com.sunyutian.core;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;


public class BLayout {
	private JPanel p=new JPanel();
	//yellow 1, red 2 ,blue 3 ,green 3 
	Color col[]={Color.YELLOW,Color.RED,Color.BLUE,Color.GREEN,Color.lightGray,Color.orange,Color.darkGray,Color.pink,Color.CYAN,Color.MAGENTA};
	public static int mode=0;
	public static JFrame jf1;
	public JScrollPane scroll;
	public String s;
	public JTextArea ta;
	public static int position;
	
	
	
	
	public void createLayout()
	{
		
		 p=new JPanel();
		 p.setLayout(new BoxLayout(p,BoxLayout.LINE_AXIS));
         //JLabel label = new JLabel("East"+mode, JLabel.CENTER);
         //label.setFont(new Font("Courier", Font.BOLD, 36));
         //label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
         
         JPanel lPane=new JPanel();
         lPane.setLayout(new FlowLayout());
         
         //Panel rPane=new Panel();
         //rPane.setLayout(new FlowLayout());
         ListPanel bp=new ListPanel();
         JPanel rPane=bp.getPanel();
        
         this.s="";
         List<XWPFParagraph> pList=Article.pList;
         for(XWPFParagraph para : pList){
        	 s=s+para.getText()+"\n";
         }
         //System.out.println(s);
         this.ta=new JTextArea(s,50,140);
         Font f=new Font(Font.DIALOG_INPUT,Font.BOLD,11);
         
         ta.setFont(f);
         ta.setEditable(false);
         ta.setLineWrap(true);
         ta.setWrapStyleWord(true);
         ta.addMouseListener(buttonListener);
         Article.fm=ta.getFontMetrics(f);
         
        
         int i=0;
         int num=0;
         while(true)
         {
        	 if(Article.clusterOrderList.get(i)==-1)
        	 {	  i++;
        	 	continue;
        	 }
        	 if(Article.clusterOrderList.get(i)==0)
        	 {
        		 num++;
        		 if(num==mode)
        			 break;
        		 i++;	 
        	 }
        	
         }	
         highlight(ta,s,i);
         scroll = new JScrollPane (ta);
         scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
         scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        // SwingUtilities.invokeLater(doScroll);
         
         //lPane.add(scroll);
         p.add(scroll);
         
         p.add(rPane);
         
         //p.add(label);
        
	}
	
	
	public void highlight(JTextArea ta,String s)
	{
		Highlighter highlighter = ta.getHighlighter();
        
        int p0=0;
        int p1=0;
        HighlightPainter painter;
        Iterator<Entry<Integer, Integer>> it = Article.cList.entrySet().iterator();
        int i=0;
        while (i<Article.cList.size()) {
        	int q=Article.cList.get(i);
            i++;
            p1 = s.indexOf("\n", p1+1);
            if(q==-1)
            {	
            	p0=p1;
            	continue;
            }
        	
            painter = new DefaultHighlighter.DefaultHighlightPainter(col[q]);
            try
            {
           	 highlighter.addHighlight(p0, p1, painter );
           	 p0=p1;
           	 //it.remove(); // avoids a ConcurrentModificationException
            }
            catch(Exception e)
            {
           	e.printStackTrace();
           	break;
            }
           
          }
		
	}
	
	public void highlight(JTextArea ta,String s,int cluster)
	{
		Highlighter highlighter = ta.getHighlighter();
        
        int p0=0;
        int p1=0;
        HighlightPainter painter;
        int i=0;
        while (i<Article.cList.size()) {
            int q=Article.cList.get(i);
            i++;
            p1 = s.indexOf("\n", p1+1);
        	
            if(q==-1)
            {	
            	p0=p1;
            	continue;
            }
            else if(q==cluster)
            {
            	painter = new DefaultHighlighter.DefaultHighlightPainter(col[0]);
            	try
            	{
            		highlighter.addHighlight(p0, p1, painter );
            		p0=p1;
            		//it.remove(); // avoids a ConcurrentModificationException
            	}
            	catch(Exception e)
            	{
            		e.printStackTrace();
            		break;
            	}
            }
            else
            {
            	p0=p1;
            	//it.remove();
            }
          }
        
		
	}
	
	public void generateString()
	{
		 List<XWPFParagraph> pList=Article.pList;
		 List<Integer> integerList=new ArrayList<Integer>();
		 for(int index=0;index<Article.maxNumCluster;index++)
		 {		
			 integerList.clear();
			 int count=0;
			 String temp="";
			 for(XWPFParagraph para : pList)
			 {
				 if(Article.cList.get(count)==index)
		 		 {
	       	 		if(para.getText().trim().isEmpty())
	       	 		{
	       	 			count++;
	       	 			continue;
	       	 		}
					temp=temp+para.getText()+"\n";
					integerList.add(count);
		 		 } 
				 count++;
	         } 
			 
			 generateCluster(temp,index,integerList);
			 //System.out.println(temp);
			
		 }
	}
	
	public void generateCluster(String s,int clusterNo,List<Integer> integerList)
	{
		List<List<Item>> i=new ArrayList<List<Item>>();
		if(!s.isEmpty())
		{
		    Scanner scanner = new Scanner(s);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				int flag=0;
				int temp=0;
				if(s.charAt(0)!=' ')
					flag=1;
				List<Item> l=new ArrayList<Item>();
				for(int j=0;j<line.length();j++)
				{
					if(flag==0&&line.charAt(j)!=' ')
					{
						flag=1;
						temp=j;
					}
					else if(flag==1&&line.charAt(j)==' ')
					{
						flag=0;
						Item it=new Item(temp,j-1,l.size());
						System.out.println(it.value1+" "+it.value2+"\n");
						l.add(it);
					}
					if(flag==1&&j==line.length()-1&&s.charAt(j)!=' ')
					{
						Item it=new Item(temp,j,l.size());
						System.out.println(it.value1+" "+it.value2+"\n");
						l.add(it);
					}
				}
				i.add(l);
			}
			Cluster cl=new Cluster(i,s,clusterNo);
			for(int j:integerList)
			{
				cl.lineNumber.add(j);
			}
			Article.itemList.add(cl);
			Article.clusterOrderList.add(0);
			scanner.close();
		}
		else
		{	
			Article.clusterOrderList.add(-1);
			System.out.println("empty string");
		}
	}
	
	public JPanel getPanel()
	{
		return p;
	}
	
	public JFrame getFrame()
	{
		return jf1;
	}
	
	
	public void repaint()
	{
		Dimension size = jf1.getBounds().getSize();
		jf1.remove(p);
		createLayout();
		//SwingUtilities.invokeLater(doScroll);
		jf1.add(p);
		SwingUtilities.invokeLater(doScroll);
		jf1.revalidate();
		//jf1.setSize(size.width, size.height);
		
	}
	
	public void generateSeperateLine()
	{
		for(int i=0;i<Article.itemList.size();i++)
		{
			Cluster cl=Article.itemList.get(i);
		
			List<List<Item>> iList=new ArrayList<List<Item>>();
			int min=5000;
			//求出最多有多少类
			for(int k=0;k<Article.itemList.get(i).getitemList().size();k++)
	        {
				int size=Article.itemList.get(i).getitemList().get(k).size();
				iList.add(HierCluster.calculate(Article.itemList.get(i).getitemList().get(k)));
				if(size<min)
				{
					min=size;
				}
	        }
			
		    	int offset=min;
				for(int k=0;k<Article.itemList.get(i).getitemList().size();k++)
				{
					List<Range> e=HierCluster.inferSeperateLine(iList.get(k),offset);
					cl.setRangeList(e);
				}
				cl.processRange();
				//cl.removeRangeList();
			}
	
	}
	
	public BLayout()
	{
		mode=1;
		jf1=new JFrame("Structure");
		generateString();
		//Article.childFrame=jf1;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
		System.out.println(d.width+" "+d.height);
		jf1.setSize(d.width,d.height);
        jf1.setDefaultCloseOperation(jf1.DISPOSE_ON_CLOSE);
        createLayout();
        generateSeperateLine();
        jf1.setVisible(true); 	
		jf1.add(p);
        jf1.pack();
	}
	 Runnable doScroll = new Runnable() {
         public void run() {
              scroll.getVerticalScrollBar().setValue(Layout.position);
         }
     };
	
	MouseListener buttonListener = new MouseListener( ) {

		public void mouseClicked(MouseEvent e) {	
						  
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		
        
    };    
	
}
