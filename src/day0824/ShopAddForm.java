package day0824;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

//�߰��� �ϴ� Ŭ����
public class ShopAddForm extends JFrame implements ActionListener{
	
	JTextField tfSang, tfSu, tfDan;
	JLabel lblPhoto;
	String imageName;
	JButton btnImage, btnInsert;
	ShopDBModel dbmodel = new ShopDBModel();
	PhotoDraw photodraw = new PhotoDraw();
	
	
	public ShopAddForm() {
		super("��ǰ�߰�");
		this.setBounds(700, 100, 350, 300);
		this.setDesign();
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	
	private void setDesign() {
		this.setLayout(null);
		
		JLabel lbl1 = new JLabel("��ǰ��");
		lbl1.setBounds(10, 10, 50, 30);
		this.add(lbl1);
		tfSang = new JTextField();
		tfSang.setBounds(80, 10, 100, 30);
		this.add(tfSang);
		
		JLabel lbl2 = new JLabel("��������");
		lbl2.setBounds(10, 60, 50, 30);
		this.add(lbl2); //���߰�����
		btnImage = new JButton("Image");
		btnImage.setBounds(80, 60, 100, 30);
		btnImage.addActionListener(this);
		this.add(btnImage); //��ư�߰� ����
		photodraw.setBounds(200, 10, 90, 80); //x, y, width, high
		this.add(photodraw); //�̹��� ��ġ �߰� ����
		lblPhoto = new JLabel("�̹�����");
		lblPhoto.setBorder(new LineBorder(Color.pink));
		lblPhoto.setBounds(10, 100, 300, 30);
		this.add(lblPhoto); //���߰�
		
		JLabel lbl3 = new JLabel("����");
		lbl3.setBounds(10, 150, 50, 30);
		this.add(lbl3);
		tfSu = new JTextField();
		tfSu.setBounds(80, 150, 50, 30);
		this.add(tfSu);

		JLabel lbl4 = new JLabel("�ܰ�");
		lbl4.setBounds(10, 200, 50, 30);
		this.add(lbl4);
		tfDan = new JTextField();
		tfDan.setBounds(80, 200, 50, 30);
		this.add(tfDan);
		
		btnInsert = new JButton("DB�� �߰�");
		btnInsert.setBounds(170, 200, 100, 30);
		btnInsert.addActionListener(this);
		this.add(btnInsert);
		
		
	}


//	public static void main(String[] args) {
//		new ShopAddForm();
//	}

	class PhotoDraw extends Canvas{
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if(imageName!=null) {
				Image image = new ImageIcon(imageName).getImage();
				g.drawImage(image, 10, 10, 70, 70, this);
			}		
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if(ob==btnImage) {
			FileDialog dlg = new FileDialog(this, "�̹��� ��������", FileDialog.LOAD); //������ �������� �� â �ߴ� �ڵ�
			dlg.setVisible(true); //�� â�� ���̰�!!!
			//��� ������ �޼��� ����
			if(dlg.getDirectory()==null) {
				return;
			}
			imageName = dlg.getDirectory() + dlg.getFile(); //�̹����� ���(���� �ּ���!) ���ϰ�� + ���ϸ�
			lblPhoto.setText(imageName); //�̹����� �󺧿� ����ϱ�
			photodraw.repaint(); //�̹��� ���
			
			
		} else if(ob==btnInsert) {
			//ShopDto ����
			ShopDTO dto = new ShopDTO();
			
			//dto�� 4���� �����͸� �ִ´�(��ǰ��, �����̸�, ����, �ܰ�)
			dto.setSangpum(tfSang.getText());
			dto.setPhoto(imageName);
			dto.setSu(Integer.parseInt(tfSu.getText()));
			dto.setDan(Integer.parseInt(tfDan.getText()));
			
			//DB���� insert �޼ҵ� �߰�
			dbmodel.insertShop(dto); //�� ��ư�� ���� �߻��� �̺�Ʈ�� ���� ������ insert�޼��带 �����ͼ� �ű⿡ �־��ش�. 
			
			//����â �ݱ�
			this.setVisible(false);
		}
	
		
	}
	
	
}
