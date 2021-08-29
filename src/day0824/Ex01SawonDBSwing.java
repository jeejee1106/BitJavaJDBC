package day0824;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import oracle.db.DbConnect;

public class Ex01SawonDBSwing extends JFrame implements ItemListener, ActionListener{
	
	DbConnect db = new DbConnect();
	
	DefaultTableModel model;
	JTable table;
	JRadioButton[] rb = new JRadioButton[6]; //라디오 버튼은 거의 배열로? 버튼이 여러개니까?! 그래야 위치 잡을 때나..데이터 받을 때 편한듯???
	JButton btnDel, btnadd;
	
	public Ex01SawonDBSwing() {
		super("사원관리");
		this.setBounds(700,200,800,400);
		this.setDesign();
		
		//테이블 생성 후 db로부터 데이터 가져오기
		this.sawonTableWrite(1); //메서드에서 파라미터로 받는거임
		
		this.getContentPane().setBackground(Color.white);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	//db의 데이터를 가져와서 테이블에 추가
	//전체:1, 남자:2, 여자:3, 교육부:4, 홍보부:5, 관리부:6
	public void sawonTableWrite(int select) {
		String sql = "";
		if(select ==1) {
			sql = "select rownum no, num id, name, buseo, gender, to_char(pay, 'L999,999,999') pay from sawon";
		} else if(select ==2) {
			sql = "select rownum no, num id, name, buseo, gender, to_char(pay, 'L999,999,999') pay from sawon where gender='남자'";
		} else if(select ==3) {
			sql = "select rownum no, num id, name, buseo, gender, to_char(pay, 'L999,999,999') pay from sawon where gender='여자'";			
		} else if(select ==4) {
			sql = "select rownum no, num id, name, buseo, gender, to_char(pay, 'L999,999,999') pay from sawon where buseo='교육부'";	
		} else if(select ==5) {
			sql = "select rownum no, num id, name, buseo, gender, to_char(pay, 'L999,999,999') pay from sawon where buseo='홍보부'";	
		} else if(select ==6) {
			sql = "select rownum no, num id, name, buseo, gender, to_char(pay, 'L999,999,999') pay from sawon where buseo='관리부'";	
		}
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			//기존의 데이터 삭제 후 가져오기
			model.setRowCount(0); //행을 0개로 만들겠다. 5넣으면 5개로 만들겠다!(5개만 남기겠다)
			
			
			while(rs.next()) {
				//테이블에 추가할 벡터 선언. DB의 넘버라도 계산할 거 아니면 스트링으로 읽을거임.
				Vector<String> data = new Vector<String>();
				data.add(rs.getString("no"));
				data.add(rs.getString("id"));
				data.add(rs.getString("name"));
				data.add(rs.getString("buseo"));
				data.add(rs.getString("gender"));
				data.add(rs.getString("pay"));
				
				//벡터의 데이터를 행으로 추가
				model.addRow(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		
	}
	
	private void setDesign() {
		this.setLayout(null);
		String[] title = {"번호", "ID", "사원명", "부서명", "성별", "월급여"};
		model = new DefaultTableModel(title, 0); //제목만 넣고 행갯수는 일단 0개로 추가함!
		table = new JTable(model);
		JScrollPane js = new JScrollPane(table);
		js.setBounds(10, 80, 700, 300);
		this.add(js);
		
		//라디오 버튼 추가
		ButtonGroup bg = new ButtonGroup();
		String[] rb_label = {"전체", "남자", "여자", "교육부", "홍보부", "관리부"};
		int xpos = 10;
		for(int i = 0; i<rb.length; i++) {
			rb[i] = new JRadioButton(rb_label[i], i==0?true:false);
			rb[i].setBounds(xpos, 50, 90, 30);
			rb[i].setOpaque(false); //버튼배경? 없애는 코드
			//이벤트 추가
			rb[i].addItemListener(this);
			bg.add(rb[i]);
			this.add(rb[i]);
			xpos+=100;
		}
		
		//사원 삭제버튼 추가
		btnDel = new JButton("사원 삭제");
		btnDel.setBounds(10, 10, 100, 30);
		btnDel.addActionListener(this);
		this.add(btnDel);
		
		//사원 추가버튼 추가
		btnadd = new JButton("사원 추가");
		btnadd.setBounds(130, 10, 100, 30);
		btnadd.addActionListener(this);
		this.add(btnadd);
		
	}

	public static void main(String[] args) {
		Ex01SawonDBSwing ex = new Ex01SawonDBSwing();
	}

	@Override
	public void itemStateChanged(ItemEvent e) { //라디오버튼 이벤트 추가하는 메서드!
		Object ob = e.getSource();
		for(int i = 0; i<rb.length; i++) {
			if(rb[i]==ob) {
				this.sawonTableWrite(i+1);
			}
		}	
		
//		if(ob==rb[0]) {
//			this.sawonTableWrite(1);
//		} else if(ob==rb[1]) {
//			this.sawonTableWrite(2);
//		} else if(ob==rb[2]) {
//			this.sawonTableWrite(3);
//		} else if(ob==rb[3]) {
//			this.sawonTableWrite(4);
//		} else if(ob==rb[4]) {
//			this.sawonTableWrite(5);
//		}
	}

	@Override
	public void actionPerformed(ActionEvent e) { //버튼 이벤트 추가하는 메서드!
		Object ob = e.getSource();
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		String sql = "";
		
		if(ob==btnDel) {
			//행번호 얻기. 선택 안하고 삭제 버튼 누를 경우 -1 반환.
			int row = table.getSelectedRow();
//			System.out.println(row); //숫자 잘 나오는지(잘 실행되는지) 확인하기 위해 콘솔 출력
			//선택 안했을 경우
			if(row==-1) {
				JOptionPane.showMessageDialog(this, "삭제할 행을 선택해주세요");
				return;
			}
			
			//선택한 행의 in(num) 값 얻기. 배열에서 id(num) 컬럼은 인덱스1에 있음! 그래서 1을 써줌. 2를 쓰면 인덱스2 에 있는 이름이 출력됨!
			String num = (String)model.getValueAt(row, 1); //num에다가 선택한 행(row)의 인덱스1의 데이터 값을 넣어줄거야
//			System.out.println(num); //행 선택 후 삭제 누르면 콘솔에 해당 행의 id 데이터가 출력됨
			
			
			// num에해당하는 db데이터 삭제 후 테이블 다시 출력(전체)
			sql = "delete from sawon where num=?"; //행을 삭제하는 찐 코드
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, num); //1번 물음표에 num값 넣겠다!
				//실행
				pstmt.execute();
				//삭제 후 바로 전체 데이터 다시 불러오기
				this.sawonTableWrite(1);
				//라디오 버튼도 0번 전체 선택으로 넘어가게
				rb[0].setSelected(true);
				
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				db.dbClose(pstmt, conn);
			}
			
		} else if(ob==btnadd) {
			//버튼을 누르면 이름, 성별, 부서, 급여를 입력 받아야함!
			String name = JOptionPane.showInputDialog("사원명을 입력해주세요");
			String gender = JOptionPane.showInputDialog("성별을 입력해주세요");
			String buseo = JOptionPane.showInputDialog("부서(교육부, 홍보부, 관리부)를 입력해주세요");
			String pay = JOptionPane.showInputDialog("급여를 입력해주세요");
			sql = "insert into sawon values (seq_sawon.nextval, ?, ?, ?, ?)";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, gender);
				pstmt.setString(3, buseo);
				pstmt.setString(4, pay);
				pstmt.execute();
				
				this.sawonTableWrite(1);
				rb[0].setSelected(true);
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				db.dbClose(pstmt, conn);
			}
			
			
		}
		
	}

}
