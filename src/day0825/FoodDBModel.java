package day0825;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.db.DbConnect;

public class FoodDBModel {
	DbConnect db = new DbConnect(); //����Ŭ ����̹��� ������ Ŭ������ ����������!
	
	//food insert
	public void insertFood(FoodDTO dto) {
		Connection conn = db.getLocalOracle(); //����Ŭ ����̹��� Local�� ����!
		PreparedStatement pstmt = null; //PreparedStatement�� SQL���� ��� ������ �ϴ� �������̽��̴�.
		String sql = "insert into food values (seq_food.nextval, ?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getFood()); //setString�� ������ �Ű�����(1�� ����ǥ�� dto���� �о�� food��)�� java StringŸ������ ��ȯ���ش�.
											   //������ SQL���� varchar2 Ÿ���̴ϱ� java�� String����!!!(SQLŸ�԰� ȣȯ�Ǵ� Ÿ���� ���ش�) �� ���� pstmt�� ����?
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
	
	//food�� num ��ü ��ȯ
	public String[] getNumArray() { //�迭��ȯŸ���̸� �޺��ڽ��� �ٷ� ���� �� �ִ�. (���ͷ� ��ȯ�ް� ���߿� ����ȯ�صε�)
		String sql = "select num from food ordwr by num";
		Vector<String> num_list = new Vector<String>();
		
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				num_list.add(rs.getString(1)); //"num"���� �ص� �ǰ� "1" ����ȣ�� �ص� �ǰ�
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		//toArray()�� Object[]�� ��ȯ�ϹǷ�!!! �츮�� String[] �� ����ȯ �ؼ� ��ȯ
		String[] data = (String[])num_list.toArray();
		return data;
		
	}
	
	//num�� �ش��ϴ� Food��ȯ
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
