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
	public String[] getNumArray() { //배열반환타입이면 콤보박스에 바로 넣을 수 있다. (벡터로 반환받고 나중에 형번환해두됨)
		String sql = "select num from food ordwr by num";
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
		//toArray()는 Object[]로 반환하므로!!! 우리는 String[] 로 형변환 해서 반환
		String[] data = (String[])num_list.toArray();
		return data;
		
	}
	
	//num에 해당하는 Food반환
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
				dto.setIoc(rs.getString("ioc"));
				dto.setPhoto(rs.getString("photo"));
				dto.setShop(rs.getString("shop"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		
		return dto;
	}
	
	
	
	
	
	
}
