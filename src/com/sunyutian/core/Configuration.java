package com.sunyutian.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Configuration {
	
	private JFrame jf;
	private JTextField jt1,jt2,jt3,jt4;
	
	public Configuration()
	{
		jf=new JFrame();
		jf.setSize(600, 300);
		
		Font f=new Font(Font.DIALOG_INPUT,Font.BOLD,11);
		JPanel jp=new JPanel();
		jp.setFont(f);
		JLabel jl=new JLabel("Parameter for line vector: Number");
		JLabel j2=new JLabel("Character:");
		JLabel j3=new JLabel("Others:");
		JLabel j4=new JLabel("Blank Line:");
		jt1=new JTextField();
		jt1.setText(Article.NUMBER+"");
		jt2=new JTextField();
		jt2.setText(Article.CHARACTER+"");
		jt3=new JTextField();
		jt3.setText(Article.OTHERS+"");
		jt4=new JTextField();
		jt4.setText(Article.BLANK+"");
		jp.add(jl);
		jp.add(jt1);
		jp.add(j2);
		jp.add(jt2);
		jp.add(j3);
		jp.add(jt3);
		jp.add(j4);
		jp.add(jt4);
		jp.setBackground(Color.white);
		
		 ActionListener changeListener = new ActionListener( ) {
             public void actionPerformed(ActionEvent event) {
            	 Article.NUMBER=Double.parseDouble(jt1.getText());
            	 Article.CHARACTER=Double.parseDouble(jt2.getText());
            	 Article.OTHERS=Double.parseDouble(jt3.getText());
            	 Article.BLANK=Double.parseDouble(jt4.getText());
            	 jf.setVisible(false);
             }
         };
		
		JButton jb=new JButton("OK");
		jb.addActionListener(changeListener);
		jp.add(jb);
		jf.add(jp);
		jf.setResizable(false);
		jf.setVisible(true);
		
	}
}
