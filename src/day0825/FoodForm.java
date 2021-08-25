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
		super("새 메뉴 추가");
		this.setBounds(600, 130, 300, 400);
		this.setDesign();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void setDesign() {
		//1. 메뉴명 라벨과 텍스트 필드 생성
		this.setLayout(null);
		
		JLabel lbl1 = new JLabel("메뉴명");
		lbl1.setBounds(10, 10, 50, 30);
		this.add(lbl1);
		
		tfFood = new JTextField();
		tfFood.setBounds(70, 10, 100, 30);
		this.add(tfFood);
		
		//2. 가격 라벨과 텍스트 필드 생성
		JLabel lbl2 = new JLabel("가격");
		lbl2.setBounds(10, 50, 50, 30);
		this.add(lbl2);
		
		tfPrice = new JTextField();
		tfPrice.setBounds(70, 50, 100, 30);
		this.add(tfPrice);
		
		
		//3. 사진선택 라벨생성, 버튼생성, 사진위치지정, 사진경로라벨 생성
		JLabel lbl3 = new JLabel("사진선택");
		lbl3.setBounds(10, 90, 50, 30);
		this.add(lbl3);
		
		btnPhoto = new JButton("photo");
		btnPhoto.setBounds(70, 90, 80, 30);
		btnPhoto.addActionListener(this);
		this.add(btnPhoto);
		
		draw.setBounds(180, 10, 100, 100);
		this.add(draw);
		
		lblPhoto = new JLabel();
		lblPhoto.setBorder(new TitledBorder("사진경로"));
		lblPhoto.setBounds(10, 130, 260, 50);
		this.add(lblPhoto);
		
		//4. 상호명 라벨과 콤보박스 생성
		JLabel lbl4 = new JLabel("상호명");
		lbl4.setBounds(10, 190, 50, 30);
		this.add(lbl4);
		
		String[] fname = {"중국집", "피자헛", "도미노", "초밥집", "김밥나라"};
		cbShop = new JComboBox<String>(fname);
		cbShop.setBounds(70, 190, 100, 30);
		this.add(cbShop);
		
		//5. 위치 라벨과 텍스트필드 생성
		JLabel lbl5 = new JLabel("위치");
		lbl5.setBounds(10, 240, 50, 30);
		this.add(lbl5);
		
		tfIoc = new JTextField();
		tfIoc.setBounds(70, 240, 100, 30);
		this.add(tfIoc);
		
		//6. Save(저장) 버튼 생성
		btnSave = new JButton("DB에 메뉴 저장");
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
				g.drawImage(image, 0, 0, 100, 100, this); //보이지 않는 사진 넣는 칸이 프레임 안에 만들어짐. 얘랑 사진 크기가 같아야 사진이 안짤리고 보임
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
			JFileChooser chooser = new JFileChooser("C:\\Users\\PC\\Desktop\\study\\image\\음식이미지"); //기본 디렉토리 설정
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG & GIF Images", "jpg", "gif", "png", "PNG", "JPG"); //확장자 지정
		    chooser.setFileFilter(filter); //확장자 받음
		    int returnVal = chooser.showOpenDialog(this); //다이얼로그 보여지게 하는거 (parent --> this로 바꾸기)
		    if(returnVal == JFileChooser.APPROVE_OPTION) { //다이얼로그에서 멀 누르면 그 반환값을 받아서 밑에 코드가 뜨게 하는거
//		       System.out.println("You chose to open this file: " +
//		            chooser.getSelectedFile().getAbsolutePath()); //복붙해오면 getName인데, getAbsolutePath이걸로 바꿔주기. getName은 파일명 포함이라 파일명이 두번 뜸
		    		//근데 어차피 콘솔 출력이라 주석처리함!!
		    
		    	photoName = chooser.getSelectedFile().getAbsolutePath(); ////선택한 사진 저장. 여기두 getAbsolutePath로 써주당
		    	draw.repaint(); //이미지 그리기
		    	lblPhoto.setText(photoName); //라벨에도 출력
		    
		    }
		}else if(ob==btnSave) { //푸드,프라이스,샵,ioc,포토
			//db저장 후 모든 데이터(이미지 포함) 초기화
			
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
			JOptionPane.showMessageDialog(this, "주문한 음식이 저장되었습니다!"); //this는 모달창의 위치를 지정하는 것. this라고 주면 프레임 어딘가에, btnSave로 주면 버튼위치에!
			
		}
	}

}
