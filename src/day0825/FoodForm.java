package day0825;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FoodForm extends JFrame implements ActionListener{
	FoodDBModel dbmodel = new FoodDBModel();
	FoodDraw draw = new FoodDraw();
	String photoName;
	
	JTextField tfFood, tfPrice, tfIoc;
	JComboBox<String> cbShop;
	JLabel lblPhoto;
	JButton btnPhoto, btnSave;
	
	public FoodForm() {
		super("�� �޴� �߰�");
		this.setBounds(600, 130, 300, 400);
		this.setDesign();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void setDesign() {
		//1. �޴��� �󺧰� �ؽ�Ʈ �ʵ� ����
		this.setLayout(null);
		
		JLabel lbl1 = new JLabel("�޴���");
		lbl1.setBounds(10, 10, 50, 30);
		this.add(lbl1);
		
		tfFood = new JTextField();
		tfFood.setBounds(70, 10, 100, 30);
		this.add(tfFood);
		
		//2. ���� �󺧰� �ؽ�Ʈ �ʵ� ����
		JLabel lbl2 = new JLabel("����");
		lbl2.setBounds(10, 50, 50, 30);
		this.add(lbl2);
		
		tfPrice = new JTextField();
		tfPrice.setBounds(70, 50, 100, 30);
		this.add(tfPrice);
		
		
		//3. �������� �󺧻���, ��ư����, ������ġ����, ������ζ� ����
		JLabel lbl3 = new JLabel("��������");
		lbl3.setBounds(10, 90, 50, 30);
		this.add(lbl3);
		
		btnPhoto = new JButton("photo");
		btnPhoto.setBounds(70, 90, 80, 30);
		btnPhoto.addActionListener(this);
		this.add(btnPhoto);
		
		draw.setBounds(180, 10, 100, 100);
		this.add(draw);
		
		lblPhoto = new JLabel();
		lblPhoto.setBorder(new TitledBorder("�������"));
		lblPhoto.setBounds(10, 130, 260, 50);
		this.add(lblPhoto);
		
		//4. ��ȣ�� �󺧰� �޺��ڽ� ����
		JLabel lbl4 = new JLabel("��ȣ��");
		lbl4.setBounds(10, 190, 50, 30);
		this.add(lbl4);
		
		String[] fname = {"�߱���", "������", "���̳�", "�ʹ���", "��䳪��"};
		cbShop = new JComboBox<String>(fname);
		cbShop.setBounds(70, 190, 100, 30);
		this.add(cbShop);
		
		//5. ��ġ �󺧰� �ؽ�Ʈ�ʵ� ����
		JLabel lbl5 = new JLabel("��ġ");
		lbl5.setBounds(10, 240, 50, 30);
		this.add(lbl5);
		
		tfIoc = new JTextField();
		tfIoc.setBounds(70, 240, 100, 30);
		this.add(tfIoc);
		
		//6. Save(����) ��ư ����
		btnSave = new JButton("DB�� �޴� ����");
		btnSave.setBounds(50, 300, 150, 30);
		btnSave.addActionListener(this);
		this.add(btnSave);
		
	}
	
	class FoodDraw extends Canvas{
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if(photoName!=null) {
				Image image = new ImageIcon(photoName).getImage();
				g.drawImage(image, 0, 0, 100, 100, this); //������ �ʴ� ���� �ִ� ĭ�� ������ �ȿ� �������. ��� ���� ũ�Ⱑ ���ƾ� ������ ��©���� ����
			}
		}
	}
	
	public static void main(String[] args) {
		new FoodForm();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if(ob==btnPhoto) {
			JFileChooser chooser = new JFileChooser("C:\\Users\\PC\\Desktop\\study\\image\\�����̹���"); //�⺻ ���丮 ����
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG & GIF Images", "jpg", "gif", "png", "PNG", "JPG"); //Ȯ���� ����
		    chooser.setFileFilter(filter); //Ȯ���� ����
		    int returnVal = chooser.showOpenDialog(this); //���̾�α� �������� �ϴ°� (parent --> this�� �ٲٱ�)
		    if(returnVal == JFileChooser.APPROVE_OPTION) { //���̾�α׿��� �� ������ �� ��ȯ���� �޾Ƽ� �ؿ� �ڵ尡 �߰� �ϴ°�
//		       System.out.println("You chose to open this file: " +
//		            chooser.getSelectedFile().getAbsolutePath()); //�����ؿ��� getName�ε�, getAbsolutePath�̰ɷ� �ٲ��ֱ�. getName�� ���ϸ� �����̶� ���ϸ��� �ι� ��
		    		//�ٵ� ������ �ܼ� ����̶� �ּ�ó����!!
		    
		    	photoName = chooser.getSelectedFile().getAbsolutePath(); ////������ ���� ����. ����� getAbsolutePath�� ���ִ�
		    	draw.repaint(); //�̹��� �׸���
		    	lblPhoto.setText(photoName); //�󺧿��� ���
		    
		    }
		}else if(ob==btnSave) { //Ǫ��,�����̽�,��,ioc,����
			//db���� �� ��� ������(�̹��� ����) �ʱ�ȭ
			
			FoodDTO dto = new FoodDTO();
			dto.setFood(tfFood.getText());
			dto.setPrice(Integer.parseInt(tfPrice.getText()));
//			JComboBox<String> cbShop;
			dto.setShop(String.valueOf(cbShop.getSelectedIndex()));
			dto.setPhoto(photoName);
			dto.setIoc(tfIoc.getText());
			
			tfFood.setText("");
			tfPrice.setText("");
			cbShop = null;
			lblPhoto.setText("");
			tfIoc.setText("");
			
			
			dbmodel.insertFood(dto);
			JOptionPane.showMessageDialog(this, "�ֹ��� ������ ����Ǿ����ϴ�!"); //this�� ���â�� ��ġ�� �����ϴ� ��. this��� �ָ� ������ ��򰡿�, btnSave�� �ָ� ��ư��ġ��!
			
		}
	}

}
