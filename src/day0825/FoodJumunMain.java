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

	//주문 목록 출력에 필요한 컴포넌트들
	DefaultTableModel tableModel;
	JTable table;
	SelectImage sImage = new SelectImage();
	String selectName;
	Vector<JoinDTO> jumunList;


	public FoodJumunMain() {
		super("배달의 민족");
		this.setBounds(100, 60, 1000, 700);
		this.setDesign();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void setDesign() {
		this.setLayout(null);

		//음식등록추가 버튼 만들자!
		btnFood = new JButton("새 메뉴 등록하기"	);
		btnFood.setBounds(70, 20, 200, 30);
		btnFood.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				foodForm.setVisible(true); //메뉴 추가창 보이게 하기
			}
		});
		this.add(btnFood);

		//제목? 라벨 생성
		JLabel lbl0 = new JLabel("** 주문 정보 입력 **", JLabel.CENTER);
		lbl0.setBounds(70, 100, 150, 30);
		lbl0.setBorder(new LineBorder(Color.blue,5));
		this.add(lbl0);

		//주문자 이름 라벨, 텍스트 생성
		JLabel lbl1 = new JLabel("주문자명");
		lbl1.setBounds(70, 150, 100, 30);
		this.add(lbl1);
		tfName = new JTextField();
		tfName.setBounds(130, 150, 100, 30);
		this.add(tfName);

		//주문번호 라벨, 콤보박스 생성
		JLabel lbl2 = new JLabel("주문번호");
		lbl2.setBounds(70, 200, 100, 30);
		this.add(lbl2);

		Object[] fname = dbModel.getNumArray();
		cbjumunNo = new JComboBox<Object>(fname);
		cbjumunNo.setBounds(130, 200, 100, 30);
		this.add(cbjumunNo);

		//사진 위치 설정
		lblFoodName = new JLabel("음식명"); //굳이 제목을 줄 필요는 없당. 위치 보려고 제목 넣어봄
		lblFoodName.setBounds(70, 230, 100, 30);
		this.add(lblFoodName);
		fimage.setBounds(70, 270, 200, 200); //캔버스 프레임 크기랑 위치!
		fimage.setBackground(Color.black);
		this.add(fimage);

		//주소라벨, 텍스트생성
		JLabel lbl3 = new JLabel("주소");
		lbl3.setBounds(70, 500, 100, 30);
		this.add(lbl3);
		tfAddr = new JTextField();
		tfAddr.setBounds(130, 500, 100, 30);
		this.add(tfAddr);


		//주문 버튼 생성
		btnJumun = new JButton("주문하기");
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

				dbModel.insertJumun(dto); //jumunWrite() 출력메서드 보다 먼저 써줘야함. 왜냐면 데이터를 넣은 후에 출력을 해야하니까!
				
				// 주문데이터 출력 메서드
				jumunWrite();

				//값 지우기
				tfName.setText("");
				tfAddr.setText("");
				cbjumunNo.setSelectedIndex(0);

//				JOptionPane.showMessageDialog(FoodJumunMain.this, "주문이 완료되었습니다.");
			}
		});

		this.add(btnJumun);



		//주문번호의 첫번째 번호(num)에 대한 데이터가 초기값으로 들어가도록??
		int firstnum = Integer.parseInt(cbjumunNo.getSelectedItem().toString()); //실행하면 DB의 첫번째 번호(나 같은 경우 시퀀스번호 61)가 자동으로 선택되오있음. 그걸 firstnum에 넣을거임
		//cbjumunNo.getSelectedItem()가 오브젝트 타입이라서 스트링으로 변환 후 다시 인티져로 변환해야한다. (한번에 인티져로 변환하는 코드는 없..는듯?)
		foodDto = dbModel.getFoodData(firstnum); //firstnum에 해당하는 데이터 dto를 얻는다.(시퀀스61, 음식명 까르보나라 등등)
		photoName = foodDto.getPhoto(); //사진명 얻기(까르보나라의 경로를 photoName에 넣음)
		lblFoodName.setText(foodDto.getFood());
		fimage.repaint();

		//주문번호 콤보박스 Change 이벤트 구현
		cbjumunNo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) { //위에 초기값 주는 코드랑 완전 똑같다. 복붙. 왜냐면 어......체인지 이벤트 발생하면서.......
				int changenum = Integer.parseInt(cbjumunNo.getSelectedItem().toString()); //cbjumunNo가 오브젝트타입이라서 스트링으로 변환 후 인티져로 변환해야한다.
				foodDto = dbModel.getFoodData(changenum); //changenum에 해당하는 데이터 dto를 얻는다
				photoName = foodDto.getPhoto(); //사진명 얻기
				lblFoodName.setText(foodDto.getFood());
				fimage.repaint();
			}
		});

		// 테이블 추가
		String[] title = {"주문자", "주소", "메뉴명", "가격", "가게명", "가게위치"};
		tableModel = new DefaultTableModel(title, 0);
		table = new JTable(tableModel);
		JScrollPane js = new JScrollPane(table);
		js.setBounds(350, 250, 600, 300);
		this.add(js);
		jumunWrite();

		//테이블 클릭시 이미지 출력
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				//클릭한 행번호
				int row=table.getSelectedRow();
				//row 에 해당하는 dto
				JoinDTO dto=jumunList.get(row);
				//사진명을 selectName 에 넣구 페인트 메서드 호출
				selectName=dto.getPhoto();
				sImage.repaint();

				super.mouseClicked(e);
			}
		});
	}


	public void jumunWrite() {
		//기존 데이터 삭제 (데이터가 계속 누적 되지 않도록!)
		tableModel.setRowCount(0);

		//db 로부터 전체 주문 목록 가져오기
		jumunList = dbModel.getJumunList();

		for(JoinDTO dto:jumunList) {
			Vector<String> data = new Vector<String>();
			data.add(dto.getName());
			data.add(dto.getAddr());
			data.add(dto.getFood());
			data.add(String.valueOf(dto.getPrice()));
			data.add(dto.getShop());
			data.add(dto.getIoc());

			//테이블에 추가
			tableModel.addRow(data);
		}
	}

	//사진이 나올 캔버스
	class FoodImage extends Canvas{
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if(photoName != null) {
				Image image = new ImageIcon(photoName).getImage();
				g.drawImage(image, 0, 0, 200, 200, this); //위 fimage.setBounds(70, 230, 100, 100); 에서 캔버스에서의 시작 위치 0,0				
			}		
		}
	}



	//테이블 행 선택 시 사진이 나올 캔버스
	class SelectImage extends Canvas{
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if(selectName != null) {
				Image image = new ImageIcon(selectName).getImage();
				g.drawImage(image, 0, 0, 230, 230, this); //위 fimage.setBounds(70, 230, 100, 100); 에서 캔버스에서의 시작 위치 0,0				
			}		
		}
	}



	public static void main(String[] args) {
		new FoodJumunMain();
	}

}
