package day0825;

import javax.swing.JButton;
import javax.swing.JFrame;

public class FoodJumunMain extends JFrame{
	FoodForm foodForm = new FoodForm();
	JButton btnFood;
	
	public FoodJumunMain() {
		super("����� ����");
		this.setBounds(100, 60, 1000, 700);
		this.setDesign();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void setDesign() {
		this.setLayout(null);
		
		//���ĵ���߰� ��ư ������!
		btnFood = new JButton("�� �޴� ����ϱ�"	);
		btnFood.setBounds(70, 20, 200, 30);
		this.add(btnFood);
		
	}

	public static void main(String[] args) {
		new FoodJumunMain();
	}

}
