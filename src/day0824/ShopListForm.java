package day0824;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ShopListForm extends JFrame implements ActionListener{
	ShopDBModel dbmodel = new ShopDBModel();
	
	DefaultTableModel model;
	JTable table;
	
	public ShopListForm() {
		super("��ü��ǰ ���");
		this.setBounds(600, 100, 800, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setDesign();
		this.setVisible(true);
	}
	
	private void setDesign() {
		this.setLayout(null);

		dbmodel.getAllSangpum();
		String[] title = {"��ȣ", "��ǰ��", "����", "�ܰ�", "�ѱݾ�"};
		model = new DefaultTableModel(title, 0); //���� �ְ� �హ���� �ϴ� 0���� �߰���!
		table = new JTable(model);
		JScrollPane js = new JScrollPane(table);
		js.setBounds(10, 80, 500, 300);
		this.add(js);
	}
	
	
	public static void main(String[] args) {
		new ShopListForm();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		
	}
	
	
	
}
