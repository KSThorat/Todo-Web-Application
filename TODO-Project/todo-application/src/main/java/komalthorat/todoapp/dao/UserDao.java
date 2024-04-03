package komalthorat.todoapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import komalthorat.todoapp.model.User;
import komalthorat.todoapp.utils.JDBCUtils;

public class UserDao {
	
	
	public int registerEmployee(User employee) throws ClassNotFoundException {
		
		String INSERT_USERS_SQL = "insert into users(first_name,last_name,username,password) values(?,?,?,?)";
		
		int result = 0;
		
		try(Connection connection = JDBCUtils.getDBConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_USERS_SQL)){
			
			
			ps.setString(1, employee.getFirstName());
			ps.setString(2, employee.getLastName());
			ps.setString(3, employee.getUsername());
			ps.setString(4, employee.getPassword());
			
			System.out.println(ps);
			result = ps.executeUpdate();
		}catch(SQLException e) {
			JDBCUtils.printSQLException(e);
		}
		return result;
	}
}
