package com.clyao.resume.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JLabel;

import org.apache.ibatis.session.SqlSession;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import com.clyao.resume.core.dao.MyBatisSessionFactory;
import com.clyao.resume.core.entity.Person;
import com.clyao.resume.ui.frame.MainFrame;
import com.clyao.resume.ui.panel.ImagebgJPanel;
import com.clyao.resume.util.ScreenShotsUtil;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**   
* @Title: 自助简历查询系统简历显示窗体
* @Package com.clyao.swingsimpleresume.views
* @Description: 自助简历查询系统简历显示窗体
* @author clyao   
* @date 2017-03-17 15:00 
* @version V1.0   
*/
public class ShowDialog extends JDialog {

	private static final long serialVersionUID = 2145655278149671435L;
	private ImagebgJPanel panelShowResume;
	private JLabel lblUserName;
	private JLabel lblAge;
	private JLabel lblNation;
	private JLabel lblPhone;
	private JLabel lblHeight;
	private JLabel lblSchool;
	private JLabel lblDegree;
	private JLabel lblAddress;
	private JLabel lblSex;
	private JLabel lblMarriage;
	private JLabel lblEmail;
	private JLabel lblPostion;
	public static Integer resumeId;
	private JLabel lblCreateDate;
	private JLabel lblSpeciality;
	private JLabel lblAbout;
	private JButton btnScreenShots;
	private JButton btnClose;

	/** 
	* @Title: 初始化简历显示窗体的布局
	* @Description: 初始化简历显示窗体的布局
	* @param 无
	* @return void 
	* @throws 布局异常
	*/
	public ShowDialog() {
		setTitle("简历详情");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 550);
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		panelShowResume = new ImagebgJPanel(this.getClass().getResource("/images/showresumebg.png"));
		panelShowResume.setBorder(null);
		getContentPane().add(panelShowResume, BorderLayout.CENTER);
		panelShowResume.setLayout(null);
		
		lblUserName = new JLabel("New label");
		lblUserName.setBounds(105, 134, 115, 15);
		panelShowResume.add(lblUserName);
		lblUserName.setText(resumeId + "");
		
		lblNation = new JLabel("New label");
		lblNation.setBounds(280, 164, 68, 15);
		panelShowResume.add(lblNation);
		
		lblPhone = new JLabel("New label");
		lblPhone.setBounds(105, 281, 115, 15);
		lblPhone.setForeground(Color.RED);
		panelShowResume.add(lblPhone);
		
		lblHeight = new JLabel("New label");
		lblHeight.setBounds(280, 191, 68, 15);
		panelShowResume.add(lblHeight);
		
		lblSchool = new JLabel("New label");
		lblSchool.setBounds(105, 223, 115, 15);
		panelShowResume.add(lblSchool);
		
		lblDegree = new JLabel("New label");
		lblDegree.setBounds(105, 251, 115, 15);
		panelShowResume.add(lblDegree);
		
		lblAddress = new JLabel("New label");
		lblAddress.setBounds(464, 251, 193, 15);
		panelShowResume.add(lblAddress);
		
		lblSex = new JLabel("New label");
		lblSex.setBounds(280, 134, 68, 15);
		panelShowResume.add(lblSex);
		
		lblMarriage = new JLabel("New label");
		lblMarriage.setBounds(105, 194, 115, 15);
		panelShowResume.add(lblMarriage);
		
		lblEmail = new JLabel("New label");
		lblEmail.setBounds(464, 283, 193, 15);
		panelShowResume.add(lblEmail);
		
		lblPostion = new JLabel("New label");
		lblPostion.setBounds(360, 73, 226, 15);
		panelShowResume.add(lblPostion);
		lblPostion.setForeground(Color.RED);
		
		lblAge = new JLabel("New label");
		lblAge.setBounds(280, 109, 68, 15);
		panelShowResume.add(lblAge);
		
		lblCreateDate = new JLabel("New label");
		lblCreateDate.setBounds(105, 73, 115, 15);
		panelShowResume.add(lblCreateDate);
		
		lblSpeciality = new JLabel("New label");
		lblSpeciality.setBounds(464, 223, 193, 15);
		panelShowResume.add(lblSpeciality);
		
		lblAbout = new JLabel("Copyright © 2017 技术支持:clyao 837904664@qq.com Version:1.0");
		lblAbout.setBounds(154, 486, 372, 15);
		panelShowResume.add(lblAbout);
		
		btnScreenShots = new JButton("截屏窗口");
		btnScreenShots.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Dimension dimension = getContentPane().getSize();
				Point point = getContentPane().getLocationOnScreen();
				ScreenShotsUtil.screntShotsToSave(point.x, point.y, dimension.width, dimension.height);
			}
		});
		btnScreenShots.setBounds(8, 481, 93, 23);
		btnScreenShots.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		panelShowResume.add(btnScreenShots);
		
		btnClose = new JButton("关闭窗口");
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainFrame.showDialog.dispose();
			}
		});
		btnClose.setBounds(581, 482, 93, 23);
		btnClose.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		panelShowResume.add(btnClose);
		
		//数据库获取简历id对应用户信息
		getPersonById();
	}
	
	public void getPersonById(){
		SqlSession session = MyBatisSessionFactory.getSession();
		try {
			Person person = session.selectOne("selectPersonById", resumeId);
			lblUserName.setText(person.getUserName());
			lblNation.setText(person.getNation());
			lblPhone.setText(person.getPhone());
			lblHeight.setText(String.valueOf(person.getHeight()));
			lblSchool.setText(person.getSchool());
			lblDegree.setText(person.getDegree());
			lblAddress.setText(person.getAddress());
			lblSex.setText(person.getSex());
			lblMarriage.setText(String.valueOf(person.getMarriage()));
			lblEmail.setText(person.getEmail());
			lblSpeciality.setText(person.getSpeciality());
			lblPostion.setText(person.getPosition());
			lblAge.setText(String.valueOf(person.getAge()));
			lblCreateDate.setText(String.valueOf(person.getCreateDate()));
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
