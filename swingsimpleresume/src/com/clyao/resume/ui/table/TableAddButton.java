package com.clyao.resume.ui.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import com.clyao.resume.ui.dialog.ShowDialog;
import com.clyao.resume.ui.frame.MainFrame;

public class TableAddButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton button; 
	private JPanel jPanel;
	private Integer personId;
	

	public TableAddButton() {
		super();
		button = new JButton();
		button.setBounds(25, 4, 80, 25);
		button.setText("详细信息");
		button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		button.addActionListener(this);
		
		jPanel = new JPanel();
		jPanel.setBorder(null);
		jPanel.setLayout(null);
		jPanel.add(button);
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(personId!=null && personId>0 && MainFrame.resumeNumber>0){
			MainFrame.resumeNumber = MainFrame.resumeNumber-1;
			MainFrame.lblResumeNumber.setText(String.valueOf(MainFrame.resumeNumber));
			MainFrame.lblResumeNumber.updateUI();
			ShowDialog.resumeId = personId;
			MainFrame.showDialog = new ShowDialog();
			MainFrame.showDialog.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(null, "本次简历查询次数已用完！", "查看详情失败", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		personId =  (Integer) table.getValueAt(row, 0);
		return jPanel;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		return jPanel;
	}

}
