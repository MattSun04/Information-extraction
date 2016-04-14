package com.sunyutian.core;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextPane;


//extend textpane for drawing lines

public class MyTextPane extends JTextPane{
	
	private static final long serialVersionUID = 1L;

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(255, 0, 0, 128));

        Article.fm = g2.getFontMetrics();
        

       
        int height=this.getHeight();
        
        int t=SLayout.mode-1;
        for(int i: Article.itemList.get(t).seperateLine)
        {
        	
        	int width=Article.fm.stringWidth(this.getText().substring(0,i+1));
        	g2.drawLine(width, 0, width, height);
        }
        g2.dispose();
    }
}
