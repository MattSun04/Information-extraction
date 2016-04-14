package com.sunyutian.core;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
 

public class ListDemo extends JPanel {
    private JList list,list2;
    private DefaultListModel listModel;
    private DefaultListModel listModel2;
    private DefaultListModel listModel3;
    
    private static final String hireString = "Hire";
    private static final String fireString = "Fire";
    private JButton fireButton,j,j2;
    
 
    public ListDemo() {
        super(new BorderLayout());
 
        

        listModel = new DefaultListModel();
        listModel2 = new DefaultListModel();
        
        for(int i=0;i<Article.itemList.size();i++)
        {
        	int k=i+1;
        	if(Article.itemList.get(BLayout.mode-1).lowerLevel.contains(i))
        		continue;
        	if(k==BLayout.mode)
        		continue;
        		
        	listModel.addElement("Cluster"+k);
        }
        for(int i:Article.itemList.get(BLayout.mode-1).lowerLevel)
        {
        	int k=i+1;
        	listModel2.addElement("Cluster"+k);
        }
        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list2 = new JList(listModel2);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list2.setSelectedIndex(0);
        list.setVisibleRowCount(15);
        JScrollPane listScrollPane = new JScrollPane(list);
        JScrollPane listScrollPane2 = new JScrollPane(list2);
        Dimension d=new Dimension(300,200);
        listScrollPane2.setPreferredSize(d);
        listScrollPane.setPreferredSize(d);
        
      
 
        fireButton = new JButton(fireString);
        fireButton.setActionCommand(fireString);
        //fireButton.addActionListener(new FireListener());
 
              
        JPanel bPane = new JPanel();
        bPane.setLayout(new BoxLayout(bPane,
        		BoxLayout.PAGE_AXIS));
        j=new JButton(">>");
        j.addActionListener(new RightListener());
        JLabel s=new JLabel(" ");
        j2=new JButton("<<");
        
        j2.addActionListener(new LeftListener());
        bPane.add(j);
        bPane.add(s);
        bPane.add(j2);
        
        
        JPanel displayPane = new JPanel();
        displayPane.setLayout(new BoxLayout(displayPane,
                                           BoxLayout.LINE_AXIS));
        //splitPane.setOneTouchExpandable(true);
        displayPane.add(listScrollPane);
        displayPane.add(bPane);
        displayPane.add(listScrollPane2);
        
        add(displayPane,BorderLayout.CENTER);
    }
    class RightListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           
            int index = list.getSelectedIndex();
           
            int size2 = listModel.getSize();
           
            int size = listModel.getSize();
 
            if (size == 0||index==-1) { //Nobody's left, disable firing.
                 //j.setEnabled(false);
 
            } else { //Select an index.
            	j.setEnabled(true);
                list.setSelectedIndex(index);
                String d=(String)listModel.getElementAt(index);
                listModel.removeElementAt(index);
                int f=Integer.parseInt(d.substring(d.length()-1, d.length()))-1;
                Article.itemList.get(BLayout.mode-1).lowerLevel.add(f);
                int index2 = list2.getSelectedIndex(); //get selected index
                if (index2 == -1) { //no selection, so insert at beginning
                    index2 = 0;
                } else {           //add after the selected item
                    index2++;
                }
     
                listModel2.insertElementAt(d, index2);
                
            }
        }
    }
    
    class LeftListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           
            int index = list2.getSelectedIndex();
           
 
            int size = listModel2.getSize();
 
            if (size == 0||index==-1) { 
                 //j2.setEnabled(false);
 
            } else { //Select an index.
            	j2.setEnabled(true);
                list2.setSelectedIndex(index);
                String d=(String)listModel2.getElementAt(index);
                listModel2.removeElementAt(index);
                
                int index2 = list.getSelectedIndex(); //get selected index
                if (index2 == -1) { //no selection, so insert at beginning
                    index2 = 0;
                } else {           //add after the selected item
                    index++;
                }
     
                listModel.insertElementAt(d, index2);
                
            }
        }
    }
 

    
 
   
 

}