package com.sunyutian.core;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*; 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import javax.swing.UIManager.*;



public class Entrance extends JMenuBar {
      /**
	 * 
	 */
	public static JFrame frame;
	public JPanel jp;
	JButton jb;
	private static final long serialVersionUID = 1L;
	String[] fileItems = new String[] { "Open", "Save", "About","Config" };
      //String[] editItems = new String[] { "Undo", "Cut", "Copy", "Paste" };
      char[] fileShortcuts = { 'N','O','S','X' };
      char[] editShortcuts = { 'Z','X','C','V' };
      private JFileChooser fc=new JFileChooser();; 
      private File f;  
      private int flag; 
      private JFrame frm; 
      public Entrance( ) {
          JMenu fileMenu = new JMenu("File");
          JMenu editMenu = new JMenu("About");
        
          
          // Assemble the File menus with mnemonics.
          ActionListener printListener = new ActionListener( ) {
                  public void actionPerformed(ActionEvent event) {
                      System.out.println("Menu item [" + event.getActionCommand( ) +"] was pressed.");
                      JOptionPane.showMessageDialog(frame, "Version:Beta\n Author:SunYutian\nsunyutian@hotmail.com ");
                  }
              };
              
          ActionListener openFileListener = new ActionListener( ) {
                  public void actionPerformed(ActionEvent event) {
                	  fc.setDialogTitle("Open File");
                	  FileNameExtensionFilter filter = new FileNameExtensionFilter(
                  	        "doc&docx", "doc", "docx");
                     fc.setFileFilter(filter);
                	  InputStream is;
                	  try{    
                               flag=fc.showOpenDialog(frm); 
                               
                               if(flag==JFileChooser.APPROVE_OPTION)   
                               {   
                            	   Article.clearArticle();
                                   f=fc.getSelectedFile(); 
                                   System.out.println("open file----"+f.getName());   
                                   is =new FileInputStream(f);
                                   XWPFDocument doc = new XWPFDocument(is);
                                   Article.pList = doc.getParagraphs();
                                   Article.calVector();
                                   Layout t=new Layout();
                                   Article.lay=t;
                                      //frame.remove(Article.p);
                                      //frame.add(t.getPanel());
                                     // frame.pack( );
                                     
                                }   
                            
                   	}   
                  	catch(Exception head){    
                  				
                               System.out.println("Open File Dialog ERROR!");  
                               head.printStackTrace();
                        } 
                   
                         
                    
                  }
              };    
             
          ActionListener saveFileListener = new ActionListener() {
                public void actionPerformed(ActionEvent event) 
                  {   
                      String fileName;   
                      fc.setDialogTitle("Save File");    
                      FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    	        "doc&docx", "doc", "docx");
                      fc.setFileFilter(filter);
                      try{    
                           flag=fc.showSaveDialog(frm); 
                           
                      	}   
                      catch(HeadlessException he){   
                           System.out.println("Save File Dialog ERROR!");    
                      }   
           
                    //如果按下确定按钮，则获得该文件。   
                      if(flag==JFileChooser.APPROVE_OPTION)   
                      {   
                          //获得你输入要保存的文件   
                            f=fc.getSelectedFile();   
                          //获得文件名   
                            fileName=fc.getName(f);     
                            System.out.println(fileName);   
                      }   
                  }                 
              };
              ActionListener configListener = new ActionListener() {
                  public void actionPerformed(ActionEvent event) 
                    {   
                	  Configuration con=new Configuration();
                    }
                };
              
              
              
              
         
              JMenuItem item = new JMenuItem(fileItems[0], fileShortcuts[0]);
              item.addActionListener(openFileListener);
              fileMenu.add(item);
              
              item = new JMenuItem(fileItems[1], fileShortcuts[1]);
              item.addActionListener(saveFileListener);
              fileMenu.add(item);
              
              item = new JMenuItem(fileItems[2], fileShortcuts[2]);
              item.addActionListener(printListener);
              editMenu.add(item);
              item = new JMenuItem(fileItems[3], fileShortcuts[3]);
              item.addActionListener(configListener);
              editMenu.add(item);
              
