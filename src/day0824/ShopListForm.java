package day0824;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import oracle.db.DbConnect;

public class ShopListForm extends JFrame{
	DbConnect db = new DbConnect();
	ShopDBModel dbmodel = new ShopDBModel();
	DefaultTableModel model;
	JTable table;
	ImageDraw image = new ImageDraw(); //�̹��� ����� ���� ���� Ŭ���� ����
	String imageName;
	Vector<ShopDTO> list;
	
	JButton btnRefresh;

	public ShopListForm() {
		super("��ü��ǰ ���");
		this.setBounds(600, 100, 800, 500);
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setDesign();
		this.setVisible(true);
	}

	private void setDesign() {
		this.setLayout(null);
		list = dbmodel.getAllSangpum(); //db�� ���� �ҷ��� ��ü��ǰ ����(getAllSangpum)�޼��带 list�� ���

		//table ������ ���� �⺻ �ڵ�(?)
		String[] title = {"��ȣ", "��ǰ��", "����", "�ܰ�", "�ѱݾ�"};
		model = new DefaultTableModel(title, 0); //���� �ְ�(��) �హ���� �ϴ� 0���� �߰���! ���߿� �߰��ϰ�
		table = new JTable(model);
		JScrollPane js = new JScrollPane(table);
		js.setBounds(10, 80, 400, 250);
		this.add(js);

		this.dataWrite(); //���ΰ�ħ�� �Ϸ��� �޼���� �������


		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				int selectRow = table.getSelectedRow(); //�� ��ȣ ���! ���ȣ�� selectRow������ �ִ´�.
				imageName = list.get(selectRow).getPhoto(); // ���� �� ��ȣ�� imageName������ �ִ´�.
				image.repaint();
			}
		});
		//���ΰ�ħ ��ư �߰� �� �̺�Ʈ
		btnRefresh = new JButton("���ΰ�ħ");
		btnRefresh.setBounds(150, 350, 100, 30);
		this.add(btnRefresh);
		btnRefresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				list = dbmodel.getAllSangpum(); //db�κ��� ��� �ٽ� ���(��, ���ΰ�ħ�̶�� �Ҹ�)
				dataWrite();
			}
		});
		//���� �ֱ�
		image.setBounds(470, 10, 300, 300);
		this.add(image);

	}


	private void dataWrite() {//(���ΰ�ħ�� ���� �޼���� ���� ����)
		//���� ������ ����
		model.setRowCount(0); //���ΰ�ħ�� �Ϸ��� ���� �����͸� ����� ���ο� �����Ͱ� �;��ϱ� ����!
		
		//���̺� ������ �߰��ϱ�. (���ΰ�ħ�� ���� �޼���� ���� ����)
		for(ShopDTO dto:list) { //DBŬ�������� (while������ �������� �о) dto.set�� ���� �����͵��� list�� �����ž�. 
			Vector<String> data = new Vector<String>();
			data.add(dto.getNum());
			data.add(dto.getSangpum());
			data.add(String.valueOf(dto.getSu()));
			data.add(String.valueOf(dto.getDan()));
			data.add(String.valueOf(dto.getSu()*dto.getDan()));
			data.add(String.valueOf(dto.getGuipday()));

			model.addRow(data); //�� �߰��� �����͸� model�� ���ٷ�(��) �߰����ְڴ�.
		}
	}


	class ImageDraw extends Canvas{ //�̹��� ����� ���� ���� Ŭ����
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if(imageName!=null) {
				Image image = new ImageIcon(imageName).getImage();
				g.drawImage(image, 90, 75, 150, 160, this);
			}
		}
	}

//		public static void main(String[] args) {
//			new ShopListForm();
//		}
}
