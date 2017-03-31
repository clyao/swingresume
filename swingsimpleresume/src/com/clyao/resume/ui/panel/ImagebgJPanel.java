package com.clyao.resume.ui.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**   
* @Title: JPanle图片背景
* @Package com.clyao.swingsimpleresume.views
* @Description: JPanle图片背景
* @author clyao   
* @date 2017-03-17 15:00 
* @version V1.0   
*/
public class ImagebgJPanel extends JPanel { 
	
	private static final long serialVersionUID = 1L;
	
	private Image image = null;  
  
    public ImagebgJPanel(URL imagePath) { 
        this.image = new ImageIcon(imagePath).getImage();  
    }  
    
    /** 
	* @Title: 设置JPanel背景图片
	* @Description: 设置JPanel背景图片
	* @param 无
	* @return void 
	* @throws 无
	*/
    public void setBackgroundImage(String imagePath){
    	this.image = new ImageIcon(imagePath).getImage();
    	this.repaint();
    }
    
    //重新写JPanel
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);  
    }  
}  