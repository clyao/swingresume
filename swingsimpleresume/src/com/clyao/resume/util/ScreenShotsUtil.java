package com.clyao.resume.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

/**   
* @Title: 截屏工具
* @Package com.clyao.swingsimpleresume.util
* @Description: 截屏工具
* @author clyao   
* @date 2017-03-17 15:00 
* @version V1.0   
*/
public class ScreenShotsUtil implements Transferable {
	
	private Image image;
	
	public ScreenShotsUtil(Image image) {
		super();
		this.image = image;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{DataFlavor.imageFlavor};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		 return DataFlavor.imageFlavor.equals(flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (!DataFlavor.imageFlavor.equals(flavor)) {
			throw new UnsupportedFlavorException(flavor);
			}   
        return image; 
	}
	
	/** 
	* @Title: 将截屏的图片放入到系统的剪切板中
	* @Description: 将截屏的图片放入到系统的剪切板中
	* @param x 屏幕的x坐标   y 屏幕的y坐标  width 截屏的宽    height 截屏的高
	* @return void 
	* @throws 截图异常
	*/
	public static void screntShotsToClipBoard(int x, int y, int width, int height){
		try {
			Robot robot = new Robot();
			BufferedImage screenBufferedImage = robot.createScreenCapture(new Rectangle(x, y, width, height));
			ScreenShotsUtil screenShotsUtil = new ScreenShotsUtil(screenBufferedImage);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(screenShotsUtil, null);
			JOptionPane.showMessageDialog(null, "截屏图片已成功复制到系统剪切板！", "截屏成功", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "截屏失败！", "截屏失败", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/** 
	* @Title: 将截屏的图片保存到桌面
	* @Description: 将截屏的图片保存到桌面
	* @param x 屏幕的x坐标   y 屏幕的y坐标  width 截屏的宽    height 截屏的高
	* @return void 
	* @throws 截图异常
	*/
	public static void screntShotsToSave(int x, int y, int width, int height){
		try {
			Robot robot = new Robot();
			BufferedImage screenBufferedImage = robot.createScreenCapture(new Rectangle(x, y, width, height));
			String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
			ImageIO.write(screenBufferedImage, "jpg", new File(desktopPath + "\\" + System.currentTimeMillis() + ".jpg"));
			JOptionPane.showMessageDialog(null, "截屏图片已成功保存到桌面！", "截屏成功", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "截屏失败！", "截屏失败", JOptionPane.ERROR_MESSAGE);
		}
	}

}
