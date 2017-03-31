package com.clyao.resume.ui.frame;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.List;

import javax.swing.SwingConstants;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import com.clyao.resume.core.dao.MyBatisSessionFactory;
import com.clyao.resume.core.entity.Person;
import com.clyao.resume.main.ResumeMain;
import com.clyao.resume.ui.dialog.ShowDialog;
import com.clyao.resume.ui.panel.ImagebgJPanel;
import com.clyao.resume.ui.table.TableAddButton;
import com.clyao.resume.util.DateUtil;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.apache.ibatis.session.SqlSession;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Cursor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**   
* @Title: 自助简历查询系统主窗体
* @Package com.clyao.swingsimpleresume.views
* @Description: 自助简历查询系统主窗体
* @author clyao   
* @date 2017-03-17 15:00 
* @version V1.0   
*/
public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 5016400648866870174L;
	public static ShowDialog showDialog;
	public static int resumeNumber;
	private JPanel contentPane;
	private ImagebgJPanel panel;
	private JPanel panelResume;
	private JButton btnExit;
	private JLabel lblAbout;
	private JLabel lblTime;
	private JLabel lblDate;
	public static JLabel lblResumeNumber;
	private JLabel lblResumeTitle;
	private Timer timer;
	private JTabbedPane tabbedPane;
	private JPanel panelResumeSearch;
	private JPanel panelWebBrowser;
	private JLabel lblResumeSearchTitle;
	private JTextField tfResumeSearchKeyWord;
	private JButton btnResumeSearch;
	private JWebBrowser webBrowserIE;
	private JWebBrowser webBrowserWK;
	private JTable tableResume;
	private DefaultTableModel defaultTableModel;
	private String[] tableHeaderData;
	private Object[][] tableContentData;
	private JScrollPane scrollPaneResume;
	private JButton btnKeyBoard;
	private JPanel panelWebKit;

	/** 
	* @Title: 初始化主窗体的布局
	* @Description: 初始化主窗体的布局
	* @param 无
	* @return void 
	* @throws 布局异常
	*/
	public MainFrame() {
		setTitle("自助简历查询");
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel = new ImagebgJPanel(this.getClass().getResource("/images/resumebg.png"));
		panel.setBorder(null);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		panelResume = new JPanel();
		panelResume.setBorder(null);
		panelResume.setBounds(0, 85, 1024, 630);
		panel.add(panelResume);
		panelResume.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 68, 1004, 551);
		panelResume.add(tabbedPane);
		
		panelResumeSearch = new JPanel();
		panelResumeSearch.setBorder(null);
		tabbedPane.addTab("自助简历查询", new ImageIcon(this.getClass().getResource("/images/resumesearch.png")), panelResumeSearch, null);
		panelResumeSearch.setLayout(null);
		
		scrollPaneResume = new JScrollPane();
		scrollPaneResume.setBorder(null);
		scrollPaneResume.setBounds(0, 0, 999, 513);
		panelResumeSearch.add(scrollPaneResume);
		
		tableResume = new JTable();
		tableHeaderData = new String[9];
		tableHeaderData[0] = "简历编号";
		tableHeaderData[1] = "姓名";
		tableHeaderData[2] = "性别";
		tableHeaderData[3] = "年龄";
		tableHeaderData[4] = "籍贯";
		tableHeaderData[5] = "学历";
		tableHeaderData[6] = "求职意向";
		tableHeaderData[7] = "日期";
		tableHeaderData[8] = "操作";
		tableContentData = new Object[][]{};
		defaultTableModel = new DefaultTableModel(tableContentData, tableHeaderData);
		getPersonAll(defaultTableModel);
		tableResume.setModel(defaultTableModel);
		tableResume.setRowHeight(35);
		tableResume.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableResume.getColumnModel().getColumn(1).setPreferredWidth(50);
		tableResume.getColumnModel().getColumn(2).setPreferredWidth(50);
		tableResume.getColumnModel().getColumn(3).setPreferredWidth(50);
		tableResume.getColumnModel().getColumn(4).setPreferredWidth(50);
		tableResume.getColumnModel().getColumn(5).setPreferredWidth(50);
		tableResume.getColumnModel().getColumn(6).setPreferredWidth(120);
		tableResume.getColumnModel().getColumn(7).setPreferredWidth(80);
		tableResume.getColumnModel().getColumn(8).setPreferredWidth(80);
		
		//将按钮设置到表格里
		tableResume.getColumn("操作").setCellEditor(new TableAddButton());
		tableResume.getColumn("操作").setCellRenderer(new TableAddButton());
		
		scrollPaneResume.setViewportView(tableResume);
		
		panelWebBrowser = new JPanel();
		panelWebBrowser.setBorder(null);
		panelWebBrowser.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("网页微信登陆", new ImageIcon(this.getClass().getResource("/images/webbrowser.png")), panelWebBrowser, null);
		//初始化IE浏览器
		initIEWebBrowser();
		
		panelWebKit = new JPanel();
		panelWebKit.setBorder(null);
		panelWebKit.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("网页QQ登陆", new ImageIcon(this.getClass().getResource("/images/webkitqq.png")), panelWebKit, null);
		//初始化webkit浏览器
		initWebkitWebBrowser();
		
		btnKeyBoard = new JButton();
		btnKeyBoard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Runtime.getRuntime().exec("cmd /c osk");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnKeyBoard.setBounds(660, 27, 40, 25);
		btnKeyBoard.setIcon(new ImageIcon(MainFrame.class.getResource("/images/keyboard.png")));
		panelResume.add(btnKeyBoard);
		
		lblResumeSearchTitle = new JLabel("请输入关键字：");
		lblResumeSearchTitle.setBounds(216, 22, 84, 36);
		panelResume.add(lblResumeSearchTitle);
		
		tfResumeSearchKeyWord = new JTextField();
		tfResumeSearchKeyWord.setBounds(310, 22, 403, 36);
		tfResumeSearchKeyWord.setColumns(10);
		panelResume.add(tfResumeSearchKeyWord);
		//输入文本框的监听事件
		tfResumeSearchKeyWord.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnKeyBoard.setVisible(true);
				btnKeyBoard.repaint();
			}
		});
		
		btnResumeSearch = new JButton("搜索");
		btnResumeSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnResumeSearch.setFont(new Font("宋体", Font.PLAIN, 16));
		btnResumeSearch.setBounds(723, 22, 125, 36);
		btnResumeSearch.setIcon(new ImageIcon(this.getClass().getResource("/images/search.png")));
		btnResumeSearch.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		panelResume.add(btnResumeSearch);
		//搜索按钮的监听事件
		btnResumeSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String searchKeyWord = "%" + tfResumeSearchKeyWord.getText().trim() + "%";
				DefaultTableModel model =(DefaultTableModel) tableResume.getModel();
				while(model.getRowCount()>0){
				     model.removeRow(model.getRowCount()-1);
				}
				getPersonByPosition(model,searchKeyWord);
			}
		});
		
		btnExit = new JButton();
		btnExit.setContentAreaFilled(false);
		btnExit.setBorder(null);
		btnExit.setBounds(894, 713, 130, 55);
		btnExit.setIcon(new ImageIcon(this.getClass().getResource("/images/backnormal.png")));
		panel.add(btnExit);
		//返回按钮的监听事件
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnExit.setIcon(new ImageIcon(this.getClass().getResource("/images/backpress.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnExit.setIcon(new ImageIcon(this.getClass().getResource("/images/backnormal.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				timer.stop();
				ResumeMain.loginFrame.setVisible(true);
				LoginFrame.mainFrame.setVisible(false);
			}
		});
		
		lblAbout = new JLabel("Copyright © 2017 技术支持:clyao 837904664@qq.com Version:1.0");
		lblAbout.setFont(new Font("宋体", Font.PLAIN, 13));
		lblAbout.setForeground(Color.WHITE);
		lblAbout.setBounds(299, 730, 425, 23);
		panel.add(lblAbout);
		
		lblTime = new JLabel("08:30");
		lblTime.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(new Font("宋体", Font.PLAIN, 30));
		lblTime.setForeground(Color.WHITE);
		lblTime.setBounds(906, 15, 108, 38);
		lblTime.setText(DateUtil.getCurrentTimeString());
		panel.add(lblTime);
		
		timer = new Timer(60000, this);
		timer.start();
		
		lblDate = new JLabel("2017-03-17 星期五");
		lblDate.setForeground(Color.WHITE);
		lblDate.setBounds(906, 55, 108, 15);
		lblDate.setText(DateUtil.getTodateString() + " " + DateUtil.getWeekOfDateString());
		panel.add(lblDate);
		
		lblResumeNumber = new JLabel("30");
		lblResumeNumber.setHorizontalTextPosition(SwingConstants.CENTER);
		lblResumeNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblResumeNumber.setFont(new Font("宋体", Font.PLAIN, 36));
		lblResumeNumber.setForeground(Color.RED);
		lblResumeNumber.setBounds(812, 35, 84, 40);
		lblResumeNumber.setText(String.valueOf(resumeNumber));
		panel.add(lblResumeNumber);
		
		lblResumeTitle = new JLabel("剩余条数：");
		lblResumeTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblResumeTitle.setFont(new Font("宋体", Font.PLAIN, 16));
		lblResumeTitle.setForeground(Color.WHITE);
		lblResumeTitle.setBounds(812, 15, 84, 15);
		panel.add(lblResumeTitle);
	}
	
	//初始化IE浏览器
	public void initIEWebBrowser(){
		try {
			webBrowserIE = new JWebBrowser();
			panelWebBrowser.add(webBrowserIE, BorderLayout.CENTER);
			webBrowserIE.setBarsVisible(false);
			webBrowserIE.setMenuBarVisible(false);
			webBrowserIE.setLocationBarVisible(false);
			webBrowserIE.setButtonBarVisible(false);
			webBrowserIE.setStatusBarVisible(false);
			webBrowserIE.setJavascriptEnabled(true);
			webBrowserIE.navigate("https://wx.qq.com/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//初始化WebKit浏览器
	public void initWebkitWebBrowser(){
		try {
			webBrowserWK = new JWebBrowser(JWebBrowser.useWebkitRuntime());
			panelWebKit.add(webBrowserWK, BorderLayout.CENTER);
			webBrowserWK.setBarsVisible(false);
			webBrowserWK.setMenuBarVisible(false);
			webBrowserWK.setLocationBarVisible(false);
			webBrowserWK.setButtonBarVisible(false);
			webBrowserWK.setStatusBarVisible(false);
			webBrowserWK.setJavascriptEnabled(true);
			webBrowserWK.navigate("http://web2.qq.com/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//查询所有的简易简历
	public void getPersonAll(DefaultTableModel model){
		SqlSession session = MyBatisSessionFactory.getSession();
		try {
			List<Person> personList = session.selectList("selectPerson");
			Object[] rowData = new Object[9];
			for (Person person : personList) {
				rowData[0] = person.getUserId();
				rowData[1] = person.getUserName();
				rowData[2] = person.getSex();
				rowData[3] = person.getAge();
				rowData[4] = person.getNation();
				rowData[5] = person.getDegree();
				rowData[6] = person.getPosition();
				rowData[7] = person.getCreateDate();
				//tableContentData[i][8] = person.getOperator();
				model.addRow(rowData);
			}
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	//根据条件查询的简易简历
	public void getPersonByPosition(DefaultTableModel model, String searchKeyWord){
		SqlSession session = MyBatisSessionFactory.getSession();
		try {
			List<Person> personList = session.selectList("selectPersonByPosition", searchKeyWord);
			Object[] rowData = new Object[9];
			for (Person person : personList) {
				rowData[0] = person.getUserId();
				rowData[1] = person.getUserName();
				rowData[2] = person.getSex();
				rowData[3] = person.getAge();
				rowData[4] = person.getNation();
				rowData[5] = person.getDegree();
				rowData[6] = person.getPosition();
				rowData[7] = person.getCreateDate();
				//tableContentData[i][8] = person.getOperator();
				model.addRow(rowData);
			}
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		lblTime.setText(DateUtil.getCurrentTimeString());
	}
}
