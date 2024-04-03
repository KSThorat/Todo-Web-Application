package komalthorat.todoapp.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class JDBCUtils {
//	
//	private static String jdbcURL = "jdbc:mysql://localhost:3306/TodoWebApplication";
//	private static String jdbcUsername = "Komal@3921";
//	private static String jdbcPassword = "root";
	
	public static Connection getDBConnection()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
//			System.out.println("Driver Loaded");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TodoWebApplication","root","Komal@3921");
		}catch(Exception e) {
			System.out.println(e);
		}
		return con;
	}




//	public static Connection getConnection() {
//		
//		Connection con = null;
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			System.out.println("Driver Loaded");
//			con = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}catch(ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		return con;
//	}
	
	public static void printSQLException(SQLException ex) {
		
		for(Throwable e :ex) {
			
			if(e instanceof SQLException) {
				
				e.printStackTrace(System.err);
				System.err.println("SQLState: "+((SQLException)e).getSQLState());
				System.err.println("Error COde : "+((SQLException)e).getErrorCode());
				System.err.println("MEssage: "+e.getMessage());
				Throwable t = ex.getCause();
				
				while(t != null) {
					System.out.println("Cause: "+t);
					t = t.getCause();
				}
			}
		}
	}
	
	public static Date getSQLDate(LocalDate date) {
		return java.sql.Date.valueOf(date);	
	}
	
	public static LocalDate getUtilDate(Date sqlDate) {
		return sqlDate.toLocalDate();
	}
}
