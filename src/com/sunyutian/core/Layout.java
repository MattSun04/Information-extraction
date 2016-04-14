package com.sunyutian.core;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
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


public class Layout {
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
		 p.setLayout(new FlowLayout(FlowLayout.LEFT));
         //JLabel label = new JLabel("East"+mode, JLabel.CENTER);
         //label.setFont(new Font("Courier", Font.BOLD, 36));
         //label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
         
         JPanel lPane=new JPanel();
         lPane.setLayout(new FlowLayout());
         
         //Panel rPane=new Panel();
         //rPane.setLayout(new FlowLayout());
         ButtonPanel bp=new ButtonPanel();
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
         if(mode==0)
        	 highlight(ta,s);
         else if(mode>0)
        	 highlight(ta,s,mode-1);
        	 
         
         scroll = new JScrollPane (ta);
         scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
         scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        // SwingUtilities.invokeLater(doScroll);
         
         //lPane.add(scroll);
         p.add(scroll);
         //rPane.setBounds(10, 75, 40,40);
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
            else
            {
            	p0=p1;
            	//it.remove();
            }
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
	
	public Layout()
	{
		jf1=new JFrame("Frame1");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
		ImageIcon img = new ImageIcon("1.jpg");
        jf1.setIconImage(img.getImage());
        jf1.setDefaultCloseOperation(jf1.DISPOSE_ON_CLOSE);
        createLayout();
        jf1.setVisible(true); 
		jf1.add(p);
        //jf1.pack();
		jf1.setSize(d.width,d.height-10);
	}
	 Runnable doScroll = new Runnable() {
         public void run() {
              scroll.getVerticalScrollBar().setValue(Layout.position);
         }
     };
	
	MouseListener buttonListener = new MouseListener( ) {

		public void mouseClicked(MouseEvent e) {	
			  int y = e.getY();   
			  JTextArea s = (JTextArea)e.getSource();  //TextArea的高度  
			  int h = s.getHeight();  				 //TextArea的行数  
			  int r = Article.pList.size();  				 //TextArea的一行的高度  
			  int row_height = h/r;  				 //算出鼠标点击时所在的行数,第一行为0(0,1,2...)  
			  int line_no = y/row_height;  			//TextArea中的内容可以先存在ArrayList<String>中，根据获得的行数删除  
			  Layout.position=scroll.getVerticalScrollBar().getValue();
			  int j=Article.cList.get(line_no)+1;			
			  if(mode==0&&j==0)
			  {
				  
			  }
			  else if(j!=0)
			  {
				Article.cList.put(line_no,-1);  
				repaint();
				
			  }
			  else
			  {
				  Article.cList.put(line_no,mode-1);
				  repaint();
				 
			  }
			  
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
