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
	public Object[] getNumArray() { //�迭��ȯŸ���̸� �޺��ڽ��� �ٷ� ���� �� �ִ�.
		String sql = "select num from food order by num";
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
		
		//toArray()�� Object[]�� ��ȯ
		return num_list.toArray();
		
	}
	
	//num(������ ��ȣ��)�� �ش��ϴ� ������ ��ü ��ȯ(����ϴ� �޼���)!!!!!!!!!!!!
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
	
	//�ֹ� �߰�
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
	
	//�ֹ�����Ʈ ��ȯ
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
				
				// list�� �߰�
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
