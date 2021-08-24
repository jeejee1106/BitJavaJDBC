package day0824;
//DB ó���� �ϴ� Ŭ����

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracleDB.DBConnect2;
//db�� �����ϴ� ��� �ڵ��.
public class ShopDBModel {
	DBConnect2 db= new DBConnect2();
	
	public void insertShop(ShopDTO dto) {
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		
		String sql = "insert into shop values (seq_test.nextval, ?,?,?,?,sysdate)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSangpum());
			pstmt.setString(2, dto.getPhoto());
			pstmt.setInt(3, dto.getSu());
			pstmt.setInt(4, dto.getDan());
			//����
			pstmt.execute();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
	}
	
	//db�� ��ü ������ ��ȯ
	public Vector<ShopDTO> getAllSangpum(){
		Vector<ShopDTO> list = new Vector<ShopDTO>();
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from shop order by sangpum";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ShopDTO dto = new ShopDTO(); // db���� �ϳ��� ���ڵ带 �о dto�� �������̴�!
				dto.setNum(rs.getString("num"));
				dto.setSangpum(rs.getString("sangpum"));
				dto.setPhoto(rs.getString("photo"));
				dto.setSu(rs.getInt("su"));
				dto.setDan(rs.getShort("dan"));
				dto.setGuipday(rs.getDate("guipday"));
				
			
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		return list;
	}
	
	
}
