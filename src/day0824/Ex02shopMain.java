package day0824;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Ex02shopMain extends JFrame implements ActionListener{
	
	JButton btnAdd, btnDel, btnUpdate, btnList;
	
	public Ex02shopMain() {
		super("Shop ����");
		this.setBounds(100, 100, 200, 300);
		this.setDesign();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}

	private void setDesign() {
		this.setLayout(new GridLayout(4,1)); //4�� 1���� �׸��� ���̾ƿ�!
		btnAdd = new JButton("��ǰ�߰�");
		btnDel = new JButton("��ǰ����");
		btnUpdate = new JButton("��ǰ����");
		btnList = new JButton("��ǰ���");
		
		this.add(btnAdd);
		this.add(btnDel);
		this.add(btnUpdate);
		this.add(btnList);
		
		btnAdd.addActionListener(this);
		btnDel.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnList.addActionListener(this);
	}

	public static void main(String[] args) {
		Ex02shopMain ex = new Ex02shopMain();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if(ob==btnAdd) {
			ShopAddForm add = new ShopAddForm();
		} else if(ob==btnDel){
			
		} else if(ob==btnUpdate){
			
		} else if(ob==btnList){
			ShopListForm list = new ShopListForm();
		}
		
		
		
	}

}
