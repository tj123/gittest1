package com.thread;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class TestTableAdvanced extends JFrame {
	private Object[] columnNames = {"学号", "姓名", "年龄", "性别"};
	private Object[][] rowData = {
		{"s001", "张三", 23, "男"},
		{"s002", "李四", 22, "男"},
		{"s003", "王麻子", 21, "男"}
	};
	private DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames);
	private JTable table = new JTable(tableModel);
	private JButton jbtSave = new JButton("Save");
	private JButton jbtClear = new JButton("Clear");
	private JButton jbtRestore = new JButton("Restore");
	private JButton jbtAddRow = new JButton("Add New Row");
	private JButton jbtAddColumn = new JButton("Add New Column");
	private JButton jbtDeleteRow = new JButton("Delete Selected Row");
	private JButton jbtDeleteColumn = new JButton("Delete Selected Column");
	private JLabel timeLabel = new JLabel();
	
	public TestTableAdvanced() {
		super("JTable高级应用示例");
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocation();
		init();
		addListener();
		setVisible(true);
	}
	
	public void init() {
		JScrollPane scroll = new JScrollPane(table);
		getContentPane().add(scroll);
		
		JPanel p1 = new JPanel(); //存放Save,Clear,Restore按钮
		p1.add(jbtSave);
		p1.add(jbtClear);
		p1.add(jbtRestore);
		
		JPanel p2 = new JPanel(); //存放操作Table的功能按钮
		p2.setLayout(new GridLayout(2,2));
		p2.add(jbtAddRow);
		p2.add(jbtAddColumn);
		p2.add(jbtDeleteRow);
		p2.add(jbtDeleteColumn);
		
		JPanel p3 = new JPanel(); //窗口底部的大面板
		p3.setLayout(new GridLayout(2,1));
		p3.add(p1);
		p3.add(p2);
		getContentPane().add(p3, BorderLayout.SOUTH);
		
		JPanel p4 = new JPanel();
		p4.setLayout(new BorderLayout());
		p4.add(timeLabel, BorderLayout.EAST);
		getContentPane().add(p4, BorderLayout.NORTH);
		
		TimeRunning running = new TimeRunning();
		running.start();
	}
	
	public void setLocation() {
		Dimension d = this.getSize();
		int width = d.width;
		int height = d.height;
		Toolkit kit = this.getToolkit();
		int screenWidth = kit.getScreenSize().width;
		int screenHeight = kit.getScreenSize().height;
		this.setLocation((screenWidth - width) / 2, (screenHeight - height) / 2);
	}
	
	public void addListener() {
		jbtAddRow.addActionListener(new ActionListener() { //匿名内部类实现按钮的监听
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if (index >= 0) {
					tableModel.insertRow(index, new Vector()); //在选择中行上新增数据
				} else {
					tableModel.addRow(new Vector()); //在Table的末尾新增数据
				}
			}
		});
		
		jbtAddColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog("输入列的名字：");
				tableModel.addColumn(name, new Vector());
			}
		});
		
		jbtDeleteRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if (index >= 0) {
					tableModel.removeRow(index);
				}
			}
		});
		
		jbtDeleteColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedColumn();
				if (index >= 0) {
					TableColumnModel columnModel = table.getColumnModel();
					TableColumn tableColumn = columnModel.getColumn(index);
					columnModel.removeColumn(tableColumn);
				}
			}
		});
		
		jbtSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tableModel.dat"));
					out.writeObject(tableModel.getDataVector());
					out.writeObject(getColumnNames());
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		jbtRestore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ObjectInputStream input = new ObjectInputStream(new FileInputStream("tableModel.dat"));
					Vector rowData = (Vector) input.readObject();
					Vector columnNames = (Vector) input.readObject();
					tableModel.setDataVector(rowData, columnNames);
					input.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});		
		
		jbtClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel.setRowCount(0);
			}
		});
		
	}
	
	private Vector getColumnNames() { //获取Table表格中的所有列名
		Vector<String> columnNames = new Vector<String>();
		for (int i = 0; i < table.getColumnCount(); i++) {
			columnNames.add(table.getColumnName(i));
		}
		return columnNames;
	}
	
	class TimeRunning extends Thread { //定义
		public void run() {
			while (true) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String s = sdf.format(new Date());
					timeLabel.setText(s);
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new TestTableAdvanced();
	}
}