package day0824;
//DB 처리만 하는 클래스

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.db.DbConnect;
//db와 연동하는 모든 코드들.
public class ShopDBModel {
	DbConnect db= new DbConnect();
	
	public void insertShop(ShopDTO dto) { //DB에 데이터 insert 해주는 메서드!
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		
		String sql = "insert into shop values (seq_test.nextval, ?,?,?,?,sysdate)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSangpum());
			pstmt.setString(2, dto.getPhoto());
			pstmt.setInt(3, dto.getSu());
			pstmt.setInt(4, dto.getDan());
			//실행
			pstmt.execute();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
	}
	
	//db의 전체 데이터 반환
	//데이터는 한 번에 넣어서 보내야 하기 때문에 벡터로 넣었따. 리스트, 어레이 리스트 다 가능하다.
	public Vector<ShopDTO> getAllSangpum(){ //DB의 모든 상품을 sangpum의 오름차순으로 불러와서 Vector list에 출력해주는 메서드
		Vector<ShopDTO> list = new Vector<ShopDTO>();
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from shop order by sangpum";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) { //sql문의 "num", "sangpum" 등을 한줄한줄(rs.) 읽어와서(getString) dto의 setNum(세터) 에 넣을것이다!
				ShopDTO dto = new ShopDTO(); // db에서 하나의 레코드를 읽어서 dto에 넣을것이다!
				dto.setNum(rs.getString("num"));
				dto.setSangpum(rs.getString("sangpum"));
				dto.setPhoto(rs.getString("photo"));
				dto.setSu(rs.getInt("su"));
				dto.setDan(rs.getInt("dan"));
				dto.setGuipday(rs.getDate("guipday"));	
				
				list.add(dto); //list에 dto추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		return list;
	}
	
	
	//num을 받아서 삭제하는 메서드
	public int deleteShop(int num) {
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		String sql = "delete from shop where num=?";
		int n = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num); //바인딩!!!! 1번 물음표에 num을 넣겠다!!
			n = pstmt.executeUpdate(); //실행. 삭제할 행의 num를 반환해준다.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
		return n;
	}
	
	//num에 해당하는 dto를 반환하는 메서드
	public ShopDTO getData(String num) {
		ShopDTO dto = new ShopDTO();
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from shop where num=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num); //바인딩
			rs = pstmt.executeQuery(); //실행
			if(rs.next()) { //행번호 num에 해당하는 것들 다 넣어줄거임!(써줄거임?세터니까)
				dto.setNum(rs.getString("num"));
				dto.setSangpum(rs.getString("sangpum"));
				dto.setPhoto(rs.getString("photo"));
				dto.setSu(rs.getInt("su"));
				dto.setDan(rs.getInt("dan"));
				dto.setGuipday(rs.getDate("guipday"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
		return dto;
	}
	
	//수정하는 메서드
	public void updateShop(ShopDTO dto) {
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		String sql = "update shop set sangpum=?, su=?, dan=?, photo=? where num=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSangpum());
			pstmt.setInt(2, dto.getSu());
			pstmt.setInt(3, dto.getDan());
			pstmt.setString(4, dto.getPhoto());
			pstmt.setString(5, dto.getNum());
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
