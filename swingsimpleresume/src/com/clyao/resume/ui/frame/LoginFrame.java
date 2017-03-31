package com.clyao.resume.ui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.ibatis.session.SqlSession;
import org.jb2011.swing9patch.toast.Toast;

import com.clyao.resume.core.dao.MyBatisSessionFactory;
import com.clyao.resume.core.entity.PersonCheck;
import com.clyao.resume.main.ResumeMain;
import com.clyao.resume.ui.dialog.QRCodeDialog;
import com.clyao.resume.ui.panel.ImagebgJPanel;
import com.clyao.resume.util.DateUtil;

import java.awt.CardLayout;

import javax.swing.JPasswordField;

/**   
* @Title: 自助简历查询系统登录窗体
* @Package com.clyao.swingsimpleresume.views
* @Description: 自助简历查询系统登录窗体
* @author clyao   
* @date 2017-03-17 15:00 
* @version V1.0   
*/
public class LoginFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = -2845819441343166462L;
	public static MainFrame mainFrame;
	public static AdminFrame adminFrame;
	public static QRCodeDialog qrcodeDialog;
	private JPanel contentPane;
	private ImagebgJPanel panel;
	private JTextField tfPhone;
	private JButton btnCompanyLogin;
	private JLabel lblTime;
	private JLabel lblDate;
	private Timer timer;
	private JButton btnKeyBoard1;
	private JPanel panelLogin;
	private ImagebgJPanel panelAdmin;
	private ImagebgJPanel panelCompany;
	private JButton btnKeyBoard2;
	private JButton btnAdminLogin;
	private JLabel lblCompany;
	private JLabel lblAdmin;
	private CardLayout cardLayout;
	private JPasswordField pfAdmin;
	private JButton btnQRCode;
	
	/** 
	* @Title: 初始化登录窗体的布局
	* @Description: 初始化登录窗体的布局
	* @param 无
	* @return void 
	* @throws 布局异常
	*/
	public LoginFrame() {
		setTitle("用户登陆");
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new ImagebgJPanel(this.getClass().getResource("/images/loginbg.png"));
		panel.setBorder(null);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		cardLayout = new CardLayout(0, 0);
		panelLogin = new JPanel();
		panelLogin.setBorder(null);
		panelLogin.setBounds(579, 271, 335, 303);
		panelLogin.setLayout(cardLayout);
		panel.add(panelLogin);
		
		panelCompany = new ImagebgJPanel(this.getClass().getResource("/images/companyloginbg.png"));
		panelLogin.add(panelCompany, "panelCompany");
		panelCompany.setLayout(null);
		
		tfPhone = new JTextField();
		tfPhone.setBounds(52, 115, 215, 24);
		tfPhone.setText("请输入已登记的手机号");
		tfPhone.setFont(new Font("宋体", Font.PLAIN, 16));
		tfPhone.setForeground(Color.BLACK);
		tfPhone.setOpaque(false);
		tfPhone.setBorder(null);
		tfPhone.setColumns(10);
		panelCompany.add(tfPhone);
		//输入电话号码的监听事件
		tfPhone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(tfPhone.getText().equalsIgnoreCase("请输入已登记的手机号")){
					tfPhone.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(tfPhone.getText().equalsIgnoreCase("")){
					tfPhone.setText("请输入已登记的手机号");
				}
			}
		});
		
		
		btnKeyBoard1 = new JButton();
		btnKeyBoard1.setBounds(275, 113, 40, 25);
		btnKeyBoard1.setContentAreaFilled(false);
		btnKeyBoard1.setIcon(new ImageIcon(LoginFrame.class.getResource("/images/keyboard.png")));
		panelCompany.add(btnKeyBoard1);
		//企业界面的键盘监听事件
		btnKeyBoard1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					tfPhone.requestFocus();
					Runtime.getRuntime().exec("cmd /c osk");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnCompanyLogin = new JButton();
		btnCompanyLogin.setBounds(90, 190, 139, 35);
		btnCompanyLogin.setContentAreaFilled(false);
		btnCompanyLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCompanyLogin.setBorder(null);
		btnCompanyLogin.setIcon(new ImageIcon(this.getClass().getResource("/images/loginnormal.png")));
		panelCompany.add(btnCompanyLogin);
		//企业登录按钮的监听事件
		btnCompanyLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCompanyLogin.setIcon(new ImageIcon(this.getClass().getResource("/images/loginpress.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnCompanyLogin.setIcon(new ImageIcon(this.getClass().getResource("/images/loginnormal.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				String mobile = tfPhone.getText().trim();
				if(!mobile.equals("") || !mobile.equals("请输入已登记的手机号") || mobile!=""){
					int checkNum = checkPerson(mobile);
					if(checkNum!=0){
						tfPhone.setText("");
						timer.stop();
						MainFrame.resumeNumber = checkNum;
						mainFrame = new MainFrame();
						mainFrame.setVisible(true);
						ResumeMain.loginFrame.setVisible(false);
					}else{
						JOptionPane.showMessageDialog(null, "此手机号没有登记或您今天已查阅过了！", "登录失败", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		lblAdmin = new JLabel();
		lblAdmin.setBounds(205, 22, 100, 20);
		lblAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelCompany.add(lblAdmin);
		
		//管理员标题的监听事件
		lblAdmin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(panelLogin, "panelAdmin");
			}
		});
		
		btnQRCode = new JButton();
		btnQRCode.setFocusPainted(false);
		btnQRCode.setContentAreaFilled(false);
		btnQRCode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnQRCode.setBounds(300, 270, 32, 32);
		btnQRCode.setIcon(new ImageIcon(LoginFrame.class.getResource("/images/qrcode.png")));
		panelCompany.add(btnQRCode);
		//二维码按钮的监听事件
		btnQRCode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					qrcodeDialog = new QRCodeDialog();
					qrcodeDialog.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
					Toast.showTost(5000, "无法检测到摄像头，请与管理员联系！", null);
				}
			}
		});
		
		panelAdmin = new ImagebgJPanel(this.getClass().getResource("/images/adminloginbg.png"));
		panelLogin.add(panelAdmin, "panelAdmin");
		panelAdmin.setLayout(null);
		
		pfAdmin = new JPasswordField();
		pfAdmin.setBorder(null);
		pfAdmin.setBounds(60, 115, 210, 24);
		panelAdmin.add(pfAdmin);
		
		btnKeyBoard2 = new JButton();
		btnKeyBoard2.setContentAreaFilled(false);
		btnKeyBoard2.setBounds(275, 113, 40, 25);
		btnKeyBoard2.setIcon(new ImageIcon(LoginFrame.class.getResource("/images/keyboard.png")));
		panelAdmin.add(btnKeyBoard2);
		
		//企业界面的键盘监听事件
		btnKeyBoard2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					pfAdmin.requestFocus();
					Runtime.getRuntime().exec("cmd /c osk");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnAdminLogin = new JButton();
		btnAdminLogin.setContentAreaFilled(false);
		btnAdminLogin.setBorder(null);
		btnAdminLogin.setBounds(90, 190, 139, 35);
		btnAdminLogin.setIcon(new ImageIcon(this.getClass().getResource("/images/loginnormal.png")));
		panelAdmin.add(btnAdminLogin);
		//管理员登录按钮的监听事件
		btnAdminLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAdminLogin.setIcon(new ImageIcon(this.getClass().getResource("/images/loginpress.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAdminLogin.setIcon(new ImageIcon(this.getClass().getResource("/images/loginnormal.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				String adminPW = String.valueOf(pfAdmin.getPassword()).trim();
				if(!adminPW.equals("") && adminPW.equalsIgnoreCase("king123")){
					pfAdmin.setText("");
					adminFrame = new AdminFrame();
					adminFrame.setVisible(true);
					ResumeMain.loginFrame.setVisible(false);
				}else{
					JOptionPane.showMessageDialog(null, "管理员密码错误！", "登录失败", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		lblCompany = new JLabel();
		lblCompany.setBounds(30, 22, 100, 20);
		lblCompany.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelAdmin.add(lblCompany);
		//企业登录标题的监听事件
		lblCompany.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(panelLogin, "panelCompany");
			}
		});
		
		lblTime = new JLabel("08:30");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setForeground(Color.WHITE);
		lblTime.setFont(new Font("宋体", Font.PLAIN, 36));
		lblTime.setBounds(884, 10, 130, 44);
		lblTime.setText(DateUtil.getCurrentTimeString());
		panel.add(lblTime);
		
		lblDate = new JLabel("2017-03-16 星期四");
		lblDate.setForeground(Color.WHITE);
		lblDate.setFont(new Font("宋体", Font.PLAIN, 14));
		lblDate.setBounds(884, 55, 130, 23);
		lblDate.setText("2017-03-27 星期一");
		panel.add(lblDate);
		
		timer = new Timer(60000, this);
		timer.start();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		lblTime.setText(DateUtil.getCurrentTimeString());
		//启动界面检查网络
		checkNetWork();
	}
	
	//检查网络是否通畅
	public void checkNetWork(){
		try {
			InetAddress address=InetAddress.getByName("www.baidu.com");
			System.out.println(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Toast.showTost(5000, "网络连接异常，请与管理员联系！", null);
		}
	}
}
