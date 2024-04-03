package komalthorat.todoapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//import com.mysql.cj.protocol.Resultset;
//import com.mysql.cj.xdevapi.Result;

import komalthorat.todoapp.model.Todo;
import komalthorat.todoapp.utils.JDBCUtils;

public class TodoDaoImpl implements TodoDao {
	
	private static final String INSERT_TODODS_SQL = "INSERT INTO TODOS (title,username,description,target_date,is_done)VALUES (?,?,?,?,?)";
	
	private static final String SELECT_TODO_BY_ID = "select id,title,username,description,target_date,is_done from TODOS where id =?";
    private static final String SELECT_ALL_TODOS = "select * from TODOS";
    private static final String DELETE_TODO_BY_ID = "delete from TODOS where id = ?";
    private static final String UPDATE_TODO = "update TODOS set title = ?, username= ?, description =?, target_date =?, is_done = ? where id = ?";

   public TodoDaoImpl() {
	   
   }

	@Override
	public void insertTodo(Todo todo) throws SQLException {
		System.out.println(INSERT_TODODS_SQL);
		
		try(Connection con = JDBCUtils.getDBConnection();
			PreparedStatement ps = con.prepareStatement(INSERT_TODODS_SQL)) 
		{
			ps.setString(1, todo.getTitle());
			ps.setString(2, todo.getUsername());
			ps.setString(3, todo.getDescription());
			ps.setDate(4, JDBCUtils.getSQLDate(todo.getTargetDate()));
			ps.setBoolean(5, todo.getStatus());
			System.out.println(ps);
			ps.executeUpdate();
		} catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
	}

	@Override
	public Todo selectTodo(long todoId) {
		Todo todo = null;
		
		try(Connection con = JDBCUtils.getDBConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_TODO_BY_ID);)
		{
			ps.setLong(1, todoId);
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String username = rs.getString("username");
				String description = rs.getString("description");
				LocalDate targetDate = rs.getDate("target_date").toLocalDate();
				boolean isDone = rs.getBoolean("is_done");
				todo = new Todo(id,title,username,description,targetDate,isDone);
			}
		}catch (SQLException e) {
			JDBCUtils.printSQLException(e);
		}
		return todo;
	}

	@Override
	public List<Todo> selectAllTodos() {
		List<Todo> todos = new ArrayList<>();
		
		try(Connection con = JDBCUtils.getDBConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_ALL_TODOS);)
		{
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String username = rs.getString("username");
				String description = rs.getString("description");
				LocalDate targetDate = rs.getDate("target_date").toLocalDate();
				boolean isDone = rs.getBoolean("is_done");
				todos.add(new Todo(id,title,username,description,targetDate,isDone));
			}
		}catch(SQLException e) {
			JDBCUtils.printSQLException(e);
		}
		return todos;
	}

	@Override
	public boolean deleteTodo(int id) throws SQLException {
		boolean rowDeleted;
		
		try(Connection con = JDBCUtils.getDBConnection();PreparedStatement ps = con.prepareStatement(DELETE_TODO_BY_ID);)
		{
			ps.setInt(1, id);
			rowDeleted = ps.executeUpdate()>0;
		}
		return rowDeleted;
	}

	@Override
	public boolean updateTodo(Todo todo) throws SQLException {
		boolean rowUpdated;
		
		try(Connection con = JDBCUtils.getDBConnection();PreparedStatement ps = con.prepareStatement(UPDATE_TODO);)
		{
			ps.setString(1, todo.getTitle());
			ps.setString(2, todo.getUsername());
			ps.setString(3, todo.getDescription());
			ps.setDate(4, JDBCUtils.getSQLDate(todo.getTargetDate()));
			ps.setBoolean(5, todo.getStatus());
			ps.setLong(6, todo.getId());
			rowUpdated=ps.executeUpdate()>0;
		}
		return rowUpdated;
	}

}
