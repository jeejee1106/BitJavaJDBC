package BitNCS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import oracle.db.DbConnect;

public class StudentTable0827 {
	DbConnect db = new DbConnect();
	Scanner sc = new Scanner(System.in);
	
	public void insertStudent() {
		System.out.println("�л��� �Է�");
		String name = sc.nextLine();
		System.out.println("������ �Է�");
		String blood = sc.nextLine();
		System.out.println("���� �Է�");
		int score = Integer.parseInt(sc.nextLine());
		System.out.println("���� �Է�");
		String birth = sc.nextLine(); 		
		
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		//num,  name, blood, score, birth
		String sql = "insert into student values (seq_ncs.nextval, ?,?,?, to_date(?,'yyyy-mm-dd'))";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, blood);
			pstmt.setInt(3, score);
			pstmt.setString(4, birth);
			
			pstmt.execute();
			
			System.out.println("DB���� �߰��Ǿ����ϴ�");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
	}
	
	public static void main(String[] args) {
		StudentTable0827 student = new StudentTable0827();
		student.insertStudent();
	}

}
