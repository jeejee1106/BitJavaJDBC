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

public class ShopUpdateForm extends JFrame implements ActionListener{
	
	JTextField tfSang, tfSu, tfDan;
	JLabel lblPhoto;
	String imageName;
	JButton btnImage, btnUpdate;
	ShopDBModel dbmodel = new ShopDBModel();
	PhotoDraw photodraw = new PhotoDraw();
	String num; //메인으로부터 받아올 넘버값!!!
	ShopDTO dto; //db로부터 받아온 num에 해당하는 데이터를 넣을 변수
	
	public ShopUpdateForm() {
		super("상품수정");
		this.setBounds(700, 100, 350, 300);
		this.setDesign();
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		this.setVisible(true); //수정은 데이터를 넣은 후에 보여야하기 때문에 주석처리..?
		
	}
	
	
	private void setDesign() {
		this.setLayout(null);
		
		JLabel lbl1 = new JLabel("상품명");
		lbl1.setBounds(10, 10, 50, 30);
		this.add(lbl1);
		tfSang = new JTextField();
		tfSang.setBounds(80, 10, 100, 30);
		this.add(tfSang);
		
		JLabel lbl2 = new JLabel("사진선택");
		lbl2.setBounds(10, 60, 50, 30);
		this.add(lbl2); //라벨추가했음
		btnImage = new JButton("Image");
		btnImage.setBounds(80, 60, 100, 30);
		btnImage.addActionListener(this);
		this.add(btnImage); //버튼추가 했음
		photodraw.setBounds(200, 10, 90, 80); //x, y, width, high
		this.add(photodraw); //이미지 위치 추가 했음
		lblPhoto = new JLabel("이미지명");
		lblPhoto.setBorder(new LineBorder(Color.pink));
		lblPhoto.setBounds(10, 100, 300, 30);
		this.add(lblPhoto); //라벨추가
		
		JLabel lbl3 = new JLabel("수량");
		lbl3.setBounds(10, 150, 50, 30);
		this.add(lbl3);
		tfSu = new JTextField();
		tfSu.setBounds(80, 150, 50, 30);
		this.add(tfSu);

		JLabel lbl4 = new JLabel("단가");
		lbl4.setBounds(10, 200, 50, 30);
		this.add(lbl4);
		tfDan = new JTextField();
		tfDan.setBounds(80, 200, 50, 30);
		this.add(tfDan);
		
		btnUpdate = new JButton("DB 수정");
		btnUpdate.setBounds(170, 200, 100, 30);
		btnUpdate.addActionListener(this);
		this.add(btnUpdate);
		
		
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
			FileDialog dlg = new FileDialog(this, "이미지 가져오기", FileDialog.LOAD);
			dlg.setVisible(true);
			//취소 누르면 메서드 종료
			if(dlg.getDirectory()==null) {
				return;
			}
			imageName = dlg.getDirectory() + dlg.getFile(); //이미지명 얻기(파일 주소임!)
			lblPhoto.setText(imageName); //이미지명 라벨에 출력하기
			photodraw.repaint(); //이미지 출력
			
			
		} else if(ob==btnUpdate) {
			//ShopDto 생성
			ShopDTO dto = new ShopDTO();
			
			//dto에 5개의 데이터를 넣는다(num, 상품명, 사진이름, 수량, 단가)
			dto.setNum(num); //수정시 반드시 num값을 넣어줘야한다. 행번호인 num값을 받아서 수정할거니까!!!
			dto.setSangpum(tfSang.getText());
			dto.setPhoto(imageName);
			dto.setSu(Integer.parseInt(tfSu.getText()));
			dto.setDan(Integer.parseInt(tfDan.getText()));
			
			//DB모델의 update 메소드 추가
			dbmodel.updateShop(dto);
			
			//현재창 닫기
			this.setVisible(false);
		}
	
		
	}
	
	
}
