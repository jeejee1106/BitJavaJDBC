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
	ImageDraw image = new ImageDraw(); //이미지 출력을 위한 내부 클래스 선언
	String imageName;
	Vector<ShopDTO> list;
	
	JButton btnRefresh;

	public ShopListForm() {
		super("전체상품 목록");
		this.setBounds(600, 100, 800, 500);
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setDesign();
		this.setVisible(true);
	}

	private void setDesign() {
		this.setLayout(null);
		list = dbmodel.getAllSangpum(); //db를 통해 불러온 전체상품 정보(getAllSangpum)메서드를 list에 담기

		//table 생성을 위한 기본 코드(?)
		String[] title = {"번호", "상품명", "수량", "단가", "총금액"};
		model = new DefaultTableModel(title, 0); //제목만 넣고(열) 행갯수는 일단 0개로 추가함! 나중에 추가하게
		table = new JTable(model);
		JScrollPane js = new JScrollPane(table);
		js.setBounds(10, 80, 400, 250);
		this.add(js);

		this.dataWrite(); //새로고침을 하려고 메서드로 만들어줌


		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				int selectRow = table.getSelectedRow(); //행 번호 얻기! 행번호는 selectRow변수에 넣는다.
				imageName = list.get(selectRow).getPhoto(); // 얻은 행 번호를 imageName변수에 넣는다.
				image.repaint();
			}
		});
		//새로고침 버튼 추가 및 이벤트
		btnRefresh = new JButton("새로고침");
		btnRefresh.setBounds(150, 350, 100, 30);
		this.add(btnRefresh);
		btnRefresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				list = dbmodel.getAllSangpum(); //db로부터 목록 다시 얻기(즉, 새로고침이라는 소리)
				dataWrite();
			}
		});
		//사진 넣기
		image.setBounds(470, 10, 300, 300);
		this.add(image);

	}


	private void dataWrite() {//(새로고침을 위해 메서드로 따로 만듬)
		//기존 데이터 삭제
		model.setRowCount(0); //새로고침을 하려먼 기존 데이터를 지우고 새로운 데이터가 와야하기 때문!
		
		//테이블에 데이터 추가하기. (새로고침을 위해 메서드로 따로 만듬)
		for(ShopDTO dto:list) { //DB클래스에서 (while문으로 한줄한줄 읽어서) dto.set에 넣은 데이터들을 list에 넣을거야. 
			Vector<String> data = new Vector<String>();
			data.add(dto.getNum());
			data.add(dto.getSangpum());
			data.add(String.valueOf(dto.getSu()));
			data.add(String.valueOf(dto.getDan()));
			data.add(String.valueOf(dto.getSu()*dto.getDan()));
			data.add(String.valueOf(dto.getGuipday()));

			model.addRow(data); //위 추가된 데이터를 model에 한줄로(행) 추가해주겠다.
		}
	}


	class ImageDraw extends Canvas{ //이미지 출력을 위한 내부 클래스
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
