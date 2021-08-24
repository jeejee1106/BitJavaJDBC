package day0823;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import oracle.db.DbConnect;

public class Ex03SungjukDB {
	DbConnect db = new DbConnect();
	Scanner sc = new Scanner(System.in);
	
	public void insertSungjuk() {
		System.out.println("�л���");
		String name = sc.nextLine();
		System.out.println("�ڹ� ����");
		int java = 0;
		try {
		java = Integer.parseInt(sc.nextLine());
		}catch(NumberFormatException e) {
			System.out.println("�ڹ������� ���ڷθ� �Է����ּ���");
			return;
		}
		
		System.out.println("����Ŭ ����");
		int oracle = 0;
		try {
		oracle = Integer.parseInt(sc.nextLine());
		} catch(NumberFormatException e) {
			System.out.println("����Ŭ ������ ���ڷθ� �Է����ּ���");
			return;
		}
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		String sql = "insert into sungjuk (num, name, java, oracle) values (seq1_test.nextval, ?,?,?)"; 
		
		try {
			pstmt = conn.prepareStatement(sql); //sql�� ���� ��. createStatement�� ������ �ٸ�
			
			//����ǥ�� ���ε��̶�� �ϴµ�, ù��°���� 1��, 2����. 1�� ����ǥ�� name�� ~
			pstmt.setString(1, name); //ù��° ����ǥ!
			pstmt.setInt(2, java); //�ι�° ����ǥ!
			pstmt.setInt(3, oracle); //����° ����ǥ!
			
			//����ǥ ������ŭ ��� ���ε� �� ����
			pstmt.execute(); //���� : ��ȣ �ȿ� sql���� //insert,delete,update�� ��쿡�� excute �Ǵ� excuteUpdate �ƹ��ų� �ᵵ ��) execute�� ��ȯ�� ���� �� ���°���. //**select�� �� excuteQuery**
			System.out.println("�߰��Ǿ����ϴ�.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
		
	}
	
	public void deleteSungjuk() {
//		System.out.println("������ �л� ��ȣ�� �Է����ּ���");
//		int seq1_test = Integer.parseInt(sc.nextLine());
//		
//		String sql = "delete from sungjuk where num ="+seq1_test;
//		
//		Connection conn = db.getLocalOracle();
//		Statement stmt = null;
//		
//		try {
//			stmt = conn.createStatement();
//			
//			int n = stmt.executeUpdate(sql);
//			if(n==0) {
//				System.out.println(seq1_test +"���� ��ܿ� �����ϴ�.");
//			} else {
//				System.out.println(seq1_test + "���� �����߽��ϴ�.");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			db.dbClose(stmt,conn);
//		}
		
		//����� �ڵ�. PreparedStatement����� �����ϱ�. ���ε��� ��Ȯ�� ����??
		System.out.println("������ �л� ��ȣ�� �Է����ּ���");
		int seq1_test = Integer.parseInt(sc.nextLine());
		
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		String sql = "delete from sungjuk where num =?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			//���ε� (�����̳� ������ ���ε��� ���Ƽ� prepareStatement�� ���ִ°� ����.)
			pstmt.setInt(1, seq1_test); //1�� ����??? ù��° ����ǥ!!!!!! ù��° ����ǥ�� seq1_test �� ���� �ְڴ�.
			//����
			int n = pstmt.executeUpdate(); //�������� execute�� ���� �˾ƺ��� �����ϱ� 
			if(n==0) {
				System.out.println(seq1_test +"���� ��ܿ� �����ϴ�.");
			} else {
				System.out.println(seq1_test + "���� �����߽��ϴ�.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.dbClose(pstmt,conn);
		}
		
	}
	
	public void updateSungjuk() {
		System.out.println("������ �л��� ��ȣ�� �Է����ּ���");
		int num = Integer.parseInt(sc.nextLine());
		System.out.println("������ java������ �Է����ּ���");
		int java = Integer.parseInt(sc.nextLine());
		System.out.println("������ oracle������ �Է����ּ���");
		int oracle = Integer.parseInt(sc.nextLine());
		
		Connection conn = db.getLocalOracle();
		
		PreparedStatement pstmt = null;
		String sql = "update sungjuk set java=?, oracle=? where num=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, java);
			pstmt.setInt(2, oracle);
			pstmt.setInt(3, num);
			
			int n = pstmt.executeUpdate();
			if(n==0) { //pstmt.executeUpdate();�� ������ ������ ����(����� ���� ����)�� ��ȯ�Ѵ�. �츮�� ������ ������ ��������ȣ�� �ϱ� ������ �����Ǵ� �����ʹ� 1���ϼ��ۿ� ����(�������� ������ �ϳ��ۿ� ���� ����). 
					   //���� ������ ������ �̸����� �Ϸ��� �Ѵٸ�, ���������� ���� �� �ְ� �׷� �� ����ŭ�� ���� ��ȯ�Ѵ�.
				System.out.println(num + "�� �л��� �������� �ʽ��ϴ�.");
			} else {
				System.out.println(num + "�� �л��� ������ �����Ͽ����ϴ�.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
		
	}
	
	//���� ��� �ٽ� ���
	public void calcTotalAverage() {
		Connection conn = db.getLocalOracle();
		Statement stmt = null;
		String sql = "update sungjuk set total = java+oracle, average = (java+oracle)/2";
		
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(stmt, conn);
		}
		
	}
	
	public void writeAll() {
		this.calcTotalAverage();
		
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from sungjuk order by num";
		System.out.println("\t\t**�л� ���� ���**");
		System.out.println();
		System.out.println("��ȣ\t�̸�\t�ڹ�\t����Ŭ\t����\t���");
		System.out.println("===========================================");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt("num") + "\t" + rs.getString("name") + "\t" + rs.getInt("java") + "\t" + rs.getInt("oracle") + "\t" + rs.getInt("total") + "\t" + rs.getDouble("average"));
			} //"num" ���� ��� �÷����� ��Ʈ������ �ִ� �����(�׷��� ū����ǥ ��) �÷���ȣ�� ���ڷ� �ִ� ����� �ִ�. 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		
		
	}
	
	public void process() {
		while(true) {
			System.out.println("1.�л����� �߰�\n2.�л����� ����\n3.�л����� ����\n4.�л���ü���\n9.����");
			
			int num = Integer.parseInt(sc.nextLine());
			switch(num) {
			case 1:
				this.insertSungjuk();
				break;
			case 2:
				this.deleteSungjuk();
				break;
			case 3:
				this.updateSungjuk();
				break;
			case 4:
				this.writeAll();
			default:
				System.out.println("**�������� ���α׷��� �����մϴ�.**");
				System.exit(0);
			}
		}
	}
		
	public static void main(String[] args) {
		
	Ex03SungjukDB ex = new Ex03SungjukDB();
	ex.process();	
		
		
	}
}
