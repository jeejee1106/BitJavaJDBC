package day0825;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;


public class FoodJumunMain extends JFrame{
	FoodForm foodForm = new FoodForm();
	JButton btnFood;
	JTextField tfName, tfAddr;
	JLabel lblFoodName;
	JComboBox<Object> cbjumunNo;
	FoodImage fimage = new FoodImage();
	String photoName;
	FoodDBModel dbModel = new FoodDBModel();
	JButton btnJumun;
	FoodDTO foodDto;

	//�ֹ� ��� ��¿� �ʿ��� ������Ʈ��
	DefaultTableModel tableModel;
	JTable table;
	SelectImage sImage = new SelectImage();
	String selectName;
	Vector<JoinDTO> jumunList;


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
		btnFood.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				foodForm.setVisible(true); //�޴� �߰�â ���̰� �ϱ�
			}
		});
		this.add(btnFood);

		//����? �� ����
		JLabel lbl0 = new JLabel("** �ֹ� ���� �Է� **", JLabel.CENTER);
		lbl0.setBounds(70, 100, 150, 30);
		lbl0.setBorder(new LineBorder(Color.blue,5));
		this.add(lbl0);

		//�ֹ��� �̸� ��, �ؽ�Ʈ ����
		JLabel lbl1 = new JLabel("�ֹ��ڸ�");
		lbl1.setBounds(70, 150, 100, 30);
		this.add(lbl1);
		tfName = new JTextField();
		tfName.setBounds(130, 150, 100, 30);
		this.add(tfName);

		//�ֹ���ȣ ��, �޺��ڽ� ����
		JLabel lbl2 = new JLabel("�ֹ���ȣ");
		lbl2.setBounds(70, 200, 100, 30);
		this.add(lbl2);

		Object[] fname = dbModel.getNumArray();
		cbjumunNo = new JComboBox<Object>(fname);
		cbjumunNo.setBounds(130, 200, 100, 30);
		this.add(cbjumunNo);

		//���� ��ġ ����
		lblFoodName = new JLabel("���ĸ�"); //���� ������ �� �ʿ�� ����. ��ġ ������ ���� �־
		lblFoodName.setBounds(70, 230, 100, 30);
		this.add(lblFoodName);
		fimage.setBounds(70, 270, 200, 200); //ĵ���� ������ ũ��� ��ġ!
		fimage.setBackground(Color.black);
		this.add(fimage);

		//�ּҶ�, �ؽ�Ʈ����
		JLabel lbl3 = new JLabel("�ּ�");
		lbl3.setBounds(70, 500, 100, 30);
		this.add(lbl3);
		tfAddr = new JTextField();
		tfAddr.setBounds(130, 500, 100, 30);
		this.add(tfAddr);


		//�ֹ� ��ư ����
		btnJumun = new JButton("�ֹ��ϱ�");
		btnJumun.setBounds(100, 570, 100, 30);
		btnJumun.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JumunDTO dto = new JumunDTO();
				String name = tfName.getText();
				int num = Integer.parseInt(cbjumunNo.getSelectedItem().toString());
				String addr = tfAddr.getText();

				dto.setName(name);
				dto.setNum(num);
				dto.setAddr(addr);

				dbModel.insertJumun(dto); //jumunWrite() ��¸޼��� ���� ���� �������. �ֳĸ� �����͸� ���� �Ŀ� ����� �ؾ��ϴϱ�!
				
				// �ֹ������� ��� �޼���
				jumunWrite();

				//�� �����
				tfName.setText("");
				tfAddr.setText("");
				cbjumunNo.setSelectedIndex(0);

