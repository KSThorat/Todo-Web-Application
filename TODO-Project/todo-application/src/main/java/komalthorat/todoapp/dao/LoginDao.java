package komalthorat.todoapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import komalthorat.todoapp.model.Login;
import komalthorat.todoapp.utils.JDBCUtils;

public class LoginDao {
	
	public boolean validate(Login login) throws ClassNotFoundException{
		
		boolean status = false;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		try(Connection con = JDBCUtils.getDBConnection();
				PreparedStatement ps = con.prepareStatement("select * from users where username = ? and password = ?")){
			
				ps.setString(1, login.getUsername());
				ps.setString(2, login.getPassword());
				
				System.out.println(ps);
				ResultSet rs = ps.executeQuery();
				
				status = rs.next();
		}catch(SQLException e) {
			JDBCUtils.printSQLException(e);
		}
		return status;
	}
}
