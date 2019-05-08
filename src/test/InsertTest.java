package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertTest {

	public static boolean insert(String name) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean result = true;
		try {
			// 1. JDBC Driver(MariaDB) 로딩

			Class.forName("org.mariadb.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.1.250:3307/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			System.out.println("연결성공");

			// 3. statement 객체 생성
			stmt = conn.createStatement();

			// 4. SQL문 실행 - 완결된 쿼리를 만들어서 JDBC 한테 넘겨준다.
						// mariaDB는 데이트 타입을 기본적으로 포맷하는 형식이있다.
			String sql = "insert into department values(null,'" + name + "')";
			int count = stmt.executeUpdate(sql);
			
			// 5. 결과 가져오기
			result = count == 1;
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}

				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		boolean result = insert("디자인팀");
		
		if(result) {
			System.out.println("성공");
		}
	}

}
