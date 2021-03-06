package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionTest {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC Driver(MariaDB) 로딩

			Class.forName("org.mariadb.jdbc.Driver"); // DB와의 연결을 제공하는 Driver
			
			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.1.250:3307/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			System.out.println("연결성공");

			// 3. statement 객체 생성
			stmt = conn.createStatement();

			// 4. SQL문 실행
			String sql = "select no, name from department";
			rs = stmt.executeQuery(sql);

			// 5. 결과 가져오기
			while (rs.next()) {

				long no = rs.getLong(1);
				String name = rs.getString(2);
				System.out.println(no + ":" + name);

			}

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(stmt!=null) {
					stmt.close();
				}
				
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
