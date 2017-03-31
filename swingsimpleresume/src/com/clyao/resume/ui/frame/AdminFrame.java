package com.clyao.resume.ui.frame;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import com.clyao.resume.core.dao.MyBatisSessionFactory;
import com.clyao.resume.core.entity.PersonCheck;
import com.clyao.resume.main.ResumeMain;
import com.clyao.resume.ui.panel.ImagebgJPanel;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.session.SqlSession;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

/**   
* @Title: 自助简历查询系统管理系统
* @Package com.clyao.swingsimpleresume.views
* @Description: 自助简历查询系统管理系统
* @author clyao   
* @date 2017-03-17 15:00 
* @version V1.0   
*/
public class AdminFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ImagebgJPanel panel;
	private JButton btnExit;
	private JToolBar toolBar;
	private JButton btnAddPerson;
	private JLabel lblAbout;
	//窗体移动变量定义
	private int x;
	private int y;
	private boolean isDraging;
	private JPanel panelAddPersonCheck;
	private JTextField tfPhone;
	private JTextField tfCheckNum;
	private JTable table;
	private JLabel lblPhone;
	private JLabel lblCheckNum;
	private JButton btnAddPersonCheck;
	private JScrollPane scrollPane;
	private JButton btnSafair;
	private JPanel panelSafair;
	private JPanel panelContent;
	private CardLayout cardLayout;
	private JWebBrowser webBrowserWK;
	private DefaultTableModel defaultTableModel;
	private String[] tableHeaderData;
	private Object[][] tableContentData;
	private JButton btnBack;

	public AdminFrame() {
		setTitle("简历管理系统");
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new ImagebgJPanel(this.getClass().getResource("/images/resumebg.png"));
		panel.setBorder(null);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBounds(0, 85, 1024, 70);
		toolBar.addSeparator();
		panel.add(toolBar);
		
		btnAddPerson = new JButton("添加用户");
		btnAddPerson.setFocusPainted(false);
		btnAddPerson.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAddPerson.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAddPerson.setIcon(new ImageIcon(this.getClass().getResource("/images/addperson.png")));
		toolBar.add(btnAddPerson);
		//工具栏添加用户按钮的监听事件
		btnAddPerson.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(panelContent, "panelAddPersonCheck");
			}
		});
		
		btnSafair = new JButton("网上冲浪");
		btnSafair.setFocusPainted(false);
		btnSafair.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSafair.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSafair.setIcon(new ImageIcon(this.getClass().getResource("/images/safair.png")));
		toolBar.add(btnSafair);
		//工具栏网上冲浪按钮的监听事件
		btnSafair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(panelContent, "panelSafair");
			}
		});
		
		cardLayout = new CardLayout(0, 0);
		panelContent = new JPanel();
		panelContent.setBorder(null);
		panelContent.setBounds(0, 155, 1024, 560);
		panel.add(panelContent);
		panelContent.setLayout(cardLayout);
		
		panelAddPersonCheck = new JPanel();
		panelContent.add(panelAddPersonCheck, "panelAddPersonCheck");
		panelAddPersonCheck.setLayout(null);
		
		lblPhone = new JLabel("手机号：");
		lblPhone.setBounds(67, 34, 50, 15);
		panelAddPersonCheck.add(lblPhone);
		
		lblCheckNum = new JLabel("查看简历数量：");
		lblCheckNum.setBounds(457, 34, 85, 15);
		panelAddPersonCheck.add(lblCheckNum);
		
		tfPhone = new JTextField();
		tfPhone.setBounds(117, 31, 300, 21);
		panelAddPersonCheck.add(tfPhone);
		tfPhone.setColumns(10);
		
		tfCheckNum = new JTextField();
		tfCheckNum.setBounds(542, 31, 300, 21);
		panelAddPersonCheck.add(tfCheckNum);
		tfCheckNum.setColumns(10);
		
		btnAddPersonCheck = new JButton("添加");
		btnAddPersonCheck.setBounds(875, 30, 93, 23);
		btnAddPersonCheck.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		panelAddPersonCheck.add(btnAddPersonCheck);
		//添加用户按钮的监听事件
		btnAddPersonCheck.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String phone = tfPhone.getText().trim();
				String checkNum = tfCheckNum.getText().trim();
				if(!phone.equalsIgnoreCase("") && !checkNum.equalsIgnoreCase("")){
					if(insertPersonCheck(phone, checkNum)>0){
						JOptionPane.showMessageDialog(null, "用户添加成功！", "新增成功", JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "用户添加失败！", "新增失败", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "手机或查看数量为空！", "新增失败", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 85, 1004, 465);
		panelAddPersonCheck.add(scrollPane);
		
		table = new JTable();
		tableHeaderData = new String[5];
		tableHeaderData[0] = "企业编号";
		tableHeaderData[1] = "手机号码";
		tableHeaderData[2] = "查看数量";
		tableHeaderData[3] = "今天是否查看";
		tableHeaderData[4] = "添加日期";
		tableContentData = new Object[][]{};
		defaultTableModel = new DefaultTableModel(tableContentData, tableHeaderData);
		getPersonCheckAll(defaultTableModel);
		table.setModel(defaultTableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(120);
		scrollPane.setViewportView(table);
		
		panelSafair = new JPanel();
		panelSafair.setBorder(null);
		panelContent.add(panelSafair, "panelSafair");
		panelSafair.setLayout(new BorderLayout(0, 0));
		
		btnExit = new JButton();
		btnExit.setBounds(894, 715, 130, 53);
		btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExit.setIcon(new ImageIcon(this.getClass().getResource("/images/exitnormal.png")));
		panel.add(btnExit);
		//退出按钮监听事件
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnExit.setIcon(new ImageIcon(this.getClass().getResource("/images/exitpress.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnExit.setIcon(new ImageIcon(this.getClass().getResource("/images/exitnormal.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(1);
			}
		});
		
		btnBack = new JButton();
		btnBack.setBounds(0, 715, 130, 53);
		btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBack.setIcon(new ImageIcon(this.getClass().getResource("/images/backnormal.png")));
		panel.add(btnBack);
		
		//返回按钮监听事件
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnBack.setIcon(new ImageIcon(this.getClass().getResource("/images/backpress.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnBack.setIcon(new ImageIcon(this.getClass().getResource("/images/backnormal.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				ResumeMain.loginFrame.setVisible(true);
				LoginFrame.adminFrame.setVisible(false);
			}
		});
		
		lblAbout = new JLabel("Copyright © 2017 技术支持:clyao 837904664@qq.com Version:1.0");
		lblAbout.setForeground(Color.WHITE);
		lblAbout.setBounds(299, 730, 425, 23);
		panel.add(lblAbout);
		
		moveFrame();
		initWebkitWebBrowser();
	}
	
	//新增用户
	public int insertPersonCheck(String phone, String checkNum){
		SqlSession session = MyBatisSessionFactory.getSession();
		try {
			PersonCheck personCheck = new PersonCheck();
			personCheck.setPhone(phone);
			personCheck.setCheckNum(Integer.parseInt(checkNum));
			personCheck.setIsCheck(0);
			int insertResult = session.insert("insertPersonCheck", personCheck);
			session.commit();
			return insertResult;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return 0;
	}
	
	//初始化WebKit浏览器
	public void initWebkitWebBrowser(){
		try {
			webBrowserWK = new JWebBrowser(JWebBrowser.useWebkitRuntime());
			panelSafair.add(webBrowserWK, BorderLayout.CENTER);
			webBrowserWK.setBarsVisible(false);
			webBrowserWK.setMenuBarVisible(false);
			webBrowserWK.setLocationBarVisible(true);
			webBrowserWK.setButtonBarVisible(false);
			webBrowserWK.setStatusBarVisible(false);
			webBrowserWK.setJavascriptEnabled(true);
			webBrowserWK.navigate("https://www.baidu.com/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//查询所有的企业用户
	public void getPersonCheckAll(DefaultTableModel model){
		SqlSession session = MyBatisSessionFactory.getSession();
		try {
			List<PersonCheck> personCheckList = session.selectList("selectPersonCheck");
			Object[] rowData = new Object[5];
			for (PersonCheck personCheck : personCheckList) {
				rowData[0] = personCheck.getId();
				rowData[1] = personCheck.getPhone();
				rowData[2] = personCheck.getCheckNum();
				rowData[3] = personCheck.getIsCheck();
				rowData[4] = personCheck.getCreateDate();
				model.addRow(rowData);
			}
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	//无标题栏实现窗体移动
	public void moveFrame(){
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				isDraging = true;
				x = e.getX();
				y = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				isDraging = false;
			}
			
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (isDraging) {
					int left = getLocation().x;
					int top = getLocation().y;
					setLocation(left + e.getX() - x, top + e.getY() - y);
				} 
			}
		});
	}
}
