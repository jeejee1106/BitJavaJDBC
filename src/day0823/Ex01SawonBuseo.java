package day0823;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.db.DbConnect;

public class Ex01SawonBuseo {
	DbConnect db = new DbConnect();
	
	public void writeSawonBuseo() {
//		Connection conn = db.getLocalOracle();
		Connection conn = db.getCloudOracle();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select buseo, count(buseo) count, round(avg(pay),0) avgpay, "
				+ "max(pay) maxpay, min(pay) minpay from sawon group by buseo"; //�ʹ� �� �� �ٿ� ��
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.println("�μ���\t�ο���\t��ձ޿�\t�ְ�޿�\t�����޿�");
			System.out.println("===============================================");
			
			while(rs.next()) {
				String buseo = rs.getString("buseo");
				int count = rs.getInt("count");
				int avgpay = rs.getInt("avgpay");
				int maxpay = rs.getInt("maxpay");
				int minpay = rs.getInt("minpay");
				
				System.out.println(buseo + "\t" + count + "\t" + avgpay + "\t" + maxpay + "\t" + minpay);
				
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, stmt, conn);
		}
		
		
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		Ex01SawonBuseo ex = new Ex01SawonBuseo();
		ex.writeSawonBuseo();
		
		
	}

}