          // Assemble the File menus with keyboard accelerators.
          /*for (int i=0; i < editItems.length; i++) {
             item = new JMenuItem(editItems[i]);
              item.setAccelerator(KeyStroke.getKeyStroke(editShortcuts[i],
                  Toolkit.getDefaultToolkit( ).getMenuShortcutKeyMask( ), false));
              item.addActionListener(printListener);
              editMenu.add(item);
          }*/
          // Insert a separator in the Edit menu in Position 1 after "Undo".
          editMenu.insertSeparator(1);
          // Assemble the submenus of the Other menu.
         
          /*subMenu2.add(item = new JMenuItem("Extra 2"));
          item.addActionListener(printListener);
          subMenu.add(item = new JMenuItem("Extra 1"));
          item.addActionListener(printListener);
          subMenu.add(subMenu2);
          // Assemble the Other menu itself.
          otherMenu.add(subMenu);
          otherMenu.add(item = new JCheckBoxMenuItem("Check Me"));
          item.addActionListener(printListener);
          otherMenu.addSeparator( );
          ButtonGroup buttonGroup = new ButtonGroup( );
          otherMenu.add(item = new JRadioButtonMenuItem("Radio 1"));
          item.addActionListener(printListener);
          buttonGroup.add(item);
          otherMenu.add(item = new JRadioButtonMenuItem("Radio 2"));
          item.addActionListener(printListener);
          buttonGroup.add(item);
          otherMenu.addSeparator( );
          otherMenu.add(item = new JMenuItem("Potted Plant",    new ImageIcon("image.gif")));
          item.addActionListener(printListener);*/
          // Finally, add all the menus to the menu bar.
          add(fileMenu);
          add(editMenu);
          //add(otherMenu);
          
          
          
      }
      
      private JButton createButton()
      {
    	  ActionListener openFileListener = new ActionListener( ) {
              public void actionPerformed(ActionEvent event) {
            	  fc.setDialogTitle("Open File");
            	  FileNameExtensionFilter filter = new FileNameExtensionFilter(
                	        "doc&docx", "doc", "docx");
                   fc.setFileFilter(filter);
            	  InputStream is;
                  //这里显示打开文件的对话框   
            	  try{    
                           flag=fc.showOpenDialog(frm); 
                           if(flag==JFileChooser.APPROVE_OPTION)   
                           {   
                                //获得该文件   
                        	   	  Article.clearArticle();
                                  f=fc.getSelectedFile(); 
                                  System.out.println("open file----"+f.getName());   
                                  is =new FileInputStream(f);
                                  XWPFDocument doc = new XWPFDocument(is);
                                  Article.pList = doc.getParagraphs();
                                  Article.calVector();
                                  Layout t=new Layout();
                                  Article.lay=t;
                                 
                            }   
                        
               	}   
              	catch(Exception head){    
              				
                           System.out.println("Open File Dialog ERROR!");  
                           head.printStackTrace();
                    } 
               
       
              }
          };    
    	  jb=new JButton("Open a file");
    	  jb.addActionListener(openFileListener);
    	  return jb;
      }
      
      
      public static void main(String s[]) {
    	  try {
    		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
    		    if ("Nimbus".equals(info.getName())) {
    		        UIManager.setLookAndFeel(info.getClassName());
    		        break;
    		     }
    		}
    		} catch (Exception e) {
    		    System.out.println("nimbus EXCEPTION");
    		}
          frame = new JFrame("Entrance");
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          
          frame.getContentPane().setBackground(Color.WHITE);
          frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.PAGE_AXIS));
          Entrance en=new Entrance();
          frame.setJMenuBar(en);
          JPanel jp=new JPanel();
          jp.setBackground(Color.white);
          jp.add(en.createButton());
          frame.setJMenuBar(en);
          frame.getContentPane().add(jp);
         
          JPanel jp2=new JPanel();
       
          Font f=new Font(Font.DIALOG_INPUT,Font.BOLD,25);
          
          
          JLabel jl=new JLabel("Version:Beta ");
          jl.setFont(f);
          JLabel jl1=new JLabel("Author:SunYutian");
          jl1.setFont(f);
          JLabel jl2=new JLabel("sunyutian@hotmail.com ");
          jl2.setFont(f);
          jp2.add(jl);
          jp2.add(jl1);
          jp2.add(jl2);
          jp2.setBackground(Color.white);
          frame.add(jp2);
         
          //Layout l=new Layout();
          //frame.getContentPane().add(l.getPanel());
          //frame.pack( );
          frame.setVisible(true);
          
         
          frame.setSize(1000, 400);
          //Article.p=l.getPanel();
          
      }
}