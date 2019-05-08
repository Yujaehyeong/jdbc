package hr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
	
	public List<EmployeeVo> getList(String keyword) {
		List<EmployeeVo> result = new ArrayList<EmployeeVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC Driver(MariaDB) 로딩

			Class.forName("org.mariadb.jdbc.Driver"); // DB와의 연결을 제공하는 Driver
			
			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.1.250:3307/employees";
			conn = DriverManager.getConnection(url, "hr", "hr");
			System.out.println("연결성공");

			// SQL 준비
			String sql = 
					"select emp_no, birth_date, first_name ,last_name, gender , hire_date" + 
					" from employees" + 
					" where  first_name like ?"+ 
					" or last_name like ?";
			
			pstmt =  conn.prepareStatement(sql);
				
			// 4. 바인딩  - Mybatis도 내부적으로 PreparedStatement 사용함
			// Statement는  모든 쿼리문을 완성시켜서 보내고 PreparedStatement는  '?'를 사용후 바인딩하여 변수에 값을 저장한다.
			// sql 문에 '?'에 들어갈 문자열을 인덱스 순서대로 매핑시킴
			pstmt.setString(1, "%"+ keyword+ "%");
			pstmt.setString(2, "%"+ keyword+ "%");
			
			
			// 5. 쿼리실행
			rs = pstmt.executeQuery();
			while (rs.next()) {
				long no = rs.getLong(1);
				String birthDate = rs.getString(2);
				String firstName = rs.getString(3);
				String lastName = rs.getString(4);
				String gender = rs.getString(5);
				String hireDate = rs.getString(6);
				
				EmployeeVo employeeVo = new EmployeeVo();
				employeeVo.setNo(no);
				employeeVo.setBirthDate(birthDate);
				employeeVo.setFirstName(firstName);
				employeeVo.setLastName(lastName);
				employeeVo.setGender(gender);
				employeeVo.setHireDate(hireDate);
				result.add(employeeVo);
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
				if(pstmt!=null) {
					pstmt.close();
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
	
}
