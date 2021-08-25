package day0824;
//DB ó���� �ϴ� Ŭ����

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.db.DbConnect;
//db�� �����ϴ� ��� �ڵ��.
public class ShopDBModel {
	DbConnect db= new DbConnect();
	
	public void insertShop(ShopDTO dto) { //DB�� ������ insert ���ִ� �޼���!
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
	//�����ʹ� �� ���� �־ ������ �ϱ� ������ ���ͷ� �־���. ����Ʈ, ��� ����Ʈ �� �����ϴ�.
	public Vector<ShopDTO> getAllSangpum(){ //DB�� ��� ��ǰ�� sangpum�� ������������ �ҷ��ͼ� Vector list�� ������ִ� �޼���
		Vector<ShopDTO> list = new Vector<ShopDTO>();
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from shop order by sangpum";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) { //sql���� "num", "sangpum" ���� ��������(rs.) �о�ͼ�(getString) dto�� setNum(����) �� �������̴�!
				ShopDTO dto = new ShopDTO(); // db���� �ϳ��� ���ڵ带 �о dto�� �������̴�!
				dto.setNum(rs.getString("num"));
				dto.setSangpum(rs.getString("sangpum"));
				dto.setPhoto(rs.getString("photo"));
				dto.setSu(rs.getInt("su"));
				dto.setDan(rs.getInt("dan"));
				dto.setGuipday(rs.getDate("guipday"));	
				
				list.add(dto); //list�� dto�߰�
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		return list;
	}
	
	
	//num�� �޾Ƽ� �����ϴ� �޼���
	public int deleteShop(int num) {
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		String sql = "delete from shop where num=?";
		int n = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num); //���ε�!!!! 1�� ����ǥ�� num�� �ְڴ�!!
			n = pstmt.executeUpdate(); //����. ������ ���� num�� ��ȯ���ش�.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
		return n;
	}
	
	//num�� �ش��ϴ� dto�� ��ȯ�ϴ� �޼���
	public ShopDTO getData(String num) {
		ShopDTO dto = new ShopDTO();
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from shop where num=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num); //���ε�
			rs = pstmt.executeQuery(); //����
			if(rs.next()) { //���ȣ num�� �ش��ϴ� �͵� �� �־��ٰ���!(���ٰ���?���ʹϱ�)
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
	
	//�����ϴ� �޼���
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
