package com.clyao.resume.ui.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JDialog;
import javax.swing.Timer;

import com.clyao.resume.core.dao.MyBatisSessionFactory;
import com.clyao.resume.core.entity.PersonCheck;
import com.clyao.resume.main.ResumeMain;
import com.clyao.resume.ui.frame.LoginFrame;
import com.clyao.resume.ui.frame.MainFrame;
import com.clyao.resume.ui.panel.ImagebgJPanel;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import org.apache.ibatis.session.SqlSession;
import org.jb2011.swing9patch.toast.Toast;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

public class QRCodeDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Webcam webcam;
	private Timer timer;
	private ImagebgJPanel panel;
	private WebcamPanel webcamPanel;
	private JLabel lblAbout;

	public QRCodeDialog() {
		setTitle("二维码扫描");
		setBounds(100, 100, 656, 519);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		//监听扫描窗体关闭事件，如果关闭了就把摄像头也关闭
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				timer.stop();
				webcam.close();
			}
		});
		getContentPane().setLayout(new BorderLayout());
		panel = new ImagebgJPanel(this.getClass().getResource("/images/qrcodebg.png"));
		panel.setBackground(Color.RED);
		panel.setBorder(null);
		getContentPane().add(panel, BorderLayout.CENTER);
		initCapture();
		timer = new Timer(1000, this);
		timer.start();
	}
	
	public void initCapture(){
		webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		panel.setLayout(null);
		
		webcamPanel = new WebcamPanel(webcam);
		webcamPanel.setBounds(374, 168, 212, 164);
		panel.add(webcamPanel);
		
		lblAbout = new JLabel("技术支持:clyao 837904664@qq.com");
		lblAbout.setFont(new Font("宋体", Font.PLAIN, 13));
		lblAbout.setForeground(Color.WHITE);
		lblAbout.setBounds(370, 356, 220, 15);
		panel.add(lblAbout);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		parseQRCode();
	}
	
	public void parseQRCode(){
		try {
			LuminanceSource source = new BufferedImageLuminanceSource(webcam.getImage());
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = new MultiFormatReader().decode(bitmap, hints);
			if(result.getText()!=""){
				int checkNum = checkPerson(result.getText());
				if(checkNum>0){
					timer.stop();
					webcam.close();
					MainFrame.resumeNumber = checkNum;
					LoginFrame.mainFrame = new MainFrame();
					LoginFrame.mainFrame.setVisible(true);
					LoginFrame.qrcodeDialog.setVisible(false);
					ResumeMain.loginFrame.setVisible(false);
				}else{
					Toast.showTost(1000, "此用户未登记！", null);
				}
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//检查企业用户是否登记了
	public int checkPerson(String phone){
		SqlSession session = MyBatisSessionFactory.getSession();
		try {
			PersonCheck personCheck = session.selectOne("selectPersonCheckByPhone", phone);
			if(personCheck!=null){
				//session.update("updatePersonCheckById");
			}
			session.commit();
			return personCheck.getCheckNum();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return 0;
	}
}
