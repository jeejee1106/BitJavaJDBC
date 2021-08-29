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

//추가만 하는 클래스
public class ShopAddForm extends JFrame implements ActionListener{
	
	JTextField tfSang, tfSu, tfDan;
	JLabel lblPhoto;
	String imageName;
	JButton btnImage, btnInsert;
	ShopDBModel dbmodel = new ShopDBModel();
	PhotoDraw photodraw = new PhotoDraw();
	
	
	public ShopAddForm() {
		super("상품추가");
		this.setBounds(700, 100, 350, 300);
		this.setDesign();
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
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
		
		btnInsert = new JButton("DB에 추가");
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
			FileDialog dlg = new FileDialog(this, "이미지 가져오기", FileDialog.LOAD); //파일을 가져오는 그 창 뜨는 코드
			dlg.setVisible(true); //그 창이 보이게!!!
			//취소 누르면 메서드 종료
			if(dlg.getDirectory()==null) {
				return;
			}
			imageName = dlg.getDirectory() + dlg.getFile(); //이미지명 얻기(파일 주소임!) 파일경로 + 파일명
			lblPhoto.setText(imageName); //이미지명 라벨에 출력하기
			photodraw.repaint(); //이미지 출력
			
			
		} else if(ob==btnInsert) {
			//ShopDto 생성
			ShopDTO dto = new ShopDTO();
			
			//dto에 4개의 데이터를 넣는다(상품명, 사진이름, 수량, 단가)
			dto.setSangpum(tfSang.getText());
			dto.setPhoto(imageName);
			dto.setSu(Integer.parseInt(tfSu.getText()));
			dto.setDan(Integer.parseInt(tfDan.getText()));
			
			//DB모델의 insert 메소드 추가
			dbmodel.insertShop(dto); //위 버튼을 눌러 발생한 이벤트에 넣은 값들을 insert메서드를 가져와서 거기에 넣어준다. 
			
			//현재창 닫기
			this.setVisible(false);
		}
	
		
	}
	
	
}
