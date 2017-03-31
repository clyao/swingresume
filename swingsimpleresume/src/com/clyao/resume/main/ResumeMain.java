package com.clyao.resume.main;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.plaf.InsetsUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;

import com.clyao.resume.ui.frame.LoginFrame;

/**   
* @Title: 自助简历查询系统主窗体
* @Package com.clyao.swingsimpleresume.main
* @Description: 自助简历查询系统主窗体
* @author clyao   
* @date 2017-03-17 15:00 
* @version V1.0   
*/
public class ResumeMain {
	
	public static LoginFrame loginFrame;
	
	public static void main(String[] args) {
		NativeInterface.open();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
					BeautyEyeLNFHelper.launchBeautyEyeLNF();
					UIManager.put("RootPane.setupButtonVisible", false);
					UIManager.put("TabbedPane.tabAreaInsets", new InsetsUIResource(2,2,2,2));
					loginFrame = new LoginFrame();
					loginFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		NativeInterface.runEventPump();
	}
}
