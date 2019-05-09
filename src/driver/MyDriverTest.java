package driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDriverTest {

	public static void main(String[] args) {
		Connection conn = null;

		try {
			// 1. JDBC Driver(MariaDB) 로딩
			//자바 가상머신이 동작을 시작하고 코드가 실행 되기 전까지는 어떤 JDBC드라이버가 사용이 될지 알지 못함. 때문에 JDBC드라이버를 동적으로 로딩할 필요가 있음 
			Class.forName("driver.MyDriver"); // DB와의 연결을 제공하는 Driver
			
			// 2. 연결하기
			String url = "jdbc:mydb://192.168.1.20:3307/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			System.out.println("연결성공: "+ conn);

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
