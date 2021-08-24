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
		System.out.println("학생명");
		String name = sc.nextLine();
		System.out.println("자바 점수");
		int java = 0;
		try {
		java = Integer.parseInt(sc.nextLine());
		}catch(NumberFormatException e) {
			System.out.println("자바점수는 숫자로만 입력해주세요");
			return;
		}
		
		System.out.println("오라클 점수");
		int oracle = 0;
		try {
		oracle = Integer.parseInt(sc.nextLine());
		} catch(NumberFormatException e) {
			System.out.println("오라클 점수는 숫자로만 입력해주세요");
			return;
		}
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		String sql = "insert into sungjuk (num, name, java, oracle) values (seq1_test.nextval, ?,?,?)"; 
		
		try {
			pstmt = conn.prepareStatement(sql); //sql이 여기 들어감. createStatement랑 구조가 다름
			
			//물음표는 바인딩이라고 하는데, 첫번째부터 1번, 2번임. 1번 물음표에 name값 ~
			pstmt.setString(1, name); //첫번째 물음표!
			pstmt.setInt(2, java); //두번째 물음표!
			pstmt.setInt(3, oracle); //세번째 물음표!
			
			//물음표 갯수만큼 모두 바인딩 후 실행
			pstmt.execute(); //주의 : 괄호 안에 sql없당 //insert,delete,update일 경우에는 excute 또는 excuteUpdate 아무거나 써도 됌) execute는 반환값 없을 때 쓰는거임. //**select일 땐 excuteQuery**
			System.out.println("추가되었습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
		
	}
	
	public void deleteSungjuk() {
//		System.out.println("삭제할 학생 번호를 입력해주세요");
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
//				System.out.println(seq1_test +"번은 명단에 없습니다.");
//			} else {
//				System.out.println(seq1_test + "번을 삭제했습니다.");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			db.dbClose(stmt,conn);
//		}
		
		//강사님 코드. PreparedStatement사용방법 공부하기. 바인딩이 정확히 뭐지??
		System.out.println("삭제할 학생 번호를 입력해주세요");
		int seq1_test = Integer.parseInt(sc.nextLine());
		
		Connection conn = db.getLocalOracle();
		PreparedStatement pstmt = null;
		String sql = "delete from sungjuk where num =?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			//바인딩 (수정이나 삭제는 바인딩이 많아서 prepareStatement로 써주는게 좋다.)
			pstmt.setInt(1, seq1_test); //1이 뭐지??? 첫번째 물음표!!!!!! 첫번째 물음표에 seq1_test 이 값을 넣겠다.
			//실행
			int n = pstmt.executeUpdate(); //여러가지 execute의 쓸모도 알아보고 공부하기 
			if(n==0) {
				System.out.println(seq1_test +"번은 명단에 없습니다.");
			} else {
				System.out.println(seq1_test + "번을 삭제했습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			db.dbClose(pstmt,conn);
		}
		
	}
	
	public void updateSungjuk() {
		System.out.println("수정할 학생의 번호를 입력해주세요");
		int num = Integer.parseInt(sc.nextLine());
		System.out.println("수정할 java점수를 입력해주세요");
		int java = Integer.parseInt(sc.nextLine());
		System.out.println("수정할 oracle점수를 입력해주세요");
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
			if(n==0) { //pstmt.executeUpdate();은 수정된 데이터 갯수(실행된 행의 갯수)를 반환한다. 우리는 데이터 수정을 시퀀스번호로 하기 때문에 수정되는 데이터는 1개일수밖에 없다(시퀀스는 고유값 하나밖에 없기 때문). 
					   //만약 데이터 수정을 이름으로 하려고 한다면, 동명이인이 있을 수 있고 그럼 그 수만큼의 값을 반환한다.
				System.out.println(num + "번 학생은 존재하지 않습니다.");
			} else {
				System.out.println(num + "번 학생의 정보를 수정하였습니다.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(pstmt, conn);
		}
		
	}
	
	//총점 평균 다시 계산
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
		System.out.println("\t\t**학생 성적 출력**");
		System.out.println();
		System.out.println("번호\t이름\t자바\t오라클\t총점\t평균");
		System.out.println("===========================================");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt("num") + "\t" + rs.getString("name") + "\t" + rs.getInt("java") + "\t" + rs.getInt("oracle") + "\t" + rs.getInt("total") + "\t" + rs.getDouble("average"));
			} //"num" 같은 경우 컬럼명을 스트링으로 주는 방법과(그래서 큰따옴표 씀) 컬럼번호를 숫자로 주는 방법이 있다. 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.dbClose(rs, pstmt, conn);
		}
		
		
	}
	
	public void process() {
		while(true) {
			System.out.println("1.학생성적 추가\n2.학생정보 삭제\n3.학생성적 수정\n4.학생전체출력\n9.종료");
			
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
				System.out.println("**성적관리 프로그램을 종료합니다.**");
				System.exit(0);
			}
		}
	}
		
	public static void main(String[] args) {
		
	Ex03SungjukDB ex = new Ex03SungjukDB();
	ex.process();	
		
		
	}
}
