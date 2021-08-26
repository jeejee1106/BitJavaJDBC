package day0825;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.db.DbConnect;

public class FoodDBModel {
	DbConnect db = new DbConnect(); //오라클 드라이버를 연결한 클래스를 생성해주자!
	
	//food insert
	public void insertFood(FoodDTO dto) {
		Connection conn = db.getLocalOracle(); //오라클 드라이버의 Local로 연결!
		PreparedStatement pstmt = null; //PreparedStatement는 SQL문을 담는 역할을 하는 인터페이스이다.
		String sql = "insert into food values (seq_food.nextval, ?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getFood()); //setString은 지정된 매개변수(1번 물음표의 dto에서 읽어온 food값)를 java String타입으로 변환해준다.
											   //원래는 SQL문의 varchar2 타입이니까 java가 String으로!!!(SQL타입과 호환되는 타입을 써준당) 그 다음 pstmt에 저장?
			pstmt.setInt(2, dto.getPrice());
			pstmt.setString(3, dto.getShop());
			pstmt.setString(4, dto.getIoc());
			pstmt.setString(5, dto.getPhoto());
			
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
	}
	
	//food의 num 전체 반환
	public Object[] getNumArray() { //배열반환타입이면 콤보박스에 바로 넣을 수 있다.
		String sql = "select num from food order by num";
		Vector<String> num_list = new Vector<String>();
		
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				num_list.add(rs.getString(1)); //"num"으로 해도 되고 "1" 열번호로 해도 되고
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		
		//toArray()는 Object[]로 반환
		return num_list.toArray();
		
	}
	
	//num(시퀀스 번호에)에 해당하는 데이터 전체 반환(출력하는 메서드)!!!!!!!!!!!!
	public FoodDTO getFoodData(int num) {
		FoodDTO dto = new FoodDTO();
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from food where num=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setNum(rs.getInt("num"));
				dto.setFood(rs.getString("food"));
				dto.setPrice(rs.getInt("price"));
				dto.setShop(rs.getString("shop"));
				dto.setIoc(rs.getString("ioc"));
				dto.setPhoto(rs.getString("photo"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		
		return dto;
	}
	
	//주문 추가
	public void insertJumun(JumunDTO dto) {
		String sql = "insert into jumun values (seq_food.nextval, ?,?,?)";
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getNum());
			pstmt.setString(3, dto.getAddr());
			
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
	}
	
	//주문리스트 반환
	public Vector<JoinDTO> getJumunList(){
		String sql ="select num1, name, addr, f.num, food, price, shop, ioc, photo from food f, jumun j where f.num = j.num";
		Vector<JoinDTO> list = new Vector<JoinDTO>();
		
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				JoinDTO dto = new JoinDTO();
				dto.setNum1(rs.getString("num1"));
				dto.setName(rs.getString("name"));
				dto.setAddr(rs.getString("addr"));
				dto.setNum(rs.getInt("num"));
				dto.setFood(rs.getString("food"));
				dto.setPrice(rs.getInt("price"));
				dto.setShop(rs.getString("shop"));
				dto.setIoc(rs.getString("ioc"));
				dto.setPhoto(rs.getString("photo"));
				
				// list에 추가
				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		
		
		return list;
	}
	
	
	
}
