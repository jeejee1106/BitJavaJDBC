package day0823;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import oracle.db.DbConnect;

public class Ex02SawonAdd {
	DbConnect db = new DbConnect();
	Scanner sc = new Scanner(System.in);
	
	public void insertSawon() {
		System.out.println("**��� �߰��ϱ�**");
		System.out.println("�����");
		String name = sc.nextLine();
		System.out.println("����(����/����)");
		String buseo = sc.nextLine();
		System.out.println("�μ���");
		String gender = sc.nextLine();
		System.out.println("���޿�");
		int pay = Integer.parseInt(sc.nextLine());
		
		String sql = "insert into sawon values (seq_sawon.nextval, '"+name+"', '"+gender+"', '"+buseo+"', "+pay+")";
		
//		System.out.println(sql); //�ڵ尡 Ʋ���� �ʰ� �� ������ �ֿܼ� Ȯ���ϱ� ���� Ȯ�ο�
		
		Connection conn = db.getLocalOracle();
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement(); //createStatement�� 
			stmt.execute(sql); //sql�� �����ε� insert,delete,update�� ��쿡�� excute �Ǵ� excuteUpdate �ƹ��ų� �ᵵ ��) execute ��ȯ�� ���� �� ���°���.
			//**select�� �� excuteQuery**
//			int a = stmt.executeUpdate(sql);//������ �������� ����
//			System.out.println(a);
			
			System.out.println("DB���� �߰��Ǿ����ϴ�");
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(stmt, conn);
		}
	}
	
	public void deleteSawon() {
		System.out.println("������ ������� �Է����ּ���.");
		String name = sc.nextLine();
		String sql = "delete from sawon where name='" +name+ "'";
//		System.out.println(sql);
		
		Connection conn = db.getLocalOracle();
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			
			//���� ������ ���ڵ��� ������ ���. ��ȯ�� �������� n���� ��.
			//�̸��� ���� ��� 0�� ��ȯ�ȴ�. �̸��� 0���ϱ�! 
			int n = stmt.executeUpdate(sql);
			if(n==0) { //�� �ڵ忡�� �̸��� ���� �� 0�� ��ȯ�ǰ�, n�� 0�� �� if�ڵ�, 0�� �ƴ� ��  else�ڵ�!
				System.out.println(name + "���� ��� ��ܿ� �����ϴ�.");
			} else {
				System.out.println(name + "���� �����߽��ϴ�.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(stmt,conn);
		}
	}
	
	public void searchName() {
		System.out.println("�˻��� ������� �Է��ϼ���");
		String name = sc.nextLine();
		String sql = "select * from sawon where name like '%" + name+ "%'";
		System.out.println(sql);
		System.out.println("\t\t**�˻� ��� ���**");
		System.out.println();
		System.out.println("������\t�����\t����\t�μ���\t���޿�");
		System.out.println("==========================================");
		
		//db����
		Connection conn = db.getLocalOracle();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				System.out.println(rs.getInt("num") + "\t" + rs.getString("name") + "\t" + rs.getString("gender") 
				+ "\t" + rs.getString("buseo") + "\t" + rs.getString("pay"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, stmt, conn);
		}
	}
	
	public void writeSawonAll() {
		String sql = "select num,name,gender, buseo, to_char(pay, 'L999,999,999') pay from sawon";
		System.out.println("\t\t**��ü��� ���**");
		System.out.println();
		System.out.println("������\t�����\t����\t�μ���\t���޿�");
		System.out.println("==========================================");
		
		//db����
		Connection conn = db.getLocalOracle();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement(); //
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				System.out.println(rs.getInt("num") + "\t" + rs.getString("name") + "\t" + rs.getString("gender") 
				+ "\t" + rs.getString("buseo") + "\t" + rs.getString("pay"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, stmt, conn);
		}
	}
	
	public void process() {
		while(true) {
			System.out.println("1.����߰�\n2.��ü������\n3.�������\n4.����˻�\n9.�ý��� ����");
			int num = Integer.parseInt(sc.nextLine());
			switch(num) {
			case 1:
				this.insertSawon();
				break;
			case 2:
				this.writeSawonAll();
				break;
			case 3:
				this.deleteSawon();
				break;
			case 4:
				this.searchName();
				break;
			default:
				System.out.println("**�����մϴ�.**");
				System.exit(0);
					
			}
			System.out.println();
			
		}
	}
	
	public static void main(String[] args) {
		Ex02SawonAdd ex = new Ex02SawonAdd();
		ex.process();
		
	}

}
