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
		super("Shop 관리");
		this.setBounds(100, 100, 200, 300);
		this.setDesign();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}

	private void setDesign() {
		this.setLayout(new GridLayout(4,1)); //4행 1열의 그리드 레이아웃!
		btnAdd = new JButton("상품추가");
		btnDel = new JButton("상품삭제");
		btnUpdate = new JButton("상품수정");
		btnList = new JButton("상품출력");
		
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