//				JOptionPane.showMessageDialog(FoodJumunMain.this, "�ֹ��� �Ϸ�Ǿ����ϴ�.");
			}
		});

		this.add(btnJumun);



		//�ֹ���ȣ�� ù��° ��ȣ(num)�� ���� �����Ͱ� �ʱⰪ���� ������??
		int firstnum = Integer.parseInt(cbjumunNo.getSelectedItem().toString()); //�����ϸ� DB�� ù��° ��ȣ(�� ���� ��� ��������ȣ 61)�� �ڵ����� ���õǿ�����. �װ� firstnum�� ��������
		//cbjumunNo.getSelectedItem()�� ������Ʈ Ÿ���̶� ��Ʈ������ ��ȯ �� �ٽ� ��Ƽ���� ��ȯ�ؾ��Ѵ�. (�ѹ��� ��Ƽ���� ��ȯ�ϴ� �ڵ�� ��..�µ�?)
		foodDto = dbModel.getFoodData(firstnum); //firstnum�� �ش��ϴ� ������ dto�� ��´�.(������61, ���ĸ� ������� ���)
		photoName = foodDto.getPhoto(); //������ ���(��������� ��θ� photoName�� ����)
		lblFoodName.setText(foodDto.getFood());
		fimage.repaint();

		//�ֹ���ȣ �޺��ڽ� Change �̺�Ʈ ����
		cbjumunNo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) { //���� �ʱⰪ �ִ� �ڵ�� ���� �Ȱ���. ����. �ֳĸ� ��......ü���� �̺�Ʈ �߻��ϸ鼭.......
				int changenum = Integer.parseInt(cbjumunNo.getSelectedItem().toString()); //cbjumunNo�� ������ƮŸ���̶� ��Ʈ������ ��ȯ �� ��Ƽ���� ��ȯ�ؾ��Ѵ�.
				foodDto = dbModel.getFoodData(changenum); //changenum�� �ش��ϴ� ������ dto�� ��´�
				photoName = foodDto.getPhoto(); //������ ���
				lblFoodName.setText(foodDto.getFood());
				fimage.repaint();
			}
		});

		// ���̺� �߰�
		String[] title = {"�ֹ���", "�ּ�", "�޴���", "����", "���Ը�", "������ġ"};
		tableModel = new DefaultTableModel(title, 0);
		table = new JTable(tableModel);
		JScrollPane js = new JScrollPane(table);
		js.setBounds(350, 250, 600, 300);
		this.add(js);
		jumunWrite();

		//���̺� Ŭ���� �̹��� ���
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				//Ŭ���� ���ȣ
				int row=table.getSelectedRow();
				//row �� �ش��ϴ� dto
				JoinDTO dto=jumunList.get(row);
				//�������� selectName �� �ֱ� ����Ʈ �޼��� ȣ��
				selectName=dto.getPhoto();
				sImage.repaint();

				super.mouseClicked(e);
			}
		});
	}


	public void jumunWrite() {
		//���� ������ ���� (�����Ͱ� ��� ���� ���� �ʵ���!)
		tableModel.setRowCount(0);

		//db �κ��� ��ü �ֹ� ��� ��������
		jumunList = dbModel.getJumunList();

		for(JoinDTO dto:jumunList) {
			Vector<String> data = new Vector<String>();
			data.add(dto.getName());
			data.add(dto.getAddr());
			data.add(dto.getFood());
			data.add(String.valueOf(dto.getPrice()));
			data.add(dto.getShop());
			data.add(dto.getIoc());

			//���̺� �߰�
			tableModel.addRow(data);
		}
	}

	//������ ���� ĵ����
	class FoodImage extends Canvas{
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if(photoName != null) {
				Image image = new ImageIcon(photoName).getImage();
				g.drawImage(image, 0, 0, 200, 200, this); //�� fimage.setBounds(70, 230, 100, 100); ���� ĵ���������� ���� ��ġ 0,0				
			}		
		}
	}



	//���̺� �� ���� �� ������ ���� ĵ����
	class SelectImage extends Canvas{
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if(selectName != null) {
				Image image = new ImageIcon(selectName).getImage();
				g.drawImage(image, 0, 0, 230, 230, this); //�� fimage.setBounds(70, 230, 100, 100); ���� ĵ���������� ���� ��ġ 0,0				
			}		
		}
	}



	public static void main(String[] args) {
		new FoodJumunMain();
	}

}
