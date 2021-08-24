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
		System.out.println("**사원 추가하기**");
		System.out.println("사원명");
		String name = sc.nextLine();
		System.out.println("성별(남자/여자)");
		String buseo = sc.nextLine();
		System.out.println("부서명");
		String gender = sc.nextLine();
		System.out.println("월급여");
		int pay = Integer.parseInt(sc.nextLine());
		
		String sql = "insert into sawon values (seq_sawon.nextval, '"+name+"', '"+gender+"', '"+buseo+"', "+pay+")";
		
//		System.out.println(sql); //코드가 틀리지 않고 잘 썻는지 콘솔에 확인하기 위한 확인용
		
		Connection conn = db.getLocalOracle();
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement(); //createStatement는 
			stmt.execute(sql); //sql문 실행인데 insert,delete,update일 경우에는 excute 또는 excuteUpdate 아무거나 써도 됌) execute 반환값 없을 때 쓰는거임.
			//**select일 땐 excuteQuery**
//			int a = stmt.executeUpdate(sql);//성공한 데이터의 갯수
//			System.out.println(a);
			
			System.out.println("DB문에 추가되었습니다");
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(stmt, conn);
		}
	}
	
	public void deleteSawon() {
		System.out.println("삭제할 사원명을 입력해주세요.");
		String name = sc.nextLine();
		String sql = "delete from sawon where name='" +name+ "'";
//		System.out.println(sql);
		
		Connection conn = db.getLocalOracle();
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			
			//삭제 성공한 레코드의 갯수를 얻기. 반환된 갯수값이 n으로 들어감.
			//이름이 없을 경우 0이 반환된다. 이름이 0개니까! 
			int n = stmt.executeUpdate(sql);
			if(n==0) { //위 코드에서 이름이 없을 때 0이 반환되고, n이 0일 땐 if코드, 0이 아닐 땐  else코드!
				System.out.println(name + "님은 사원 명단에 없습니다.");
			} else {
				System.out.println(name + "님을 삭제했습니다.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(stmt,conn);
		}
	}
	
	public void searchName() {
		System.out.println("검색할 사원명을 입력하세요");
		String name = sc.nextLine();
		String sql = "select * from sawon where name like '%" + name+ "%'";
		System.out.println(sql);
		System.out.println("\t\t**검색 사원 명단**");
		System.out.println();
		System.out.println("시퀀스\t사원명\t성별\t부서명\t월급여");
		System.out.println("==========================================");
		
		//db연결
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
		System.out.println("\t\t**전체사원 명단**");
		System.out.println();
		System.out.println("시퀀스\t사원명\t성별\t부서명\t월급여");
		System.out.println("==========================================");
		
		//db연결
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
			System.out.println("1.사원추가\n2.전체사원출력\n3.사원삭제\n4.사원검색\n9.시스템 종료");
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
				System.out.println("**종료합니다.**");
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
